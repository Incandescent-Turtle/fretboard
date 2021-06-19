package fretboard;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * Class with all static methods to ease
 */
public class Util 
{
	/**
	 * Converts an Image into a BufferedImage
	 * @param image the Image for conversion
	 * @return the converted now BufferedImage
	 */
	public static BufferedImage imageToBufferedImage(Image image) 
	{
	     BufferedImage bufferedImage = new BufferedImage (image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	     Graphics bufferedGraphics = bufferedImage.getGraphics();
	     bufferedGraphics.drawImage(image, 0, 0, null);
	     bufferedGraphics.dispose();
	     return bufferedImage;
	}
	
	public BufferedImage colorImage(BufferedImage loadImg, int red, int green, int blue) {
	    BufferedImage img = new BufferedImage(loadImg.getWidth(), loadImg.getHeight(),
	        BufferedImage.TRANSLUCENT);
	    Graphics2D graphics = img.createGraphics(); 
	    Color newColor = new Color(red, green, blue, 0 /* alpha needs to be zero */);
	    graphics.setXORMode(newColor);
	    graphics.drawImage(loadImg, null, 0, 0);
	    graphics.dispose();
	    return img;
	}
}