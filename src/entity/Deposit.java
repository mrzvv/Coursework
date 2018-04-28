package entity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Deposit {
	private String contractId = "";
	private String clientId = "";
	private String depositType = "";
	private String currency = "";
	private String startDate = "";
	private String endDate = "";
	private String sum = "";
	private String sumWithFillings = "";
	private String depositPercent = "";
	private String status = "";
	private String closureDate = "";
	
	//конструктор для извлечения данных о депозите из бд и для добавления в бд (тк ключ - не автоинкремент)
	public Deposit (String contractId, String clientId, String depositType, 
			String currency, String startDate, String endDate, String sum,
			String sumWithFillings,	String depositPercent, String status) {
		this.contractId = contractId;
		this.clientId = clientId;
		this.depositType = depositType;
		this.currency = currency;
		this.startDate = startDate;
		this.endDate = endDate;
		this.sum = sum;
		this.depositPercent = depositPercent;
		this.sumWithFillings = sumWithFillings;
		this.status = status;
	}
	
	//конструктор для извлечения данных о депозите из бд и для добавления в бд (тк ключ - не автоинкремент)
		public Deposit (String contractId, String clientId, String depositType, 
				String currency, String startDate, String endDate, String sum,
				String sumWithFillings,	String depositPercent, String status, String closureDate) {
			this.contractId = contractId;
			this.clientId = clientId;
			this.depositType = depositType;
			this.currency = currency;
			this.startDate = startDate;
			this.endDate = endDate;
			this.sum = sum;
			this.depositPercent = depositPercent;
			this.sumWithFillings = sumWithFillings;
			this.status = status;
			this.closureDate = closureDate;
		}
	
	//конструктор для создания пустой записи
	public Deposit() {
		this.contractId = "";
		this.clientId = "";
		this.depositType = "";
		this.currency = "";
		this.startDate = "";
		this.endDate = "";
		this.sum = "";
		this.sumWithFillings = "";
		this.depositPercent = "";
		this.status = "";
	}
	
	public static boolean validateEndDate(String date) {
		if (date.equals("")) {
			return true;
		}
		Matcher matcher = Pattern.compile("\\d{4}-\\d{2}-\\d{2}").matcher(date);
		return matcher.matches();
	}
	
	public static double splitPercent(String percentString) {
		String[] subStr;
	    String delimeter = "%"; // Разделитель
	    subStr = percentString.split(delimeter);
	    
		return Double.parseDouble(subStr[0]);
	}
	
	
	public String getContractId() {
		return this.contractId;
	}
	
	public String getClientId() {
		return this.clientId;
	}
	
	public String getDepositType() {
		return this.depositType;
	}
	
	public String getCurrency() {
		return this.currency;
	}
	
	public String getStartDate() {
		return this.startDate;
	}
	
	public String getEndDate() {
		return this.endDate;
	}
	
	public String getSum() {
		return this.sum;
	}
	
	public String getSumWithFillings() {
		return this.sumWithFillings;
	}
	
	public String getDepositPercent() {
		return this.depositPercent;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public String getClosureDate() {
		return this.closureDate;
	}
	
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	public void setDepositType(String depositType) {
		this.depositType = depositType;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public void setSum(String sum) {
		this.sum = sum;
	}
	
	public void setSumWithFillings(String sum) {
		this.sumWithFillings = sum;
	}
	
	public void setDepositPercent(String depositPercent) {
		this.depositPercent = depositPercent;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setClosureDate(String closureDate) {
		this.closureDate = closureDate;
	}
}
