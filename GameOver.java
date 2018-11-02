package nl.han.ica.Parashooting;

import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;

/**
 * @author Ties Klappe
 * @author Wiek van den Elzen
 * De game-over klasse zorgt ervoor dat het spel 
 * opnieuw gestart kan worden als de speler dood is
 */

import nl.han.ica.OOPDProcessingEngineHAN.Objects.SpriteObject;

/**
 *
 * @author Ties Klappe
 * @author Wiek van den Elzen
 * 
 * De gameover klasse regelt dat er een gameover scherm spawnt als de speler dood is
 * en geeft de mogelijkheid het spel opnieuw te starten met een muisklik.
 */

public class GameOver extends SpriteObject {

	/** Referentie naar de wereld. */
	private Parashooting world;

	/**
	 * Creëerd de game-over instantie en laat het spriteobject spawnen door de tweede (super) constructor aan te roepen.
	 *
	 * @param world the world
	 */
	public GameOver(Parashooting world) {
		this(new Sprite("src/main/java/nl/han/ica/Parashooting/media/Game over!.png"));
		this.world = world;
	}

	/**
	 * Maakt een nieuw spriteobject aan
	 *
	 * @param sprite De sprite (afbeelding) die je terugziet als het spriteobject aangemaakt wordt.
	 */
	public GameOver(Sprite sprite) {
		super(sprite);
	}

	@Override
	public void update() {

	}

	@Override
	public void mouseClicked(int x, int y, int button) {

		resetActies();
	}

	/**
	 * Reset acties, deze wordt aangeroepen als er geklikt wordt.
	 */
	private void resetActies() {

		world.deleteAllGameOBjects();
		world.deleteAllDashboards();
		world.restartSound();
		world.setupGame();
		world.resumeGame();
		world.setSpelAfgelopen(false);
		world.refreshDasboardText();
		
	}
}
