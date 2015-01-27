package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import models.CustomerModel;
import views.CustomerView;

public class CustomerController {

	private CustomerView views;
	private CustomerModel model;

	public CustomerController(CustomerView views , CustomerModel model ) {
		this.views = views;
		views.btnSearchListener(new BtnSearchListener());
		
		this.model = model;
		model.addWaringObserver(new WarningObserver());
		model.addTrackingInfoObserver(new TrackingObserver());
	}

	class BtnSearchListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			model.Search(views.getTracking());		

		}
	}
	
	class WarningObserver implements Observer {
		public void update(Observable obs, Object arg) {
			views.showWarning(model.warning.get());
		}
	}
	
	class TrackingObserver implements Observer {
		public void update(Observable obs, Object arg) {
			views.setTrackingInfo(model.trackinginfo.get());
		}
	}
	  
	public void clear() {
		views.clearAll();
	}
	
	

}
