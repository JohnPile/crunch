package crunch.service;

import crunch.model.CompanyArticle;
import org.jsoup.nodes.Document;

import java.util.Set;

public interface PageArticleParser {

    public Set<CompanyArticle> parseArticles(Document document);

}
