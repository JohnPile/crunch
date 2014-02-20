package crunch.module;

import crunch.model.IgnoredWords;

import javax.inject.Provider;

public class IgnoredWordsProvider implements Provider<IgnoredWords> {

    private static IgnoredWords IGNORED_WORDS=null;

    public IgnoredWords get() {
        if (IGNORED_WORDS==null) {
            synchronized (this) {
                if (IGNORED_WORDS==null) {
                    // TODO: Populate from a more robust source
                    IGNORED_WORDS=new IgnoredWords("a", "i", "an", "in", "on", "if", "we", "but", "the", "now", "new",
                            "good", "last", "when", "most", "today", "former", "internet",
                            "canadian", "relatively");
                }
            }
        }
        return IGNORED_WORDS;
    }
}
