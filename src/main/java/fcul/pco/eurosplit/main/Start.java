package fcul.pco.eurosplit.main;

import java.io.IOException;
import java.util.Scanner;
import fcul.pco.eurosplit.domain.UserCatalog;
import fcul.pco.eurosplit.domain.ExpenseCatalog;
import fcul.pco.eurosplit.domain.SplitCatalog;

/**
 * 
 * @author fc45764, fc49049
 *
 * Overall, the application is running fine with no errors.
 *  
 * We've implemented all the methods and we've handled some exceptions.
 * We are aware that the splits balance is never saved so the values can vary depending on the remaining of the division.
 * This remaining is distributed equally but randomly each time the user uses the "balance" command, which makes it funny because the user's balance can be different. 
 * 
 * We are also aware that the expense method "toString()" ends with a comma(, ) and the same method from the split class ends with a colon (:). 
 * We could have easily solved this "issue" but we chose to leave it like that to make the "fromString()" methods simpler as well as the "save()" and "load()" methods from the respective persistence classes.
 * 
 * We didn't know what to do with the method "askYNQuestion". It's never mentioned in any of the tasks so we commented it to avoid the warning "this method is never used.".
 * 
 * Finally, we add some comments to our code to make it easy to understand. All the files we've created are documented.
 * 
 */

public class Start {
	private static UserCatalog userCatalog = UserCatalog.getInstance();
	private static ExpenseCatalog expenseCatalog = ExpenseCatalog.getInstance();
	private static SplitCatalog splitCatalog = SplitCatalog.getInstance();

	/**
	 * This method is used to run the application.
	 * @param args
	 * @throws IOException if an error occurs.
	 */
	public static void main(String[] args) throws IOException {
		run();
	}
	
	/**
	 * This method is used to get the instance of the User Catalog.
	 * @return the UserCatalog.
	 */
	public static UserCatalog getUserCatalog() {
		return userCatalog;
	}

	/**
	 * This method is used to get the instance of the Expense Catalog.
	 * @return the ExpenseCatalog.
	 */
	public static ExpenseCatalog getExpenseCatalog() {
		return expenseCatalog;
	}
	
	/**
	 * This method is used to get the instance of the Split Catalog.
	 * @return the SplitCatalog.
	 */	
	public static SplitCatalog getSplitCatalog() {
		return splitCatalog;
	}
	
	/**
	 * This method is used to initialize each catalog instance.
	 * @throws IOException if an error occurs initializing a catalog.
	 */
	public static void initialize() throws IOException {
		try {
			userCatalog.load();
		}
		catch(IOException e) {
			System.err.println("Existem erros no cat�logo dos utilizadores.");
		}
		
		try {
			expenseCatalog.load();
		}
		catch(IOException e) {
			System.err.println("Existem erros no cat�logo das despesas.");
		}
		
		try {
			splitCatalog.load();
		}
		catch(IOException e) {
			System.err.println("Existem erros no cat�logo dos splits.");
		}
	}
	
	/**
	 * This method is called in the main method and is used to run the application.
	 * @throws IOException if an error occurs.
	 */
	private static void run() throws IOException {
		Scanner input = new Scanner(System.in);
		initialize();
		Interp_ interp = new Interp_(input);
		String command = "";
		
		do {
			command = interp.nextToken();
			interp.execute(command, input);
		} while (!command.equals("quit"));
	}
}

