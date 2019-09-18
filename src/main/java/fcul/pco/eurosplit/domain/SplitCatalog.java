package fcul.pco.eurosplit.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author fc45764, fc49049
 *
 */

public class SplitCatalog {
	private Map<User, List<Split>> splitCatalog;
	static SplitCatalog instance;
	
	/**
	 * This constructed method uses the Singleton pattern so it's empty because the Split Catalog only allows one instance to be created.
	 * Also, this method is private to avoid other classes to use this constructor. 
	 * @ensures a single Expense Catalog instance.
	 */
	private SplitCatalog() {}
	
	/**
	 * This method is used to get the SplitCatalog instance, if there's none it creates one and returns it.
	 * @return an SplitCatalog instance.
	 */
	public static SplitCatalog getInstance() {
		if(instance == null) {
			instance = new SplitCatalog();
		}
		return instance;
	}
	
	/**
	 * This method is used to get all the splits form a given User.
	 * @param u is an User.
	 * @return an ArrayList with all the splits from a user.
	 */
	public ArrayList<Split> getUserSplits(User u){
		return new ArrayList<Split>(instance.splitCatalog.get(u));
	}
	
	/**
	 * This method is used to add a given User to a given Split.
	 * @param u is an User.
	 * @param s is a Split.
	 */
	public void addSplit(User u, Split s) {
		if(instance.splitCatalog.containsKey(u)) {
			instance.splitCatalog.get(u).add(s);
		} else{
			List<Split> list = new ArrayList<>();
			instance.splitCatalog.put(u, list);
			instance.splitCatalog.get(u).add(s);
		}
	}
	
	/**
	 * This method saves the Split Catalog to a file.
	 * @throws IOException if there's an error saving the data to the file.
	 * @see fcul.pco.eurosplit.persistence.SplitCatalog.save().
	 */
	public void save() throws IOException {
		fcul.pco.eurosplit.persistence.SplitCatalog.save(splitCatalog);
	}
	
	/**
	 * This method loads the Split Catalog from a file.
	 * @throws IOException if there's an error loading the data to the file
	 * @see fcul.pco.eurosplit.persistence.SplitCatalog.load()
	 */	
	public void load() throws IOException {
		splitCatalog = fcul.pco.eurosplit.persistence.SplitCatalog.load();
	}

}
