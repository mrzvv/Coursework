package util;

import javax.servlet.http.HttpServletRequest;

import entity.Deposit;

public class DepositValidator {
	public static String validate(Deposit deposit, HttpServletRequest request) {
		String error_message = "";
		double depositSum = 0;
		if (validateSum(request.getParameter("sum"))) {
			depositSum = Double.parseDouble(request.getParameter("sum"));
		}
		
		if (deposit.getDepositType().equals("")) {
			error_message += "Необходимо указать вид депозита.";
		} else if (deposit.getCurrency().equals("")) {
			error_message += "Необходимо указать валюту вклада.";
		} else if (!PersonValidator.validateDate(deposit.getStartDate())) {
			error_message += "Пожалуйста, укажите дату начала депозитной программы в формате [YYYY-MM-DD].";
		} else if (!Deposit.validateEndDate(deposit.getEndDate())) {
			error_message += "Пожалуйста, укажите дату окончания депозитной программы в формате [YYYY-MM-DD].";
		} else if (deposit.getSum().equals("")) {
			error_message += "Необходимо указать сумму вклада.";
		} else if (deposit.getDepositType().equals("Вклад до востребования")
				&& !deposit.getEndDate().equals("")) {
			error_message += "Вклад до востребования не может иметь дату окончания программы.";
		} else if (deposit.getDepositType().equals("Срочный вклад") && deposit.getEndDate().equals("")) {
			error_message += "Срочный вклад должен иметь дату окончания программы.";
		} else if (request.getParameter("sum").equals("")) {
			error_message += "Поле для ввода суммы должно быть заполнено.";
		} else if (!DepositValidator.validateSum(request.getParameter("sum"))
				&& !request.getParameter("sum").equals("")) {
			error_message += "Некорректный формат суммы.\n";
		} else if (deposit.getDepositType().equals("Вклад до востребования") && depositSum < 1) {
			error_message += "Сумма вклада по данному депозиту должна быть больше 1 у.е.";
		} else if (deposit.getDepositType().equals("Срочный вклад") && depositSum < 50) {
			error_message += "Сумма вклада по данному депозиту должна быть больше 50 у.е.";
		}
		return error_message;
	}
	
	public static boolean validateSum(String sum) {
		double control = 0;
		try {
			control = Double.parseDouble(sum);
			if (control <= 0) {
				return false;
			}
			return true;
		} catch(NumberFormatException e){
			return false;
		}
	}
}
