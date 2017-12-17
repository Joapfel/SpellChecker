package com.example.SpellCheckerNew.shared;

import java.io.Serializable;

public class Word implements Serializable{

	private String content = "";
	private int contentBeginPosition; 
	private int contentEndPosition;

	public Word(){
		
	}
	
	public Word(String content) {
		super();
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getContentBeginPosition() {
		return contentBeginPosition;
	}

	public void setContentBeginPosition(int contentBeginPosition) {
		this.contentBeginPosition = contentBeginPosition;
	}

	public int getContentEndPosition() {
		return contentEndPosition;
	}

	public void setContentEndPosition(int contentEndPosition) {
		this.contentEndPosition = contentEndPosition;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + contentBeginPosition;
		result = prime * result + contentEndPosition;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Word other = (Word) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (contentBeginPosition != other.contentBeginPosition)
			return false;
		if (contentEndPosition != other.contentEndPosition)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Word [content=" + content + "]";
	}
}
