package fcul.pco.eurosplit.persistence;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import fcul.pco.eurosplit.domain.Expense;
import fcul.pco.eurosplit.main.ApplicationConfiguration;

/**
 * 
 * @author fc45764, fc49049
 *
 */

public class ExpenseCatalog {
	private static String ECFILEPATH = ApplicationConfiguration.ROOT_DIRECTORY + ApplicationConfiguration.EXPENSES_CATALOG_FILENAME;

	/**
	 * This method creates a new file and deletes the old one to guarantee that the information is up to date and to avoid conflicts.
	 * This method is also used to save the Expense Catalog to the created file using the toString() method for each expense. 
	 * @see fcul.pco.eurosplit.main.ApplicationConfiguration to see the name of the file.
	 * @param expenses is a Map<Integer, Expense>.
	 * @throws IOException is there's an error saving the information to the file.
	 */
	public static void save(Map<Integer, Expense> expenses) throws IOException {
		File file = new File(ECFILEPATH);
		file.delete();
		BufferedWriter writer = new BufferedWriter(new FileWriter(ECFILEPATH, true));
		
		for(Expense e:expenses.values()) {
			writer.append(e.toString());
			writer.newLine();
		}
		
		writer.close();
	}
	
	/**
	 * This method checks if a file already exists, if it doesn't creates one.
	 * This method is also used to create a Map<Integer, Expense> which will be used to create the Expense Catalog.
	 * This method reads from the file and adds each expense to the Map using its fromString() method.
	 * @see fcul.pco.eurosplit.main.ApplicationConfiguration to see the name of the file.
	 * @return a Map<Integer, Expense>.
	 * @throws IOException is there's an error loading the file.
	 */
	public static Map<Integer, Expense> load() throws IOException{
		Map<Integer, Expense> catalog = new HashMap<>();
		
		File file = new File (ECFILEPATH);
		file.createNewFile();
		
		Scanner reader = new Scanner(new FileReader(file));
		
		while (reader.hasNextLine()) {
			Expense e = Expense.fromString(reader.nextLine());
			catalog.put(e.getid(), e);
		}
		reader.close();
		return catalog;
	}
}
