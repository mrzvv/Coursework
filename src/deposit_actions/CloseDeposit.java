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

public class CloseDeposit {
	public static boolean close(Deposit deposit, String closureDate, Account bankCash, Account clientCurrentAccount,
			Account clientPercentAccount, DBWorker db) {

		int operationIdInt1 = Bankwork.generateBankKey();
		String operationId1 = String.valueOf(operationIdInt1);

		int operationIdInt2 = Bankwork.generateBankKey();
		String operationId2 = String.valueOf(operationIdInt2);

		int operationIdInt3 = Bankwork.generateBankKey();
		String operationId3 = String.valueOf(operationIdInt3);

		int operationIdInt4 = Bankwork.generateBankKey();
		String operationId4 = String.valueOf(operationIdInt4);

		int operationIdInt5 = Bankwork.generateBankKey();
		String operationId5 = String.valueOf(operationIdInt5);

		int operationIdInt6 = Bankwork.generateBankKey();
		String operationId6 = String.valueOf(operationIdInt6);

		ResultSet db_data_deposit = db.getDBData(
				"SELECT * FROM `client_" + deposit.getClientId() + "_current_" + deposit.getCurrency().toLowerCase()
						+ "_account` where `Credit` is not null and `OperationDescription` LIKE '%"
						+ deposit.getContractId() + "%'");
		double currentDepositSum = 0;
		try {
			while (db_data_deposit.next()) {
				currentDepositSum += Double.parseDouble(db_data_deposit.getString("Credit"));
			}
		} catch (SQLException e1) {
			System.out.println(e1.toString());
		}
		System.out.println(currentDepositSum);

		ResultSet db_data_deposit_percent = db.getDBData(
				"SELECT * FROM `client_" + deposit.getClientId() + "_percent_" + deposit.getCurrency().toLowerCase()
						+ "_account` where `Credit` is not null and `OperationDescription` LIKE '%"
						+ deposit.getContractId() + "%'");
		double percentDepositSum = 0;
		try {
			while (db_data_deposit_percent.next()) {
				percentDepositSum += Double.parseDouble(db_data_deposit_percent.getString("Credit"));
			}
		} catch (SQLException e1) {
			System.out.println(e1.toString());
		}
		System.out.println(percentDepositSum);

		String queryForClientCurrentAccount = "insert into `client_" + deposit.getClientId() + "_current_"
				+ deposit.getCurrency().toLowerCase() + "_account`"
				+ "(`OperationId`, `OperationDescription`, `Debit`) values ('" + operationIdInt1
				+ "', 'Закрытие депозита " + deposit.getContractId() + "', " + "'" + currentDepositSum + "');";

		String queryForBankCashDebitCurrent = "insert into bankwork.bank_cash_" + deposit.getCurrency().toLowerCase()
				+ " (`OperationId`,`OperationDescription`, `Debit`) values ('" + operationIdInt3
				+ "', 'Перевод денег с текущего счета клиента " + deposit.getClientId() + " в кассу (закрыте депозита "
				+ deposit.getContractId() + ")', '" + currentDepositSum + "');";		

		String queryForBankCashCreditCurrent = "insert into bankwork.bank_cash_" + deposit.getCurrency().toLowerCase()
				+ " (`OperationId`, `OperationDescription`, `Credit`) values ('"+ operationIdInt4 +"', 'Выдача денег из кассы клиенту "
				+ deposit.getClientId() + " по текущему счету (закрытие депозита " + deposit.getContractId() + ")', '"
				+ currentDepositSum + "');";		

		String queryForClientPercentAccount = "insert into `client_" + deposit.getClientId() + "_percent_"
				+ deposit.getCurrency().toLowerCase() + "_account`"
				+ "(`OperationId`, `OperationDescription`, `Debit`) values ('" + operationIdInt2
				+ "', 'Закрытие депозита " + deposit.getContractId() + "', " + "'" + percentDepositSum + "');";		

		String queryForBankCashDebitPercent = "insert into bankwork.bank_cash_" + deposit.getCurrency().toLowerCase()
				+ " (`OperationId`, `OperationDescription`, `Debit`) values ('" + operationIdInt5
				+ "', 'Перевод денег с процентного счета клиента " + deposit.getClientId()
				+ " в кассу (закрыте депозита " + deposit.getContractId() + ")', '" + percentDepositSum + "');";		

		String queryForBankCashCreditPercent = "insert into bankwork.bank_cash_" + deposit.getCurrency().toLowerCase()
				+ " (`OperationId`, `OperationDescription`, `Credit`) values ('" + operationIdInt6
				+ "', 'Выдача денег из кассы клиенту " + deposit.getClientId()
				+ " по процентному счету (закрытие депозита " + deposit.getContractId() + ")', '" + percentDepositSum
				+ "');";
		

		String queryForDepositAccountsStatus = "update `deposit_accounts` set `DepositStatus`='closed' where `ContractId`="
				+ deposit.getContractId();
		String queryForDepositAccountsClosureDate = "update `deposit_accounts` set `ClosureDate`='" + closureDate
				+ "' where `ContractId`=" + deposit.getContractId();

		try {
			java.sql.Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankwork", "root",
					"root");
			System.out.println("connected");
			Statement st = connection.createStatement();
			System.out.println("created");
			st.executeUpdate(queryForClientCurrentAccount);
			System.out.println("запрос для текущего счета");
			st.executeUpdate(queryForBankCashDebitCurrent);
			System.out.println("дебет кассы");
			st.executeUpdate(queryForBankCashCreditCurrent);
			System.out.println("кредит кассы");
			st.executeUpdate(queryForClientPercentAccount);
			System.out.println("запрос для процентного счета");
			st.executeUpdate(queryForBankCashDebitPercent);
			System.out.println("дебет кассы");
			st.executeUpdate(queryForBankCashCreditPercent);
			System.out.println("кредит кассы");
			st.executeUpdate(queryForDepositAccountsClosureDate);
			System.out.println("для даты закрытия");
			st.executeUpdate(queryForDepositAccountsStatus);
			System.out.println("executed");
			
			clientCurrentAccount.insertIntoAccountOperations(operationId1, new Operation(operationId1,
					"Закрытие депозита " + deposit.getContractId(), String.valueOf(currentDepositSum), " "));
			bankCash.getAccountOperations().put(operationId3,
					new Operation(operationId3, "Перевод денег с текущего счета клиента " + deposit.getClientId(),
							String.valueOf(currentDepositSum), " "));
			bankCash.getAccountOperations().put(operationId4,
					new Operation(operationId4,
							"Выдача денег из кассы клиенту " + deposit.getClientId()
									+ " по текущему счету (закрытие депозита " + deposit.getContractId(),
							" ", String.valueOf(currentDepositSum)));
			clientPercentAccount.insertIntoAccountOperations(operationId2, new Operation(operationId2,
					"Закрытие депозита " + deposit.getContractId(), String.valueOf(percentDepositSum), " "));
			bankCash.getAccountOperations().put(operationId5,
					new Operation(operationId5,
							"Перевод денег с процентного счета клиента " + deposit.getClientId()
									+ " в кассу (закрыте депозита " + deposit.getContractId(),
							String.valueOf(percentDepositSum), " "));
			bankCash.getAccountOperations().put(operationId6,
					new Operation(operationId6,
							"Выдача денег из кассы клиенту " + deposit.getClientId()
									+ " по процентному счету (закрытие депозита " + deposit.getContractId(),
							" ", String.valueOf(percentDepositSum)));			
			
			deposit.setStatus("closed");
			deposit.setClosureDate(closureDate);
			System.out.println("put");
			return true;
		} catch (SQLException e) {
			System.out.println(
					"[An error has occured under trying to close deposit id = " + deposit.getContractId() + "]");
			System.out.println(e.toString());
		}
		return false;
	}
}