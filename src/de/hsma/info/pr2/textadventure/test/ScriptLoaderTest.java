package de.hsma.info.pr2.textadventure.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import de.hsma.info.pr2.textadventure.facade.Game;
import de.hsma.info.pr2.textadventure.infrastructure.BrokenAdventureFileException;
import de.hsma.info.pr2.textadventure.infrastructure.PlayerNotFoundException;
import de.hsma.info.pr2.textadventure.infrastructure.ScriptLoader;

class ScriptLoaderTest {

	@Test
	void testLoadAdventureFile() throws FileNotFoundException { // teste Einlesen

		ScriptLoader file = new ScriptLoader("adventure/testItems.af");

		Game game = new Game(file);

		assertEquals("Devadventure", game.getStoryName());
		assertEquals("Cedric", game.getStoryAutor());
		assertEquals("1", game.getStoryVersion());
	}

	@Test
	void testScriptLoaderException() throws FileNotFoundException { // teste custom Exception


		Exception exception = assertThrows(BrokenAdventureFileException.class, () -> {
			ScriptLoader file = new ScriptLoader("adventure/testItems2.af");		
		});


	}

}
