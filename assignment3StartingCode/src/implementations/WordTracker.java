/**
 * WordTracker is a class that implements BSTree and helper method WordTrackerData, it loads, save a BST based on the user input from the command line and creates BST from the input text file. Depending on the input, it can output the frequency and where the words in the text file.
 * 
 * @author Daniel Chu
 * @version 1.0
 */

package implementations;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;

import implementations.BSTreeNode;


public class WordTracker implements Serializable
{
	private static final long serialVersionUID = 2025L;
	
	BSTree<WordTrackerData> wordTree = new BSTree<>();
	
	/**
	 * Processes the input file, parses through it line by line to extract the words and log their occurrences into an ArrayList, and the line it appears on
	 * Preconditions: A valid WordTracker Object must exist, user inputs must be valid
	 * Postconditions: words from the input file are placed into a ArrayList
	 * 
	 * @param filePath Relative path of the input file
	 */
	public void processFile(String filePath)
	{
		String path = "./res/";
		filePath = path + filePath;
		
		String line;
		int lineNum = 0;
		System.out.println(filePath);// testing
		
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath)))
		{

			
			while ((line = reader.readLine()) != null)
			{
				lineNum++;
				ArrayList<String> wordList = makeList(line);
				
				for (String word : wordList)
				{
					if (!word.isEmpty())
					{
						freqCounter(word, filePath, lineNum); // pass new word
					}
				}
			}
		}
		
		catch (IOException e)
		{
			System.err.println("Error reading the file: " + e.getMessage());
		}
	}


	/**
	 * Updates the frequency of a word from the input text file. if the word was not in the BST, it will start a new count for the word
	 * Preconditions: a valid WordTracker Object must exist, word cannot be null or empty or blank
	 * Postconditions: the frequency of the word from the text file is updated
	 * 
	 * @param word Word from the text file
	 * @param fileName Name of the input file
	 * @param lineNum Number of the line from where the word was extracted
	 */
	public void freqCounter(String word, String fileName, int lineNum)
	{
		WordTrackerData temp = new WordTrackerData(word);
		
		BSTreeNode<WordTrackerData> node = wordTree.search(temp);
		
		if(node != null)
		{
			node.getElement().addOccuence(fileName, lineNum);
		}
		
		else
		{
			temp.addOccuence(fileName, lineNum);
			wordTree.add(temp);
		}
	}
	
	/**
	 * Converts a line of from input file into an ArrayList of words without the punctuation
	 * Preconditions: a valid WordTracker Object must exist. A valid input file and a non-null line from the file must exist.  
	 * Postconditions: an ArrayList Object from containing all the words from one line of text is created
	 * 
	 * @param line A line of words from the input text
	 * @return Returns an ArrayList containing the words from a line of text
	 */
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
	
	/**
	 * Processes a single word, strips all unwanted characters and whitespace, converts it to lowercase
	 * Preconditions: a valid WordTracker Object must exist. The word input cannot be null
	 * Postconditions: returns the word without the unwanted characters in lowercase
	 * 
	 * @param word Word from the line of text
	 * @return Returns the word without the unwanted characters in lowercase
	 */
	private String MicrosoftWord(String word) //word processor
	{
		word = word.replaceAll(",.?/!\";:()", "").toLowerCase();
		word =word.trim();
		
		return word;
	}
	
	/**
	 * main method of the WordTracker class
	 * Preconditions: none
	 * Postconditions: the main program of WordTracker is run
	 * 
	 * @param args Input arguments from the CLI from the user
	 */
	public static void main(String[] args)
	{
		WordTracker treeTracker = new WordTracker();
		
		treeTracker.handleUser(args);
	}
	
	/**
	 * Saves the current BST WordTracker Object into specified file
	 * Preconditions: a valid WordTracker Object must exist. A valid filename must be input from the user
	 * Postconditions: A BST WordTracker Object is serialized and saved into the specified file
	 * 
	 * @param fileName Name of the file where the WordTracker Object is saved.
	 */
	public void saveTree(String fileName) 
	{		
		try (ObjectOutputStream save = new ObjectOutputStream(new FileOutputStream(fileName))) 
		{
			save.writeObject(wordTree);
			System.out.println("Tree saved");
		} 
		
		catch (IOException e) {
			System.err.println("Error saving tree: " + e.getMessage());
		}
	}
	
	/**
	 * Checks for an existing saved BST WordTracker Object in and loads the BST if one is found
	 * 
	 * Preconditions: none
	 * Postconditions: a WordTracker Object is loaded from the save file if one is found
	 * 
	 */
	public void checkSave()
	{		
		File savedFile = new File("./testOutput/repository.ser");
		
		if(savedFile.exists())
		{
			System.out.println("Previous save found, loading tree...");
			loadTree();
			return;
		}
		
		else
		{
			return;
		}
	}
	
	/**
	 * Loads the BST WordTracker Object from a serialized file
	 * 
	 * Preconditions: a valid serialized WordTracker Object must exist in the specified file
	 * Postconditions: a BST WordTracker Object is loaded in with the data from the file
	 * 
	 */
	public void loadTree() {
		try (ObjectInputStream load = new ObjectInputStream(new FileInputStream("./testOutput/repository.ser"))) 
		{
			wordTree = (BSTree<WordTrackerData>) load.readObject();
			System.out.println("Tree loaded from repository.ser");
		} 
		
		catch (IOException | ClassNotFoundException e) {
			System.err.println("Error loading tree: " + e.getMessage());
		}
	}
	
	/**
	 * Handles the user inputs, processes input file and saved file and saves the file based on user inputs
	 * Preconditions: a valid WordTracker Object must exist
	 * Postconditions: a BST WordTracker Object is created and output is printed based on the user input
	 * 
	 * @param args CLI arguments from the user containing the input and output file and the type of processing to be displayed
	 */
	public void handleUser(String[] args)
	{
		if (args.length < 2)
		{
			System.out.println("Enter the input file, then the processing method with: \njava -jar WordTracker.jar <input.txt> -pf/-pl/-po [-f<output.txt>]");
			return;
		}
		
		String inputFile = args[0];
		String option = args[1];
		String outputFile = null;

		if (args.length == 3 && args[2].startsWith("-f")) {
			outputFile = args[2].substring(2);
		}

		checkSave(); 
		processFile(inputFile); 

		StringBuilder output = new StringBuilder();
		utilities.Iterator<WordTrackerData> it = wordTree.inorderIterator();

		while (it.hasNext())
		{
			WordTrackerData data = it.next();
			switch (option) {
				case "-pf":
					output.append(data.printFilesOnly()).append("\n");
					break;
				case "-pl":
					output.append(data.printFilesLines()).append("\n");
					break;
				case "-po":
					output.append(data.printFilesLinesFrequency()).append("\n");
					break;
				default:
					System.out.println("Invalid option. Use -pf, -pl, or -po.");
					return;
			}
		}

		if (outputFile != null) 
		{
			try (PrintWriter writer = new PrintWriter(outputFile)) 
			{
				writer.print(output.toString());
			} catch (IOException e) {
				System.err.println("Error writing to file: " + e.getMessage());
			}
		}
			else 
			{
				System.out.print(output.toString());
			}

		if(outputFile != null)
		{
//			System.out.println("if output fike is not null" + outputFile);
			saveTree(outputFile);
		}
		
		else
		{
			saveTree("./testOutput/repository.ser");
		}
	}
	
	
//	public void printTree() // testing
//	{
//		if (wordTree.isEmpty())
//		{
//			System.out.println("Tree has no words");
//			return;
//		}
//		
//		utilities.Iterator<WordTrackerData> iterator = wordTree.inorderIterator();
//		
//		while(iterator.hasNext())
//		{
//			System.out.println(iterator.next());
//		}
//	}
}
