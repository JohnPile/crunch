package crunch.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class IgnoredWords {
    private Set<String> ignoredWords;

    public IgnoredWords(String... words) {
        ignoredWords = new HashSet<String>();
        add(words);
    }

    public void add(String ignoredWord) {
        ignoredWords.add(ignoredWord);
    }

    public void add(String[] words) {
        Collections.addAll(ignoredWords, words);
    }

    public boolean contains(String word) {
        return ignoredWords.contains(word);
    }

}
