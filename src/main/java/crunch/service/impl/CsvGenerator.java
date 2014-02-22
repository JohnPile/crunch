package crunch.service.impl;

import crunch.model.CompanyArticle;

import java.io.PrintStream;
import java.util.Set;

public class CsvGenerator {

    public static void toCsv(Set<CompanyArticle> companyArticles, PrintStream out) {
        for (CompanyArticle companyArticle : companyArticles) {
            out.format("\"%s\",\"%s\",\"%s\",\"%s\"%n",
                    clean(companyArticle.getCompany().getName()),
                    clean(companyArticle.getCompany().getWebsite()),
                    clean(companyArticle.getArticle().getTitle()),
                    clean(companyArticle.getArticle().getUrl()));
        }
    }

    private static String clean(Object field) {
        return field.toString().replace("\"", "\\\"");
    }
}
