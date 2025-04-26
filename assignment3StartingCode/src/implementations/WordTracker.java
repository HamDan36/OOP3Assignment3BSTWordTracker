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

//	public String getWord()
//	{
//		return word;
//	}
	
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
		word = word.replaceAll(",.?/!\";:()", "").toLowerCase();
		word =word.trim();
		
		return word;
	}
	
	public static void main(String[] args)
	{
		WordTracker treeTracker = new WordTracker();
		
		treeTracker.handleUser(args);
	}
	

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

		saveTree("./testOutput/repository.ser"); 
	}
	
	public void printTree() // testing
	{
		if (wordTree.isEmpty())
		{
			System.out.println("Tree has no words");
			return;
		}
		
		utilities.Iterator<WordTrackerData> iterator = wordTree.inorderIterator();
		
		while(iterator.hasNext())
		{
			System.out.println(iterator.next());
		}
	}
}
