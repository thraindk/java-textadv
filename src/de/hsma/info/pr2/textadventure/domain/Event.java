package de.hsma.info.pr2.textadventure.domain;

public abstract class Event { // Generischer Ereignistyp

	private String keyword;
	private String descr;

	public Event(String keyword, String descr) {
		this.keyword = keyword;
		this.descr = descr;
	}

	public String getKeyword() {
		return keyword;
	}

	public String getDesc() {
		return descr;
	}

}
