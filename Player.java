package nl.han.ica.Parashooting;

import java.util.ArrayList;
import nl.han.ica.OOPDProcessingEngineHAN.Alarm.Alarm;
import nl.han.ica.OOPDProcessingEngineHAN.Alarm.IAlarmListener;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.SpriteObject;

/**
 * De klasse Player (de bestuurbare straaljager).
 *
 * @author Ties Klappe
 * @author Wiek van den Elzen
 */

public class Player extends SpriteObject implements IAlarmListener {

	/** De snelheid van de player (straaljager). */
	private int speed = 8;
	
	/** Boolean die bijhoudt of de er bullets aangemaakt mogen worden. */
	private boolean kanSchieten = true;
	
	/** Het aantal kogels dat er per seconde mogen worden afgeschoten. */
	private float bulletsPerSecond = 2;
	
	/** De grootte van de kogel. */
	private int size = 115;
	
	/** De snelheid van de kogel. */
	private int bulletSpeed = 12;
	
	/** Referentie naar de wereld. */
	private Parashooting world;
	
	/** De toetsen die er gebruikt worden. */
	char[] keys = { 'q', 'a', ' ' };

	/** De arraylist waarin de toetsen die gebruikt worden worden opgeslagen. */
	ArrayList<PressedKey> keyList = new ArrayList<>();

	
	/**
	 * Maakt een nieuwe speler aan (straaljager) door de tweede (super) constructor te gebruiken.
	 *
	 * @param world Referentie naar de wereld.
	 * @param bulletsPerSecond Aantal kogels per seconden.
	 */
	public Player(Parashooting world, float bulletsPerSecond) {

		this(new Sprite("src/main/java/nl/han/ica/Parashooting/media/Jetplayer.png"));
		this.world = world;
		this.bulletsPerSecond = bulletsPerSecond;

		setFriction(0.1f);
		startAlarm();

		for (int i = 0; i < keys.length; i++) {

			keyList.add(new PressedKey(keys[i]));

		}
	}

	/**
	 * Maakt de player (straaljager) aan door een sprite (afbeelding) mee te geven.
	 *
	 * @param sprite De afbeelding van de straaljager wordt hier meegegeven.
	 */
	public Player(Sprite sprite) {

		super(sprite);

	}

	
	/**
	 * Start alarm, dit alarm houdt bij of er weer geschoten mag worden.
	 */
	private void startAlarm() {
		Alarm alarm = new Alarm("New bullet", 1 / bulletsPerSecond);
		alarm.addTarget(this);
		alarm.start();
	}

	
	/**
	 * Deze update houdt bij of de player niet onder of boven uit het beeld verdwijnt,
	 * en welke toets er is ingedrukt en wat er dan moet gebeuren.
	 */
	@Override
	public void update() {

		if (getY() <= 0) {
			setySpeed(0);
			setY(0);
		}

		if (getY() >= world.getHeight() - size) {
			setySpeed(0);
			setY(world.getHeight() - size);
		}

		if (keyList.get(0).getIsPressed()) {

			setDirectionSpeed(0, speed);

		}

		if (keyList.get(1).getIsPressed()) {
			setDirectionSpeed(180, speed);

		}

		if (keyList.get(2).getIsPressed()) {

			if (kanSchieten == true) {

				Bullet b = new Bullet(world, 3, "east", kanSchieten, bulletSpeed);
				world.addGameObject(b, this.getCenterX() + (this.getWidth() / 2) + 1, this.getCenterY());
				playShootSound();
				kanSchieten = false;

			}
		}
	}

	
	/**
	 * Als een toets wordt ingedrukt wordt deze op ispressed gezet.
	 */
	@Override
	public void keyPressed(int keyCode, char key) {

		for (PressedKey vergelijkingsToets : keyList) {

			if (key == vergelijkingsToets.getTheKeyPressed()) {
				vergelijkingsToets.setIsPressed(true);
			}
		}
	}

	
	/**
	 * Als de toets weer wordt losgelaten wordt deze op ispressed = false gezet.
	 */
	@Override
	public void keyReleased(int keyCode, char key) {

		for (PressedKey vergelijkingsToets : keyList) {

			if (key == vergelijkingsToets.getTheKeyPressed()) {
				vergelijkingsToets.setIsPressed(false);
			}
		}
	}

	
	/**
	 * Als het alarm voorbij is mag er weer geschoten worden.
	 */
	@Override
	public void triggerAlarm(String alarmName) {

		kanSchieten = true;
		startAlarm();

	}

	
	/**
	 * Om de bullets per seconde aan te passen (handig voor oa powerups).
	 *
	 * @param up Variable die aangeeft of er meer of minder bullets per seconde moeten zijn.
	 */
	public void setBulletsPerSecond(boolean up) {

		float changeValue = (float) 5;

		if (up) {

			bulletsPerSecond = bulletsPerSecond + changeValue;

		}

		else {

			bulletsPerSecond = bulletsPerSecond - changeValue;
		}
	}
	
	
	/**
	 * Voor het afspelen van het schiet geluid.
	 */
	private void playShootSound() {
		
		world.bulletSound.rewind();
		world.bulletSound.play();
			
	}
}
