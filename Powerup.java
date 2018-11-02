package nl.han.ica.Parashooting;

import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.SpriteObject;


/**
 *
 * @author Ties Klappe
 * @author Wiek van den Elzen
 * 
 * De abstracte Powerup klasse (hierin wordt polymorfie gebruikt)
 */

public abstract class Powerup extends SpriteObject {

	/** Referentie naar de wereld. */
	private Parashooting world;
	
	/** De grootte van de powerup */
	private int powerupSize;

	
	/**
	 * Maakt een nieuw spriteobject powerup aan door de tweede (super) constructor te gebruiken.
	 *
	 * @param world Referentie naar de wereld.
	 * @param img De sprite (afbeelding) van de powerup.
	 */
	public Powerup(Parashooting world, Sprite img) {
		this(img);
		this.world = world;
	}

	
	/**
	 * Maakt een nieuwe powerup aan.
	 *
	 * @param sprite De afbeelding van de powerup.
	 */
	public Powerup(Sprite sprite) {
		super(sprite);
		setySpeed(2);
	}

	
	/**
	 * Controleert of de powerup buiten de map is gevallen, indien dit het geval is wordt deze verwijderd.
	 */
	@Override
	public void update() {
		if (getY() > 800 - getPowerupSize()) {
			world.deleteGameObject(this);
		}
	}

	/**
	 * De powerup actie, dit is de methode waar polymorfie wordt toegepast.
	 */
	public void doPowerupAction() {

	}

	
	/**
	 * Haalt de waarde van de size van de powerup op en returnt deze.
	 *
	 * @return size van de powerup
	 */
	public int getPowerupSize() {
		return powerupSize;
	}

	/**
	 * Zet de waarde van de powerup size op de meegegeven waarde.
	 *
	 * @param powerupSize De size van de powerup.
	 */
	public void setPowerupSize(int powerupSize) {
		this.powerupSize = powerupSize;
	}
}
