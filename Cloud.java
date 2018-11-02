package nl.han.ica.Parashooting;

import java.util.Random;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.SpriteObject;

/**
 *
 * @author Ties Klappe
 * @author Wiek van den Elzen
 * 
 * Een wolk is een spelobject dat zelfstandig beweegt
 */

public class Cloud extends SpriteObject {

	/** Object random die bepaald op welke y positie de wolk spawnt. */
	private Random random;
	
	/** Verwijzing naar de wereld. */
	private Parashooting world;

	/**
	 * Geeft de wereld mee en creërt een nieuwe wolk door de tweede (super)-constructor aan te roepen.
	 *
	 * @param world referentie naar de wereld.
	 */
	public Cloud(Parashooting world) {
		this(new Sprite("src/main/java/nl/han/ica/Parashooting/media/Cloud.png"));
		this.world = world;
	}

	
	/**
	 * Maakt een nieuwe wolk aan door de super constructor aan te roepen.
	 *
	 * @param sprite Sprite (afbeelding) van de wolk.
	 */
	public Cloud(Sprite sprite) {
		super(sprite);
		setxSpeed(-1);
	}

	
	/** 
	 * Zorgt ervoor dat de wolk weer aan het begin spawnt op het moment dat deze uit beeld is verdwenen.
	 */
	@Override
	public void update() {

		if (getX() + getWidth() <= 0) {
			random = new Random();
			setX(world.getWidth());
			setY(random.nextInt(world.getHeight() - 300));
		}
	}
}
