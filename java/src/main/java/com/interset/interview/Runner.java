package com.interset.interview;


import java.io.IOException;
import java.util.List;

public class Runner {

    /**
     * This is main method which is starting point for this application.
     * It requires 1 arguments to run successfully.
     *
     * @param: args[0] : Path to JSON or CSV file to read.
     *
     * The JSON and CSV files must contain the following fields:
     *  name, siblings, favourite_food, birth_timezone, birth_timestamp
     *
     * This application parses the files and prints out the following information:
     *       - Average number of siblings (round up)
     *       - Top 3 favourite foods
     *       - How many people were born in each month of the year (uses the month of each person's respective timezone of birth)
     *
     */
    public static void main(String args[]) {

        if (args.length != 1) {
            System.out.println("We currently only expect 1 argument! A path to a JSON or CSV file to read.");
            System.exit(1);
        }

        else
        {
        	String filePath = args[0];
        	FileParser process = new FileParser(filePath);
        	try
        	{
        		process.fileAnalyzer(filePath);
        		process.getPopulation().printResults();
        	}catch(Exception e)
        	{
        		e.printStackTrace();
        	}
        	
        }     

    }
}
