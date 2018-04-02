/*
Kyle Tsujimoto
8-3-17
MissingBracket.java



*/
import java.util.ArrayList;		// used by expression evaluator
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MissingBracket
{
	private String str, inFileName, outFileName;
	private Scanner input;
	private ArrayList<String> openBrackets;
	private ArrayList<String> errors;
	private ArrayList<Integer> lineNums;

	public MissingBracket(String name)
	{
		openBrackets = new ArrayList<String>();
		errors = new ArrayList<String>();
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
