package fcul.pco.eurosplit.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import fcul.pco.eurosplit.main.Start;

/**
 * 
 * @author fc45764, fc49049
 *
 */

public class Split {
	private static int SPLIT_ID_COUNTER = 0;
	private int id;
	private User owner;
	private String event;
	private final ArrayList<Expense> expenses;
	
	/**
	 * This constructor method creates a Split instance, must be given the right parameters.
	 * @param owner, an instance of User that represents which user created the split.
	 * @param event, a String that represents the name of the split.
	 * @ensures a new Split instance.
	 */
	public Split(User owner, String event) {
		id = SPLIT_ID_COUNTER++;
		this.owner = owner;
		this.event = event;
		expenses = new ArrayList<Expense>();
	}
	
	/**
	 * This secondary constructor is used to create a Split instance that was read from a data source, like a file.
	 * @param id, an Integer value
	 * @param owner, an instance of User
	 * @param event, a String
	 * @see the first constructor.
	 */
	public Split(int id, User owner, String event) {
		this.id = id;
		this.owner = owner;
		this.event = event;
		expenses = new ArrayList<Expense>();
	}
	
	/**
	 * Sets the event name to a given value. This method is never used.
	 * @param event, a String.
	 */
	void setEvent(String event) {
		this.event = event;
	}
	
	/**
	 * This method is used to get the owner of the Split.
	 * @return an User instance, owner
	 */
	public User getOwner() {
		return owner;
	}
	
	/**
	 * This method is used to get the event name of the Split.
	 * @return a String, event.
	 */
	public String getEvent() {
		return event;
	}
	
	/**
	 * Adds an expense to the split.
	 * @param e, an expense instance. 
	 */
	public void addExpense(Expense e) {
		expenses.add(e);
	}
	
	/**
	 * This method calculates how much money each user paid or has to pay in this split.
	 * @return a Map where the keys are users and the values are the balance of each user.
	 */
	public Map<User, Integer> getbalance() {
    	Map<User, Integer> balanceMap = new HashMap<>();
		
		for(Expense e : expenses) {
			// Cost of the expense
			int value = e.getvalue();
			// The number of people the expense was paid for
			int paidFor = e.getpaidFor().size();
			// The user who paid for the expense
			User paidBy = e.getpaidBy();
			// The remainder of the expense division.
			int remainder = value%paidFor;
			// The value each user has to pay.
    		int dividedValue = Math.floorDiv(value, paidFor);
    		
    		if(balanceMap.containsKey(paidBy)){
    			balanceMap.replace(paidBy, balanceMap.get(paidBy) + value);
    		}else {
    			balanceMap.put(paidBy, value);
    		}
    		
    		for(User u : e.getpaidFor()) {
    			if(balanceMap.containsKey(u)) {
    				balanceMap.replace(u, balanceMap.get(u) - dividedValue);
    			} else{
    				balanceMap.put(u, -dividedValue);
    			}
    		}
    		
    		// New random object
    		Random rn = new Random();
    		// New Users List with all the users who paid for the expense
    		ArrayList<User> usersList = new ArrayList<>();
    		usersList.addAll(e.getpaidFor());
    		
    		while (remainder > 0){
    			// Random n between 0 and the size of the users list created
    			int n = rn.nextInt(usersList.size() - 0);
    			// Selects a user based on the random number generated
    			User u = usersList.get(n);
    			// Removes the user from the list so he doesn't pay twice more remainder than the others
    			usersList.remove(u);
    			balanceMap.replace(u, balanceMap.get(u)-1);
    			remainder--;
    			if(usersList.size() == 0) {
    				// If there's still remainder to pay, puts all the users that paid for the expense back in the list crated
    				usersList.addAll(e.getpaidFor());
    			}
    		}	
    	}
		return balanceMap;
    }
	
	
	/**
	 * This method overrides the default toString() method from Javadoc.
	 * @return a String in the format: "id#owner's email#event name#expense id:expense id: expense id:..."
	 */
	@Override
	public String toString() {
		String s = id + "#" + owner.getEmail() + "#" + event + "#";
		
		for(Expense e : expenses) {
			s += e.getid()+":";
		}
		
		return s;
	}
	
	/**
	 * This method takes a String in the format: "id#owner's email#event name#expense id:expense id: expense id:..."
	 * @param data is a String.
	 * @return a new Split instance.
	 */
	public static Split fromString(String data) {
		// Split ID
		int id = Integer.parseInt(data.split("#")[0]);
		// Split Owner
		User owner = Start.getUserCatalog().getUserById(data.split("#")[1]);
		// Split event
		String event = data.split("#")[2];
		
		Split split = new Split(id, owner, event);
		SPLIT_ID_COUNTER = ++id;
		
		if (data.split("#").length == 3) {
			return split;
		}
		
		// Split expenses
		for (String s : data.split("#")[3].split(":")) {
			split.addExpense(Start.getExpenseCatalog().getExpenseById(Integer.parseInt(s)));
		}
		return split;
	}
}
