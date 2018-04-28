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

public class ReturnAccountMoney {
	public static boolean returnMoney(Deposit deposit, Account clientCurrentAccount, Account bankDevelopmentFund,
			DBWorker db) {
		
		int operationIdInt = Bankwork.generateBankKey();
		String operationId = String.valueOf(operationIdInt);
		
		ResultSet db_data_bankFund = db.getDBData(
				"SELECT * FROM `bank_development_fund` where `Credit` is not null and `OperationDescription` LIKE '%средств на нужды банка (депозит "
						+ deposit.getContractId() + ")%'");
		
		double takenSum = 0;
		double accountSumWithFillings = Double.parseDouble(deposit.getSumWithFillings());
		
		try {
			while (db_data_bankFund.next()) {
				takenSum += Double.parseDouble(db_data_bankFund.getString("Credit"));
			}
		} catch (SQLException e1) {
			System.out.println(e1.toString());
		}

		String queryForBankFundDebit = "insert into `bank_development_fund` (`OperationId`, `OperationDescription`, `Debit`) values ('"+ operationIdInt +"', 'Возврат использованных банком средств клиента "
				+ deposit.getClientId() + " (депозит " + deposit.getContractId() + ")', '" + takenSum + "');";
		String queryForCurrentAccountCredit = "insert into `client_" + deposit.getClientId() + "_current_"
				+ deposit.getCurrency().toLowerCase()
				+ "_account` (`OperationId`, `OperationDescription`, `Credit`) values ('"+ operationIdInt +"', 'Возврат использованных банком средств (депозит "
				+ deposit.getContractId() + ")', '" + takenSum + "');";
		String queryForDepositAccounts = "update `deposit_accounts` set `SumWithFillings`='"
				+ String.valueOf(accountSumWithFillings + takenSum) + "' where `ContractId`=" + deposit.getContractId();

		try {
			java.sql.Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankwork", "root",
					"root");
			System.out.println("connected");
			Statement st = connection.createStatement();
			System.out.println("created");
			st.executeUpdate(queryForBankFundDebit);
			st.executeUpdate(queryForCurrentAccountCredit);
			st.executeUpdate(queryForDepositAccounts);
			System.out.println("executed");
			
			bankDevelopmentFund.getAccountOperations().put(operationId, new Operation(operationId, "Возврат использованных банком средств клиента "
				+ deposit.getClientId() + " (депозит " + deposit.getContractId(), String.valueOf(takenSum), " "));
			clientCurrentAccount.insertIntoAccountOperations(operationId, new Operation(operationId, "Возврат использованных банком средств (депозит "
				+ deposit.getContractId(), " ", String.valueOf(takenSum)));
			
			deposit.setSumWithFillings(String.valueOf(accountSumWithFillings + takenSum));
			System.out.println("put");
			return true;
		} catch (SQLException e) {
			System.out.println("[An error has occured under trying to return money for deposit id = "
					+ deposit.getContractId() + "]");
			System.out.println(e.toString());
		}
		return false;
	}
}
