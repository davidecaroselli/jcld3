package it.davidecaroselli.jcld3;

public class LanguageResult implements Comparable<LanguageResult> {

    public final String language;
    public final float probability;
    public final boolean reliable;
    public final float proportion;

    public LanguageResult(String language, float probability, boolean reliable, float proportion) {
        this.language = language;
        this.probability = probability;
        this.reliable = reliable;
        this.proportion = proportion;
    }

    @Override
    public String toString() {
        return language + " (probability=" + probability +
                ", is_reliable=" + reliable +
                ", proportion=" + proportion +
                ')';
    }

    @Override
    public int compareTo(LanguageResult o) {
        return Float.compare(probability, o.probability);
    }
}
