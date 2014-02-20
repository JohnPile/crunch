package crunch.service.impl;

import crunch.model.IgnoredWords;
import crunch.model.Interpretation;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class CompanyInterpreter {

    private final Map<String,Interpretation> interpretationMap;
    private final IgnoredWords ignoredWords;

    @Inject
    public CompanyInterpreter(IgnoredWords ignoredWords) {
        interpretationMap=new HashMap<String,Interpretation>();
        this.ignoredWords=ignoredWords;
    }

    public Interpretation interpretCompany(Element articleBlock) {
        interpretationMap.clear();
        evaluateCapitalizedWords(text(articleBlock.select("p.excerpt")));
        evaluateCapitalizedWords(text(articleBlock.select("h2")),3);
        return topInterpretation();
    }

    private String text(Elements elements) {
        if (elements!=null) {
            return elements.text();
        }
        return "";
    }

    private void evaluateCapitalizedWords(String text) {
        evaluateCapitalizedWords(text, 10);
    }

    private void evaluateCapitalizedWords(String text, int score) {
        String[] words=text.split("[^A-Za-z0-9]");
        for (String word : words) {
            if (word.length()>0 && Character.isUpperCase(word.charAt(0))) {
                String normalizedWord=word.toLowerCase();
                if (!ignoredWords.contains(normalizedWord)) {
                    // Words near the front of the sentence have more weight
                    addInterpretation(new Interpretation(word, Math.max(1,score--)));
                }
            }
        }
    }

    private void addInterpretation(Interpretation interpretation) {
        String lc=interpretation.getName().toLowerCase();
        if (interpretationMap.containsKey(lc)) {
            Interpretation existing=interpretationMap.get(lc);
            interpretation=new Interpretation(interpretation.getName(), interpretation.getConfidence()+existing.getConfidence());
        }
        interpretationMap.put(lc, interpretation);
    }

    private Interpretation topInterpretation() {
        int max=0;
        Interpretation topInterpretation=null;
        for (Interpretation interpretation : interpretationMap.values()) {
            if (interpretation.getConfidence()>max) {
                topInterpretation=interpretation;
                max=interpretation.getConfidence();
            }
        }
        return topInterpretation;
    }
}
