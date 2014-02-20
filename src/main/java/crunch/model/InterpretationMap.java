package crunch.model;

import java.util.HashMap;
import java.util.Map;

public class InterpretationMap {

    Map<String,Interpretation> interpretationMap;

    public InterpretationMap() {
        this.interpretationMap=new HashMap<String,Interpretation>();
    }

    public void addInterpretation(Interpretation interpretation) {
        String lc=interpretation.getName().toLowerCase();
        if (interpretationMap.containsKey(lc)) {
            Interpretation existing=interpretationMap.get(lc);
            interpretation=new Interpretation(interpretation.getName(), interpretation.getConfidence()+existing.getConfidence());
        }
        interpretationMap.put(lc, interpretation);
    }

    public Interpretation topInterpretation() {
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

    public void clear() {
        interpretationMap.clear();
    }
}
