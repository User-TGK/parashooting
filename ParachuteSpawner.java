package nl.han.ica.Parashooting;

import java.util.Random;

import nl.han.ica.OOPDProcessingEngineHAN.Alarm.Alarm;
import nl.han.ica.OOPDProcessingEngineHAN.Alarm.IAlarmListener;

/**
 *
 * @author Ties Klappe
 * @author Wiek van den Elzen
 * 
 * Klasse die parachutisten genereert met configureerbare snelheid.
 */

public class ParachuteSpawner implements IAlarmListener {

	/** Het aantal parachutisten per spawn moment.  */
	private final int numberOfParachutesPerSpawn = 2;
	
	
	/** 
	 *  Boolean die true is zolang het alarm nog aanstaat, als deze false is wordt het 
	 *  alarm op dát moment stop gezet en kan er geen nieuw alarm gestart worden totdat de variable true wordt. 
	 */
	private boolean alarmOn = true;
		
	/** Referentie naar de wereld. */
	private Parashooting world;
	
	/** Tijd tussen een spawn moment. */
	private float timePerSpawn;
	
	/** Array die de y positie van gespawnde parachutisten opslaat. */
	private int yPosGespawndeParachutes[] = new int[numberOfParachutesPerSpawn];
	
	/** Hoogte van de parachutist */
	private final int parachuteHeight = 200;

	/** Een parachutist. */
	Parachute parachute;
	
	/** Object random die een random waarde voor de snelheid van een parachutist bevat. */
	Random randomSpeed;
	
	/** Object random die een random waarde voor de y-positie van een parachutist bevat. */
	Random random;

	
	/**
	 * Maakt een parachuteSpawner aan.
	 *
	 * @param world referentie naar de wereld.
	 * @param timePerSpawn de tijd tussen een spawn moment.
	 */
	public ParachuteSpawner(Parashooting world, float timePerSpawn) {

		this.timePerSpawn = timePerSpawn;
		this.world = world;

		random = new Random();
		randomSpeed = new Random();

		setAlarmChoice(alarmOn);

	}


	/**
	 * Met deze methode kan het alarm worden aangezet of stop gezet.
	 *
	 * @param on Boolean die wordt meegeven om aan te geven of het alarm aan of stop moeten worden gezet.
	 */
	public void setAlarmChoice(boolean on) {

		Alarm alarm = new Alarm("New parachute", timePerSpawn);
		alarm.addTarget(this);

		if (on) {

			alarm.start();

		}

		else

			alarm.stop();
	}


	@Override
	public void triggerAlarm(String alarmName) {

		if (alarmOn) {

			for (int x = 0; x < numberOfParachutesPerSpawn; x++) {

				yPosGespawndeParachutes[x] = newYLocation();
				checkYPosDistance();

			}

			checkYPosDistance();

			for (int i = 0; i < numberOfParachutesPerSpawn; i++) {

				int xSpeed = -(randomSpeed.nextInt(5 - 3) + 3);
				parachute = new Parachute(world, xSpeed);
				world.addGameObject(parachute, world.getWidth(), yPosGespawndeParachutes[i]);

			}

			setAlarmChoice(alarmOn);

		}
	}

	
	/**
	 * Bepaalt een nieuwe willekeurige Y locatie en returnt deze op basis van het object random.
	 *
	 * @return the int - de y positie die willekeurig berekend is.
	 */
	private int newYLocation() {

		int newYPos = random.nextInt((world.getHeight() - 200) + 1);
		return newYPos;
	}

	
	
	/**
	 * Test of de y positie al in gebruik is op basis van twee meegegeven intergers.
	 *
	 * @param yPos1 Eerste y positie.
	 * @param yPos2 Tweede y positie.
	 * @return true indien succesvol
	 */
	private boolean testIfYPosIsUsed(int yPos1, int yPos2) {

		if (Math.abs(yPos1 - yPos2) > parachuteHeight) {

			return true;

		}

		else {

			return false;
		}
	}

	/**
	 * Controleer de afstanden in een array. !!! Deze array mag uit maximaal drie getallen bestaan anders werkt deze methode niet meer.
	 */
	private void checkYPosDistance() {

		for (int x = 0; x < numberOfParachutesPerSpawn; x++) {
			for (int h = x + 1; h < numberOfParachutesPerSpawn; h++) {

				if (testIfYPosIsUsed(yPosGespawndeParachutes[x], yPosGespawndeParachutes[h])) {

				}

				else {

					while (testIfYPosIsUsed(yPosGespawndeParachutes[x], yPosGespawndeParachutes[h]) == false) {

						yPosGespawndeParachutes[x] = newYLocation();

					}
				}
			}
		}
	}

	
	/**
	 * Pauzeert de spawner (het alarm wordt stop gezet).
	 */
	public void deleteSpawner() {

		alarmOn = false;

	}
}
