package de.hsma.info.pr2.textadventure.domain;

// Interaktionswörter

public class Action extends Event {

	// Felder;
	private int gotoStage;

	// Zusatzeigenschaften
	private boolean requiresItem = false;
	private String requiredItem;
	private String itemMissing;
	private String newItem; // Action erzeugt neues Item im Invetar

	// Konstruktoren

	// voller Konstruktor
	public Action(String keyword, String descr, int gotoStage, String requiredItem, String itemMissing, String newItem) {
		super(keyword, descr);
		this.gotoStage = gotoStage;
		this.requiredItem = requiredItem;
		this.itemMissing = itemMissing;
		this.newItem = newItem;

		if (requiredItem != null) {
			requiresItem = true;
		}	
	}

	// Action benötigt Item, erzeugt kein neues Item

	public Action(String keyword, String descr, int gotoStage, String requiredItem, String itemMissing) {
		this(keyword, descr, gotoStage, requiredItem, itemMissing, null);


	}

	// Action benötigt KEIN Item, aber erzeugt NEUES Item
	public Action(String keyword, String descr, int gotoStage, String newItem) {
		this(keyword, descr, gotoStage, null, null, newItem);
	}

	// Action benötigt KEIN Item, erzeugt auch nichts
	public Action(String keyword, String descr, int gotoStage) {
		this(keyword, descr, gotoStage, null, null, null);
	}

	public int getGotoStage() {
		return gotoStage;
	}

	public String getItemMissing() {
		return itemMissing;
	}

	public boolean actionRequiresItem() {
		return requiresItem;
	}

	public String getNewItem() {

		if (newItem == null) // Gibt kein neues
			return "0";

		return newItem;
	}

	public String requiredItem() {
		return requiredItem;
	}

}
