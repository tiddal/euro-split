package fcul.pco.eurosplit.domain;

import java.util.ArrayList;
import fcul.pco.eurosplit.main.Start;

/**
 * 
 * @author fc45764, fc49049
 *
 */

public class Expense {
	private static int EXPENSE_ID_COUNTER = 0;
	private int id;
	private String label;
	private int value;
	private User paidBy;
	private Date date; 
	private ArrayList<User> paidFor;

	/**
	 * This constructed method creates an Expense instance, must be given the right parameters.
	 * @param label is a String that expresses some information about the expense.
	 * @param value is an Integer that expresses the cost of the expense. 
	 * @param paidBy is an User object that expresses the user that paid for the expense.
	 * @ensures a new Expense instance with an unique ID, a label, a value, 
	 * who paid for the expense, to whom was it paid and the date it was made.
	 */
	public Expense(String label, int value, User paidBy){
		this.id = EXPENSE_ID_COUNTER++;
		this.label = label;
		this.value = value;
		this.paidBy = paidBy;
		this.date = Date.now();
		this.paidFor = new ArrayList<User>();
	}
	
	/**
	 * This secondary constructor is used to create an Expense instance that was read from a data source, like a file.
	 * @param id is an Integer.
	 * @param label is a String.
	 * @param value is an Integer.
	 * @param paidBy is an User.
	 * @param date is a Date
	 * @see the first constructor.
	 */
	public Expense(int id, String label, int value, User paidBy, Date date) {
		this.id = id;
		this.label = label;
		this.value = value;
		this.paidBy = paidBy;
		this.date = date;
		this.paidFor = new ArrayList<User>();		
	}
	
	/**
	 * This method is used to get the id of the expense.
	 * @return an Integer id.
	 */
	public int getid() {
		return id;
	}
	
	/**
	 * This method is used to get the value (cost) of the expense.
	 * @return an Integer value.
	 */
	public int getvalue() {
		return value;
	}
	
	/**
	 * This method is used to get the users to whom the expense was paid.
	 * @return an ArrayList of users, paidFor.
	 */
	public ArrayList<User> getpaidFor() {
		return paidFor;
	}
	
	/** 
	 * This method is used to get the user who paid the expense.
	 * @return an User paidBy.
	 */
	public User getpaidBy() {
		return paidBy;
	}
	
	/**
	 * This method is used to add a user to the paidFor list.
	 * @param u is an User.
	 */
	public void addPaidFor(User u){
		paidFor.add(u);
	}

	/**
	 * This method overrides the default toString() method from Javadoc.
	 * @return a String in the format: �id - label, value, email, date " paid for: " email, email, ...�
	 */
	@Override
	public String toString(){
		String paidfor = "";
		for(User u:paidFor){
			paidfor += u.getEmail()+", ";
		}
		return id + " - " + label + ", " + value + ", " + paidBy.getEmail() + ", " + date + " paid for: "+ paidfor;
	}
	
	/**
	 * This method takes a String in the format: �id - label, value, email, date " paid for: " email, email, ...�
	 * @param data is a String.
	 * @return a new Expense instance.
	 */
	public static Expense fromString(String data) {
		// Expense ID
		Integer id = Integer.parseInt(data.split(" - ")[0]);
		// Expense label, value, user, paid by and date
		String[] expense = data.split(" - ")[1].split(" paid for: ")[0].split(", ");
		// Expense label
		String label = expense[0];
		// Expense value
		int value = Integer.parseInt(expense[1]);
		// User that paid the expense
		User paidBy = Start.getUserCatalog().getUserById(expense[2]);
		// Date of the expense
		Date date = Date.fromString(expense[3]);

		Expense e = new Expense(id, label, value, paidBy, date);
		EXPENSE_ID_COUNTER = ++id;

		if(data.split(" - ")[1].split(": ").length == 1) {
			return e;
		}
		
		// Users which the expense was paid for
		String[] paidFor = data.split(" - ")[1].split(" paid for: ")[1].split(", ");
		for (String s : paidFor) {
			User u = Start.getUserCatalog().getUserById(s);
			e.addPaidFor(u);
		}
		return e;
	}
}
