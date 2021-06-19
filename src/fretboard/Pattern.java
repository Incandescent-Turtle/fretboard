package fretboard;

public class Pattern 
{
	private final PatternCode code;
	private final Note rootNote;
	
	public Pattern(PatternCode code, Note rootNote) 
	{
		this.code = code;
		this.rootNote = rootNote;
	}
}