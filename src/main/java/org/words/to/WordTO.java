package org.words.to;
/**
 * @COPYRIGHT (C) 2016 Schenker AG
 * <p/>
 * All rights reserved
 */

import java.util.HashSet;
import java.util.Set;

/**
 * Word Transfer Object
 @author Eric Wang
 **/
public class WordTO extends AbstractTO{
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
        String old = this.name;
        this.name = name;
        changeSupport.firePropertyChange("name", old, name);
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
    public String toString() {
        return "WordTO{" +
                "name='" + name + '\'' +
                '}';
    }
}

