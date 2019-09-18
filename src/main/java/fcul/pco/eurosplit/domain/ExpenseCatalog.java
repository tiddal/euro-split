package fcul.pco.eurosplit.domain;

import java.io.IOException;
import java.util.Map;

/**
 * 
 * @author fc45764, fc49049
 *
 */

public class ExpenseCatalog {
	private Map<Integer, Expense> expenseCatalog;
	static ExpenseCatalog instance;
	
	/**
	 * This constructed method uses the Singleton pattern so it's empty because the Expense Catalog only allows one instance to be created.
	 * Also, this method is private to avoid other classes to use this constructor. 
	 * @ensures a single Expense Catalog instance.
	 */
	private ExpenseCatalog() {}
	
	/**
	 * This method is used to get the ExpenseCatalog instance, if there's none it creates one and returns it.
	 * @return an ExpenseCatalog instance.
	 */
	public static ExpenseCatalog getInstance() {
		if(instance == null) {
			instance = new ExpenseCatalog();
		}
		return instance;
	}
	
	/**
	 * This method is used to add an Expense to the Expense Catalog.
	 * @param e is an Expense.
	 */
	public void addExpense(Expense e) {
		expenseCatalog.put(e.getid(), e);
	}
	
	/**
	 * This method is used to get an Expense by its id.
	 * @param id is an Integer.
	 * @return an Expense.
	 */
	public Expense getExpenseById(int id) {
		return expenseCatalog.get(id);
	}
	
	/**
	 * This method checks if there's an Expense with the given id in the catalog. 
	 * It returns true if there is and false if there isn't.
	 * @param id is an Integer.
	 * @return a Boolean value (true or false). 
	 */
	public boolean hasExpenseWithId(int id) {
		if(expenseCatalog.get(id) == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * This method saves the Expense Catalog to a file.
	 * @throws IOException if there's an error saving the data to the file.
	 * @see fcul.pco.eurosplit.persistence.ExpenseCatalog.save().
	 */
	public void save() throws IOException {
		fcul.pco.eurosplit.persistence.ExpenseCatalog.save(expenseCatalog);
	}
	
	/**
	 * This method loads the Expense Catalog from a file.
	 * @throws IOException if there's an error loading the data to the file
	 * @see fcul.pco.eurosplit.persistence.ExpenseCatalog.load()
	 */
	public void load() throws IOException {
		expenseCatalog = fcul.pco.eurosplit.persistence.ExpenseCatalog.load();
	}
	
}
