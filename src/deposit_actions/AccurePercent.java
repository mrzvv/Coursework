package deposit_actions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import entity.Account;
import entity.Bankwork;
import entity.Deposit;
import entity.Operation;

public class AccurePercent {
	public static boolean accure(Deposit deposit, Account bankFundAccount, Account clientPercentAccount) {

		int operationIdInt = Bankwork.generateBankKey();
		String operationId = String.valueOf(operationIdInt);

		double sum = Double.parseDouble(deposit.getSumWithFillings());
		double percent = Deposit.splitPercent(deposit.getDepositPercent());
		double takeFromFund = 0;
		if (deposit.getCurrency().equals("BYN")) {
			takeFromFund = (sum * (percent / 100)) / 12;
		} else if (deposit.getCurrency().equals("USD")) {
			takeFromFund = (sum * (percent / 100) * 2.0) / 12;
		} else if (deposit.getCurrency().equals("EUR")) {
			takeFromFund = (sum * (percent / 100) * 2.45) / 12;
		}

		System.out.println(takeFromFund);

		double takeFromFundRounded = new BigDecimal(takeFromFund).setScale(2, RoundingMode.UP).doubleValue();
		System.out.println(takeFromFundRounded);

		double resultSum = sum + takeFromFundRounded;
		double resultSumRounded = new BigDecimal(resultSum).setScale(2, RoundingMode.UP).doubleValue();
		System.out.println(resultSumRounded);

		String queryForBankFundAccount = "insert into bankwork.bank_development_fund (`OperationId`, `OperationDescription`, `Debit`) values ('"
				+ operationIdInt + "', 'Начисление клиенту " + deposit.getClientId() + " процентов на депозит "
				+ deposit.getContractId() + "', '" + takeFromFundRounded + "');";
		String queryForPercentCurrentAccountFilling = "insert into `client_" + deposit.getClientId() + "_percent_"
				+ deposit.getCurrency().toLowerCase() + "_account`"
				+ "(`OperationId`, `OperationDescription`, `Credit`) values ('"+ operationIdInt +"', 'Начисление процентов по депозиту "
				+ deposit.getContractId() + "', " + "'" + takeFromFundRounded + "');";
		String queryForDepositAccounts = "update `deposit_accounts` set `SumWithFillings`='"
				+ String.valueOf(resultSumRounded) + "' where `ContractId`=" + deposit.getContractId();
		try {
			java.sql.Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankwork", "root",
					"root");
			System.out.println("connected");
			Statement st = connection.createStatement();
			System.out.println("created");
			st.executeUpdate(queryForBankFundAccount);
			st.executeUpdate(queryForPercentCurrentAccountFilling);
			st.executeUpdate(queryForDepositAccounts);
			System.out.println("executed");
			
			deposit.setSumWithFillings(String.valueOf(resultSumRounded));
			
			bankFundAccount.getAccountOperations().put(operationId, new Operation(operationId,
					"Начисление клиенту " + deposit.getClientId() + " процентов на депозит " + deposit.getContractId(),
					String.valueOf(takeFromFundRounded), " "));
			
			clientPercentAccount.insertIntoAccountOperations(operationId, new Operation(operationId,
					"Начисление процентов по депозиту " + deposit.getContractId(), " ", String.valueOf(takeFromFundRounded)));
			
			System.out.println("put");
			return true;
		} catch (SQLException e) {
			System.out.println(
					"[An error has occured under trying to update deposit id = " + deposit.getContractId() + "]");
			System.out.println(e.toString());
		}
		return false;
	}
}
