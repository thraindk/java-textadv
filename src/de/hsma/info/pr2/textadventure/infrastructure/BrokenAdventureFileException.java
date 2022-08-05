package de.hsma.info.pr2.textadventure.infrastructure;

// Benötigt von ScriptLoader

@SuppressWarnings("serial")
public class BrokenAdventureFileException extends RuntimeException {
	
	// https://www.tutorialspoint.com/how-to-create-a-custom-unchecked-exception-in-java

	public BrokenAdventureFileException(String message) {
	      super(message);
	   }

	   public BrokenAdventureFileException(Throwable cause) {
	      super(cause);
	   }

	   public BrokenAdventureFileException(String message, Throwable throwable) {
	      super(message, throwable);
	   }
	}

