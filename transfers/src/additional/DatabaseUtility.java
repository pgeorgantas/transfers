package additional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtility {

	private String url = "jdbc:mysql://83.212.119.122/teipir?characterEncoding=UTF-8";
	private String username = "teipir";
	private String pass = "12345678";

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPass() {
		return pass;
	}

	public boolean login(String usr, String pass, String role) { 
		Connection conn;
		try {
			conn = DriverManager.getConnection(url, username, this.pass);
			Statement stm = conn.createStatement();

			String stmt = "select * from users where username=\"" + usr
					+ "\" and role=\"" + role + "\"";

			ResultSet rs = stm.executeQuery(stmt);
			rs.next();
			if (rs.getString("password").equals(pass)) {
				rs.close();
				stm.close();
				conn.close();
				return true;
			} else {
				rs.close();
				stm.close();
				conn.close();
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

}
