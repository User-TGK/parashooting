package nl.han.ica.Parashooting;

/**
 *
 * @author Ties Klappe
 * @author Wiek van den Elzen
 * 
 * Deze klasse die bijhoudt of een toets is ingedrukt
 */

public class PressedKey {

	/** De ingedrukte toets. */
	private char theKeyPressed;
	
	/** Kijkt of de toets al ingedrukt is. */
	private boolean isPressed = false;

	/**
	 * Maakt een nieuwe toets aan.
	 *
	 * @param theKeyPressed de toets die moet worden toegevoegd.
	 */
	public PressedKey(char theKeyPressed) {
		this.theKeyPressed = theKeyPressed;

	}

	/**
	 * Haalt op of de toets al is ingedrukt (true or false).
	 *
	 * @return isPressed (of de toets al ingedrukt is true or false).
	 */
	public boolean getIsPressed() {
		return isPressed;
	}

	/**
	 * Zet de variable isPressed op de meegegeven parameter (true or false).
	 *
	 * @param isPressed of hij al is ingedrukt true or false.
	 */
	public void setIsPressed(boolean isPressed) {
		this.isPressed = isPressed;
	}

	/**
	 * Haalt de waarde op van de toets die is ingedrukt (true or false).
	 *
	 * @return de ingedrukte toets.
	 */
	public char getTheKeyPressed() {
		return theKeyPressed;
	}

}
