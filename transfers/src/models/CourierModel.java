package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Observable;
import java.util.Observer;
import additional.DatabaseUtility;
import additional.ObservableObject;

public class CourierModel extends Observable{
	
	public ObservableObject<Integer> switchpanel = new ObservableObject<>();
	public ObservableObject<String[]> tbrow = new ObservableObject<>();
	
	public CourierModel() {}
	
	public String Submit(String combobx, int id) {
		
		if (combobx.equals("OK"))
			combobx = "DELIVERED";
		
		if (id >= 0){
			id++;
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();

							
				DatabaseUtility dbUtil = new DatabaseUtility();
				Connection conn = DriverManager.getConnection(dbUtil.getUrl(), dbUtil.getUsername(), dbUtil.getPass());
				String sqlgrapsimo = "update transfers set status='"+combobx.toLowerCase()+"' where id="+id;
				PreparedStatement ps = conn.prepareStatement(sqlgrapsimo);
				ps.executeUpdate();

				ps.close();
				conn.close();
				
				return combobx;

			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
		return combobx;
	}
	
	public String Info(int id, int pa){
		
		pa = (pa > 2)? 1: 3;
		if (pa != 3)
			switchpanel.set(pa);
		
		if (pa == 3 && id >= 0){
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();

				
				DatabaseUtility dbUtil = new DatabaseUtility();
				Connection conn = DriverManager.getConnection(dbUtil.getUrl(), dbUtil.getUsername(), dbUtil.getPass());
				Statement stm = conn.createStatement();
				
				switchpanel.set(pa);
				id++;
				
				String sqldiavasmaid = "select * from transfers where id=" + id;
				ResultSet rs = stm.executeQuery(sqldiavasmaid);
				
				rs.next();
				
				String info = "Packet Information\n\n";
				
				info +="Tracking Number:\t"+ rs.getString("tracking")+"\n";
				info +="Posted on:\t"+ rs.getString("date")+"\n";
				info +="Status:\t\t"+ rs.getString("status").toUpperCase()+"\n";
				info +="\nDestination\n\n";
				info +="Name:\t\t"+ rs.getString("name")+"\n";
				info +="Surname:\t\t"+ rs.getString("surname")+"\n";
				info +="street:\t\t"+ rs.getString("street")+"\n";
				info +="number:\t\t"+ rs.getString("number")+"\n";
				info +="region:\t\t"+ rs.getString("region")+"\n";
				info +="Postal Code:\t"+ rs.getString("postcode")+"\n";
				info +="city:\t\t"+ rs.getString("city")+"\n";
				info +="Country:\t\t"+ rs.getString("country")+"\n";
				info +="\nComments\n\n"+ rs.getString("comments");
				
				rs.close();
				stm.close();
				conn.close();
				
				return info;

			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
		return null;
	}
	
	/*public ImageIcon Map(int id, int pa, int[] dim) {
		
		pa = (pa == 2)? 1: 2;
		if (pa != 2)
			switchpanel.set(pa);
		
		if (pa == 2 && id >= 0){
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();

				
				DatabaseUtility dbUtil = new DatabaseUtility();
				Connection conn = DriverManager.getConnection(dbUtil.getUrl(), dbUtil.getUsername(), dbUtil.getPass());
				Statement stm = conn.createStatement();

				switchpanel.set(pa);
				id++;
				
				String x = Integer.toString(dim[0]-10);
				String y = Integer.toString(dim[1]-10); 
				
				String sqldiavasmaid = "select * from transfers where id=" + id;
				ResultSet rs = stm.executeQuery(sqldiavasmaid);
				
				rs.next();

				String address = rs.getString("street").replaceAll(" ", "+") +  rs.getString("number" +  rs.getString("region"));
				String postcode = rs.getString("postcode");
				String country = rs.getString("country").replaceAll(" ", "+");;
				
				address = GreekToGreeklish.convert(address);
				country = GreekToGreeklish.convert(country);
				
				String imageUrl = "http://maps.googleapis.com/maps/api/staticmap?";
				imageUrl += "center="+address+","+postcode+","+country;
				imageUrl +=	"&zoom=16&size="+y+"x"+x+"&maptype=roadmap";
				imageUrl += "&markers=color:red%7Clabel:A%7C"+address+","+postcode+","+country;
				imageUrl += "&sensor=false";
				
				try {
					URL url1;
					url1 = new URL(imageUrl);
					ImageIcon img = new ImageIcon(new ImageIcon(url1).getImage());
					return img;
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}
			}catch(Exception e1){
				e1.printStackTrace();
			}

		}
		return null;
	}*/
	
	public void Connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			
			DatabaseUtility dbUtil = new DatabaseUtility();
			Connection conn = DriverManager.getConnection(dbUtil.getUrl(), dbUtil.getUsername(), dbUtil.getPass());
			Statement stm = conn.createStatement();

			String[] tablerow = new String [7];
			
			//kanonika o kathe Courier den tha kanei select ola.. alla analoga me to postal code
			
			String sqldiavasmaid = "select * from transfers order by id";
			ResultSet rs = stm.executeQuery(sqldiavasmaid);
			
			
			while(rs.next()) {
				tablerow[0] = rs.getString("name");
				tablerow[1] = rs.getString("surname");
				tablerow[2] = rs.getString("street");
				tablerow[3] = rs.getString("number");
				tablerow[4] = rs.getString("region");
				tablerow[5] = rs.getString("city");
				tablerow[6] = rs.getString("status").toUpperCase();
				
				tbrow.set(tablerow);
			}
			rs.close();
			stm.close();
			conn.close();

		}catch(Exception e1){
			e1.printStackTrace();
		}

	}
	
    public void addPanelObserver(Observer controller) {
    	switchpanel.addObserver(controller);
    }
    
    public void addTBRObserver(Observer controller) {
    	tbrow.addObserver(controller);
    }

}
