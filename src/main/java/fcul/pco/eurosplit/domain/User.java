package fcul.pco.eurosplit.domain;

/**
 * 
 * @author fc45764, fc49049
 *
 */

public class User implements Comparable<User>{
	
	private String email;
	private String name;
	
	/**
	 * This constructor method creates an User instance, must be given the right parameters.
	 * @param email is a String.
	 * @param name is a String.
	 */
	public User(String email, String name) {
		this.email = email;
		this.name = name;
	}
	
	
	/**
	 * This method is used to get the name of the user.
	 * @return name, a String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * This method is used to get the user's email.
	 * @return email, a String
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * This method overrides the default compareTo() method
	 * @return which name is first in alphabetical order
	 */
	@Override
	public int compareTo(User u) {
		return name.compareTo(u.name);
	}
	
	
	/**
	 * This method overrides the default toString() method from Javadoc.
	 * @return a String with the name and the email of the user. 
	 */
	@Override
	public String toString() {
		return name + ", " + email;
	}
	
	/**
	 * This method creates a new User instance with the given parameters.
	 * @param data is a String that contains the user name and the email.
	 * @return a new User instance.
	 */
	public static User fromString(String data) {
		// User name
		String name = data.split(", ")[0];
		// User email
		String email = data.split(", ")[1];
		
		return new User(email, name);
	}
	
}