package deposit_actions;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entity.Account;
import entity.Bankwork;
import entity.Deposit;
import entity.Operation;
import util.DBWorker;

public class TakeAwayAccountMoney {

	public static boolean take(Deposit deposit, String takeAwaySum, String accountType, Account bankCash,
			Account clientCurrentAccount, Account clientPercentAccount, Account bankDevelopmentFund,
			boolean forBankNeeds, DBWorker db) {

		int operationId1Int = Bankwork.generateBankKey();
		String operationId1 = String.valueOf(operationId1Int);

		int operationId2Int = Bankwork.generateBankKey();
		String operationId2 = String.valueOf(operationId2Int);

		ResultSet db_data_deposit = db.getDBData("SELECT * FROM `client_" + deposit.getClientId() + "_" + accountType
				+ "_" + deposit.getCurrency().toLowerCase()
				+ "_account` where `Credit` is not null and `OperationDescription` LIKE '%" + deposit.getContractId()
				+ "%'");
		double currentAccountSum = 0;
		double takeAwaySumDouble = Double.parseDouble(takeAwaySum);
		try {
			while (db_data_deposit.next()) {
				currentAccountSum += Double.parseDouble(db_data_deposit.getString("Credit"));
			}
		} catch (SQLException e1) {
			System.out.println(e1.toString());
		}
		System.out.println(currentAccountSum);

		if (takeAwaySumDouble > currentAccountSum) {
			return false;
		}

		double depositSumWithFillings = Double.parseDouble(deposit.getSumWithFillings());
		double remainingSumWithFillings = depositSumWithFillings - takeAwaySumDouble;

		String queryForBankCashDebit = "";
		String queryForBankCashCredit = "";
		String queryForClientCurrentAccount = "";
		String queryForBankDevelopmentFund = "";

		if (forBankNeeds == false) {
			queryForBankCashDebit = "insert into bankwork.bank_cash_" + deposit.getCurrency().toLowerCase()
					+ " (`OperationId`, `OperationDescription`, `Debit`) values ('" + operationId1Int
					+ "', 'Перевод денег с " + accountType + " счета клиента " + deposit.getClientId()
					+ " в кассу (депозит " + deposit.getContractId() + ")', '" + takeAwaySum + "');";
			queryForBankCashCredit = "insert into bankwork.bank_cash_" + deposit.getCurrency().toLowerCase()
					+ " (`OperationId`, `OperationDescription`, `Credit`) values ('" + operationId2Int
					+ "', 'Выдача денег из кассы клиенту " + deposit.getClientId() + " по депозиту "
					+ deposit.getContractId() + "', '" + takeAwaySum + "');";
			queryForClientCurrentAccount = "insert into `client_" + deposit.getClientId() + "_" + accountType + "_"
					+ deposit.getCurrency().toLowerCase() + "_account`"
					+ "(`OperationId`, `OperationDescription`, `Debit`) values ('" + operationId2Int
					+ "', 'Снятие средств со счета с последующей выдачей по депозиту " + deposit.getContractId() + "', "
					+ "'" + takeAwaySum + "');";
		} else {
			queryForBankDevelopmentFund = "insert into bankwork.bank_development_fund (`OperationId`, `OperationDescription`, `Credit`) values ('"
					+ operationId1Int + "', 'Перевод со счета клиента " + deposit.getClientId()
					+ " средств на нужды банка (депозит " + deposit.getContractId() + ")', '" + takeAwaySum + "');";
			queryForClientCurrentAccount = "insert into `client_" + deposit.getClientId() + "_" + accountType + "_"
					+ deposit.getCurrency().toLowerCase() + "_account`"
					+ "(`OperationId`, `OperationDescription`, `Debit`) values ('" + operationId1Int
					+ "', 'Изъятие средств со счета для нужд банка по депозиту " + deposit.getContractId() + "', " + "'"
					+ takeAwaySum + "');";
		}

		String queryForDepositAccounts = "update `deposit_accounts` set `SumWithFillings`='"
				+ String.valueOf(remainingSumWithFillings) + "' where `ContractId`=" + deposit.getContractId();

		try {
			java.sql.Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankwork", "root",
					"root");
			System.out.println("connected");
			Statement st = connection.createStatement();
			System.out.println("created");
			if (forBankNeeds) {
				st.executeUpdate(queryForBankDevelopmentFund);
				st.executeUpdate(queryForClientCurrentAccount);

				bankDevelopmentFund.getAccountOperations().put(operationId1,
						new Operation(operationId1,
								"Перевод со счета клиента " + deposit.getClientId()
										+ " средств на нужды банка (депозит " + deposit.getContractId(),
								" ", takeAwaySum));
				clientCurrentAccount.insertIntoAccountOperations(operationId1,
						new Operation(operationId1,
								"Изъятие средств со счета для нужд банка по депозиту " + deposit.getContractId(),
								takeAwaySum, " "));
			} else {
				st.executeUpdate(queryForBankCashDebit);
				st.executeUpdate(queryForBankCashCredit);
				st.executeUpdate(queryForClientCurrentAccount);

				if (accountType.equals("percent")) {
					bankCash.getAccountOperations().put(operationId1,
							new Operation(operationId1, "Перевод денег с процентного счета клиента "
									+ deposit.getClientId() + " в кассу (депозит " + deposit.getContractId(),
									takeAwaySum, " "));
					bankCash.getAccountOperations().put(operationId2,
							new Operation(operationId2, "Выдача денег из кассы клиенту " + deposit.getClientId()
									+ " по депозиту " + deposit.getContractId(), " ", takeAwaySum));
					clientPercentAccount.insertIntoAccountOperations(operationId2, new Operation(operationId2,
							"Снятие средств со счета с последующей выдачей по депозиту " + deposit.getContractId(),
							takeAwaySum, " "));
				} else if (accountType.equals("current")) {
					bankCash.getAccountOperations().put(operationId1,
							new Operation(operationId1, "Перевод денег с текущего счета клиента "
									+ deposit.getClientId() + " в кассу (депозит " + deposit.getContractId(),
									takeAwaySum, " "));
					bankCash.getAccountOperations().put(operationId2,
							new Operation(operationId2, "Выдача денег из кассы клиенту " + deposit.getClientId()
									+ " по депозиту " + deposit.getContractId(), " ", takeAwaySum));
					clientCurrentAccount.insertIntoAccountOperations(operationId2, new Operation(operationId2,
							"Снятие средств со счета с последующей выдачей по депозиту " + deposit.getContractId(),
							takeAwaySum, " "));
				}
			}
			st.executeUpdate(queryForDepositAccounts);
			System.out.println("executed");
			deposit.setSumWithFillings(String.valueOf(remainingSumWithFillings));
			System.out.println("put");
			return true;
		} catch (SQLException e) {
			System.out.println("[An error has occured under trying to take away money from deposit id = "
					+ deposit.getContractId() + "]");
			System.out.println(e.toString());
		}
		return false;
	}
}
