package fretboard;

/**
 * Patterns to hold different scales etc
 *
 */
public enum PatternCode 
{
	Major(2212221);
	
	/** Pattern where 1 is a half-step and 2 and a whole-step */
	final String pattern;
	
	PatternCode(int pattern)
	{
		this.pattern = Integer.toString(pattern);
	}
}