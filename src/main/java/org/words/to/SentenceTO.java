package org.words.to;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SentenceTO extends AbstractTO {
	private String id;
    private int version;
    private String english;
    private String chinese;
    private WordTO word;


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
        String oldVal = this.english;
        this.english = english;
        changeSupport.firePropertyChange("english", oldVal, english);
    }

    public void setChinese(String chinese) {
        String oldVal = this.chinese;
        this.chinese = chinese;
        changeSupport.firePropertyChange("chinese", oldVal, chinese);
    }

    public WordTO getWord() {
        return word;
    }

    public void setWord(WordTO word) {
        WordTO old = new WordTO();
        this.word = word;
        changeSupport.firePropertyChange("word", old, word);
    }

    @Override
    public String toString() {
        return "SentenceTO(" + english + ":" + chinese + ")";
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
