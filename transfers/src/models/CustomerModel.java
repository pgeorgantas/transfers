package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Observable;
import java.util.Observer;

import additional.DatabaseUtility;
import additional.ObservableObject;

public class CustomerModel extends Observable {
		
	public ObservableObject<Integer> warning = new ObservableObject<>();
	public ObservableObject<String> trackinginfo = new ObservableObject<>();
	
	public CustomerModel() { }
	
	public void Search(String tracking){
		
		if (tracking.isEmpty()) {
			warning.set(1);
		}else if (!tracking.matches("[A-Za-z]{2}\\d{9}[A-Za-z]{2}|\\d{0}")){
			warning.set(2);
		}else{
			try {
				
				String query;
				
				Class.forName("com.mysql.jdbc.Driver").newInstance();
								
				DatabaseUtility dbUtil = new DatabaseUtility();
				Connection conn = DriverManager.getConnection(dbUtil.getUrl(), dbUtil.getUsername(), dbUtil.getPass());
				Statement stm = conn.createStatement();

				String sql = "select * from transfers where tracking='"+ tracking + "'";

				ResultSet rs = stm.executeQuery(sql);

				if (rs.next()) {
					String name = rs.getString("name");
					String surname = rs.getString("surname");
					String country = rs.getString("country");
					String date = rs.getString("date");
					String address = rs.getString("street") + " " + rs.getString("number") + " " + rs.getString("region");
					String phone = rs.getString("phone");
					String city = rs.getString("city");
					String comments = rs.getString("comments");
					String fragile;
					if (rs.getString("fragile").equals("1"))
						fragile = "Yes";
					else
						fragile = "No";
					query = "To be delivered to:\t" + surname + " " + name
							+ "\n" + "Destination:\t\t" + country + "\n"
							+ "Posted:\t\t" + date
							+ "\nAddress:\t\t" + address + "\n\t\t" + city + " " + country
							+ "\nPhone:\t\t" + phone
							+ "\n\nFragile:\t\t" + fragile
							+ "\n\nComments:\t\t" + comments;
				}else{
					query = "Tracking: "+tracking+" was not found";
				}
				rs.close();
				stm.close();
				conn.close();
				
				trackinginfo.set(query);

			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
    public void addWaringObserver(Observer controller) {
    	warning.addObserver(controller);
    }
    
    public void addTrackingInfoObserver(Observer controller) {
    	trackinginfo.addObserver(controller);
    }

}
