package nl.han.ica.Parashooting;

import java.util.Random;
import nl.han.ica.OOPDProcessingEngineHAN.Alarm.Alarm;
import nl.han.ica.OOPDProcessingEngineHAN.Alarm.IAlarmListener;

/**
 *
 * @author Ties Klappe
 * @author Wiek van den Elzen
 * 
 * De klasse die willekeurig een van de aanwezige
 * Powerups laat generen met een configureerbaar
 * spawn ratio
 */

public class PowerupSpawner implements IAlarmListener {

	/** Een verwijzing naar de speler (straaljager). */
	Player player;

	/** Boolean die bijhoud of het alarm gestopt moet worden of aan mag blijven. */
	private boolean alarmOn = true;
	
	/** Het ratio waarin powerups spawnen. */
	private float spawnRate;
	
	/** Referentie naar de wereld. */
	private Parashooting world;
	
	/** Object van het type random die gebruikt wordt de locatie waar een powerup spawnt te bepalen. */
	private Random random;
	
	/** De marge vanaf 0 vanaf waar er powerups mogen spawnen. */
	final int marge = 350;
	
	/** Het aantal verschillende soorten powerups. */
	final int aantalPowerups = 2;

	
	/**
	 * Maakt een nieuwe powerup-spawner aan.
	 *
	 * @param world Referentie naar de wereld.
	 * @param spawnRate Het ratio waarin powerups spawnen.
	 * @param player Referentie naar de speler (straaljager).
	 */
	public PowerupSpawner(Parashooting world, float spawnRate, Player player) {
		
		this.world = world;
		this.spawnRate = spawnRate;
		this.player = player;
		
		random = new Random();
	
		setAlarmChoice(alarmOn);
	}

	/**
	 * Zet het alarm aan of uit op basis van de meegegeven parameter.
	 *
	 * @param on parameter die bepaalt of het alarm aan of direct stop moet worden gezet.
	 */
	public void setAlarmChoice(boolean on) {

		Alarm alarm = new Alarm("New powerUp", 1 / spawnRate);
		alarm.addTarget(this);

		if (on) {

			alarm.start();

		}

		else

			alarm.stop();
	}

	
	/**
	 *  Op het moment dat het alarm getriggerd wordt wordt er 
	 *  random bepaald of de powerup health-up of bullet-up is. 
	 */
	@Override
	public void triggerAlarm(String alarmName) {

		Powerup powerup;
		int Xpower;

		if (alarmOn) {

			int powerType = random.nextInt(aantalPowerups);
			int Ypower = 0;

			if (powerType == 0) {

				powerup = new HealthUp(world);
			
			}

			else  {

				powerup = new BulletUp(world, player);
			
			}

			Xpower = random.nextInt(((world.getWidth() - (int) powerup.getWidth()) - marge) - 1) + marge;
			world.addGameObject(powerup, Xpower, Ypower);
			setAlarmChoice(true);

		}
	}

	/**
	 * Delete spawner.
	 */
	public void deleteSpawner() {

		alarmOn = false;

	}
}
