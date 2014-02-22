package crunch.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CompanyArticleTester {

    public void testConstructorGoodData() {
        Company company = new Company("Example Corp", "example.com");
        Article article = new Article("random title", null);
        CompanyArticle companyArticle = new CompanyArticle(company, article);
        assertEquals(company, companyArticle.getCompany());
        assertEquals(article, companyArticle.getArticle());
    }

    public void testConstructorInvalidData() {
        Company company = new Company("Company", "example.com");
        Article article = new Article("random", null);
        try {
            new CompanyArticle(company, null);
            fail("Expected NPE from missing article");
        } catch (NullPointerException ex) {
            // expected
        }
        try {
            new CompanyArticle(null, article);
            fail("Expected NPE from missing company");
        } catch (NullPointerException ex) {
            // expected
        }
    }
}
