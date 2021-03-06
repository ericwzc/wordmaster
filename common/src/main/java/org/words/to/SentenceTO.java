package org.words.to;

import java.util.*;

/**
 * Sentence TO class
 */
public class SentenceTO extends AbstractTO implements Cloneable{
	private String id;
    private int version;
    private String english;
    private String chinese;
    private WordTO word;
    private MeaningTO meaning;
    private Set<RecordTO> record = new HashSet<>();

	public SentenceTO() {
		// this form used by Hibernate
	}

    /**
     * Constructor set chinese, english text
     *
     * @param english english txt
     * @param chinese chinese txt
     */
	public SentenceTO(String english, String chinese) {
		// for application use, to create new events
        this.chinese = chinese;
        this.english = english;
	}

    public MeaningTO getMeaning() {
        return meaning;
    }

    public void setMeaning(MeaningTO meaning) {
        this.meaning = meaning;
    }

    public Set<RecordTO> getRecord() {
        return record;
    }

    @SuppressWarnings("unused")
    public void setRecord(Set<RecordTO> record) {
        this.record = record;
    }

    /**
     * Add record
     * @param recordTO record
     */
    public void addRecord(RecordTO recordTO){
        this.record.add(recordTO);
    }

    public String getId() {
		return id;
	}

	@SuppressWarnings("unused")
    private void setId(String id) {//NOSONAR
		this.id = id;
	}

    public String getEnglish() {
        return english;
    }

    public String getChinese() {
        return chinese;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    @SuppressWarnings("unused")
    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public WordTO getWord() {
        return word;
    }

    public void setWord(WordTO word) {
        this.word = word;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {//NOSONAR
        return super.clone();
    }

    @Override
    public String toString() {
        return "Sentence(" + english + ":" + chinese + ")";
    }

    @Override
    public int hashCode() {
        List<?> list = new ArrayList<>(Arrays.asList(english, chinese));
        return list.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof SentenceTO))
            return false;
        SentenceTO s2 = (SentenceTO) obj;
        List<?> list = new ArrayList<>(Arrays.asList(english, chinese));
        List<?> list2 = new ArrayList<>(Arrays.asList(s2.english, s2.chinese));
        return list.equals(list2);
    }
}
