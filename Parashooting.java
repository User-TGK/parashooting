package nl.han.ica.Parashooting;

import nl.han.ica.OOPDProcessingEngineHAN.Dashboard.Dashboard;
import nl.han.ica.OOPDProcessingEngineHAN.Engine.GameEngine;
import nl.han.ica.OOPDProcessingEngineHAN.Persistence.FilePersistence;
import nl.han.ica.OOPDProcessingEngineHAN.Persistence.IPersistence;
import nl.han.ica.OOPDProcessingEngineHAN.Sound.Sound;
import nl.han.ica.OOPDProcessingEngineHAN.View.View;
import processing.core.PApplet;

/**
 * De klasse Parashooting: het hoofdprogramma (de app).
 *
 * @author Ties Klappe
 * @author Wiek van den Elzen
 */

@SuppressWarnings("serial")
public class Parashooting extends GameEngine {

	/** De statistieken die op het beeld staan. */
	private TextObject visualStats;
	
	private IPersistence persistence;
	
	private Sound backgroundSound;
	/** Geluid dat je hoort als je schiet. */
	
	public Sound bulletSound;
	/** Geluid dat je hoort als je een powerup pakt. */
	
	public Sound powerupSound;
	
	private Player player;
	
	/** Het aantal levens dat de straaljager op dit moment heeft. 
	 *  Deze beginnen op 5 en kunnen door het pakken van Power-ups maximaal 10 worden. */
	private int currentLifes = 5;
	
	/** De score op dit moment. */
	private int score;
	
	/** De highscore op dit moment. */
	private int highScore;
	
	/** Een boolean die aangeeft of het spel op dit moment is afgelopen. */
	private boolean spelAfgelopen = false;
	
	/** De spawner die parachutes laat spawnen. */
	ParachuteSpawner spawnerPa;
	
	/** De spawner die parachutes laat spawnen. */
	PowerupSpawner spawnerPo;

	/**
     * In deze methode worden de voor het spel
     * noodzakelijke zaken geïnitialiseerd
     */
	
	@Override
	public void setupGame() {

		int worldWidth = 1500;
		int worldHeight = 800;

		createDashboard(worldWidth, 700);
		createView(worldWidth, worldHeight);
		initializePersistence();
		initializeSound();
		createObjects();
		setSpawners();
		
		setCurrentLifes(5);
		score = 0;

	}

	public static void main(String[] args) {
		PApplet.main(new String[] { "nl.han.ica.Parashooting.Parashooting" });
	}

	/**
	 * Maakt het beeld aan.
	 *
	 * @param screenWidth de breedte van het beeld
	 * @param screenHeight de hoogte van het beeld
	 */
	private void createView(int screenWidth, int screenHeight) {
		View view = new View(screenWidth, screenHeight);
		view.setBackground(loadImage("src/main/java/nl/han/ica/Parashooting/media/achtergrond.jpg"));

		setView(view);
		size(screenWidth, screenHeight);
	}

	/**
	 * Inialiteer geluid.
	 */
	private void initializeSound() {
		backgroundSound = new Sound(this, "src/main/java/nl/han/ica/Parashooting/media/MusicbyChicoArts.mp3");
		backgroundSound.loop(-1);
		bulletSound = new Sound(this, "src/main/java/nl/han/ica/Parashooting/media/shot.mp3");
		powerupSound = new Sound(this, "src/main/java/nl/han/ica/Parashooting/media/powerup.mp3");

	}

	/**
	 * Herstart geluid.
	 */
	
	public void restartSound() {
		backgroundSound.pause();
		backgroundSound.rewind();
	}

	/**
	 * Deze methode maakt de game objecten aan.
	 */
	
	private void createObjects() {

		Cloud c = new Cloud(this);
		addGameObject(c, this.getWidth(), 200);
		player = new Player(this, (float) 1.5);
		addGameObject(player, 25, 300);

	}

	/**
	 * Deze methode maakt de spawners aan.
	 */
	public void setSpawners() {

		spawnerPa = new ParachuteSpawner(this, (float) 3.5);
		spawnerPo = new PowerupSpawner(this, (float) 0.1, player);
		
	}

	/**
	 * Deze methode verwijderd de spawners, verwijderd de speler en zet spelAfgelopen op true.
	 */
	public void deleteSpawners() {

		spawnerPo.deleteSpawner();
		spawnerPa.deleteSpawner();
		deleteGameObject(player);
		setSpelAfgelopen(true);
	}

	/**
	 * Inialiseert de waarde van highscore en slaat deze op en laadt indien mogelijk een opgeslagen waarde.
	 */
	private void initializePersistence() {
		persistence = new FilePersistence("main/java/nl/han/ica/Parashooting/media/highScore.txt");
		if (persistence.fileExists()) {
			highScore = Integer.parseInt(persistence.loadDataString());
			refreshDasboardText();
		}
	}

	/**
	 * Creates the dashboard.
	 *
	 * @param dashboardWidth de breedte van het dashboard.
	 * @param dashboardHeight de hoogte van het dashboard.
	 */
	private void createDashboard(int dashboardWidth, int dashboardHeight) {

		Dashboard dashboard = new Dashboard(0, 0, dashboardWidth, dashboardHeight);
		visualStats = new TextObject("");
		dashboard.addGameObject(visualStats);
		addDashboard(dashboard);

	}

	/**
	 * Refreshed de dasboard tekst.
	 */
	public void refreshDasboardText() {

		visualStats.setText("                    Aantal levens: " + getCurrentLifes() + "                     Score: "
				+ score + "                      Highscore: " + highScore);

	}

	/**
	 * Maakt het game over scherm aan.
	 */
	private void createGameOver() {

		GameOver G = new GameOver(this);
		addGameObject(G, this.getWidth() / 2 - G.getWidth() / 2, this.getHeight() / 2 - G.getHeight() / 2);

	}

	@Override
	public void update() {

		if (getCurrentLifes() == 0) {

			this.deleteSpawners();
			this.pauseGame();
			this.createGameOver();

		}
	}

	/**
	 * Zet de waarde van currentLifes.
	 *
	 * @param currentLifes de nieuwe waarde van currentLifes
	 */
	public void setCurrentLifes(int currentLifes) {
		
		if (currentLifes <= 10) {
		this.currentLifes = currentLifes;
		refreshDasboardText();
		
		}
	}

	/**
	 * Haalt de waarde van currentLifes op.
	 *
	 * @return currentLifes
	 */
	public int getCurrentLifes() {
		return currentLifes;
	}

	/**
	 * Zet de waarde van score en eventueel highScore.
	 *
	 * @param score zet de score op een nieuwe score, indien de score hoger is dan de highscore wordt de highscore de huidige score.
	 */
	public void setScore(int score) {
		this.score = score;

		if (score > highScore) {

			highScore = score;
			persistence.saveData(Integer.toString(highScore));

		}

		refreshDasboardText();
	}

	/**
	 * Haalt de huidige waarde van score op.
	 *
	 * @return De score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Controleert of het spel afgelopen is. 
	 *
	 * @return true, als het spel afgelopen
	 */
	public boolean isSpelAfgelopen() {
		return spelAfgelopen;
	}

	/**
	 * Voegt de waarde van spelAfgelopen in en zet de waarde hiervan op de ingevoegde waarde.
	 *
	 * @param spelAfgelopen boolean die true is als het spel afgelopen is.
	 * @return true als het klopt.
	 */
	public boolean setSpelAfgelopen(boolean spelAfgelopen) {
		this.spelAfgelopen = spelAfgelopen;
		return spelAfgelopen;
	}
}
