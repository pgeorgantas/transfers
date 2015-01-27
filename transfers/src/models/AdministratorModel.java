package models;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.jfree.data.general.DefaultPieDataset;

import additional.DatabaseUtility;

public class AdministratorModel {
	
	public AdministratorModel() {}
	
	public DefaultPieDataset ComboBox(String selection) {
		DefaultPieDataset dataset = null;
		
		if (!selection.equals("None")){
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();

				
				DatabaseUtility dbUtil = new DatabaseUtility();
				Connection conn = DriverManager.getConnection(dbUtil.getUrl(), dbUtil.getUsername(), dbUtil.getPass());
				Statement stm = conn.createStatement();

				int idall = 0;
				int idquery = 0;
				String query = "tracking=true";

				ResultSet rs = stm.executeQuery("select id from transfers");
				while(rs.next()) {
					idall++;
				}

				if (selection.equals("Fragile"))
					query = "fragile=1";
				if (selection.equals("Delivered"))
					query = "status='delivered'";
				if (selection.equals("To be Delivered"))
					query = "status='undelivered'";
				if (selection.equals("Damaged"))
					query = "status='damaged'";
				if (selection.equals("Unavailable"))
					query = "status='unavailable'";
				if (selection.equals("Wrong Address"))
					query = "status='wrong address'";
				
				rs = stm.executeQuery("select id from transfers where " + query);
				
				while(rs.next()) {
					idquery++;
				}
				dataset = new DefaultPieDataset();
				
				dataset.setValue(selection, idquery);
				dataset.setValue("OK",idall - idquery);

				rs.close();
				stm.close();
				conn.close();
				
				return dataset;

			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
		return dataset;
	}
	
	public void Export(){
		String filename = "myjdbcfile";
		JFileChooser chooser = new JFileChooser( );
		chooser.setSelectedFile(new File( filename + ".csv"));
		int state = chooser.showSaveDialog(null);
		File file = chooser.getSelectedFile();

		if (file != null && state == JFileChooser.APPROVE_OPTION){

			try {
				FileWriter csv = new FileWriter(file);
				Class.forName("com.mysql.jdbc.Driver").newInstance();

				DatabaseUtility dbUtil = new DatabaseUtility();
				Connection conn = DriverManager.getConnection(dbUtil.getUrl(), dbUtil.getUsername(), dbUtil.getPass());
				Statement stm = conn.createStatement();
				ResultSet rs = stm.executeQuery("select * from transfers");

				csv.append("ID;surname;name;street;number;region;postcode;city;country;phone;fragile;tracking;comments;status;date\n");

				while (rs.next()) {
					for (int i = 1; i < 16; i++){
						csv.append(rs.getString(i));
						char end = (i == 15)?'\n':';';
						csv.append(end);
					}
				}
				
				Desktop.getDesktop().open(new File(file.getAbsolutePath().replace("\\" + file.getName()," ")));
				
				csv.flush();
				csv.close();
				rs.close();
				stm.close();
				conn.close();

			}catch(Exception e1){
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
		}
	}

}
