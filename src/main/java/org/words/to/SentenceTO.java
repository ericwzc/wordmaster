package org.words.to;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SentenceTO extends AbstractTO implements Cloneable{
	private String id;
    private int version;
    private String english;
    private String chinese;
    private WordTO word;
    private TaskTO task;


	public SentenceTO() {
		// this form used by Hibernate
	}

	public SentenceTO(String english, String chinese) {
		// for application use, to create new events
        this.chinese = chinese;
        this.english = english;
	}

	public String getId() {
		return id;
	}

	private void setId(String id) {
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

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public WordTO getWord() {
        return word;
    }

    public void setWord(WordTO word) {
        this.word = word;
    }

    public TaskTO getTask() {
        return task;
    }

    public void setTask(TaskTO task) {
        this.task = task;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
        }
        return null;
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
