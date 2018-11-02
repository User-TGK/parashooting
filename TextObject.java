package nl.han.ica.Parashooting;

import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import processing.core.PGraphics;

/**
 *
 * @author Ties Klappe
 * @author Wiek van den Elzen
 * 
 * De klasse die een tekst laat afbeelden.
 */

public class TextObject extends GameObject {
	
    /** De tekst die moet worden afgebeeld. */
    private String text;

    
    /**
     * Maakt een nieuw tekst opbject aan.
     *
     * @param text De tekst die moet worden afgebeeld.
     */
    public TextObject(String text) {
        this.text=text;
    }
    
    /**
     * Stelt de text in op de meegegeven parameter.
     *
     * @param text De tekst waarin text moet veranderen.
     */
    public void setText(String text) {
        this.text=text;
    }

  
    @Override
    public void update() {

    }


    @SuppressWarnings("static-access")
	@Override
    public void draw(PGraphics g) {
       	
    	g.textAlign(g.LEFT,g.TOP);
        g.textSize(30);
        g.text(text,getX(),getY());
             
    }
}

