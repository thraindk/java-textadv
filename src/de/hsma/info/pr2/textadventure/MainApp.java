package de.hsma.info.pr2.textadventure;
import java.io.FileNotFoundException;

import de.hsma.info.pr2.textadventure.facade.Game;
import de.hsma.info.pr2.textadventure.infrastructure.ScriptLoader;
import de.hsma.info.pr2.textadventure.ui.Gui;
import de.hsma.info.pr2.textadventure.ui.TextUi;

/*
 written by Cedric Mingham @thraindk
 
 letzte Änderung 05.07.2021

 Kurzanleitung Textadventure: Mit passenden Stichworten durch die Geschichte laufen.

 */


public class MainApp {

	public static void main(String[] args) {

		try {

			ScriptLoader file = new ScriptLoader("adventure/SchlossSchauerstein.af");

			Game game = new Game(file);

			TextUi ui = new TextUi(game);

			ui.menu(); // Spielerverwaltung

			//ui.playGame(); // Gameloop

			Gui gui = new Gui(game);

		}catch (FileNotFoundException e) {

			System.out.println("Programm konnte Adventure-Datei nicht laden und wird beendet.");

		}


	}

}
