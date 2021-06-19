package fretboard;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

/**
 * Used to load images from files
 */
public class ImageLoader 
{
	/**
	 * @param path the png path in plaintext omitting .png
	 * @return the wanted image as a BufferedImage
	 * 
	 */
	public BufferedImage loadImage(String path)
	{
		try {
			return ImageIO.read(getClass().getResource("/" + path + ".png"));
		} catch (Exception e) {
			System.err.println(path + " doesnt work");
		}
		return null;
	}	
}