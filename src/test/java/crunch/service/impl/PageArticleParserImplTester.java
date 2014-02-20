package crunch.service.impl;

import crunch.model.Company;
import crunch.model.CompanyArticle;
import crunch.model.IgnoredWords;
import crunch.module.IgnoredWordsProvider;
import crunch.service.KnownCompanies;
import crunch.service.PageArticleParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PageArticleParserImplTester {

    @Test
    public void testSample1Parse() {
        KnownCompanies knownCompanies=new KnownCompaniesImpl();
        IgnoredWords ignoredWords=new IgnoredWordsProvider().get();
        CompanyInterpreter interpreter=new CompanyInterpreter(ignoredWords);
        Company fb=new Company("facebook", "facebook.com");
        Company aber=new Company("abercrombe", "aaa");
        knownCompanies.put(aber);
        knownCompanies.put(fb);
        knownCompanies.put(new Company("billybob", "bbb"));
        PageArticleParser pageArticleParser=new PageArticleParserImpl(knownCompanies, interpreter);
        Document doc = Jsoup.parse(SampleData.QUICK_SAMPLE);
        Set<CompanyArticle> companyArticles=pageArticleParser.parseArticles(doc);
        Map<Company,CompanyArticle> lookup=new HashMap<Company,CompanyArticle>();
        for (CompanyArticle companyArticle : companyArticles) {
            lookup.put(companyArticle.getCompany(),companyArticle);
        }
        assertTrue(lookup.containsKey(fb));
        assertFalse(lookup.containsKey(aber));
    }

    @Test
    public void testSample2Parse() {
        KnownCompanies knownCompanies=new KnownCompaniesImpl();
        IgnoredWords ignoredWords=new IgnoredWordsProvider().get();
        CompanyInterpreter interpreter=new CompanyInterpreter(ignoredWords);
        Company yikyak=new Company("yik yak", "yikyak.com");
        Company apple=new Company("apple", "apple.com");
        knownCompanies.put(apple);
        knownCompanies.put(yikyak);
        knownCompanies.put(new Company("billybob", "bbb"));
        PageArticleParser pageArticleParser=new PageArticleParserImpl(knownCompanies, interpreter);
        Document doc = Jsoup.parse(SampleData.THICK_SAMPLE);
        Set<CompanyArticle> companyArticles=pageArticleParser.parseArticles(doc);
        Map<Company,CompanyArticle> lookup=new HashMap<Company,CompanyArticle>();
        for (CompanyArticle companyArticle : companyArticles) {
            lookup.put(companyArticle.getCompany(),companyArticle);
        }
        assertTrue(lookup.containsKey(apple));
        assertTrue(lookup.containsKey(yikyak));
    }
}
