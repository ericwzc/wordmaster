package org.words.hbm;
/**
 * @COPYRIGHT (C) 2016 Schenker AG
 * <p>
 * All rights reserved
 */

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Meaning of words, explanations
 *
 * @author Eric Wang
 **/
public class Meaning {
    private String id;
    private int version;
    private String txt;
    private Set<Sentence> sentences = new HashSet<>();

    public Meaning() {
    }

    public Meaning(String txt) {
        this.txt = txt;
    }

    public Set<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(Set<Sentence> sentences) {
        this.sentences = sentences;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public void addSentence(Sentence sentence) {
        sentence.setMeaning(this);
        sentences.add(sentence);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Meaning))
            return false;
        Meaning meaning = (Meaning) o;
        return Objects.equals(getTxt(), meaning.getTxt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTxt());
    }
}

