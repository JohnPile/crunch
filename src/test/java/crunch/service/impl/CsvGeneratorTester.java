package crunch.service.impl;

import crunch.model.Article;
import crunch.model.Company;
import crunch.model.CompanyArticle;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class CsvGeneratorTester {

    private static final String CHARSET="UTF-8";
    private static final String SEP=System.getProperty("line.separator");

    @Test
    public void testCsvNormal() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        Set<CompanyArticle> companyArticles=new HashSet<CompanyArticle>();
        Company company=new Company("example company", "example.com");
        URL url=new URL("http://example.com");
        Article article=new Article("title", url);
        companyArticles.add(new CompanyArticle(company, article));
        CsvGenerator.toCsv(companyArticles, ps);
        assertEquals("\"example company\",\"example.com\",\"title\",\"http://example.com\"" + SEP, baos.toString(CHARSET));
    }

    @Test
    public void testCsvEmpty() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        Set<CompanyArticle> companyArticles=new HashSet<CompanyArticle>();
        CsvGenerator.toCsv(companyArticles, ps);
        assertEquals("", baos.toString(CHARSET));
    }

}
