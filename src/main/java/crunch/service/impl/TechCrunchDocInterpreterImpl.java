package crunch.service.impl;

import crunch.model.IgnoredWords;
import crunch.model.Interpretation;
import crunch.model.InterpretationMap;
import crunch.service.DocInterpreter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class TechCrunchDocInterpreterImpl implements DocInterpreter {

    private final IgnoredWords ignoredWords;

    @Inject
    public TechCrunchDocInterpreterImpl(IgnoredWords ignoredWords) {
        this.ignoredWords=ignoredWords;
    }

    private String text(Elements elements) {
        if (elements!=null) {
            return elements.text();
        }
        return "";
    }

    private void evaluateCapitalizedWords(InterpretationMap interpretationMap, String text) {
        evaluateCapitalizedWords(interpretationMap, text, 1000);
    }

    private void evaluateCapitalizedWords(InterpretationMap interpretationMap, String text, int score) {
        String[] words=text.split("[^A-Za-z0-9]");
        for (String word : words) {
            if (word.length()>0 && Character.isUpperCase(word.charAt(0))) {
                String normalizedWord=word.toLowerCase();
                if (!ignoredWords.contains(normalizedWord)) {
                    // Words near the front of the sentence have more weight
                    interpretationMap.addInterpretation(new Interpretation(word, Math.max(1, score)));
                    score=score*6/10;
                }
            }
        }
    }


    @Override
    public Interpretation interpretArticleTitle(Element articleBlock, Document document) {
        String articleTitle = articleBlock.attr("data-shareTitle");
        return new Interpretation(articleTitle, 100);
    }

    @Override
    public Interpretation interpretArticleUrl(Element articleBlock, Document document) {
        String articleRef = articleBlock.attr("data-permalink");
        return new Interpretation(articleRef, 100);
    }

    @Override
    public Interpretation interpretCompanyName(Element articleBlock, Document document) {
        InterpretationMap companyNameInterpretationMap=new InterpretationMap();
        evaluateCapitalizedWords(companyNameInterpretationMap, text(articleBlock.select("p.excerpt")));
        evaluateCapitalizedWords(companyNameInterpretationMap, text(articleBlock.select("h2")));
        return companyNameInterpretationMap.topInterpretation();
    }

    @Override
    public Interpretation interpretCompanyWebsite(Element articleBlock, Document document) {
        return new Interpretation(null,0);
    }
}
