package fcul.pco.eurosplit.persistence;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import fcul.pco.eurosplit.domain.Split;
import fcul.pco.eurosplit.domain.User;
import fcul.pco.eurosplit.main.ApplicationConfiguration;

/**
 * 
 * @author fc45764, fc49049
 *
 */

public class SplitCatalog {
	private static String SCFILENAME = ApplicationConfiguration.ROOT_DIRECTORY + ApplicationConfiguration.SPLIT_CATALOG_FILENAME;
	
	/**
	 * This method creates a new file and deletes the old one to guarantee that the information is up to date and to avoid conflicts.
	 * This method is also used to save the Split Catalog to the created file using the toString() method for each split. 
	 * @see fcul.pco.eurosplit.main.ApplicationConfiguration to see the name of the file.
	 * @param splits is a Map<User, List<Split>>.
	 * @throws IOException is there's an error saving the information to the file.
	 */	
	public static void save(Map<User, List<Split>> splits) throws IOException {
		File file = new File(SCFILENAME);
		file.delete();
		BufferedWriter writer = new BufferedWriter(new FileWriter(SCFILENAME));
	
		for(List<Split> list : splits.values()) {
			for(Split s : list) {
				writer.append(s.toString());
				writer.newLine();
			}
		}
		writer.close();
	}
	
	/**
	 * This method checks if a file already exists, if it doesn't creates one.
	 * This method is also used to create a Map<User, List<Split>> which will be used to create the Split Catalog.
	 * This method reads from the file and adds each split to the Map using its fromString() method.
	 * @see fcul.pco.eurosplit.main.ApplicationConfiguration to see the name of the file.
	 * @return Map<User, List<Split>>.
	 * @throws IOException is there's an error loading the file.
	 */
	public static Map<User, List<Split>> load() throws IOException{
		Map<User, List<Split>> catalog = new HashMap<>();

		File file = new File(SCFILENAME);
		file.createNewFile();
		
		Scanner reader = new Scanner(new FileReader(file));
		
		while (reader.hasNextLine()) {
			Split s = Split.fromString(reader.nextLine());
			User owner = s.getOwner();
			
			if(catalog.containsKey(owner)) {
				catalog.get(owner).add(s);
			}else {
				List<Split> list = new ArrayList<>();
				catalog.put(owner, list);
				catalog.get(owner).add(s);
			}
		}
		reader.close();
		return catalog;
	}
}
