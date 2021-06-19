package fretboard;
import static fretboard.Note.A;
import static fretboard.Note.B;
import static fretboard.Note.D;
import static fretboard.Note.E;
import static fretboard.Note.G;
import static fretboard.Note.getNotes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * A component that draws a fretboard and can place notes on the neck (placeNote)
 */
public class Fretboard extends JComponent 
{
	private static final long serialVersionUID = 1L;
	
	/**  Frame on which everything is printed*/
	private JFrame frame;
	/** Unscaled image of the fretboard*/
	private final BufferedImage fretboard;
	/** Array holding the unscaled width of each fret (nth fret is the pos-1) */
	private final double[] fretWidths = {40.2+.38, 41.26, 43.42, 41.27, 44.49, 41.26, 43.42, 41.26, 38.88, 38.54, 38.88, 27.36, 25.98, 25.61, 21.67, 21.72, 19.30, 20.39, 21.65, 16.39, 16.29, 18.43, 20.13};
	/** Vertical size of the unscaled neck */
	private final double neckHeight = 68.77;
	/** Horizontal size of the unscaled neck */
	private final double neckWidth = 762.80; 
	/** The unscaled width of a fret marker*/
	private final double fretMarkerWidth = 2.46;
	/** The scaled image of the fretboard (mutable) */
	private Image scaledFretboard;
	/** How big notes are compared to the neck width | (width/scale) to get scalled size */
	private final int noteScale = 60;
	/** The height of an individual string */
	private final double stringHeight = 2.24;
	/** gap between strings	*/
	private final double stringGap = 7.9; 
	/** All 12 of the notes in their unscaled pngs */ 
	final BufferedImage[] noteImgs = new BufferedImage[12];
	
	public Fretboard(JFrame frame) 
	{
		setVisible(true);
		setBackground(Color.black);
		this.frame = frame;
		setBounds(0, 0, frame.getWidth(), frame.getHeight());
		ImageLoader loader = new ImageLoader();
		fretboard = loader.loadImage("neck");
		//loads the note pngs in the array
		for(Note note : getNotes()) noteImgs[getNotes().indexOf(note)] = loader.loadImage(note.toString());
	}
	
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		//scales fretboard
		scaledFretboard = fretboard.getScaledInstance(frame.getWidth(), -1, Image.SCALE_SMOOTH);
		setBounds(0, 0, frame.getWidth(), scaledFretboard.getHeight(null));
		//scales fretboard
		scaledFretboard = fretboard.getScaledInstance(frame.getWidth(), -1, Image.SCALE_SMOOTH);
		//draws fretboard
		g.drawImage(scaledFretboard, 0, 0, null);
		//places all the notes TEMP
		for(Note note : getNotes())
		{
			//placeNote(note, g);
		}
	}
	
	/**
	 * Used to place a specific note all along the fretboard
	 * <p>
	 * @param note the note as a string - sharps are represented with an "f" after the uppercase note, and sharps are the same but with "s"
	 * <p>
	 * @param g the Graphics object from the paintComponent method
	 */
	public void placeNote(Note note, Graphics g)
	{
		BufferedImage noteImg = Util.imageToBufferedImage(noteImgs[getNotes().indexOf(note)].getScaledInstance(getNoteSize(), -1, Image.SCALE_SMOOTH));
		//bottom 5 string of a guitar
		final List<Note> stringNotes = Arrays.asList(new Note[]{E, A, D, G, B});
		
		//loops through all the strings, finds the first fret on a string with the target note and uses drawNote to draw 2 notes 
		for(Note stringNote : stringNotes)
		{ 
			//how many half steps the target note is above or below the 0th note
			final int difference = getNotes().indexOf(note) - getNotes().indexOf(stringNote);
			//octave above a negative difference can be placed on the fretboard
			final int fretNum = difference >= 0 ? difference : difference + 12;
			//draws 2 notes (fretNum and fretNum + 12)
			drawNote(noteImg, fretNum, stringNotes.indexOf(stringNote)+1, g);

			//places the notes on the high E string as well
			if(stringNote == E) drawNote(noteImg, fretNum, 6, g);
		}
	}
	
	/**
	 * Draws specified note at the specified fret and string and on the fret 12 above
	 * @param img the unscaled note img (fix this once images are normalized)
	 * @param fretNum the fret position
	 * @param stringNum which string it is to be placed on <br> <b> <font color="red"> NOTE: OPPOSITE OF GUITAR, LOW E IS 1 AND HIGH E IS 6 </font> </b> 
	 * @param g the graphics object
	 * 
	 */
	private void drawNote(Image img, int fretNum, int stringNum, Graphics g)
	{ 
		
		g.drawImage(img, getPosForFret(fretNum), (int) (getScaledHeight(stringGap)*(stringNum) + getScaledHeight(stringHeight)/2 + getScaledHeight(stringHeight)*(stringNum-1) - getNoteSize()/2), null);
		//places the note an octave higher
		g.drawImage(img, getPosForFret(fretNum+12), (int) (getScaledHeight(stringGap)*(stringNum) + getScaledHeight(stringHeight)/2 + getScaledHeight(stringHeight)*(stringNum-1) - getNoteSize()/2), null);
	}
	/**
	 * 
	 * @return the diameter of the scaled notes (int)
	 */
	private int getNoteSize()
	{
		return frame.getWidth()/noteScale;
	}
	
	/**
	 * 
	 * @param fretNum the fret number
	 * @return the scaled horizontal position to place the note at the center of the given fret
	 */
	private int getPosForFret(int fretNum)
	{
		//if it is an open note the note gets placed on the edge of the screen
		if(fretNum == 0) return -getNoteSize()/2 + getNoteSize()/5;
		double pos = 0;
		//adds all the preceeding fret widths together
		for(int i = 0; i < fretNum - 1; i++)
		{
			pos+=(getScaledWidth(fretWidths[i]));
		}
		//adds half of the current fret width to center the note
		pos+=(getScaledWidth(fretWidths[fretNum-1]))/2;
		//adds the widths of all of the fret markers and subtracts half of its size to center it
		pos+=getScaledWidth(fretMarkerWidth)*(fretNum-1) - getNoteSize()/2;
		return((int) pos);
	}
	
	/**
	 * @param height vertical size of thing to be scaled
	 * @return the new height scaled in reference to the neck height
	 * 
	 */
	private double getScaledHeight(double height)
	{
		return(height/neckHeight)*scaledFretboard.getHeight(null);
	}
	
	/**
	 * @param width horizontal size of thing to be scaled
	 * @return the new width scaled in reference to the neck width
	 * 
	 */
	private double getScaledWidth(double width)
	{
		return(width/neckWidth)*scaledFretboard.getWidth(null);
	}
}