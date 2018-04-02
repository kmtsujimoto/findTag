/**
* MissingBracket.java
* For an HTML file, finds if a tag that needs to be closed is not closed and prints
* out the line and type of tag of the opening tag missing the closing tag.
* @author Kyle Tsujimoto
* @since 8-3-17
* @version 1.0
*/
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MissingBracket
{
	private String str, inFileName, outFileName;
	private Scanner input;
	private ArrayList<String> openBrackets;
	private ArrayList<Integer> lineNums;

	public MissingBracket(String name)
	{
		openBrackets = new ArrayList<String>();
		lineNums = new ArrayList<Integer>();
		inFileName = name;
		str = "";
		input = null;
	}

	public static void main(String [] args)
	{
		MissingBracket mB = new MissingBracket(args[0]);
		mB.run();
	}

	public void run()
	{
		getText();
		findBrackets();
		printBrackets();
	}
	
	/**
	 * Opens the file
	 */
	public void getText()
	{
		File inFile = new File(inFileName);
		try
		{
			input = new Scanner(inFile);
		}
		catch(FileNotFoundException e)
		{
			System.err.printf("ERROR: Cannot find/open file %s.\n", inFileName);
			System.exit(1);
		}
	}

	/**
	 * Finds the tags excluding any additional characters. If the tag includes a backslash,
	 * The corresponding opening tag is removed.
	 */
	public void findBrackets()
	{
		String current = "";
		int num = 0;

		while(input.hasNext())
		{
			num++;
			String line = input.nextLine();
			String cutLine;
			String tag;
			if(line.indexOf(" ") < line.indexOf(">") && line.indexOf(" ") != -1)
			{
				cutLine = line.substring(line.indexOf("<")+1);
				tag = line.substring(line.indexOf("<")+1, cutLine.indexOf(" ")+line.indexOf("<")+1);
				if(tag.indexOf("/") == -1 && !(tag.equals("!DOCTYPE")) && !(tag.equals("img")) && !(tag.equals("link")) && cutLine.indexOf("<") == -1)
				{
					openBrackets.add(tag);
					lineNums.add(num);
				}
				else if(tag.indexOf("/") > -1)
				{
					tag = tag.substring(1);
					int index = openBrackets.lastIndexOf(tag);
					openBrackets.remove(index);
					lineNums.remove(index);
				}
			}
			else if(line.indexOf(">") < line.indexOf(" ") && line.indexOf(">") > -1 || line.indexOf(" ") == -1 && line.indexOf(">") > -1)
			{
				cutLine = line.substring(line.indexOf("<")+1);
				tag = line.substring(line.indexOf("<")+1, cutLine.indexOf(">")+line.indexOf("<")+1);
				if(tag.indexOf("/") == -1 && !(tag.equals("!DOCTYPE")) && !(tag.equals("img")) && !(tag.equals("link")) && cutLine.indexOf("<") == -1)
				{
					openBrackets.add(tag);
					lineNums.add(num);
				}
				else if(tag.indexOf("/") > -1)
				{
					tag = tag.substring(1);
					int index = openBrackets.lastIndexOf(tag);
					openBrackets.remove(index);
					lineNums.remove(index);
				}
			}
		}
		input.close();
	}

	/**
	 * Prints the opening tags without closing tags and their lines.
	 */
	public void printBrackets()
	{
		System.out.println("\n\n\n");
		for(int i = 0; i < openBrackets.size(); i++)
		{
			System.out.println("You are missing a closing tag for the " + openBrackets.get(i) + " on line " + lineNums.get(i) + ".");
		}
		System.out.println("\n\n\n");
	}
}
