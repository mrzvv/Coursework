package entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;

import util.DBWorker;

public class Person {

	// Данные записи о человеке.
	private String id = "";
	private String name = "";
	private String surname = "";
	private String middlename = "";
	private String dateOfBirth = "";
	private String sex = "";
	private String passportSeries = "";
	private String passportId = "";
	private String passportPlaceOfIssue = "";
	private String passportDateOfIssue = "";
	private String placeOfBirth = "";
	private String actualCity = "";
	private String address = "";
	private String mobilePhone = "";
	private String homePhone = "";
	private String email = "";
	private String registrationAddress = "";
	private String maritalStatus = "";
	private String citizenship = "";
	private String disability = "";
	private String monthIncome = "";
	private HashMap<String, String> deposites = new HashMap<String, String>();

	// Конструктор для создания записи о человеке на основе данных из БД.
	public Person(String id, String name, String surname, String middlename, String dateOfBirth, String sex,
			String passportSeries, String passportId, String passportPlaceOfIssue, String passportDateOfIssue,
			String placeOfBirth, String actualCity, String address, String mobilePhone, String homePhone, String email,
			String registrationAddress, String maritalStatus, String disability, String monthIncome,
			String citizenship) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.middlename = middlename;
		this.dateOfBirth = dateOfBirth;
		this.sex = sex;
		this.passportSeries = passportSeries;
		this.passportId = passportId;
		this.passportPlaceOfIssue = passportPlaceOfIssue;
		this.passportDateOfIssue = passportDateOfIssue;
		this.placeOfBirth = placeOfBirth;
		this.actualCity = actualCity;
		this.address = address;
		this.mobilePhone = mobilePhone;
		this.homePhone = homePhone;
		this.email = email;
		this.registrationAddress = registrationAddress;
		this.maritalStatus = maritalStatus;
		this.disability = disability;
		this.monthIncome = monthIncome;
		this.citizenship = citizenship;

		// Извлечение депозитов человека из БД.
		ResultSet db_data = DBWorker.getInstance().getDBData("SELECT * FROM `deposit_accounts` WHERE `ClientId`=" + id);

		try {
			// Если у человека нет депозитов, ResultSet будет == null.
			if (db_data != null) {
				while (db_data.next()) {
					this.deposites.put(db_data.getString("ContractId"), db_data.getString("DepositType"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Конструктор для создания пустой записи о человеке.
	public Person() {
		this.id = "0";
		this.name = "";
		this.surname = "";
		this.middlename = "";
		this.dateOfBirth = "";
		this.sex = "";
		this.passportSeries = "";
		this.passportId = "";
		this.passportPlaceOfIssue = "";
		this.passportDateOfIssue = "";
		this.placeOfBirth = "";
		this.actualCity = "";
		this.address = "";
		this.mobilePhone = "";
		this.homePhone = "";
		this.email = "";
		this.registrationAddress = "";
		this.maritalStatus = "";
		this.disability = "";
		this.monthIncome = "";
		this.citizenship = "";
	}

	public static int generateId() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 5; i++) {
			Random rand = new Random();
			int x = rand.nextInt(10);
			sb.append(x);
		}
		return Integer.parseInt(sb.toString());
	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getSurname() {
		return this.surname;
	}

	public String getDateOfBirth() {
		return this.dateOfBirth;
	}

	public String getSex() {
		return this.sex;
	}

	public String getPassportSeries() {
		return this.passportSeries;
	}

	public String getPassportId() {
		return this.passportId;
	}

	public String getPassportPlaceOfIssue() {
		return this.passportPlaceOfIssue;
	}

	public String getPassportDateOfIssue() {
		return this.passportDateOfIssue;
	}

	public String getPlaceOfBirth() {
		return this.placeOfBirth;
	}

	public String getActualCity() {
		return this.actualCity;
	}

	public String getAddress() {
		return this.address;
	}

	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public String getHomePhone() {
		return this.homePhone;
	}

	public String getEmail() {
		return this.email;
	}

	public String getRegistrationAddress() {
		return this.registrationAddress;
	}

	public String getMiddlename() {
		return this.middlename;
	}

	public String getMaritalStatus() {
		return this.maritalStatus;
	}

	public String getCitizenship() {
		return this.citizenship;
	}

	public String getDisability() {
		return this.disability;
	}

	public String getMonthIncome() {
		return this.monthIncome;
	}

	public HashMap<String, String> getDeposites() {
		return this.deposites;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setPassportSeries(String passportSeries) {
		this.passportSeries = passportSeries;
	}

	public void setPassportId(String passportId) {
		this.passportId = passportId;
	}

	public void setPassportDateOfIssue(String dateOfIssue) {
		this.passportDateOfIssue = dateOfIssue;
	}

	public void setPassportPlaceOfIssue(String placeOfIssue) {
		this.passportPlaceOfIssue = placeOfIssue;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setActualCity(String actualCity) {
		this.actualCity = actualCity;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public void setDisability(String disability) {
		this.disability = disability;
	}

	public void setMonthIncome(String monthIncome) {
		this.monthIncome = monthIncome;
	}

	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setRegistrationAddress(String registrationAddress) {
		this.registrationAddress = registrationAddress;
	}

	public void setPersonalData(Person person, String name, String surname, String middlename, String sex,
			String dateOfBirth, String mobilePhone, String homePhone, String address, String email,
			String actualCity, String placeOfBirth, String registrationAddress, String passportId,
			String passportSeries, String placeOfIssue, String dateOfIssue,
			String maritalStatus, String disability, String citizenship, String monthIncome) {
		person.setActualCity(actualCity);
		person.setAddress(address);
		person.setCitizenship(citizenship);
		person.setDateOfBirth(dateOfBirth);
		person.setDisability(disability);
		person.setEmail(email);
		person.setHomePhone(homePhone);
		person.setPassportId(passportId);
		person.setMaritalStatus(maritalStatus);
		person.setMiddlename(middlename);
		person.setMobilePhone(mobilePhone);
		person.setMonthIncome(monthIncome);
		person.setPassportDateOfIssue(dateOfIssue);
		person.setPassportPlaceOfIssue(placeOfIssue);
		person.setPassportSeries(passportSeries);
		person.setPlaceOfBirth(placeOfBirth);
		person.setName(name);
		person.setSurname(surname);
		person.setSex(sex); 
	}

	public void setDeposites(HashMap<String, String> deposites) {
		this.deposites = deposites;
	}
}
