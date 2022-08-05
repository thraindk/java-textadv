package de.hsma.info.pr2.textadventure.domain;

import java.util.HashMap;

import de.hsma.info.pr2.textadventure.infrastructure.ScriptLoader;

// Jede Szene/Kapitel besteht aus Stage und Events

public class Stage {

	private int stageNumber;
	private String text;
	private HashMap<String, Action> actions = new HashMap<>();
	private HashMap<String, Item> items = new HashMap<>(); // TODO static machen?

	// Konstruktor

	private Stage(StageBuilder NewStage) {	// Übertrage Attribute aus Builder auf Stage (Builder konstruiert damit Stage)	
		text = NewStage.text;
		actions = NewStage.actions;
		items = NewStage.items;
		stageNumber = NewStage.stageNumber;
	}

	// Stage Builder-Sub-Klasse für ScriptLoader

	public static class StageBuilder {

		// Felder
		private int stageNumber;
		private String text; // Geschichte
		private HashMap<String, Action> actions = new HashMap<>(); // Mögliche Interaktionen
		private HashMap<String, Item> items = new HashMap<>(); // Verfügbare Items

		public StageBuilder() {

		}

		public StageBuilder text(String text) {
			this.text = text;
			return this; // Jeder Builder gibt sich jeweils selbst zurück (statt void)
		}

		public StageBuilder actions(HashMap<String, Action> actions) {
			this.actions = actions;
			return this;
		}

		public StageBuilder items(HashMap<String, Item> items) {
			this.items = items;
			return this;
		}

		public StageBuilder stageNumber(int n) {
			this.stageNumber = n;
			return this;
		}

		public Stage build() {
			return new Stage(this); // liefert eine Referenz auf den Speicherbereich, dem Objekt gespeichert ist
		}


	}

	// Ende Builder-Sub-Klasse

	// Methoden für game

	public String getText() {
		return text;
	}

	public boolean isAction(String s) { // Ist gültiges Aktionswort?

		if (actions.containsKey(s)) {
			return true;
		}

		return false;

	}

	public String getActionResponse(String s) {
		return actions.get(s).getDesc();
	}


	public String getActionNewItem(String s) {
		return actions.get(s).getNewItem();
	}



	public int getActionNextStage(String s) { // Hole aus aktueller Action goto Nr zu nächster Stage
		return actions.get(s).getGotoStage();
	}

	public boolean actionExpectsItemFirst(String input) {
		if (actions.get(input).actionRequiresItem()) {
			return true;
		} else return false;
	}

	public String getActionMissing(String input) {
		return actions.get(input).getItemMissing();
	}

	public String getRequiredItem(String s) {
		return actions.get(s).requiredItem();
	}

	public String getItemDescr(String item) {
		return items.get(item).getDesc();
	}



}
