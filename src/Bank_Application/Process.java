package Bank_Application;

import java.util.ArrayList;
import java.util.Scanner;

import factory.PrioritasFactory;
import factory.RegulerFactory;
import model.Bank;
import model.User;
import proxy.BankProxy;
import singleton.Database;

public class Process {
	private Scanner scan = new Scanner(System.in);
	private ArrayList<User> users = Database.getInstance();
	private PrioritasFactory prioritasFactory = new PrioritasFactory();
	private RegulerFactory regulerFactory = new RegulerFactory();
	private Bank bank = new Bank("acb", "bumi");
	private BankProxy bankProxy = new BankProxy(bank);
	public User currentUser = null;

	private int count = 0;
	
	public void checkbalance() {
		bankProxy.checkBalance(currentUser);
	}
	
	public void deposit() {
		int deposit = 0;
		
		System.out.print("Enter the amount you want to deposit:: ");
		boolean success = false;
		try {
			deposit = scan.nextInt();
			success = true;
		} catch (Exception e) {
			success = false;
		} finally {
			scan.nextLine();
		}
		
		if(success && deposit > 0) {			
			bankProxy.deposit(currentUser, deposit);
		} else {
			System.out.println("Deposit failed");
		}
	}

	public void withdraw() {
		int withdraw = 0;
		
		System.out.print("Enter the amount you want to withdraw:: ");
		boolean success = false;
		try {
			withdraw = scan.nextInt();
			success = true;
		} catch (Exception e) {
			success = false;
		} finally {
			scan.nextLine();
		}
		
		if(success && withdraw > 0) {			
			bankProxy.withdraw(currentUser, withdraw);
		} else {
			System.out.println("Withdraw failed");
		}
	}

	public void transfer() {
		User receiver = null;
		long amount = 0;
		String receiverAccountNumber = "";
		
		System.out.print("Enter the receiver account number:: ");
		receiverAccountNumber = scan.nextLine();
		receiver = findUsersByAccountNumber(receiverAccountNumber);
		
		if(receiver == null) {
			System.out.println("Receiver account not found");
			return;
		}
		
		System.out.print("Enter the amount you want to transfer:: ");
		boolean success = false;
		try {
			amount = scan.nextLong();
			success = true;
		} catch (Exception e) {
			success = false;
		} finally {
			scan.nextLine();
		}
		
		if(success && amount > 0) {
			bankProxy.transfer(currentUser, amount, receiver);
		} else {
			System.out.println("Transfer failed");
		}
	}

	public void register() {
		System.out.println(
				" ----------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println(" ***Banking System Application***");
		System.out.println(
				" -----------------------------------------------------------------------------------------------------------------------------------------------------------------");
		String username = "";
		String type = "";

		do {
			System.out.print("Input your username: ");
			username = scan.nextLine();
		} while (username.length() == 0);

		do {
			System.out.print("Input your account type [Prioritas | Reguler]: ");
			type = scan.nextLine();
		} while (!type.equals("Prioritas") && !type.equals("Reguler"));

		String code = "AC" + String.format("%3d", count++);

		if (type.equals("Prioritas")) {
			users.add(prioritasFactory.createClient(code, username, type, 0));
		} else {
			users.add(regulerFactory.createClient(code, username, type, 0));
		}

		System.out.println();
		System.out.println("Register Successfull!");
	}

	public void login() {
		System.out.println(
				" ----------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println(" ***Banking System Application***");
		System.out.println(
				" -----------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.print("Enter your username: ");
		String username = scan.nextLine();

		User user = null;

		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getName().equals(username)) {
				user = users.get(i);
				break;
			}
		}

		System.out.println();

		if (user != null) {
			System.out.println("Login Successfull!");
			System.out.println();
			currentUser = user;
			Operation.bankinfo();
			return;
		}

		System.out.println("Login Failed!");
		scan.nextLine();
		return;
	}
	
	public User findUsersByAccountNumber(String number) {
		for (User user : users) {
			if(user.getAccountNumber().equals(number)) {
				return user;
			}
		}
		return null;
	}
}