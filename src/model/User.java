package model;

import state.IdleState;
import state.State;

//bingung pake abstract apa engga
public abstract class User {

	private String accountNumber;
	private String name;
	private String accountType;
	private long balance; 
	private State state;
	
	public User(String accountNumber, String name, String accountType, long balance) {
		this.accountNumber = accountNumber;
		this.name = name;
		this.accountType = accountType;
		this.balance = balance;
		
		this.state = new IdleState(this);
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

}