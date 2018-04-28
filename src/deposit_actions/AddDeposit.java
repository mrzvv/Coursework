package deposit_actions;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;

import entity.Account;
import entity.Bankwork;
import entity.Deposit;
import entity.Operation;

public class AddDeposit {
	public static boolean add(Deposit deposit, HashMap<String, HashMap<String, Account>> clientsAccounts,
			Account bankCash) {

		int operationId1Int = Bankwork.generateBankKey();
		String operationId1 = String.valueOf(operationId1Int);

		int operationId2Int = Bankwork.generateBankKey();
		String operationId2 = String.valueOf(operationId2Int);

		String queryForDepositAccounts = "insert into `deposit_accounts` (`ContractId`, `ClientId`, `DepositType`, `Currency`, `StartDate`, `EndDate`,"
				+ "`DepositPercent`, `Sum`, `SumWithFillings`, `DepositStatus`) " + "values ('"
				+ deposit.getContractId() + "'," + "'" + deposit.getClientId() + "', '" + deposit.getDepositType()
				+ "', '" + deposit.getCurrency() + "'," + "'" + deposit.getStartDate() + "', '" + deposit.getEndDate()
				+ "'," + "'" + deposit.getDepositPercent() + "', '" + deposit.getSum() + "', '" + deposit.getSum()
				+ "', 'active');";
		String queryForClientCurrentAccountCreation = "CREATE TABLE IF NOT EXISTS `client_" + deposit.getClientId()
				+ "_current_" + deposit.getCurrency().toLowerCase() + "_account` ( " + "`OperationId` int(11) NOT NULL,"
				+ "`OperationDescription` varchar(125) DEFAULT NULL," + "`Debit` varchar(45) DEFAULT NULL,"
				+ "`Credit` varchar(45) DEFAULT NULL," + "PRIMARY KEY (`OperationId`)"
				+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		String queryForClientPercentAccountCreation = "CREATE TABLE IF NOT EXISTS `client_" + deposit.getClientId()
				+ "_percent_" + deposit.getCurrency().toLowerCase() + "_account` ( " + "`OperationId` int(11) NOT NULL,"
				+ "`OperationDescription` varchar(125) DEFAULT NULL," + "`Debit` varchar(45) DEFAULT NULL,"
				+ "`Credit` varchar(45) DEFAULT NULL," + "PRIMARY KEY (`OperationId`)"
				+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		String queryForBankCashFillingDebit = "insert into bankwork.bank_cash_" + deposit.getCurrency().toLowerCase()
				+ " (`OperationId`, `OperationDescription`, `Debit`) values ('" + operationId1Int
				+ "', 'Первичное внесение клиентом " + deposit.getClientId() + " денег для депозита "
				+ deposit.getContractId() + "', '" + deposit.getSum() + "');";
		String queryForBankCashFillingCredit = "insert into bankwork.bank_cash_" + deposit.getCurrency().toLowerCase()
				+ " (`OperationId`, `OperationDescription`, `Credit`) values ('" + operationId2Int
				+ "', 'Перевод денег клиента " + deposit.getClientId() + " на счет депозита " + deposit.getContractId()
				+ "', '" + deposit.getSum() + "');";
		String queryForClientCurrentAccountFilling = "insert into `client_" + deposit.getClientId() + "_current_"
				+ deposit.getCurrency().toLowerCase() + "_account`"
				+ "(`OperationId`, `OperationDescription`, `Credit`) values ('" + operationId2Int
				+ "', 'Первичное пополнение депозитного счета " + deposit.getContractId() + "', " + "'"
				+ deposit.getSum() + "');";

		try {
			java.sql.Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankwork", "root",
					"root");
			Statement st = connection.createStatement();
			System.out.println("connected");
			st.executeUpdate(queryForDepositAccounts);
			System.out.println("добавление в базу депозитов");
			st.executeUpdate(queryForClientCurrentAccountCreation);
			System.out.println("создание текущего счёта клиента");
			st.executeUpdate(queryForClientPercentAccountCreation);
			System.out.println("создание процентного счёта клиента");
			st.executeUpdate(queryForBankCashFillingDebit);
			st.executeUpdate(queryForBankCashFillingCredit);
			System.out.println("проводки по кассе");
			st.executeUpdate(queryForClientCurrentAccountFilling);
			System.out.println("перевод денег из кассы на счёт клиента");

			bankCash.getAccountOperations().put(operationId1, new Operation(operationId1, "Первичное внесение клиентом "
					+ deposit.getClientId() + " денег для депозита " + deposit.getContractId(), deposit.getSum(), " "));
			bankCash.getAccountOperations().put(operationId2, new Operation(operationId1,
					"Перевод денег клиента " + deposit.getClientId() + " на счет депозита " + deposit.getContractId(),
					" ", deposit.getSum()));

			LinkedHashMap<String, Operation> currentAccountOperations = new LinkedHashMap<String, Operation>();
			currentAccountOperations.put(operationId2, new Operation(operationId2,
					"Первичное пополнение депозитного счета " + deposit.getContractId(), " ", deposit.getSum()));

			HashMap<String, Account> clientAccounts = new HashMap<String, Account>();
			clientAccounts.put("Текущий счет в " + deposit.getCurrency(),
					new Account("Текущий счет клиента " + deposit.getClientId() + " в " + deposit.getCurrency(),
							currentAccountOperations));
			clientAccounts.put("Процентный счет в " + deposit.getCurrency(), new Account());

			clientsAccounts.put(deposit.getClientId(), clientAccounts);
			System.out.println("put");
			return true;
		} catch (SQLException e) {
			System.out.println("[An error has occured under trying to add new deposit]");
			System.out.println(e.toString());
		}
		return false;
	}
}
