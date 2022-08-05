package de.hsma.info.pr2.textadventure.infrastructure;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import de.hsma.info.pr2.textadventure.domain.Player;

// Speichert Spielfortschritt in Datei

public class GameSaver {

	public static boolean saveGame(Player player, String storyName) { // Speichere Player in Datei

		try {

			FileWriter fw = new FileWriter("save/"+player.getName()+".player");

			fw.write("story="+storyName);
			fw.write(System.getProperty("line.separator"));
			fw.write("stage="+player.getCurrenStagePos());
			fw.write(System.getProperty("line.separator"));
			fw.write("time="+player.getPlayTime());
			fw.close();
			// Inventar wird vorerst nicht gespeichert

		} catch (IOException e) {
			return false;
		}

		return true;
	}

	public static Player loadGame(String name, String storyName) { // erzeuge Player aus Dateiinfos und gebe diesen zurück

		try {

			Scanner sc = new Scanner(new File("save/"+name+".player"));

			String currentStagePos = "0";
			String playTime = "0";

			while(sc.hasNext()) { // Schnell Zeile für Zeile drüberlaufen

				String[] input = sc.nextLine().split("=");

				if(input.length > 1) { // Daten nicht leer

					switch(input[0]) {

					case "story":	// Nur zur Kontrolle ob richtiges Adventure
						if(!input[1].equalsIgnoreCase(storyName))
							throw new PlayerNotFoundException(name);
						break;

					case "stage":
						currentStagePos = input[1];
						break;

					case "time":
						playTime = input[1];

					}

				}else {
					throw new PlayerNotFoundException(name);
				}

			}

			sc.close();
			return new Player(name, Integer.valueOf(currentStagePos), Long.valueOf(playTime));


		} catch (FileNotFoundException e) {

			throw new PlayerNotFoundException(name);

		}

	}

	public static boolean saveExists(String name, String storyName) { // Nur erste Zeile prüfen

		try {

			Scanner sc = new Scanner(new File("save/"+name+".player"));

			String[] input = sc.nextLine().split("=");

			if(input.length > 1) { // Daten nicht leer

				switch(input[0]) {

				case "story":	// Nur zur Kontrolle ob richtiges Adventure
					if(!input[1].equalsIgnoreCase(storyName))
						return false;
					break;


				}

			}else {
				return false;
			}

			sc.close();
			return true;


		} catch (FileNotFoundException e) {

			return false;

		}
	}

}
