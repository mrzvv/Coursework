package deposit_actions;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import entity.Account;
import entity.Bankwork;
import entity.Deposit;
import entity.Operation;

public class RefillDeposit {
	public static boolean refill(Deposit deposit, String fillingSum, Account bankCash,
			Account clientCurrentAccount) {
		
		int operationId1Int = Bankwork.generateBankKey();
		String operationId1 = String.valueOf(operationId1Int);

		int operationId2Int = Bankwork.generateBankKey();
		String operationId2 = String.valueOf(operationId2Int);
		
		double sum = Double.parseDouble(deposit.getSumWithFillings());
		double sumWithFillings = sum + Double.parseDouble(fillingSum);
		
		String queryForBankCashFillingDebit = "insert into bankwork.bank_cash_" + deposit.getCurrency().toLowerCase()
				+ " (`OperationId`, `OperationDescription`, `Debit`) values ('"+ operationId1Int +"', 'Повторное внесение клиентом " + deposit.getClientId()
				+ " денег для депозита " + deposit.getContractId() + "', '" + fillingSum + "');";
		String queryForBankCashFillingCredit = "insert into bankwork.bank_cash_" + deposit.getCurrency().toLowerCase()
				+ " (`OperationId`, `OperationDescription`, `Credit`) values ('"+ operationId2Int +"', 'Перевод денег клиента " + deposit.getClientId()
				+ " на счет депозита " + deposit.getContractId() + "', '" + fillingSum + "');";
		String queryForClientCurrentAccountFilling = "insert into `client_" + deposit.getClientId() + "_current_"
				+ deposit.getCurrency().toLowerCase() + "_account`"
				+ "(`OperationId`, `OperationDescription`, `Credit`) values ('"+ operationId2Int +"', 'Повторное пополнение депозитного счета "
				+ deposit.getContractId() + "', " + "'" + fillingSum + "');";
		String queryForDepositAccounts = "update `deposit_accounts` set `SumWithFillings`='"
				+ String.valueOf(sumWithFillings) + "' where `ContractId`=" + deposit.getContractId();
		try {
			java.sql.Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankwork", "root",
					"root");
			System.out.println("connected");
			Statement st = connection.createStatement();
			System.out.println("created");
			st.executeUpdate(queryForBankCashFillingDebit);
			st.executeUpdate(queryForBankCashFillingCredit);
			st.executeUpdate(queryForClientCurrentAccountFilling);
			st.executeUpdate(queryForDepositAccounts);
			System.out.println("executed");
			
			bankCash.getAccountOperations().put(operationId1, new Operation(operationId1, "Повторное внесение клиентом "
					+ deposit.getClientId() + " денег для депозита " + deposit.getContractId(), fillingSum, " "));
			bankCash.getAccountOperations().put(operationId2, new Operation(operationId2,
					"Перевод денег клиента " + deposit.getClientId() + " на счет депозита " + deposit.getContractId(),
					" ", fillingSum));

			clientCurrentAccount.insertIntoAccountOperations(operationId2, new Operation(operationId2,
					"Повторное пополнение депозитного счета " + deposit.getContractId(), " ", fillingSum));
			
			deposit.setSumWithFillings(String.valueOf(sumWithFillings));

			System.out.println("put");
			return true;
		} catch (SQLException e) {
			System.out.println(
					"[An error has occured under trying to update deposit id = " + deposit.getContractId() + "]");
		}
		return false;
	}
}
