package models;
//Database Model:
/*
 * Table name: transfers
 * 
+----------+-------------------------------------------------------------------------+------+-----+-------------+-------+
| Field    | Type                                                                    | Null | Key | Default     | Extra |
+----------+-------------------------------------------------------------------------+------+-----+-------------+-------+
| id       | int(10) unsigned                                                        | NO   | PRI | NULL        |       |
| surname  | varchar(25)                                                             | NO   |     | NULL        |       |
| name     | varchar(25)                                                             | YES  |     | NULL        |       |
| street   | varchar(20)                                                             | YES  |     | NULL        |       |
| number   | varchar(10)                                                             | YES  |     | NULL        |       |
| region   | varchar(20)                                                             | YES  |     | NULL        |       |
| postcode | varchar(10)                                                             | YES  |     | NULL        |       |
| city     | varchar(20)                                                             | YES  |     | NULL        |       |
| country  | varchar(25)                                                             | YES  |     | NULL        |       |
| phone    | varchar(15)                                                             | YES  |     | NULL        |       |
| fragile  | tinyint(4)                                                              | YES  |     | NULL        |       |
| tracking | varchar(14)                                                             | YES  |     | NULL        |       |
| comments | varchar(50)                                                             | YES  |     | NULL        |       |
| status   | enum('undelivered','delivered','damaged','unavailable','wrong address') | NO   |     | undelivered |       |
| date     | datetime                                                                | YES  |     | NULL        |       |
+----------+-------------------------------------------------------------------------+------+-----+-------------+-------+
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Observer;
import java.util.Random;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import additional.DatabaseUtility;
import additional.ObservableObject;

public class EmployeeModel {
	
	public ObservableObject<boolean[]> warningP1 = new ObservableObject<>();
	public ObservableObject<boolean[]> warningP2 = new ObservableObject<>();
	public ObservableObject<String[]> tbrP2 = new ObservableObject<>();
	public ObservableObject<Boolean> foundP2 = new ObservableObject<>();
	private JTable table;
	private JTable copytable;
	
	public EmployeeModel() {}
	
	public String Panel1Submit(String[] text){
		
		boolean[] warning = new boolean[6];

		 for (int i = 0; i < 5; i++)
			 if (text[i].length() == 0 )
				 warning[5] = true;
		 
		 warning[0] = !text[0].matches("[a-zA-Z�-��-�\\s]*");
		 warning[1] = !text[1].matches("[a-zA-Z�-��-�\\s]*");
		 warning[2] = !text[5].matches("\\d{5}|\\d{5}");
		 warning[3] = !text[7].matches("[a-zA-Z�-��-�\\s]*");
		 warning[4] = !text[8].matches("\\d{10}|\\d{7}");
		
		 warningP1.set(warning);
		 
		 boolean ready = true;
		 
		 for (int i = 0; i < 6; i++) {
			 if (warning[i] == true) {
				 ready = false;
				 break;
			 }
		 }
		 
		 if (ready == true) {
			 try {
				 Class.forName("com.mysql.jdbc.Driver").newInstance();

								 
				 DatabaseUtility dbUtil = new DatabaseUtility();
				 Connection conn = DriverManager.getConnection(dbUtil.getUrl(), dbUtil.getUsername(), dbUtil.getPass());
				//**
				 Statement stm = conn.createStatement();
				 
				 int id = 0;
				 String sqldiavasmaid = "select id from transfers";
				 ResultSet rs = stm.executeQuery(sqldiavasmaid);
				 while(rs.next()) {
					 id = rs.getInt("id");
				 }
				 
				 String stmt = "select tracking from transfers";
				 ResultSet rs1 = stm.executeQuery(stmt);

				 Random r = new Random();
				 String trackingnumber = String.format("%09d", r.nextInt(1000000000));
				 while (rs1.next()){
					 if(rs1.getString("tracking").regionMatches(3, trackingnumber, 11, 13)){
						 trackingnumber = String.format("%09d", r.nextInt(1000000000));
					 }
				 }
				 trackingnumber = "RE"+trackingnumber+"GR";

				 String sqlstmt = "insert into transfers values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				 PreparedStatement ps = conn.prepareStatement(sqlstmt);
				 ps.setInt(1, id + 1);
				 ps.setString(2, text[0]);
				 ps.setString(3, text[1]);
				 ps.setString(4, text[2]);
				 ps.setInt(5, Integer.parseInt(text[3]));
				 ps.setString(6, text[4]);
				 ps.setInt(7, Integer.parseInt(text[5]));
				 ps.setString(8, text[6]);
				 ps.setString(9, text[7]);
				 ps.setString(10, text[8]);
				 if (text[9].equals("t"))
					 ps.setInt(11, 1);
				 else
					 ps.setInt(11, 0);
				 ps.setString(12, trackingnumber);
				 ps.setString(13, text[10]);
				 ps.setString(14, "undelivered");
				 ps.setTimestamp(15, new Timestamp(System.currentTimeMillis()));
				 ps.executeUpdate();
				 
		         rs.close();
		         stm.close();
		         conn.close();
		         
		         return trackingnumber;
					
					
			 }catch(Exception e1){
				 e1.printStackTrace();
			 }
		 }
		return null;
	}
	
	public void Panel2Search(String[] text) {
		boolean[] warning = new boolean[8];
		
		int i, count = 0;
		for (i = 0; i < 7; i++)
			if (text[i].length() == 0 )
				count++;
		warning[7] = (count == i)?true:false;	
				
		if (!warning[7]){		
			warning[0] = !text[0].matches("[A-Za-z]{2}\\d{9}[A-Za-z]{2}|\\d{0}");
			warning[1] = !text[1].matches("[a-zA-Z�-��-�\\s]*");
			warning[2] = !text[2].matches("[a-zA-Z�-��-�\\s]*");
			warning[3] = !text[3].matches("[a-zA-Z�-��-�\\s]*");
			warning[4] = !text[4].matches("\\d{5}|\\d{0}");
			warning[5] = !text[5].matches("[a-zA-Z�-��-�\\s]*");
			warning[6] = !text[6].matches("\\d{10}|\\d{0}");
		}
		
		warningP2.set(warning);
		
		boolean ready = true;
		
		for (i = 0; i < 8; i++)
			if (warning[i])
				ready = false;
		
		if (ready){
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				DatabaseUtility dbUtil = new DatabaseUtility();
				Connection conn = DriverManager.getConnection(dbUtil.getUrl(), dbUtil.getUsername(), dbUtil.getPass());
				Statement stm = conn.createStatement();

				String database[] = { " tracking="," name="," surname="," address="," postalcode="," country="," phone=" }; 
				String x = "select * from transfers where";
				String y = " and";
				boolean first = true;
				
				for (i = 0; i < 7; i++){
					if (!text[i].isEmpty()){
						if (first == true){
							x += database[i] +"'"+ text[i]+"'";
							first = false;
						}else{
							x += y + database[i] +"'"+ text[i]+"'";
						}
					}
				}
				
				String[] responce = new String[14];
				ResultSet rs = stm.executeQuery(x);
				int y1 =0;
				while (rs.next()){
					y1++;
					for (i = 0; i < responce.length; i++)
						responce[i] = rs.getString(i+2);
					tbrP2.set(responce);
				}
				if (y1 == 0)
					foundP2.set(true);
				else 
					foundP2.set(false);
				
				

			}catch(Exception e1){
				e1.printStackTrace();
			}
			
		}
	}
	
	public void getTableRow(DefaultTableModel ret) {
		copytable = new JTable(ret);
	}
	
	public boolean Panel2Submit(DefaultTableModel ret) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			
			DatabaseUtility dbUtil = new DatabaseUtility();
			Connection conn = DriverManager.getConnection(dbUtil.getUrl(), dbUtil.getUsername(), dbUtil.getPass());
			Statement stm = conn.createStatement();

			String x = "update transfers set";
			String y = " where tracking=";
			String database[] = { " surname="," name="," street="," number="," region="," postcode="," city="," country="," phone="," fragile="," tracking="," comments="," status=" };
			int count;
			String response = "";
			
			table = new JTable(ret);

			Object tablecell;
			Object table2cell;
			

			for (int i = 0; i < table.getRowCount(); i++ ) {
				response ="";
				response += x;
				count = 0;
				for (int j = 0 ; j < table.getColumnCount(); j++) {
					table2cell = table.getValueAt(i, j); // to kenourio
					tablecell = copytable.getValueAt(i, j);
					if (tablecell != table2cell) {
						count++;
						if (count == 1)
							response += database[j] +"'"+table2cell+"'";
						else
							response += "," + database[j] +"'"+table2cell+"'";
					}
				}
				response += y +"'"+ table.getValueAt(i, 10)+"'";
				if (count > 0) {
					stm.executeUpdate(response);
					return true;
				}
			}
		}catch(Exception e1){
			e1.printStackTrace();
		}
		return false;
	}
	
    public void addPanel1WaringObserver(Observer controller) {
    	warningP1.addObserver(controller);
    }
    
    public void addPanel2WaringObserver(Observer controller) {
    	warningP2.addObserver(controller);
    }
    
    public void addPanel2SearchObserver(Observer controller) {
    	tbrP2.addObserver(controller);
    }
    
    public void addPanel2FoundObserver(Observer controller) {
    	foundP2.addObserver(controller);
    }

}
