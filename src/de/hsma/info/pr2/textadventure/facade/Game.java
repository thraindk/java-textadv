package de.hsma.info.pr2.textadventure.facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hsma.info.pr2.textadventure.domain.Item;
import de.hsma.info.pr2.textadventure.domain.Player;
import de.hsma.info.pr2.textadventure.domain.Stage;
import de.hsma.info.pr2.textadventure.infrastructure.GameSaver;
import de.hsma.info.pr2.textadventure.infrastructure.PlayerNotFoundException;
import de.hsma.info.pr2.textadventure.infrastructure.ScriptLoader;

// KOORDINATION STAGES

public class Game {

	// Felder

	private String storyName;
	private String storyAutor;
	private String storyVersion;

	private int currentStagePos = 0; // Welches Kapitel/Szene. Default erstes Level
	private int oldStagePos = currentStagePos; // swap

	private HashMap<Integer, Stage> stages = new HashMap<>(); // Speichert die verschiedenen Stages
	private Player player;


	// Konstruktor

	public Game(ScriptLoader file) { // Konstruktor
		stages = file.getStages();
		storyName = file.getName();
		storyAutor = file.getAuthor();
		storyVersion = file.getVersion();

		player = new Player("Player", 0, 0);

	}

	// Methoden

	public long getPlayerPlayTime() {
		return player.getPlayTime();
	}

	public boolean loadGame(String name, String storyName) { // ersetzte neuen Spieler durch geladenen

		try {
			Player player = GameSaver.loadGame(name, storyName);

			this.player = player;
			this.currentStagePos = this.player.getCurrenStagePos();
			return true;

		} catch (PlayerNotFoundException e) {
			return false;

		}
	}

	public boolean saveGame() {

		if(GameSaver.saveGame(player, storyName))
			return true;

		return false;
	}

	public int getCurrentStage() { // Gibt aktuelle Stage zurück	
		return currentStagePos;
	}

	public String getStageScene() { // Gibt aktuelle Szene zurück	
		return stages.get(currentStagePos).getText();
	}

	public String getStoryName() {
		return storyName;
	}

	public String getStoryAutor() {
		return storyAutor;
	}

	public String getStoryVersion() {
		return storyVersion;
	}

	public String processAction(String input) { 

		Stage currentStage = stages.get(currentStagePos); // hole aktuelle Szene

		if (currentStage.isAction(input)) { // Ist Eingabe gültige Aktion innerhalb Szene?

			String response = "";		

			// hier kommt das Inventar ins Spiel

			// Action benötigt Item und Spieler hat es

			if (currentStage.actionExpectsItemFirst(input) && player.hasItem(currentStage.getRequiredItem(input))) {

				player.removeItem(currentStage.getRequiredItem(input)); // Entfernte benutztes Item aus Inventar
				response = currentStage.getActionResponse(input); // Hole Aktionsantwort
				oldStagePos = currentStagePos; // swap letzte Stage
				currentStagePos = currentStage.getActionNextStage(input);
				player.setCurrenStagePos(currentStagePos);

			}else if (currentStage.actionExpectsItemFirst(input)) { // Action benötigt Item, Spieler hat es nicht
				response = currentStage.getActionMissing(input);

			}else {

				response = currentStage.getActionResponse(input); // Action ohne Item

				// Entsteht dabei ein neues Item?

				if (!currentStage.getActionNewItem(input).equals("0")) // Wenn newItem in Action nicht null ist

					// Falls Spieler das Item nicht schon hat
					if (!player.hasItem(currentStage.getActionNewItem(input)))
						player.putItem(currentStage.getActionNewItem(input)); // ins inventar hinzfügen


				oldStagePos = currentStagePos; // swap
				currentStagePos = currentStage.getActionNextStage(input);	
			}

			return response;

		}else{ // Eingabe ist KEINE Aktion
			return genericResponse();
		}
	}

	private String genericResponse() { // Bei unbekannter Aktion	
		String [] response = {"Hmm...", "Damit kann ich nichts anfangen.", "Das ist uninteressant.", "Hier gibt es nichts zu sehen."};
		int i = (int) (Math.random()*4);
		return response[i];
	}

	public boolean changeOfStage() { // Für Entscheidung, text auszugeben
		if (currentStagePos != oldStagePos) {
			oldStagePos = currentStagePos; // Speichern
			return true;
		}
		return false;
	}

	public boolean hasEnded() { // Spiel zuende = letzte Stage
		if ((currentStagePos+1) == stages.size()) { // Stage 0-x
			return true;
		}
		return false;
	}

	public ArrayList<String> getPlayerInventory() {
		return player.getInventory();
	}

	public boolean playerHasItem(String input) {

		if (player.hasItem(input))
			return true;

		return false;
	}

	public String getItemDescr(String item) {
		return stages.get(currentStagePos).getItemDescr(item);
	}

	public void setPlayerName(String name) {
		player.setName(name);

	}

	public String getPlayerName() {
		return player.getName();
	}

	public void setPlayerPlayTime(long spielZeit) {
		this.player.setPlayTime(spielZeit);

	}
}
