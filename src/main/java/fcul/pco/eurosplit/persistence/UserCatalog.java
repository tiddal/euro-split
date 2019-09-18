package fcul.pco.eurosplit.persistence;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import fcul.pco.eurosplit.domain.User;
import fcul.pco.eurosplit.main.ApplicationConfiguration;

/**
 * 
 * @author fc45764, fc49049
 *
 */

public class UserCatalog {
	private static String UCFILEPATH = ApplicationConfiguration.ROOT_DIRECTORY + ApplicationConfiguration.USER_CATALOG_FILENAME;
	
	/**
	 * This method creates a new file and deletes the old one to guarantee that the information is up to date and to avoid conflicts.
	 * This method is also used to save the User Catalog to the created file using the toString() method for each user. 
	 * @see fcul.pco.eurosplit.main.ApplicationConfiguration to see the name of the file.
	 * @param users is a Map<String, User>.
	 * @throws IOException is there's an error saving the information to the file.
	 */	
	public static void save(Map<String, User> users) throws IOException {
		File file = new File(UCFILEPATH);
		file.delete();
		BufferedWriter writer = new BufferedWriter(new FileWriter(UCFILEPATH, true));
		
		for(User u:users.values()) {
			writer.append(u.toString());
			writer.newLine();
		}
		writer.close();
	}
	
	/**
	 * This method checks if a file already exists, if it doesn't creates one.
	 * This method is also used to create a Map<String, User> which will be used to create the Split Catalog.
	 * This method reads from the file and adds each user to the Map using its fromString() method.
	 * @see fcul.pco.eurosplit.main.ApplicationConfiguration to see the name of the file.
	 * @return Map<String, User>.
	 * @throws IOException is there's an error loading the file.
	 */
	public static Map<String, User> load() throws IOException{
		Map<String, User> catalog = new HashMap<>();
		
		File file = new File (UCFILEPATH);
		file.createNewFile();
		
		Scanner reader = new Scanner(new FileReader(file));
		
		while(reader.hasNextLine()) {
			User u = User.fromString(reader.nextLine());
			catalog.put(u.getEmail(), u);
		}
		reader.close();
		return catalog;
	}
}
