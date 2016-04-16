package org.words.hbm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sentence {
	private String id;
    private int version;
    private String english;
    private String chinese;
    private Word word;
    private Task task;


	public Sentence() {
		// this form used by Hibernate
	}

	public Sentence(String english, String chinese) {
		// for application use, to create new events
        this.chinese = chinese;
        this.english = english;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void addToTask(Task task){
        task.addSentence(this);
        this.task = task;
    }

    @Override
    public String toString() {
        return "Sentence(" + english + ":" + chinese + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sentence sentence = (Sentence) o;

        if (english != null ? !english.equals(sentence.english) : sentence.english != null) return false;
        return !(chinese != null ? !chinese.equals(sentence.chinese) : sentence.chinese != null);

    }

    @Override
    public int hashCode() {
        int result = english != null ? english.hashCode() : 0;
        result = 31 * result + (chinese != null ? chinese.hashCode() : 0);
        return result;
    }
}
