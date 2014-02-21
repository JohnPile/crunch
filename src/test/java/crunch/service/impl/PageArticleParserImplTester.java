package crunch.service.impl;

import crunch.model.CompanyArticle;
import crunch.model.IgnoredWords;
import crunch.module.IgnoredWordsProvider;
import crunch.service.DocInterpreter;
import crunch.service.PageArticleParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PageArticleParserImplTester {

    @Test
    public void testSample1Parse() {
        IgnoredWords ignoredWords=new IgnoredWordsProvider().get();
        DocInterpreter docInterpreter=new TechCrunchDocInterpreterImpl(ignoredWords);
        PageArticleParser pageArticleParser=new TechCrunchPageArticleParserImpl(docInterpreter);
        Document doc = Jsoup.parse(SampleData.QUICK_SAMPLE);
        Set<CompanyArticle> companyArticles=pageArticleParser.parseArticles(doc);
        Set<String> companyNames=new HashSet<String>();
        for (CompanyArticle ca : companyArticles) {
            companyNames.add(ca.getCompany().getName());
        }
        assertTrue(companyNames.contains("Facebook"));
        assertFalse(companyNames.contains("Abercrombe"));
    }

    @Test
    public void testSample2Parse() {
        IgnoredWords ignoredWords=new IgnoredWordsProvider().get();
        DocInterpreter interpreter=new TechCrunchDocInterpreterImpl(ignoredWords);
        PageArticleParser pageArticleParser=new TechCrunchPageArticleParserImpl(interpreter);
        Document doc = Jsoup.parse(SampleData.THICK_SAMPLE);
        Set<CompanyArticle> companyArticles=pageArticleParser.parseArticles(doc);
        Set<String> companyNames=new HashSet<String>();
        for (CompanyArticle ca : companyArticles) {
            companyNames.add(ca.getCompany().getName());
        }
        assertTrue(companyNames.contains("Apple"));
        // assertTrue(companyNames.contains("Yik Yak")); Edge Case Needing to be Addressed
        // Possible approach.  Keep hash of known multi-word names.  Give scoring preference.
    }
}
