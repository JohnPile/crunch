package crunch.service.impl;

import crunch.model.CompanyArticle;
import crunch.service.JSoupConnection;
import crunch.service.PageArticleParser;
import crunch.service.PageScanner;
import org.jsoup.nodes.Document;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Set;

public class PageScannerImpl implements PageScanner {

    private JSoupConnection jSoupConnection;
    private PageArticleParser pageArticleParser;

    @Inject
    public PageScannerImpl(JSoupConnection jSoupConnection, PageArticleParser pageArticleParser) {
        this.jSoupConnection = jSoupConnection;
        this.pageArticleParser = pageArticleParser;
    }

    public Set<CompanyArticle> scanArticles(String site) {
        Document doc;
        try {
            doc= jSoupConnection.newConnection(site).get();
            return scanArticles(doc);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid site");
        }
    }

    public Set<CompanyArticle> scanArticles(Document document) {
        return pageArticleParser.parseArticles(document);
    }

}
