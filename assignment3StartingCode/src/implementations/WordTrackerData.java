/**
 * Helper method to the WordTracker class
 * 
 * @author Daniel Chu
 * @version 1.0
 */
package implementations;
import java.io.Serializable;
import java.util.*;


public class WordTrackerData implements Comparable<WordTrackerData>, Serializable
{
	private static final long serialVersionUID = 225L;
	
	private int freq;
	private String word;
	
	private Map<String, Set<Integer>> occurence;
	
	/**
	 * default parameterized method for the WordTrackerData class
	 * Preconditions: none
	 * Postconditions: A new WordTrackerData Object is created
	 * 
	 * @param word Word to be tracked for frequency and occurrence
	 */
	public WordTrackerData(String word)
	{
		this.word = word.toLowerCase(); //done twice just in case
		this.freq = 1;
		this.occurence = new HashMap<>();
	}
	
	/**
	 * Increments the frequency and updates the occurrence of the tracked word
	 * Preconditions: a valid WordTrackerData Object must exist, a valid input file with valid lines must exist
	 * Postconditions:  the frequency and occurrence of the word is updated
	 * 
	 * @param fileName Name of the input file
	 * @param lineNum Number of the line where the word was extracted
	 */
	public void addOccuence(String fileName, int lineNum)
	{
		freq++;
		
		occurence.putIfAbsent(fileName, new HashSet<>()); // creates if it doesnt already exist
		occurence.get(fileName).add(lineNum); //adds line num to file
	}
	
	/**
	 * Getter for frequency of the WordTrackerData Object
	 * Preconditions: a valid WordTrackerData Object must exist
	 * Postconditions: the frequency of the WordTrackerData Object is returned
	 * 
	 * @return Returns the frequency of the WordTrackerData Object 
	 */
	public int getFreq()
	{
		return freq;
	}
	
	/**
	 * Getter method of word in the WordTrackerData Object 
	 * Preconditions: a valid WordTrackerData Object  must exist
	 * Postconditions: the word from the WordTrackerData Object is returned
	 * 
	 * @return Returns the word from the WordTrackerData Object 
	 */
	public String getWord()
	{
		return word;
	}
	
	/**
	 * Getter method for the occurrence map of the WordTrackerData Object 
	 * Preconditions: a valid WordTrackerData Object must exist
	 * Postconditions: the occurrence map of the WordTrackerData Object is returned
	 * 
	 * @return Returns the occurrence map of the WordTrackerData Object 
	 */
	public Map<String, Set<Integer>> getOccurence()
	{
		return occurence;
	}
	
	/**
	 * Prints a string listing the word and which file it was from
	 * Preconditions: a valid WordTrackerData Object must exist
	 * Postconditions: a string listing the word and the file it was from is displayed
	 * 
	 * @return Returns a string containing the word and the file it was from 
	 */
	public String printFilesOnly() //-pf
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("Key : ===").append(word).append("=== found in file: ");
		
		for (String file : occurence.keySet()) 
		{
			sb.append(file).append(" ");
		}
		
		return sb.toString().trim();
	}

	/**
	 * Prints a string listing the word and which file and the line number it was from
	 * Preconditions: a valid WordTrackerData Object must exist
	 * Postconditions: a string listing the word and the file and the line number it was from is displayed
	 * 
	 * @return Returns a string containing the word and the file and the line number it was from 
	 */
	public String printFilesLines() //-pl
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("Key : ===").append(word).append("=== found in file: ");
		
		for (Map.Entry<String, Set<Integer>> entry : occurence.entrySet()) 
		{
			
	       sb.append(entry.getKey()).append(" at lines: ");

	        List<Integer> sortedLines = new ArrayList<>(entry.getValue());
	        
	        Collections.sort(sortedLines);
	        
	        for (Integer line : sortedLines )
	        {
	            sb.append(line).append(", ");
	        }
		}
		
		return sb.toString().trim();
	}

	/**
	 * Prints a string listing the word, file, line number it was from along with the frequency
	 * Preconditions: a valid WordTrackerData Object must exist
	 * Postconditions: a string listing the word, file, line number it was from along with the frequency it was from is displayed
	 * 
	 * @return Returns a string containing the word, file, line number it was from along with the frequency 
	 */
	public String printFilesLinesFrequency()  //-po
	{
		StringBuilder sb = new StringBuilder();
		
	    sb.append("Key : ===").append(word).append("=== ").append(freq).append(" found in file: ");

	    for (Map.Entry<String, Set<Integer>> entry : occurence.entrySet()) 
	    {
	        sb.append(entry.getKey()).append(" at lines: ");

	        // Sorts line num
	        List<Integer> sortedLines = new ArrayList<>(entry.getValue());
	        Collections.sort(sortedLines);

	        for (Integer line : sortedLines)
	        {
	            sb.append(line).append(", ");
	        }
	    }

	    if (sb.length() > 2) {
	        sb.setLength(sb.length() - 2);
	    }


	    return sb.toString();
	}
	
	@Override
	public int compareTo(WordTrackerData o) // called when alphabetical sort, see if its needed bc of bst sorting &traversal
	{
		return this.word.compareTo(o.word);
	} 
	
	@Override
	public String toString() 
	{
	    StringBuilder sb = new StringBuilder();
	    sb.append(String.format("%s (%d): ", word, freq));

	    for (Map.Entry<String, Set<Integer>> fileEntry : occurence.entrySet()) 
	    {
	        String fileName = fileEntry.getKey();
	        Set<Integer> lineData = fileEntry.getValue();

	        sb.append(String.format("[%s: ", fileName));

	        for (int lineNum : lineData) 
	        {
	        	sb.append(String.format("line %d, ", lineNum));
	        }

	        if (!lineData.isEmpty())
	        {
	            sb.setLength(sb.length() - 2);
	        }

	        sb.append("] ");
	    }

	    return sb.toString().trim();
	}

	
}
