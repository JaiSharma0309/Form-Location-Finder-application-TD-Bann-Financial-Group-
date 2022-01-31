import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

// The purpose of this class is to search for the given form ID number passed as input among the files stored in the root folder.  It works closely with the script that was created in unix.
public class FormNumberExtractor {
	
	// Creating a variable that will store the form ID number so that the program can search for the file with that specific number.
	private static String formIDNumber;
	
	// This variable will hold the path of the root directory containing all the different files and folders to search in.
	private static String path;
	
	
	// The main method serves as a central hub for the program, it's where the user input is dealt with and the primary function of the program is called.
	public static void main (String[] args) throws IOException{
		
		// Ensures the input typed in the script from the command line is correct.
		if (args.length == 2) {
			
			// The first of the two parts of the input in the script is the folder in which the user wants the form ID number to be searched for in.
			path = args[0];
			
			// The second part of the input in the script is the form ID number, the value gets stored in that variable, which is used later in the program.
			formIDNumber = args[1];
			
			// If the user does not put the root folder and form ID number in the input, line 32 accounts for that.
		} else {
			System.out.println("The input is invalid.");
		}
	
		// Creating an object of this class so that the getPDF function can be called and executed.
		FormNumberExtractor obj = new FormNumberExtractor();
		obj.getPDF();
	
		
	}
	
	
	// The purpose of this method is to search for the file containing the given form ID number.
	public void getPDF() throws IOException {
		
		
		try {
			
			// This variable holds the summary file of each sub folder from the root directory which contains the form ID number, where the given form ID number will be searched against.
			String locationOfFile = this.path + "//" + "FORMS_CTRLFUNC_RPT.DAT";
			
			// Since the program is supposed to return the folder in which the PDF storing the form ID number is stored, this variable holds the path of that folder.
			String locatioOfFolder = this.path;
			
			// Passing the location of the summary file as input to be read in by the program.
			BufferedReader br = new BufferedReader (new FileReader(locationOfFile));
			
			// Will read the first line of the summary file.
			String line = br.readLine();
			
			// Will continue to read all lines of the summary file until there's none left.
			while (line != null) {
			
				// Will split each line by the "|" character in the summary file.
				StringTokenizer split = new StringTokenizer(line, "|");
				
				// While there are still more word/characters available in the line that was broken up into pieces, the broken up word or character of the line will be stored in the variable "next."
				while (split.hasMoreTokens()) {
					String next = split.nextToken();	

					// This will check to see if the broken up word/character is equal to the form ID number.
					if (next.equals(formIDNumber)) {
						
						// If the specific form ID number is found in the file, the location of the directory in which it was found is returned.
						System.out.println("The folder in which the file resides is: " + locatioOfFolder);
						
						// Now all the user has to do is copy that folder name and search for it in finder/file explorer and retrieve the PDF file to send to the employee who asked for it.
					} 
				
			} 
				// Will get the next line in the file.
				line = br.readLine();
			}
			
			// The input stream is closed outside of the while loop, so no errors may occur.
			br.close();

			
	// When dealing with input and output, a number of errors may occur, so surrounding this block of code with a try-catch structure allows any exceptions to be handled accordingly.
	} catch (Exception e) {
		System.out.println("An exception is thrown of type : " + e);
	}
	
		
		
		
		
	}
	
	
	
	

}
