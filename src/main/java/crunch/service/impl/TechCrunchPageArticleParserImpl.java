package crunch.service.impl;

import crunch.model.Article;
import crunch.model.Company;
import crunch.model.CompanyArticle;
import crunch.service.DocInterpreter;
import crunch.service.PageArticleParser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.inject.Inject;
import javax.inject.Named;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class TechCrunchPageArticleParserImpl implements PageArticleParser {

    private final DocInterpreter docInterpreter;

    @Inject
    public TechCrunchPageArticleParserImpl(@Named("TechCrunch") DocInterpreter docInterpreter) {
        this.docInterpreter = docInterpreter;
    }

    public Set<CompanyArticle> parseArticles(Document document) {
        Set<CompanyArticle> companyArticles = new HashSet<CompanyArticle>();
        Elements articleBlocks = document.select("li.river-block");
        for (Element articleBlock : articleBlocks) {
            String articleTitle=docInterpreter.interpretArticleTitle(articleBlock, document).getName();
            String articleRef=docInterpreter.interpretArticleUrl(articleBlock, document).getName();
            String companyName=docInterpreter.interpretCompanyName(articleBlock, document).getName();
            String companyWebsite=docInterpreter.interpretCompanyWebsite(articleBlock, document).getName();
            if (articleTitle != null && articleRef != null) {
                try {
                    URL articleUrl = new URL(articleRef);
                    Company company=new Company(companyName,companyWebsite);
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
