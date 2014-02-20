package crunch.model;


public class Interpretation {
    private final String name;
    private final int confidence;

    public Interpretation(String name, int confidence) {
        this.name = name;
        this.confidence = confidence;
    }

    public String getName() {
        return name;
    }

    public int getConfidence() {
        return confidence;
    }
}
