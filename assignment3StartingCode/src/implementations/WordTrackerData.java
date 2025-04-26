package implementations;
import java.io.Serializable;
import java.util.*;


public class WordTrackerData implements Comparable<WordTrackerData>, Serializable
{
	private int freq;
	private String word;
	
	private Map<String, Set<Integer>> occurence;
	
	public WordTrackerData(String word)
	{
		this.word = word.toLowerCase(); //done twice just in case
		this.freq = 1;
		this.occurence = new HashMap<>();
	}
	
	public void addOccuence(String fileName, int lineNum)
	{
		freq++;
		
		occurence.putIfAbsent(fileName, new HashSet<>()); // creates if it doesnt already exist
		occurence.get(fileName).add(lineNum); //adds line num to file
	}
	
	public int getFreq()
	{
		return freq;
	}
	
	public String getWord()
	{
		return word;
	}
	
	public Map<String, Set<Integer>> getOccurence()
	{
		return occurence;
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
