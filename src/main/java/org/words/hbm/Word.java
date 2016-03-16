package org.words.hbm;
/**
 * @COPYRIGHT (C) 2016 Schenker AG
 * <p/>
 * All rights reserved
 */

import java.util.HashSet;
import java.util.Set;

/**
 * Word Entity
 @author Eric Wang
 **/
public class Word {
    private String id;
    private Set<Sentence> sentences = new HashSet<>();
    private int version;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(Set<Sentence> sentences) {
        this.sentences = sentences;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void addSentence(Sentence sentence){
        sentence.setWord(this);
        sentences.add(sentence);
    }
}

