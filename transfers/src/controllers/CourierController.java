package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import models.CourierModel;
import views.CourierView;

public class CourierController {
	
	private CourierView views;
	private CourierModel model;
	
	public CourierController(CourierView views, CourierModel model){
		this.views = views;
		views.btnSubmitListener(new BtnSumitListener());
		views.btnConnectListener(new BtnConnectListener());
		views.btnMoreListener(new BtninfoListener());
		
		this.model = model;
		model.addPanelObserver(new PanelObserver());
		model.addTBRObserver(new TBRObserver());
		
	}
	
	class BtnSumitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			/*
			 * Submit
			 */
			views.switchPanel(1);
			views.updateStatus(model.Submit(views.getItemStatus(), views.getTableRow()));
			
		}
	}
	
	class BtninfoListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			/*
			 * info
			 */
			views.setInfo(model.Info(views.getTableRow(), views.getPanel()));

		}
	}
	
	
	class BtnConnectListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			/*
			 * Connect
			 */
			views.switchPanel(1);
			model.switchpanel.set(1);
			views.clearTable();
			model.Connect();
			
		}
	}
	
	class PanelObserver implements Observer {
		public void update(Observable obs, Object arg) {
			views.switchPanel(model.switchpanel.get());
		}
	}
	
	class TBRObserver implements Observer {
		public void update(Observable obs, Object arg) {
			views.setTableData(model.tbrow.get());
		}
	}
	
	public void clear(){
		views.clearAll();
	}
}
