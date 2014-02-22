package crunch.service.impl;

import crunch.model.CompanyArticle;
import crunch.service.JSoupConnection;
import crunch.service.PageArticleParser;
import crunch.service.PageScanner;
import org.jsoup.nodes.Document;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Set;

public class TechCrunchPageScannerImpl implements PageScanner {

    private final JSoupConnection jSoupConnection;
    private final PageArticleParser pageArticleParser;

    @Inject
    public TechCrunchPageScannerImpl(JSoupConnection jSoupConnection, @Named("TechCrunch") PageArticleParser pageArticleParser) {
        this.jSoupConnection = jSoupConnection;
        this.pageArticleParser = pageArticleParser;
    }

    public Set<CompanyArticle> scanArticles(String site) {
        Document doc;
        try {
            doc = jSoupConnection.newConnection(site).get();
            return scanArticles(doc);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid site");
        }
    }

    public Set<CompanyArticle> scanArticles(Document document) {
        return pageArticleParser.parseArticles(document);
    }

}
