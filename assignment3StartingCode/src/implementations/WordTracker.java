package implementations;
import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;

import implementations.BSTreeNode;

public class WordTracker<E> implements Comparable<WordTracker>, Serializable
{
	private String word;
	private int frequency;
//	private Map<String, Set<Integer>> fileToLines = new HashMap<>();
	
	BSTree<WordTracker> wordTree = new BSTree<>();
	
	private void processFile(File filePath, BSTree<String> bst)
	{
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath)))
		{
			String line;
			int lineNum = 0;
			String fileName = filePath.getName();
			
			while ((line = reader.readLine()) != null)
			{
				lineNum++;
				ArrayList<String> wordList = makeList(line);
				
				for (String word : wordList)
				{
					if (!word.isEmpty())
					{
						bst.add(word);
					}
				}
			}
		}
		
		catch (IOException e)
		{
			System.err.println("Error reading the file: " + e.getMessage());
		}
	}
	
//	public BSTree<Comparable<? super E>>
	
	/**
	 * blocks:
	 * iterate through file
	 * 
	 * **remove symbols**
	 * **trims the sentence **
	 * **create arraylist
	 * 
	 * adds the entire file to the bstree
	 * 
	 */

	public String getWord()
	{
		return word;
	}
	
	public void freqCounter(String fileName, int lineNum)
	{
		fileTo
	}
	
	private ArrayList<String> makeList(String line)
	{
		ArrayList<String> wordList = new ArrayList<String>();
		
		for(String word: line.split(" "))
		{
			wordList.add(word);
		}
		
		for (int i = 0; i < wordList.size(); i++)
		{
			String tempWord = MicrosoftWord(wordList.get(i));
			
			if (!tempWord.isEmpty())
			{
				wordList.set(i, tempWord);
			}
			
			else
			{
				wordList.remove(i); // remove any empty strings
				i--;
			}
		}
		
		return wordList;
	}
	
	
	private String MicrosoftWord(String word) //word processor
	{
		word = word.replaceAll(",.?/!\";:()", "");
		word =word.trim();
		
		return word;
	}
	
	public static void main(String[] args)
	{
		
	}

	@Override
	public int compareTo(WordTracker o)
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
//	private String symbolTrim(String line)
//	{
//		line = line.replaceAll(",.?/!\";:()-", " ");
//		
//		return line;		
//	}
}
