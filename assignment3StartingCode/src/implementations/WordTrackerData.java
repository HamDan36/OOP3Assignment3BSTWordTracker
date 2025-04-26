package implementations;
import java.io.Serializable;
import java.util.*;


public class WordTrackerData implements Comparable<WordTrackerData>, Serializable
{
	private static final long serialVersionUID = 225L;
	
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
