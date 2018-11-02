package nl.han.ica.Parashooting;

import nl.han.ica.OOPDProcessingEngineHAN.Alarm.Alarm;
/**
 * @author Ties Klappe
 * @author Wiek van den Elzen
 * De power-up die extra kogels per seconde geeft -klasse
 */

import nl.han.ica.OOPDProcessingEngineHAN.Alarm.IAlarmListener;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;

/**
 * Deze klasse bevat de power-up die meer kogels per seconde voor de straaljager instelt
 *
 * @author Ties Klappe
 * @author Wiek van den Elzen
 
 */

public class BulletUp extends Powerup implements IAlarmListener {
	
	/** Verwijzing naar het player object zodat de snelheid geconfigureert kan worden. 
	 */
	
	Player player;
	
	
   /** Houdt bij of de powerUp al in gebruik is. 
	 * De powerup bulletUp mag maximaal één keer 
	 * gebruikt worden om te voorkomen dat er teveel 
	 * bullets tegelijk kunnen worden geschoten.
     */
	private static boolean powerupUsed = false;

	
	/**
	 * Maakt een powerup: bullet up aan.
	 *
	 * @param world verwijzing naar de wereld.
	 * @param player verwijzing naar de speler (straaljager).
	 */
	public BulletUp(Parashooting world, Player player) {
		super(world, (new Sprite("src/main/java/nl/han/ica/Parashooting/media/bullets.png")));
		this.player = player;
		
	}
	
	
	/**
	 * Het uitvoeren van de powerup actie door het overriden van de abstracte functie in de super klasse.
	 */
	@Override
	public void doPowerupAction() {
		
		if (powerupUsed == false) {
			
		Alarm alarm = new Alarm("Increased bullet speed powerup", 5);
		alarm.addTarget(this);
		alarm.start();	
		player.setBulletsPerSecond(true);
		powerupUsed = true;
		
		}	
	}
	
	@Override
	public void triggerAlarm(String alarmName) {
		
		player.setBulletsPerSecond(false);	
		powerupUsed = false;
		
	}
}
