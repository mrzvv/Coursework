package util;

import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import entity.Account;
import entity.Deposit;
import entity.Operation;
import entity.Person;

public class DBDataEjector {

	public static void ejectClients(LinkedHashMap<String, Person> persons, DBWorker db) {
		ResultSet db_data_person = db.getDBData("SELECT * FROM `clients` ORDER BY Surname");
		try {
			while (db_data_person.next()) {
				persons.put(db_data_person.getString("PersonId"), new Person(db_data_person.getString("PersonId"),
						db_data_person.getString("Name"), db_data_person.getString("Surname"),
						db_data_person.getString("Middlename"), db_data_person.getString("DateOfBirth"),
						db_data_person.getString("Sex"), db_data_person.getString("PassportSeries"),
						db_data_person.getString("PassportId"), db_data_person.getString("PassportPlaceOfIssue"),
						db_data_person.getString("PassportDateOfIssue"), db_data_person.getString("PlaceOfBirth"),
						db_data_person.getString("ActualCity"), db_data_person.getString("Address"),
						db_data_person.getString("MobilePhone"), db_data_person.getString("HomePhone"),
						db_data_person.getString("E-mail"), db_data_person.getString("RegistrationAddress"),
						db_data_person.getString("MaritalStatus"), db_data_person.getString("Disability"),
						db_data_person.getString("MonthIncome"), db_data_person.getString("Citizenship")));
			}
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
	}

	public static void ejectActiveDeposits(HashMap<String, Deposit> activeDeposits, DBWorker db) {
		ResultSet db_data_deposit = db.getDBData("SELECT * FROM `deposit_accounts` where `DepositStatus`='active';");
		try {
			while (db_data_deposit.next()) {
				activeDeposits.put(db_data_deposit.getString("ContractId"),
						new Deposit(db_data_deposit.getString("ContractId"), db_data_deposit.getString("ClientId"),
								db_data_deposit.getString("DepositType"), db_data_deposit.getString("Currency"),
								db_data_deposit.getString("StartDate"), db_data_deposit.getString("EndDate"),
								db_data_deposit.getString("Sum"), db_data_deposit.getString("SumWithFillings"),
								db_data_deposit.getString("DepositPercent"),
								db_data_deposit.getString("DepositStatus")));
			}
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
	}

	public static void ejectClosedDeposits(HashMap<String, Deposit> closedDeposits, DBWorker db) {
		ResultSet db_data_deposit_closed = db
				.getDBData("SELECT * FROM `deposit_accounts` where `DepositStatus`='closed';");
		try {
			while (db_data_deposit_closed.next()) {
				closedDeposits.put(db_data_deposit_closed.getString("ContractId"), new Deposit(
						db_data_deposit_closed.getString("ContractId"), db_data_deposit_closed.getString("ClientId"),
						db_data_deposit_closed.getString("DepositType"), db_data_deposit_closed.getString("Currency"),
						db_data_deposit_closed.getString("StartDate"), db_data_deposit_closed.getString("EndDate"),
						db_data_deposit_closed.getString("Sum"), db_data_deposit_closed.getString("SumWithFillings"),
						db_data_deposit_closed.getString("DepositPercent"),
						db_data_deposit_closed.getString("DepositStatus"), db_data_deposit_closed.getString("ClosureDate")));
			}
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
	}

	public static Account ejectAccountOperations(String accountName, DBWorker db) {
		LinkedHashMap<String, Operation> accountOperations = new LinkedHashMap<String, Operation>();

		ResultSet db_data_account = db.getDBData("SELECT * FROM `" + accountName + "`;");
		
		try {
			java.sql.Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankwork", "root",
					"root");
			DatabaseMetaData dbm = connection.getMetaData();
			ResultSet tables = dbm.getTables(null, null, accountName, null);
			if (tables.next()) {
			while (db_data_account.next()) {
				if (db_data_account.getString("Debit") == null || db_data_account.getString("Debit").equals("")) {
					accountOperations.put(db_data_account.getString("OperationId"),
							new Operation(db_data_account.getString("OperationId"),
									db_data_account.getString("OperationDescription"), " ",
									db_data_account.getString("Credit")));
				} else if (db_data_account.getString("Credit") == null || db_data_account.getString("Credit").equals("")) {
					accountOperations.put(db_data_account.getString("OperationId"),
							new Operation(db_data_account.getString("OperationId"),
									db_data_account.getString("OperationDescription"),
									db_data_account.getString("Debit"), " "));
				} else {
					accountOperations.put(db_data_account.getString("OperationId"),
							new Operation(db_data_account.getString("OperationId"),
									db_data_account.getString("OperationDescription"),
									db_data_account.getString("Debit"), db_data_account.getString("Credit")));
				}
			}
			Account account = new Account(accountName, accountOperations);
			connection.close();
			return account;
			}
			if(!connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException | NullPointerException e) {
			System.out.println(e.toString());
		}
		return null;
	}

	public static HashMap<String, HashMap<String, Account>> ejectClientAccounts(
			HashMap<String, HashMap<String, Account>> clientAccounts, DBWorker db) {

		ArrayList<String> clientIds = new ArrayList<String>();
		
		ResultSet db_data_clientAccounts = db
				.getDBData("SELECT * FROM bankwork.deposit_accounts group by ClientId having count(*)>=1;");
		if (db_data_clientAccounts != null) {
			try {
				while (db_data_clientAccounts.next()) {
					clientIds.add(db_data_clientAccounts.getString("ClientId"));
				}

				LinkedHashMap<String, Account> particularClientAccounts = new LinkedHashMap<String, Account>();

				for (String id : clientIds) {
					if (ejectAccountOperations("client_" + id + "_current_byn_account", db) != null) {
						particularClientAccounts.put("Текущий счет в BYN",
								ejectAccountOperations("client_" + id + "_current_byn_account", db));
						clientAccounts.put(id, particularClientAccounts);
					}
					if (ejectAccountOperations("client_" + id + "_percent_byn_account", db) != null) {
						particularClientAccounts.put("Процентный счет в BYN",
								ejectAccountOperations("client_" + id + "_percent_byn_account", db));
						clientAccounts.put(id, particularClientAccounts);
					}
					if (ejectAccountOperations("client_" + id + "_current_usd_account", db) != null) {
						particularClientAccounts.put("Текущий счет в USD",
								ejectAccountOperations("client_" + id + "_current_usd_account", db));
						clientAccounts.put(id, particularClientAccounts);
					}

					if (ejectAccountOperations("client_" + id + "_percent_usd_account", db) != null) {
						particularClientAccounts.put("Процентный счет в USD",
								ejectAccountOperations("client_" + id + "_percent_usd_account", db));
						clientAccounts.put(id, particularClientAccounts);
					}
					if (ejectAccountOperations("client_" + id + "_current_eur_account", db) != null) {
						particularClientAccounts.put("Текущий счет в EUR",
								ejectAccountOperations("client_" + id + "_current_eur_account", db));
						clientAccounts.put(id, particularClientAccounts);
					}
					
					if (ejectAccountOperations("client_" + id + "_percent_eur_account", db) != null) {
						particularClientAccounts.put("Процентный счет в EUR",
								ejectAccountOperations("client_" + id + "_percent_eur_account", db));
						clientAccounts.put(id, particularClientAccounts);
					}
				}
				return clientAccounts;
			} catch (SQLException e) {
				System.out.println(e.toString());
			}
		}
		return null;
	}
}
