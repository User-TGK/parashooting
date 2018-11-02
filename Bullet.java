package nl.han.ica.Parashooting;

import nl.han.ica.OOPDProcessingEngineHAN.Collision.ICollidableWithGameObjects;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import processing.core.PGraphics;

import java.util.List;
import java.util.Random;

/**
 *
 * @author Ties Klappe
 * @author Wiek van den Elzen
 * 
 * De kogel klasse
 */

public class Bullet extends GameObject implements ICollidableWithGameObjects {

	/** Houd bij of er geschoten mag worden. */
	private boolean kanSchieten;
	
	/** Grootte van de kogel. */
	private int bulletSize;
	
	/** Verwijzing naar de wereld. */
	private Parashooting world;
	
	/** De richting waarin een kogel geschoten word. */
	private String shootDirection;
	
	/** De lengte van de kogel van de straaljager. */
	private final int playerBulletLength = 20;
	
	/** De lengte van de kogel van een parachutist. */
	private final int parachuteBulletLength = 15;
	
	/** Random snelheid van een kogel van een parachutist, deze is 1 - 3 sneller dan de parachutist zelf. */
	Random randomSpeedBullet;

	/**
	 * Constructor.
	 *
	 * @param world verwijzing naar de wereld
	 * @param bulletSize the bullet size
	 * @param shootDirection richting waarin er geschoten wordt, deze word getest op "west" of "east"
	 * @param kanSchieten boolean die aangeeft of er toestemming is om te schieten
	 * @param xSpeed dit is de snelheid van de kogel
	 */
	
	public Bullet(Parashooting world, int bulletSize, String shootDirection, boolean kanSchieten, int xSpeed) {

		this.world = world;
		this.bulletSize = bulletSize;
		this.shootDirection = shootDirection;
		this.kanSchieten = kanSchieten;

		setWidth(bulletSize);

		randomSpeedBullet = new Random();

		if (kanSchieten == true) {		
			if (shootDirection == "east") {

				setHeight(playerBulletLength);
				setxSpeed(xSpeed);
				
			}

			else if (shootDirection == "west") {

				setHeight(parachuteBulletLength);
				setxSpeed(xSpeed - (randomSpeedBullet.nextInt(5 - 3) + 3));
				
			}
		}
	}


	@Override
	public void draw(PGraphics g) {

		if (kanSchieten == true) {
			if (shootDirection == "east") {

				g.fill(0);
				g.rect(getX(), getY(), playerBulletLength, bulletSize);

			}

			else if (shootDirection == "west") {

				g.fill(139, 0, 139);
				g.rect(getX(), getY(), parachuteBulletLength, bulletSize);

			}
		}
	}

	
	@Override
	public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
		for (GameObject g : collidedGameObjects) {

			if (shootDirection == "east") {
				if (g instanceof Parachute) {

					world.deleteGameObject(this);
					world.deleteGameObject(g);
					world.setScore(world.getScore() + 1);
					((Parachute) g).setAlarmOn(false); 					

				}

				if (g instanceof Powerup) {
					((Powerup) g).doPowerupAction();
					world.deleteGameObject(this);
					world.deleteGameObject(g);
					playPowerupSound();

				}
			}

			else if (shootDirection == "west") {
				if (g instanceof Player) {

					world.deleteGameObject(this);
					world.setCurrentLifes(world.getCurrentLifes() - 1);

				}
			}
		}
	}

	/**
	 * Voor het afspelen van een power up geluid.
	 */
	private void playPowerupSound() {
		
		world.powerupSound.rewind();
		world.powerupSound.play();
				
	}

	@Override
	public void update() {

		if (shootDirection == "east") {
			if (getX() > world.getWidth()) {
				world.deleteGameObject(this);

			}
		}

		if (shootDirection == "west") {
			if (getX() + getWidth()  <= 0) {
				world.deleteGameObject(this);

			}
		}
	}
}
