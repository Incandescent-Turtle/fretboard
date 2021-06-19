package fretboard;
import java.util.Arrays; 
import java.util.List;

/**
 * Contains all 12 musical notes
 * static getNotes() is used to index all notes
 */
public enum Note 
{
	Af("Af"), A("A"), Bf("Bf"), B("B"), C("C"), Cs("Cs"), D("D"), Ef("Ef"), E("E"), F("F"), Fs("Fs"), G("G");
	
	/** The note as a string */
	private final String note;
	
	Note(String note)
	{
		this.note = note;
	}
	
	@Override
	public String toString() 
	{
		return note;
	}
	
	/**
	 * used to index the musical notes
	 * @return the notes in order starting from Af as a list
	 * 
	 */
	public static List<Note> getNotes()
	{
		return Arrays.asList(values());
	}
}