/**	Java Programming Project 1 - Written by David McConnell
 *	Last modified: 1/30/2020
 */	

import java.util.Scanner;												// Required for keyboard input
import java.io.*;														// Required for file processing

public class McConnellDProject1 
{	
	public static String	UNPROCESSED_FILE = "UnprocssedTests.txt",	// Variable for unprocessed text filename
							RESULTS_FILE = "resultTests.txt"; 			// Variable for results text filename
	public static int		TEST_QUESTIONS = 10;						// Variable for number of test questions to check
	
	public static void main(String[] args) throws IOException
	{
		String	menuChoice;												// holds user's menu choice
		Scanner	keyboard = new Scanner(System.in);						// Scanner object to accept keyboard input
		File unprocessed = new File(UNPROCESSED_FILE);					// Unprocessed file must exist for application to work. 
		if (!unprocessed.exists()) 										// Check if it exists. If not, create it.
		{
			PrintWriter unprocessedFile = new PrintWriter(UNPROCESSED_FILE);	// Create blank file to fulfill dependency 
			unprocessedFile.close();									// close the file as it is not needed right now
		}	//	end if
		
		do { 															// This menu will print until menu choice to quit is entered
			
			display_menu();												// Display a menu to user
			menuChoice = keyboard.nextLine().toUpperCase();				// Take input as user's menu choice; case-insensitive
			switch(menuChoice) {										// Run different logic according to menu choice. D = Quit
				case "A":
					menu_choice_a();
					break;
				case "B":
					menu_choice_b();
					break;
				case "C":
					menu_choice_c();
					break;
				case "D":
					System.out.println("Selection: Quit.");				// Exit menu loop; user has decided to exit application.
					break;
				default:
					System.out.println("Invalid entry.\n");				// Invalid entry error.
			}	// end switch
			
		} while(!menuChoice.equals("D"));	// end do-while for menu; exiting means program close

		keyboard.close();												// Scanner object no longer needed; close.
		
	}	// End main
	
	
	/**  Print menu to user.
	 * 
	 */
	public static void display_menu() 									// Contains logic for displaying menu to user.
	{
		System.out.println("   *** Personality Assessment Menu ***\n");
		System.out.println("A)\tAppend a personality test.");
		System.out.println("B)\tEvaluate the unprocessed personality tests.");
		System.out.println("C)\tCount and display how many people tested fell into each personality range.");
		System.out.println("D)\tQuit");
	}	// End display_menu
	
	
	/**  Menu Option 'A' code: Append a personality test. Stores data input by user into UNPROCESSED_FILE.
	 * 
	 * @throws IOException
	 */
	public static void menu_choice_a() throws IOException 
	{
		Scanner		keyboard = new Scanner(System.in);					// Scanner object to accept user input
		String		firstName = "",										// Hold user's first name
					lastName = "",										// Hold user's last name
					fullName = "",										// Hold user's full name
					response = "";										// Hold response for a test question
		FileWriter	fwriter = new FileWriter(UNPROCESSED_FILE, true);	// FileWriter object to write to unprocessed file, append.
		PrintWriter outputFile = new PrintWriter(fwriter, true);		// PrintWriter object for printing to unprocessed file, append.
		
		System.out.println("Selection: Append a personality test./n");	// Advise user of their menu selection.
		System.out.println("Enter first name:\t");						
		firstName = keyboard.nextLine();								// Get user's first name

		while(firstName.isEmpty()){										// Check for valid entry; error if blank
			System.out.println("Error: No name entered. Enter first name:\t");
			firstName = keyboard.nextLine();
		}	// end check for first name length
		
		System.out.println("Enter last name:\t");
		lastName = keyboard.nextLine();									// Get user's last name
		
		while(lastName.length()==0) {									// Check for valid entry; error if blank
			System.out.println("Error: No name entered. Enter last name:\t");
			firstName = keyboard.nextLine();
		}	// end check for last name length
				
		fullName = capitalize(firstName + " " + lastName);				// Ensure proper capitalization of user name.
		
		System.out.println("Entering test responses for " + fullName + ".\n");
		outputFile.println(fullName);									// Print user's full name to unprocessed file.
		
		for(int question=0; question < TEST_QUESTIONS; question++) {	// Ask for user's response for each question, max # TEST_QUESTIONS
			System.out.println("Enter response for question " + (question+1) + " as A, B, C, or D:");
			
			response = keyboard.nextLine().toUpperCase();				// Get user's response to the question, case-insensitive
			
			while(!(response.equals("A")||response.equals("B")||response.equals("C")||response.equals("D")))
				// So long as the response ISN'T A or B or C or D, the entry is invalid; print error and ask again. 
			{
				System.out.println("Invalid entry. Enter A, B, C, or D:");
				response = keyboard.nextLine().toUpperCase();
			} // End while loop checking answer validity. 
			
			System.out.println("Saving response " + response + " for question " + (question+1) + ".\n");
			outputFile.println(response);								// Print response to the question to unprocessed file.
		}	// End for loop counting test responses
		
		System.out.println("Finished recording responses for " + fullName + ". Returning to menu.\n");  // Advise user of completion
		
		fwriter.close(); 												// Close file writer. 
		outputFile.close(); 											// Close output file. 
	}	// End menu_choice_a()
	
	
	/**  Menu Option 'B' code: Processes UNPROCESSED_FILE data into RESULTS_FILE.
	 * 
	 * @throws IOException
	 */
	public static void menu_choice_b() throws IOException
	{
		FileWriter	results = new FileWriter(RESULTS_FILE, true);		// Scanner object to accept user input
		PrintWriter	outputFile = new PrintWriter(results);				// PrintWriter object for printing to unprocessed file
		String		name = "",											// Hold's user's name
					response = "";										// Hold's user's response
		int			accumulator = 0;									// Tracks overall score based on answers
		File 		unprocessed = new File(UNPROCESSED_FILE);			// Create file object for unprocessed file
		Scanner 	inputFile = new Scanner(unprocessed);				// Create scanner object to read unprocessed file		
		
		System.out.println("Selection: Evaluate the unprocessed personality tests.\n");		// Advise user of their selection.
		
		if(!inputFile.hasNext())										// See if unprocessed file is blank. If so, nothing to do here.
			System.out.println("No unprocessed personality tests exist. Returning to menu.\n");
		else {															// Unprocessed test file exists, so it needs to be processed.
			while(inputFile.hasNext()) 									// Do the following for every user in unprocessed file.
			{
				accumulator = 0;										// Set accumulator to zero.
				name = inputFile.nextLine();							// Name is the first line in file.
				name = capitalize(name);								// Ensure name is capitalized; may be pre-existing file error.
				outputFile.println(name);								// Print the name to the results file

				// Calculate score based on test answer; do for every question, based on number of TEST_QUESTIONS
				for(int question=1; question < (TEST_QUESTIONS+1); question++) {
					
					response = inputFile.nextLine().toUpperCase();		// Next line contains an alphabetical response, case-insensitive.

					switch(question) {									// Some questions have same values. ID answer values by question #
						case 1:											// Question 1 has the following values
							switch(response) {							// Response is the user's answer.
								case "A":
									accumulator += 3;					// A is worth 3 pints for question 1. Same logic repeats below.
									break;
								case "B":
									accumulator += 1;
									break;
								case "C":
									accumulator += 6;
									break;
								case "D":
									accumulator += 2;
									break;
							}	// End question 1 response switch
							break;		
						
						case 2:											// Questions 2, 3, and 7 have same values; fall through.
						case 3:
						case 7:
							switch(response) {							// Values for questions 2,3, and 7
								case "A":
									accumulator += 2;
									break;
								case "B":
									accumulator += 1;
									break;
								case "C":
									accumulator += 3;
									break;
								case "D":
									accumulator += 4;
									break;
							}	// End question 2,3,7 response switch
							break;

						case 4:
							switch(response) {
								case "A":
									accumulator += 3;
									break;
								case "B":
									accumulator += 1;
									break;
								case "C":
									accumulator += 2;
									break;
								case "D":
									accumulator += 6;
									break;
							}	// End question 4 response switch
							break;
						
						case 5:
							switch(response) {
								case "A":
									accumulator += 1;
									break;
								case "B":
									accumulator += 2;
									break;
								case "C":
									accumulator += 5;
									break;
								case "D":
									accumulator += 3;
									break;
							}	// End question 5 response switch
							break;

						case 6:
							switch(response) {
								case "A":
									accumulator += 3;
									break;
								case "B":
									accumulator += 1;
									break;
								case "C":
									accumulator += 4;
									break;
								case "D":
									accumulator += 2;
									break;
							}	// End question 6 response switch
							break;

						case 8:
							switch(response) {
								case "A":
									accumulator += 4;
									break;
								case "B":
									accumulator += 3;
									break;
								case "C":
									accumulator += 2;
									break;
								case "D":
									accumulator += 5;
									break;
							}	// End question 8 response switch
							break;

						case 9:
							switch(response) {
								case "A":
									accumulator += 6;
									break;
								case "B":
									accumulator += 4;
									break;
								case "C":
									accumulator += 8;
									break;
								case "D":
									accumulator += 2;
									break;
							}	// End question 9 response switch
							break;

						case 10:
							switch(response) {
							case "A":
								accumulator += 7;
								break;
							case "B":
								accumulator += 5;
								break;
							case "C":
								accumulator += 1;
								break;
							case "D":
								accumulator += 3;
								break;
							}	// End question 10 response switch
							break;

					}	// end switch checking question number
				}	// end for question in questions
				
				outputFile.print("Personality Type ");					// Print header
				
				if(accumulator >= 43) {									// Print personality type, based on score.
					outputFile.println("4");
				}	// end if
				else if(accumulator >= 31) {
					outputFile.println("3");
				}	// end else-if
				else if(accumulator >= 21) {
					outputFile.println("2");
				}	// end else-if
				else {
					outputFile.println("1");
				}	// end else
				
			}	// End 'while input file has next' loop.
			
			FileWriter	unprocessedClear = new FileWriter(UNPROCESSED_FILE);	// Open unprocessed file, write, to overwrite with blank
			unprocessedClear.close();									// Close unprocessed file as it isn't needed.
			
			System.out.println("Output file " + RESULTS_FILE + " has been updated with unprocessed personality tests.");
			System.out.println(UNPROCESSED_FILE + " has been cleared.\n");
			
		}	// End else statement executing code while file has stuff in it. File is done being processed.
		
	inputFile.close();													// Close unprocessed file.
	outputFile.close();													// Close results file.
	
	}	// End menu_choice_b()
	
	/** Menu Option 'C' code: Processes RESULTS_FILE and prints data to screen.
	 * @throws FileNotFoundException 
	 * 
	 */
	public static void menu_choice_c() throws FileNotFoundException {
		File 		results = new File(RESULTS_FILE);					// Create file object for results file.
		Scanner 	inputFile = new Scanner(results);					// Create scanner object to read results file.
		String		line = "";											// Holds line of text in results file.
		int			persType1 = 0,										// Variable to hold number of personality type 1
					persType2 = 0,										// Variable to hold number of personality type 2
					persType3 = 0,										// Variable to hold number of personality type 3
					persType4 = 0;										// Variable to hold number of personality type 4


		// Advise user of their selection
		System.out.println("Selection: Count and display how many people tested fell into each personality range.\n");
		
		if(!inputFile.hasNext())										// If results file is empty, nothhing to do. Return.
			System.out.println("No data contained in result file " + RESULTS_FILE + ". Returning to menu./n");
		
		else {															// Results file has data; process.
				while(inputFile.hasNext())								// Do the following until all of results file has been processed.
				{
					line = inputFile.nextLine();						// Read first line, containing name. Not needed.
					line = inputFile.nextLine();						// Read second line, containing personality type.

					// Line will end with personality type- 1, 2, 3, or 4. Add one to corresponding personality type count
					
					if(line.endsWith("1"))								
						persType1 += 1;									
					else if(line.endsWith("2"))							
						persType2 += 1;									
					else if(line.endsWith("3"))
						persType3 += 1;									
					else if(line.endsWith("4"))
						persType4 += 1;									
				}	// end while loop reading through results file data.

				// Print number of personality types.
				System.out.println("Personality Type 1: " + persType1);
				System.out.println("Personality Type 2: " + persType2);
				System.out.println("Personality Type 3: " + persType3);
				System.out.println("Personality Type 4: " + persType4 + "\n");
		}	// end if-else checking if file has data. 
		inputFile.close();												// Close results file.
		
	}	// end menu_choice_c
	
	/** Returns a title-case version of string passed as argument
	 * 
	 * @param title String passed as argument to be turned to title-case
	 * @return Title-case version of string originally passed
	 */
	public static String capitalize(String title)
	{
		String	capitalized = "",										// Holds string after being converted to title-case
				letter = "";											// Tracks each individual letter in string
		boolean capitalizeNext = true;									// Identify whether letter should be capitalized or not
		
		for(int chr=0; chr < title.length(); chr++)						// Iterate through letters in title
		{
			letter = title.substring(chr,chr+1); 						// letter is the character at the given index to be checked
			
			if(capitalizeNext) {										// Default is true, then changed to false unless following a space
				capitalized += letter.toUpperCase();					// Append capitalized version of letter to capitalized
				capitalizeNext = false;									// Letter has been capitalized; next should not be.
			}	// end if 
			else {
				capitalized += letter.toLowerCase(); 					// Append lower case version of letter to capitalized.
			}	// end else
			if(letter.equals(" ")) {									// If character at index is space, next letter needs capitalization
				capitalizeNext = true;									// Set capitalization flag
			}	// end if checking for space
		}	// End for loop iterating through 'title' characters.
		
		return capitalized;												// Return title-case version of string passed.
		
	}	// End capitalize()
	
	
}	// End class
