package org.words.hbm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sentence {
	private String id;

	private String english;
    private String chinese;

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

	private void setId(String id) {
		this.id = id;
	}

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
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
        if(!(obj instanceof Sentence))
            return false;
        Sentence s2 = (Sentence) obj;
        List<?> list = new ArrayList<>(Arrays.asList(english, chinese));
        List<?> list2 = new ArrayList<>(Arrays.asList(s2.english, s2.chinese));
        return list.equals(list2);
    }
}