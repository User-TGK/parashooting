package nl.han.ica.Parashooting;

import nl.han.ica.OOPDProcessingEngineHAN.Alarm.Alarm;
import nl.han.ica.OOPDProcessingEngineHAN.Alarm.IAlarmListener;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.SpriteObject;

/**
 *
 * @author Ties Klappe
 * @author Wiek van den Elzen
 * 
 * De parachutist klasse, tevens ook de
 * klasse die het automatisch laten schiet
 * -en van de parachutisten regelt.
 */

public class Parachute extends SpriteObject implements IAlarmListener {

	/** Verwijzing naar de wereld. */
	private Parashooting world;
	
	
	/** Boolean die true is zolang het alarm nog aanstaat, als deze false is wordt het 
	 *  alarm op dát moment stop gezet en kan er geen nieuw alarm gestart worden totdat de variable true wordt. 
	 */
	boolean alarmOn = true;

	
	/** De xSpeed van de parachute. */
	int xSpeed;

	
	/**
	 * Maakt een nieuw spriteobject (de parachutist) aan door de tweede (super) constructor aan te roepen 
	 * en inialiseert variablen van de klasse met als waarde de meegegeven parameters.
	 *
	 * @param world the world
	 * @param xSpeed the x speed
	 */
	public Parachute(Parashooting world, int xSpeed) {

		this(new Sprite("src/main/java/nl/han/ica/Parashooting/media/Parachutist.png"));
		this.world = world;
		this.xSpeed = xSpeed;

		setxSpeed(xSpeed);
		setAlarmChoice(alarmOn);

	}

	/**
	 * Maakt de parachutist aan..
	 *
	 * @param sprite De sprite (afbeelding) die ervoor zorgt dat de parachutist wordt weergegeven.
	 */
	public Parachute(Sprite sprite) {
		super(sprite);

	}


	/**
	 * In deze update wordt gecontroleerd of de parachutist
	 *  van de map is en of het spel nog niet is afgelopen.
	 */
	@Override
	public void update() {

		if (getX() + getWidth() <= 0) {

			world.deleteGameObject(this);

		}

		if (world.isSpelAfgelopen() == true) {

			alarmOn = false;

		}
	}

	
	/**
	 * Met deze methode kan het alarm worden aangezet of stop gezet.
	 *
	 * @param on Boolean die wordt meegeven om aan te geven of het alarm aan of stop moeten worden gezet.
	 */
	public void setAlarmChoice(boolean on) {

		Alarm alarm = new Alarm("New parachuteBullet", (float) 1.5);
		alarm.addTarget(this);

		if (on) {

			alarm.start();

		}

		else

			alarm.stop();
	}

	
	/**
	 * Zet het alarm op de waarde van de parameter (true of false).
	 *
	 * @param alarmOn de meegegeven parameter die de klassenvariable alarmOn aanpast naar zijn waarde.
	 */
	public void setAlarmOn(boolean alarmOn) {
		this.alarmOn = alarmOn;
	}


	
	/**
	 *  Er wordt gekeken of het alarm op aan staat en door mag gaan en of het spel nog niet afgelopen is,
	 *  is dit het geval dan wordt er een kogel aangemaakt vanuit de positie van de parachute.
	 */
	@Override
	public void triggerAlarm(String alarmName) {

		if (alarmOn) {
			if (world.isSpelAfgelopen() == false) {

				Bullet parachuteBullet = new Bullet(world, 5, "west", true, xSpeed);
				world.addGameObject(parachuteBullet, this.getCenterX() - (this.getWidth() / 2) - 1, this.getCenterY());
				setAlarmChoice(true);

			}
		}
	}

}