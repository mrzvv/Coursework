package client_actions;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import entity.Person;

public class AddPerson {
	public  static boolean add(Person person) {
		String query = "insert into `clients` (`PersonId`, `Name`, `Surname`, `Middlename`, `Sex`, `DateOfBirth`, `PassportSeries`,"
				+ "`PassportId`, `PassportPlaceOfIssue`, `PassportDateOfIssue`, `Citizenship`, `PlaceOfBirth`,"
				+ "`ActualCity`, `Address`, `RegistrationAddress`, `MobilePhone`, `HomePhone`, `E-mail`,"
				+ "`MonthIncome`, `MaritalStatus`, `Disability`) "
				+ "values ('"+ person.getId() +"', '"+ person.getName() +"',"
				+ "'"+ person.getSurname() +"', '"+ person.getMiddlename() +"', '"+ person.getSex() +"',"
				+ "'"+ person.getDateOfBirth() +"', '"+ person.getPassportSeries() +"',"
				+ "'"+ person.getPassportId() +"', '"+ person.getPassportPlaceOfIssue() +"',"
				+ "'"+ person.getPassportDateOfIssue() +"', '"+ person.getCitizenship() +"',"
				+ "'"+ person.getPlaceOfBirth() +"', '"+ person.getActualCity() +"',"
				+ "'"+ person.getAddress() +"', '"+ person.getRegistrationAddress() +"',"
				+ "'"+ person.getMobilePhone() +"', '"+ person.getHomePhone() +"',"
				+ "'"+ person.getEmail() +"', '"+ person.getMonthIncome() +"',"
				+ "'"+ person.getMaritalStatus() +"', '"+ person.getDisability() +"')";
			try {
				java.sql.Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankwork",
						"root", "root");
				Statement st = connection.createStatement();
				System.out.println("connected");
				st.executeUpdate(query);
				System.out.println("executed");
				return true;
			} catch (SQLException e) {
				System.out.println("[An error has occured under trying to add new person]");
			}
		return false;
	}
}
