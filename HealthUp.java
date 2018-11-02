package nl.han.ica.Parashooting;

import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;

/**
 *
 * @author Ties Klappe
 * @author Wiek van den Elzen
 * 
 * Deze klasse bevat de power-up die een extra leven aan de straaljager geeft.
 */

public class HealthUp extends Powerup {
	
	/** The world. */
	Parashooting world;

	/**
	 * Maakt een powerup: health-up aan.
	 *
	 * @param world Referentie naar de wereld
	 */
	public HealthUp(Parashooting world) {
		super(world, (new Sprite("src/main/java/nl/han/ica/Parashooting/media/levens.png")));
		this.world = world;
	}

	@Override
	public void doPowerupAction() {
					
		world.setCurrentLifes(world.getCurrentLifes() +1);
		
	}
}
