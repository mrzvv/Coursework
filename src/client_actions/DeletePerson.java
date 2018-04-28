package client_actions;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import entity.Person;

public class DeletePerson {
	public static boolean delete (Person person) {
			try {
				java.sql.Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankwork",
						"root", "root");
				Statement st = connection.createStatement();
				st.executeUpdate("DROP TABLE IF EXISTS `client_" + person.getId() + "_current_byn_account`;");
				st.executeUpdate("DROP TABLE IF EXISTS `client_" + person.getId() + "_current_usd_account`;");
				st.executeUpdate("DROP TABLE IF EXISTS `client_" + person.getId() + "_current_eur_account`;");
				st.executeUpdate("DROP TABLE IF EXISTS `client_" + person.getId() + "_percent_byn_account`;");
				st.executeUpdate("DROP TABLE IF EXISTS `client_" + person.getId() + "_percent_byn_account`;");
				st.executeUpdate("DROP TABLE IF EXISTS `client_" + person.getId() + "_percent_byn_account`;");
				st.executeUpdate("DELETE FROM `deposit_accounts` WHERE `ClientId`=" + person.getId());
				st.executeUpdate("DELETE FROM `clients` WHERE `PersonId`=" + person.getId());
				return true;
			} catch (SQLException e) {
				System.out.println("[An error has occured under trying to delete a person with id " + person.getId() + "]");
			}
		return false;
	}
}
