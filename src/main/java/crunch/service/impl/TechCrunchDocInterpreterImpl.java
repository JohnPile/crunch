package crunch.service.impl;

import com.factual.driver.Factual;
import com.factual.driver.Query;
import com.factual.driver.ReadResponse;
import com.factual.driver.RowQuery;
import crunch.model.IgnoredWords;
import crunch.model.Interpretation;
import crunch.model.InterpretationMap;
import crunch.service.DocInterpreter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

public class TechCrunchDocInterpreterImpl implements DocInterpreter {

    private final IgnoredWords ignoredWords;
    private final Factual factual;
    private static final String PLACES="places-us";

    @Inject
    public TechCrunchDocInterpreterImpl(IgnoredWords ignoredWords, Factual factual) {
        this.ignoredWords = ignoredWords;
        this.factual = factual;
    }

    private String text(Elements elements) {
        if (elements != null) {
            return elements.text();
        }
        return "";
    }

    private void evaluateCapitalizedWords(InterpretationMap interpretationMap, String text) {
        evaluateCapitalizedWords(interpretationMap, text, 1000);
    }

    private void evaluateCapitalizedWords(InterpretationMap interpretationMap, String text, int score) {
        String[] words = text.split("[^A-Za-z0-9]");
        for (String word : words) {
            if (word.length() > 0 && Character.isUpperCase(word.charAt(0))) {
                String normalizedWord = word.toLowerCase();
                if (!ignoredWords.contains(normalizedWord)) {
                    // Words near the front of the sentence have more weight
                    interpretationMap.addInterpretation(new Interpretation(word, Math.max(1, score)));
                    score = score * 6 / 10;
                }
            }
        }
    }


    @Override
    public Interpretation interpretArticleTitle(Element articleBlock, Document searchDocument, Document detailDocument) {
        String articleTitle = articleBlock.attr("data-shareTitle");
        if (articleTitle==null || articleTitle.length()==0) {
            articleTitle = articleBlock.select("h2 a").first().text();
        }
        return new Interpretation(articleTitle, 100);
    }

    @Override
    public Interpretation interpretArticleUrl(Element articleBlock, Document searchDocument, Document detailDocument) {
        String articleRef = articleBlock.attr("data-permalink");
        if (articleRef==null || articleRef.length()==0) {
            articleRef = articleBlock.select("h2 a").attr("href");
        }
        return new Interpretation(articleRef, 100);
    }

    @Override
    public Interpretation interpretCompanyName(Element articleBlock, Document searchDocument, Document detailDocument) {
        if (detailDocument==null) {
            // Picking out a company name from the searchDocument is difficult to the point of being ill-conceived.
            return Interpretation.noneFound();
        }
        for (Element element : detailDocument.select("div.card-acc-panel strong.key")) {
            if ("Founded".equals(element.text())) {
                String companyName=element.parent().parent().parent().parent().select("h4.card-title").first().text();
                return new Interpretation(companyName, 100);
            }
        }
        return Interpretation.noneFound();
    }

    @Override
    public Interpretation interpretCompanyWebsite(Element articleBlock, Document searchDocument, Document detailDocument) {
        if (detailDocument==null) {
            // Picking out a company name from the searchDocument is difficult to the point of being ill-conceived.
            return Interpretation.noneFound();
        }
        for (Element element : detailDocument.select("li.full strong.key")) {
            if ("Website".equals(element.text())) {
                return new Interpretation(element.nextElementSibling().select("a").attr("href"), 100);
            }
        }
        return Interpretation.noneFound();
    }

}
