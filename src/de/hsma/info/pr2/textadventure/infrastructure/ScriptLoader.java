package de.hsma.info.pr2.textadventure.infrastructure;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import de.hsma.info.pr2.textadventure.domain.Action;
import de.hsma.info.pr2.textadventure.domain.Item;
import de.hsma.info.pr2.textadventure.domain.Stage;

// DTO
// Lädt Leveldaten aus Datei und baut daraus Stages für Game

public class ScriptLoader {


	// Header
	private HashMap<Integer, Stage> stages = new HashMap<>(); // Gesamtheit aller Stages zu übergeben
	private Scanner scanner;
	private int currentScannerLine;
	private String title;
	private String autor;
	private String version;
	boolean stageUnderConstruction = false; // Für Exceptionerkennung

	private int stageNumber;
	private String stageText = "";
	HashMap<String, Action> actions = new HashMap<>();	// Aktionen einer Stage werden gebündelt
	HashMap<String, Item> items = new HashMap<>(); // Items der jeweiligen Stage

	// Normaler Konstruktor

	public ScriptLoader(String path) throws FileNotFoundException {

		loadAdventureFromFile(path); // Rückggabe für Fehlerhandling? Datei nicht gefunden != Fehler beim laden

	}

	// Methoden für ScriptLoader

	private void loadAdventureFromFile(String path) throws FileNotFoundException {

		scanner = new Scanner(new File(path));

		currentScannerLine = 0;

		while (scanner.hasNext()) {

			String input = scanner.nextLine();

			if (input.length() != 0) {
				parseLine(input);
			}

			currentScannerLine++;

		}

	}

	private void parseLine(String input) {

		//action=keyword;requiredItem !=0;addsItem !=0;TextGoto;gotostage;textItemMissing
		//item=keyword,descr

		String line [] = input.split("=");

		switch (line[0]) { // Was steht vor dem = ?

		case "%": // Kommentar des Autors, gleich überspringen!
			break;

		case "title": 
			this.title=line[1];
			break;

		case "autor": 
			this.autor=line[1];
			break;

		case "version":
			this.version=line[1];
			break;

		case "stage":
			if(stageUnderConstruction == true) // letzte Stage wurde nicht abgeschlossen
				throw new BrokenAdventureFileException("unfinished Stage at line "+currentScannerLine);

			stageUnderConstruction = true;
			// Für StageBuilder
			actions = new HashMap<>(); // leeren für ggf. nächste Stage
			items = new HashMap<>();
			stageNumber = Integer.parseInt(line[1]);
			stageText = "";				
			break;

		case "text":
			try {
				stageText = line[1];
			}catch(IndexOutOfBoundsException e) {
				throw new BrokenAdventureFileException("Broken text at line "+currentScannerLine);	
			}
			break;

		case "action":
			try {
				addAction(line[1]);
			}catch(IndexOutOfBoundsException e) {
				throw new BrokenAdventureFileException("Broken action at line "+currentScannerLine);	
			}
			break;

		case "item":
			try {
				addItem(line[1]);
			}catch(IndexOutOfBoundsException e) {
				throw new BrokenAdventureFileException("Broken item at line "+currentScannerLine);
			}
			break;

		case "endstage": // Baue aktuelle Stage
			Stage.StageBuilder NewStage = new Stage.StageBuilder();
			stages.put(stageNumber, NewStage.stageNumber(stageNumber).text(stageText).actions(actions).items(items).build());
			stageUnderConstruction = false;
			break;

		default : break; // Kann nichts damit anfangen, überspringen!

		}

	}

	private void addItem(String s) { // Neues Item aus Zeile

		String field [] = s.split(";");		

		if(field.length < 2)
			throw new BrokenAdventureFileException("Corrupt Item "+s);

		items.put(field[0], new Item(field[0], field[1]));
	}

	private void addAction(String s) { // Neue Action aus Zeile
		String field [] = s.split(";");

		// Bzgl. line[]:
		//action=keyword;requiredItem !=0;addsItem !=0;TextGoto;gotostage;textItemMissing
		if(field.length < 5)
			throw new BrokenAdventureFileException("Corrupt Action "+s);

		// Unterscheidung der verschiedenen Action Konstruktoren
		if (!field [1].equals("0")) {

			// Action benötigt Item

			//new Action(String keyword, String descr, int gotoStage, String requiredItem, String itemMissing)

			actions.put(field[0], new Action(field[0], field[3], Integer.parseInt(field[4]), field[1], field[5]));		

		}else if (!field[2].equals("0")){

			// Wenn kein Item benötigt, erzeugt es ein Neues?

			// new Action(String keyword, String descr, int gotoStage, String newItem)

			actions.put(field[0], new Action(field[0], field[3], Integer.parseInt(field[4]), field[2]));

		}else {

			// Dann bleibt nur der Augenblick (Kein benötigtes Item, macht auch kein Neues)

			// new Action(String keyword, String descr, int gotoStage)

			actions.put(field[0], new Action(field[0], field[3], Integer.parseInt(field[4])));
		}



	}

	// Methoden für game

	public String getName() {
		return title;
	}

	public String getAuthor() {
		return autor;
	}

	public HashMap<Integer, Stage> getStages() { // Gibt ALLE Stages zurück
		return stages;
	}

	public String getVersion() {
		return version;
	}

}
