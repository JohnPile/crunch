package crunch.service;

import crunch.model.CompanyArticle;
import org.jsoup.nodes.Document;

import java.util.Set;

public interface PageScanner {

    public Set<CompanyArticle> scanArticles(String site);

    public Set<CompanyArticle> scanArticles(Document document);

}
