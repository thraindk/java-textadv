package de.hsma.info.pr2.textadventure.infrastructure;

// Benötigt von GameSaver

@SuppressWarnings("serial")
public class PlayerNotFoundException extends RuntimeException {

	// https://www.tutorialspoint.com/how-to-create-a-custom-unchecked-exception-in-java

		public PlayerNotFoundException(String message) {
		      super(message);
		   }

		   public PlayerNotFoundException(Throwable cause) {
		      super(cause);
		   }

		   public PlayerNotFoundException(String message, Throwable throwable) {
		      super(message, throwable);
		   }
		}