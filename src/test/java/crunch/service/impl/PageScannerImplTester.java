package crunch.service.impl;

import crunch.model.CompanyArticle;
import crunch.service.JSoupConnection;
import crunch.service.PageArticleParser;
import crunch.service.PageScanner;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PageScannerImplTester {

    private static final String FAKESITE = "fakesite.com";

    @Test
    public void testFindKnownCompany() throws Exception {
        // Prepare mocks
        JSoupConnection jSoupConnection = mock(JSoupConnection.class);
        Connection conn = mock(Connection.class);
        Document doc = mock(Document.class);
        Set<CompanyArticle> scannedArticlesFake = mock(Set.class);
        PageArticleParser pageArticleParser = mock(PageArticleParser.class);

        // Exercise pageScanner.scanArticles
        PageScanner pageScanner = new PageScannerImpl(jSoupConnection, pageArticleParser);
        when(jSoupConnection.newConnection(FAKESITE)).thenReturn(conn);
        when(conn.get()).thenReturn(doc);
        when(pageArticleParser.parseArticles(doc)).thenReturn(scannedArticlesFake);
        Set<CompanyArticle> scannedArticles = pageScanner.scanArticles(FAKESITE);
        assertEquals(scannedArticlesFake, scannedArticles);
    }
}
