package de.hsma.info.pr2.textadventure.test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import de.hsma.info.pr2.textadventure.domain.Player;
import de.hsma.info.pr2.textadventure.infrastructure.GameSaver;
import de.hsma.info.pr2.textadventure.infrastructure.PlayerNotFoundException;

class GameSaverTest {

	@Test
	void loadGameTest() throws FileNotFoundException { // Teste Speicher- und Ladefunktion

		assertTrue(GameSaver.saveGame(new Player("test", 0, 0), "test"));

		Player p1 = GameSaver.loadGame("test", "test");

		assertEquals(0, p1.getCurrenStagePos()); // Teste ob Eigenschaften korrekt übernommen werden
		assertEquals("test", p1.getName());

		Exception exception = assertThrows(PlayerNotFoundException.class, () -> {
			GameSaver.loadGame("test", "adventure"); // Für "adventure" existiert kein Savegame
		});

		String fehler = "test";
		String exceptionNachricht = exception.getMessage();

		assertTrue(exceptionNachricht.contains(fehler));
	}

}
