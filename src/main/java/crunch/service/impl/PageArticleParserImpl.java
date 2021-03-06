package crunch.service.impl;

import crunch.model.Article;
import crunch.model.Company;
import crunch.model.CompanyArticle;
import crunch.service.KnownCompanies;
import crunch.service.PageArticleParser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.inject.Inject;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class PageArticleParserImpl implements PageArticleParser {

    private KnownCompanies knownCompanies;

    @Inject
    public PageArticleParserImpl(KnownCompanies knownCompanies) {
        this.knownCompanies = knownCompanies;
    }

    public Set<CompanyArticle> parseArticles(Document document) {
        Set<CompanyArticle> companyArticles = new HashSet<CompanyArticle>();
        Elements articleBlocks = document.select("li.river-block");
        for (Element articleBlock : articleBlocks) {
            String articleRef = articleBlock.attr("data-permalink");
            String articleTitle = articleBlock.attr("data-shareTitle");
            String content = articleBlock.select("p.excerpt").text();
            Company company;
            if (content.matches("^Company: [^:]+ Location:.*")) {
                String companyName = content.substring(9, content.indexOf(':', 9) - 9);
                // TODO: Still need to determine domain name.  Maybe from Factual?
                if (knownCompanies.includes(companyName)) {
                    company = knownCompanies.lookup(companyName);
                } else {
                    company = new Company(companyName, null);

                }
            } else {
                // TODO: Look for a cheaper way of determining company
                company = knownCompanies.defaultCompany();
                String[] words = content.split("[\\s+]");
                for (int i = 0; i < words.length; i++) {
                    // One-word names
                    if (knownCompanies.includes(words[i])) {
                        company = knownCompanies.lookup(words[i]);
                        break;
                    }
                    // Two-word names
                    if (i < words.length - 2) {
                        if (knownCompanies.includes(words[i] + " " + words[i+1])) {
                            company = knownCompanies.lookup(words[i] + " " + words[i+1]);
                            break;
                        }
                    }
                    // Three-word names
                    if (i < words.length - 3) {
                        if (knownCompanies.includes(words[i] + " " + words[i+1] + " " + words[i+2])) {
                            company = knownCompanies.lookup(words[i] + " " + words[i+1] + " " + words[i+2]);
                            break;
                        }
                    }
                    // Four-word names
                    if (i < words.length - 4) {
                        if (knownCompanies.includes(words[i] + " " + words[i+1] + " " + words[i+2] + " " + words[i+3])) {
                            company = knownCompanies.lookup(words[i] + " " + words[i+1] + " " + words[i+2] + " " + words[i+3]);
                            break;
                        }
                    }
                }
            }
            if (articleRef != null && articleTitle != null) {
                try {
                    URL articleUrl = new URL(articleRef);
                    Article article = new Article(articleTitle, articleUrl);
                    companyArticles.add(new CompanyArticle(company, article));
                } catch (MalformedURLException ex) {
                    // TODO: log, nice to monitor how often this happens
                    // ignore and keep processing
                }
            }
        }
        return companyArticles;
    }


}
