/**
 * Copyright © DB Schenker. All rights reserved.
 *
 * @Title:Sentence.java
 * @Project:training2
 * @Package:com.schenker.training2.pojo
 * @author:lixu
 * @date:Mar 10, 2016  5:28:49 PM
 * @version:V1.0 
 */
package com.schenker.training2.pojo;

import java.io.Serializable;

/**
 * @ClassName: Sentence
 * @author: lixu
 * @date: Mar 10, 2016 5:28:49 PM
 */
/**
 * @ClassName: Sentence
 * @author: lixu
 * @date: Mar 11, 2016 9:38:31 AM
 */
public class Sentence implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String word;
	private String original;
	private String translate;
	
	public Sentence() {}

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
		return "Sentence [id=" + id +"\tword=" + word + "\toriginal=" + original + "\ttranslate=" + translate + "]";
	}
	
}
