package de.hsma.info.pr2.textadventure.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import de.hsma.info.pr2.textadventure.ui.TextUi;

class TextUiTest {

	@Test
	void parseConsoleInputTest() { // Teste Eingabe-Filter

		assertEquals("Stefan", TextUi.parseConsoleInput("Stefan"));
		assertEquals("Stefan", TextUi.parseConsoleInput("Stef an"));
		assertEquals("Stefan", TextUi.parseConsoleInput("Stefan "));
		assertEquals("Stefan", TextUi.parseConsoleInput(" Stefan"));
		assertEquals("Stefan", TextUi.parseConsoleInput("Stefa;n"));
		assertEquals("Stefan", TextUi.parseConsoleInput("=Stefan"));
	}

	@Test
	void formatTest() { // Teste Grenzwerte für Zeilenumbruch

		String s = "abcdefghijklmnopqrstabcdefghijklmnopqrstuabcdefghijklmnopqrstabcdefghijklmnopqrst"; // 81

		//System.out.println(s.length());

		assertEquals(2, TextUi.formatForConsole(s).lines().count()); // Anzahl der Zeilen nach format

		s = "abcdefghijklmnopqrstabcdefghijklmnopqrstabcdefghijklmnopqrstabcdefghijklmnopqrst"; // 80

		assertEquals(1, TextUi.formatForConsole(s).lines().count());

		s = "abcdefghijklmnopqrstabcdefghijklmnopqrstabcdefghijklmnopqrstabcdefghijklmnopqrst"; // 79

		assertEquals(1, TextUi.formatForConsole(s).lines().count());


	}

}
