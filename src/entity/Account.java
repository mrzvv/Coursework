package entity;

import java.util.LinkedHashMap;

public class Account {
	
	private String accountName = "";
	private LinkedHashMap<String, Operation> accountOperations = new LinkedHashMap<String, Operation>();
	
	public Account() {
		this.setAccountName("");
	}
	
	public Account(String accountName, LinkedHashMap<String, Operation> accountOperations) {
		this.accountName = accountName;
		this.accountOperations = accountOperations;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public LinkedHashMap<String, Operation> getAccountOperations() {
		return accountOperations;
	}

	public void setAccountOperations(LinkedHashMap<String, Operation> accountOperations) {
		this.accountOperations = accountOperations;
	}
	
	public void insertIntoAccountOperations(String key, Operation value) {
		this.accountOperations.put(key, value);
	}
}
