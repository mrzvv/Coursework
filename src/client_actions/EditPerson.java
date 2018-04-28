package client_actions;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import entity.Person;

public class EditPerson {
	public static boolean edit(Person person) {
		Integer id_filtered = Integer.parseInt(person.getId());
		String query = "UPDATE `clients` SET `Name` = '" + person.getName() + "', `Surname` = '" + person.getSurname() + "',"
					+ "`Middlename` = '"+ person.getMiddlename() +"', `DateOfBirth` = '"+ person.getDateOfBirth() +"',"
					+ "`Sex` = '"+ person.getSex() +"', `PassportSeries` = '"+ person.getPassportSeries() +"',"
					+ "`PassportId` = '"+ person.getPassportId() +"', `PassportPlaceOfIssue` = '"+ person.getPassportPlaceOfIssue() +"',"
					+ "`Address` = '" + person.getAddress() + "', `MobilePhone` = '" + person.getMobilePhone() + "',"
					+ "`HomePhone` = '" + person.getHomePhone() + "', `E-mail` = '" + person.getEmail() + "',"
					+ "`RegistrationAddress` = '" + person.getRegistrationAddress() + "', "
					+ "`MonthIncome` = '" + person.getMonthIncome() + "', `PlaceOfBirth` = '" + person.getPlaceOfBirth() + "', "
					+ "`ActualCity` = '" + person.getActualCity() + "', `MaritalStatus` = '" + person.getMaritalStatus() + "',"
					+ "`Citizenship` = '" + person.getCitizenship() + "', `Disability` = '" + person.getDisability() + "' "
					+ "WHERE `PersonId` = " + id_filtered;
			try {
				java.sql.Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankwork",
						"root", "root");
				System.out.println("connected");
				Statement st = connection.createStatement();
				System.out.println("created");
				st.executeUpdate(query);
				System.out.println("executed");
				System.out.println("put");
				return true;
			} catch (SQLException e) {
				System.out.println("[An error has occured under trying to update a person id = " + id_filtered  + person.getAddress() + "]");
			}
		return false;
	}
}
