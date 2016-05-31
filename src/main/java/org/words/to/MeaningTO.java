package org.words.to;
/**
 * @COPYRIGHT (C) 2016 Schenker AG
 * <p>
 * All rights reserved
 */

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Meaning TO class
 *
 * @author Eric Wang
 **/
public class MeaningTO extends AbstractTO {
    private String id;
    private int version;
    private String txt;
    private Set<SentenceTO> sentences = new HashSet<>();

    public MeaningTO() {
    }

    public MeaningTO(String txt) {
        this.txt = txt;
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

    public void addSentenceTO(SentenceTO sentenceTO){
        sentences.add(sentenceTO);
        sentenceTO.setMeaning(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof MeaningTO))
            return false;
        MeaningTO meaning = (MeaningTO) o;
        return Objects.equals(getTxt(), meaning.getTxt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTxt());
    }
}

