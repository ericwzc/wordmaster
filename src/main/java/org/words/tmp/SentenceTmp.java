package org.words.tmp; /**
 * @Title:Sentence.java
 * @author:lixu
 * @date:Mar 10, 2016  5:28:49 PM
 * @version:V1.0 
 */

import java.io.Serializable;

/**
 * @ClassName: SentenceTmp
 * @author: lixu
 * @date: Mar 10, 2016 5:28:49 PM
 */
/**
 * @ClassName: SentenceTmp
 * @author: lixu
 * @date: Mar 11, 2016 9:38:31 AM
 */
public class SentenceTmp implements Serializable { //TODO use sentence from hbm package when dao is ready
	private static final long serialVersionUID = 1L;
	private int id;
	private String word;
	private String original;
	private String translate;
	
	public SentenceTmp() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getOriginal() {
		return original;
	}
	public void setOriginal(String original) {
		this.original = original;
	}
	public String getTranslate() {
		return translate;
	}
	public void setTranslate(String translate) {
		this.translate = translate;
	}
	
	@Override
	public String toString() {
		return "SentenceTmp [id=" + id +"\tword=" + word + "\toriginal=" + original + "\ttranslate=" + translate + "]";
	}
	
}
