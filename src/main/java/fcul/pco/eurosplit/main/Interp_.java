package fcul.pco.eurosplit.main;

import fcul.pco.eurosplit.domain.Expense;
import fcul.pco.eurosplit.domain.Split;
import fcul.pco.eurosplit.domain.User;
import fcul.pco.eurosplit.domain.Table;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author tl
 */
public class Interp_ {

    /**
     * Contains the string that is correspond to interpreter's prompt. It is
     * printed on the console. The prompt is updated by the setPrompt() method.
     */
    private String prompt;
    /**
     * The input of the interpreter
     */
    private final Scanner input;
    /**
     * Contains the current user (after user creation or after login).
     */
    private User currentUser;

    /**
     * Contains the current Split
     */
    private Split currentSplit;
    /**
     *
     * @param input
     */
    public Interp_(Scanner input) {
        prompt = ApplicationConfiguration.DEFAULT_PROMPT;
        this.input = input;
    }

    /**
     * Main interpreter command
     *
     * @param command
     * @param input
     * @throws IOException 
     */
    public void execute(String command, Scanner input) throws IOException {
        switch (command) {
            case "help":
                help();
                break;
            case "new user":
                makeNewUser(input);
                break;
            case "show users":
                showUsers();
                break;
            case "login":
                login(input);
                break;
            case "new split":
                makeNewSplit(input);
                break;
            case "select split":
                selectSplit(input);
                break;
            case "new expense":
                makeNewExpense(input);
                break;
            case "balance":
                printBalance();
                break;
            case "quit":
                quit();
                break;
            default:
                System.out.println("Unknown command. [" + command + "]");
        }
    }

    private void help() {
        System.out.println("help: show commands information.");
        System.out.println("new user: create a new account.");
        System.out.println("show users: show the list of registred users.");
        System.out.println("new split: create a new split.");
        System.out.println("select split: select a split.");
        System.out.println("new expense: add an expense to current split.");
        System.out.println("balance: print the balance of the current split.");
        System.out.println("login: log a user in.");
        System.out.println("quit: terminate the program.");
    }

    private void makeNewUser(Scanner input) throws IOException {
        System.out.print("User name: ");
        String username = input.nextLine();
        System.out.print("Email address: ");
    	String email = input.nextLine();
    	
    	if (Start.getUserCatalog().hasUserWithId(email)) {
    		System.out.println("A user with this email address already exists.");
    	}else{
    		currentUser = new User(email, username);
    		Start.getUserCatalog().addUser(currentUser);
    		setPrompt();   		
    	}
    }

    private void quit() {
        save();
    }

    private void showUsers() {
    	List<List<String>> usersList = new ArrayList<>();
    	try {
        	for (User u : Start.getUserCatalog().getAllUsers()) {
        		ArrayList<String> userData = new ArrayList<>();
        		for(String s : u.toString().split(", ")) {
        			// Format: [User name, User email]
        			userData.add(s);
        		}
        		// Format: [[User name, User email], [User name, User email], ...]
        		usersList.add(userData);
        	}  
        	System.out.println(Table.tableToString(usersList));
    	} catch (Exception e){
    		System.out.println("No users found.");
    	}
    }

    private void login(Scanner input) {
        
        System.out.print("Username: ");
        String username = input.nextLine();
    	
        ArrayList<User> allUsers = Start.getUserCatalog().getAllUsers();
    	Boolean check_user = false;

        for(User u : allUsers) {
        	if(u.getName().equalsIgnoreCase(username)) {
        		check_user = true;
        	}
        }
        
        if (check_user) {
            System.out.print("Email: ");
        	String email = input.nextLine();
        	if(Start.getUserCatalog().getUserById(email) != null) {
	        	if(Start.getUserCatalog().getUserById(email).getName().equalsIgnoreCase(username)) {
	        		currentUser = Start.getUserCatalog().getUserById(email);
	        		currentSplit = null;
	        		setPrompt();
	        	} else{
	        		System.out.println("This email doesn't match the user.");
	        	}
	        } else{
        		System.out.println("Email does not exist.");
        	}
    	} else{
    		System.out.println("User not found.");
    	}
    }

    private void makeNewSplit(Scanner input) {
    	if(currentUser == null) {
    		System.out.println("Must login first to use this method.");
    	}else {
        	System.out.println("For what event is this split? (i.e. «trip to Madrid», «house expenses», etc...) ");
        	String event = input.nextLine();
        	currentSplit = new Split(currentUser, event);
        	Start.getSplitCatalog().addSplit(currentUser, currentSplit);
        	setPrompt();
    	}

    }

    private void selectSplit(Scanner input) {
    	if(currentUser == null) {
    		System.out.println("Must login first to use this method.");
    	}else {
    		System.out.print("Name of split's owner? ");
    		User splitOwner = selectUser(input, input.nextLine());
    		try{
        		ArrayList<Split> splits_list = Start.getSplitCatalog().getUserSplits(splitOwner);
        		// Option counter
        		int opt = 1;
        		for(Split s : splits_list) {
    				System.out.println(opt++ + " " + s.getEvent());
        		}
        		chooseSplit(splits_list);
    		}catch (Exception e) {
    			System.out.println(splitOwner.getName() + " has have no split yet.");
    		}
    	}
    }
    
    private void chooseSplit(ArrayList<Split> splits_list) {
		try {
    		System.out.println("Select a split number: ");
    		int choice = Integer.parseInt(input.nextLine());
    		currentSplit = splits_list.get(choice-1); 
    		setPrompt();
		}catch(Exception e) {
			System.err.println("Invalid Option");
		}
    }

    private void printBalance() {
    	if(currentUser == null) {
    		System.out.println("Must login first to use this method.");
    	}else if(currentSplit == null) {
    		System.out.println("Must select a split first to use this method.");
    	}else {
    		try {
            	Map<User, Integer> balance = currentSplit.getbalance();
            	List<List<String>> balanceList = new ArrayList<>();

            	for(User u : balance.keySet()) {
            		List<String> list = new ArrayList<>();
            		list.add(u.getName());
            		list.add(balance.get(u).toString());
            		balanceList.add(list);
            	}
            	System.out.println(Table.tableToString(balanceList));    			
    		} catch(Exception e) {
    			System.err.println("Error calculating this split's balance.");
    		}

    	}

    }

    private void save() {
        try {
            Start.getUserCatalog().save();
        } catch (IOException ex) {
            System.err.println("Error saving User Catalog.");
        }
        try {
            Start.getExpenseCatalog().save();
        } catch (IOException ex) {
            System.err.println("Error saving Expense Catalog.");
        }
        try {
        	Start.getSplitCatalog().save();
        } catch (IOException ex) {
        	System.err.println("Error saving Split Catalog");
        }
    }

    private void makeNewExpense(Scanner input) {
    	if(currentUser == null) {
    		System.out.println("Must login first to use this method.");
    	}else if(currentSplit == null) {
    		System.out.println("Must select a split first to use this method.");
    	}else {
    		System.out.print("Expense made by you "+"("+currentUser.getName()+") What did you pay for? ");
    		String infoExpense = input.nextLine();
    		System.out.print("How much did you pay? ");
    		int cost = Integer.parseInt(input.nextLine());
    		Expense e = new Expense(infoExpense, cost, currentUser);
    		Boolean flag = true;
    		
    		while (flag == true) {
    			System.out.print("Who did you pay for: («no one» to terminate) ");
    			String paidFor = input.nextLine();
    			if(!paidFor.equals("no one")) {
    				e.addPaidFor(selectUser(input, paidFor));
    			}else {
    				flag = false;
    			}
    		}
    		
    		Start.getExpenseCatalog().addExpense(e);
    		currentSplit.addExpense(e);
    	}
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt() {
        if (currentUser == null) {
            this.prompt = ApplicationConfiguration.DEFAULT_PROMPT;
        }
        else if (currentSplit == null) {
            this.prompt = currentUser.getName();
        }
        else {
            this.prompt = currentUser.getName() + "." + currentSplit.getEvent();
        }
     
    }

    String nextToken() {
        String in;
        System.out.print(prompt + "> ");
        System.out.flush();
        if (input.hasNextLine()) {
            in = input.nextLine();
            return in;
        } else {
            return "";
        }

    }

    /**
     * This method may be used to find a user in the catalog given its name, for 
     * example when we want to add "paidFor" users to an expense. The method 
     * receives a name. If there is only one user with this name, return that 
     * user. If there is no user with that name, give the opportunity to create 
     * a new user. The several users (with same name) are found, show the list 
     * and ask which one should be used.
     *
     * @param input
     * @param name
     * @return
     */
    private User selectUser(Scanner input, String name) {
    	
        List<User> list = Start.getUserCatalog().getUsersWithName(name);
        if(list.size() == 1) return list.get(0);
        int k;
        
        if (!list.isEmpty()) {
        	for(int i = 0; i < list.size(); i++) {
        		System.out.println(i + " " + list.get(i));
        	}
        	System.out.print("Select a user: ");
        	k = Integer.parseInt(input.nextLine());
        }else {
        	System.out.println("User not found.");
        	System.out.print("Name: ");
        	name = input.nextLine();
        	return selectUser(input, name);
        }
        return list.get(k);
    }

/*    private boolean askYNQuestion(Scanner input, String question) {
        System.out.print(question + "? (y/n):");
        String answer = input.nextLine();
        while (!(answer.equalsIgnoreCase("Y")
                || answer.equalsIgnoreCase("N"))) {
            System.out.print(question + "? (y/n):");
            answer = input.nextLine();
        }
        return answer.equalsIgnoreCase("Y");
    }*/
}