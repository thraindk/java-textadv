package de.hsma.info.pr2.textadventure.ui;

import java.util.Scanner;

import de.hsma.info.pr2.textadventure.facade.Game;
import de.hsma.info.pr2.textadventure.infrastructure.GameSaver;

// Ui für Eclipse Konsole mit Gameloop

public class TextUi {

	private Game game;	// Level-Host
	private Scanner scanner; // für Eingabe

	public TextUi(Game game) {

		this.game = game;
		scanner = new Scanner(System.in);

		System.out.println("## Konsolenbasierte Textadventure-Engine v 1.0 ##");
		System.out.printf("\nFolgendes Adventure wurde geladen: %s von %s (Version %s)\n\n", game.getStoryName(), game.getStoryAutor(), game.getStoryVersion());

	}

	public void menu() {

		String playerName = "";

		while(true) { // Namenseingabe

			System.out.print("Spielernamen eingeben: ");

			playerName = parseConsoleInput(scanner.nextLine());

			if(!playerName.equals(""))
				break;

			System.out.println("\nBitte gültige Eingabe machen!");

		}

		game.setPlayerName(playerName);

		if(GameSaver.saveExists(playerName, game.getStoryName())) {

			System.out.println("\nEs wurde ein Spielstand zu diesem Namen gefunden. Laden? (j/n)");

			String input = parseConsoleInput(scanner.nextLine());

			if (input.equalsIgnoreCase("j")) {

				if(game.loadGame(playerName, game.getStoryName())) { // Falls für aktuelle Story und Spieler vorhannden
					System.out.println("Spielstand erfolgreich geladen!");
				}else {
					System.err.println("Fehler: Spielstand nicht passend oder beschädigt.");
				}

			}
		}else {
			System.out.println("\nEin neues Abenteuer beginnt...");
		}

		System.out.println();

	}

	public void playGame() {


		System.out.println(printScene(game)); // Methode soll aktuelle Szene ausgeben

		gameLoop:
			do { // Eingabe-Schleife

				if (game.hasEnded()) { // letzte Stage (=Ende) erreicht?
					break gameLoop;
				}

				System.out.print("> ");

				String input = parseConsoleInput(scanner.nextLine());

				if (input.equalsIgnoreCase("ende")) {
					break gameLoop;
				}

				// Was soll gemacht werden

				String[] cmd = input.split(" ");

				switch (cmd[0].toLowerCase()) {

				case "inventar": 

					if ((cmd.length > 1) && game.playerHasItem(cmd[1].toLowerCase()))	{	
						System.out.println(game.getItemDescr(cmd[1].toLowerCase()));
					}else {			
						System.out.printf("\nMein Inventar: %s\n", game.getPlayerInventory().toString());
					}

					break;

				case "sichern":

					if(game.saveGame()) {
						System.out.println("Spielstand für '"+game.getPlayerName()+"' gespeichert!");
					}else {
						System.err.println("Spielstand konnte nicht gespeichert werden.");
					}
					break;

				default: System.out.println(formatForConsole(game.processAction(input.toLowerCase()))); // weiterreichen

				}

				if (game.changeOfStage()) { // Prüft ob Änderung bei Stage (=Text)
					System.out.println(printScene(game));				
				}

			}while(true);

		scanner.close();

	}

	private String printScene(Game game) {

		StringBuilder sb = new StringBuilder("\n# Kapitel: "+(game.getCurrentStage()+1)+"\n");

		sb.append(formatForConsole(game.getStageScene()));


		return sb.toString();

	}

	// Hilfsmethoden - public für JUnit

	public static String parseConsoleInput(String s) {

		String t = "";

		for (int i = 0; i < s.length(); i++) { // entferne Leerzeichen und ;= 
			if (!(s.charAt(i) == ';') && !(s.charAt(i) == ' ') && !(s.charAt(i) == '=')) {
				t += s.charAt(i);
			}
		}
		return t;
	}

	public static String formatForConsole(String s) { // Auf gewisse Länge zuschneiden, besser lesbar

		int cutLineAt = 80;

		StringBuilder sb = new StringBuilder();

		if (s.length() > cutLineAt) {

			int index= 0;

			while((index+cutLineAt) < s.length()) {

				sb.append(s.substring(index, (index+cutLineAt))+"-");
				sb.append(System.lineSeparator());
				index += (cutLineAt);
			}

			sb.append(s.substring(index));
			sb.append(System.lineSeparator());	

			return sb.toString();

		}

		return s;

	}

}
