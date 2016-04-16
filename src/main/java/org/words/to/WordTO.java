package org.words.to;
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
public class WordTO extends AbstractTO {
    private String id;
    private Set<SentenceTO> sentences = new HashSet<>();
    private int version;
    private String name;

    public WordTO(){}

    public WordTO(String name){
        this.name = name;
    }

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

    public Set<SentenceTO> getSentences() {
        return sentences;
    }

    public void setSentences(Set<SentenceTO> sentences) {
        this.sentences = sentences;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void addSentence(SentenceTO sentence){
        sentence.setWord(this);
        sentences.add(sentence);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WordTO wordTO = (WordTO) o;

        return !(name != null ? !name.equals(wordTO.name) : wordTO.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}

