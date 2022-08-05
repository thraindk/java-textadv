package de.hsma.info.pr2.textadventure.domain;

public class Item extends Event implements Comparable<Item> {

	public Item(String keyword, String descr) {
		super(keyword, descr);

	}

	@Override
	public int compareTo(Item i) { // Identisch anhand keyword

		if(this.getKeyword().equals(i.getKeyword()))
			return 0;
		else return -1;

	}

	// kombinierbar mit anderem item ?

}
