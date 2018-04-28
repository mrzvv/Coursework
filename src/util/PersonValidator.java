package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entity.Person;

public class PersonValidator {
	public static String validate (Person person) {
		String error_message = "";
		if (person.getSurname().equals("")) {
			error_message += "Поле для ввода фамилии должно быть заполнено.";
		} else if (person.getName().equals("")) {
			error_message += "Поле для ввода имени должно быть заполнено.";
		} else if (person.getMiddlename().equals("")) {
			error_message += "Поле для ввода отчества должно быть заполнено.";
		} else if (person.getSex().equals("")) {
			error_message += "Необходимо указать пол клиента.";
		} else if (!validateDate(person.getDateOfBirth())) {
			error_message += "Пожалуйста, укажите дату рождения в формате [YYYY-MM-DD].";
		} else if (person.getPassportSeries().equals("")) {
			error_message += "Поле для ввода серии паспорта должно быть заполнено.";
		} else if (!validatePassportId(person.getPassportId())) {
			error_message += "Номер паспорта должен быть строкой от 6 до 10 символов и содержать только цифры.";
		} else if (person.getPassportPlaceOfIssue().equals("")) {
			error_message += "Поле для ввода места выдачи паспорта должно быть заполнено.";
		} else if (!validateDate(person.getPassportDateOfIssue())) {
			error_message += "Пожалуйста, укажите дату выдачи паспорта в формате [YYYY-MM-DD].";
		} else if (person.getCitizenship().equals("")) {
			error_message += "Необходимо указать гражданство клиента.";
		} else if (person.getPlaceOfBirth().equals("")) {
			error_message += "Поле для ввода места рождения должно быть заполнено.";
		} else if (person.getRegistrationAddress().equals("")) {
			error_message += "Поле для ввода адреса прописки должно быть заполнено.";
		} else if (person.getActualCity().equals("")) {
			error_message += "Необходимо указать город фактического проживания клиента.";
		} else if (person.getAddress().equals("")) {
			error_message += "Поле для ввода адреса проживания должно быть заполнено.";
		} else if (!validatePhone(person.getMobilePhone())) {
			error_message += "Мобильный телефон должен быть строкой от 2 до 25 символов из цифр и знаков [+], [-], [#].";
		} else if (!validatePhone(person.getHomePhone())) {
			error_message += "Домашний телефон должен быть строкой от 2 до 25 символов из цифр и знаков [+], [-], [#].";
		} else if (person.getMaritalStatus().equals("")) {
			error_message += "Необходимо указать семейное положение клиента.";
		} else if (person.getDisability().equals("")) {
			error_message += "Необходимо указать наличие инвалидности у клиента.";
		}
		return error_message;
	}

	public static boolean validatePhone(String number) {
		if (number.equals("")) {
			return true;
		}
		Matcher matcher = Pattern.compile("[\\d+#-]{2,25}").matcher(number);
		return matcher.matches();
	}
	
	public static boolean validatePassportId(String passportId) {
		Matcher matcher = Pattern.compile("[\\d]{6,10}").matcher(passportId);
		return matcher.matches();
	}
	
	public static boolean validateDate(String date) {
		Matcher matcher = Pattern.compile("\\d{4}-\\d{2}-\\d{2}").matcher(date);
		return matcher.matches();
	}
}
