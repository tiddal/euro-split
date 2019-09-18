package fcul.pco.eurosplit.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author fc45764, fc49049
 *
 */

public class UserCatalog {
	private Map<String, User> userCatalog;
	static UserCatalog instance;
	
	/**
	 * This constructed method uses the Singleton pattern so it's empty because the User Catalog only allows one instance to be created.
	 * Also, this method is private to avoid other classes to use this constructor. 
	 * @ensures a single User Catalog instance.
	 */
	private UserCatalog() {}
	
	/**
	 * This method is used to get the UserCatalog instance, if there's none it creates one and returns it.
	 * @return an UserCatalog instance.
	 */
	public static UserCatalog getInstance() {
		if(instance == null) {
			instance = new UserCatalog();
		}
		return instance;
	}
	
	/**
	 * This method is used to add an User to the User Catalog.
	 * @param u is an User.
	 */
	public void addUser(User u) {
		userCatalog.put(u.getEmail(), u);
	}
	
	/**
	 * This method is used to get an User by its email.
	 * @param email is a String.
	 * @return an User.
	 */
	public User getUserById(String email) {
		return userCatalog.get(email);
	}
	
	/**
	 * This method is used to get a list of the users with a given name.
	 * @param name is a String
	 * @return a list with all the users with the given name.
	 */
	public List<User> getUsersWithName(String name) {
		List<User> l = new ArrayList<>();
		for (User u : userCatalog.values()) {
			if(u.getName().equalsIgnoreCase(name)) {
				l.add(u);
			}
		}
		return l;
	}
	
	/**
	 * This method is used to get a list with all the users in the catalog sorted alphabetically.
	 * @return a list with all the users in the catalog.
	 */
	public ArrayList<User> getAllUsers(){
		ArrayList<User> l = new ArrayList<>();
		l.addAll(userCatalog.values());	
		Collections.sort(l);
		return l;
	}
	
	/**
	 * This method checks if there's an User with the given email in the catalog. 
	 * It returns true if there is and false if there isn't.
	 * @param email is a String.
	 * @return a Boolean value (true or false). 
	 */	
	public boolean hasUserWithId(String email) {
		if(userCatalog.get(email) == null) {
			return false;
		}
		return true;
	}

	/**
	 * This method saves the User Catalog to a file.
	 * @throws IOException if there's an error saving the data to the file.
	 * @see fcul.pco.eurosplit.persistence.UserCatalog.save().
	 */
	public void save() throws IOException{
		fcul.pco.eurosplit.persistence.UserCatalog.save(userCatalog);
	}

	/**
	 * This method loads the User Catalog from a file.
	 * @throws IOException if there's an error loading the data to the file
	 * @see fcul.pco.eurosplit.persistence.UserCatalog.load()
	 */
	public void load() throws IOException{
		userCatalog = fcul.pco.eurosplit.persistence.UserCatalog.load();
	}

}
