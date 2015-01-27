package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import models.AdministratorModel;
import views.AdministratorView;

public class AdministratorController {
	
	private AdministratorView views;
	private AdministratorModel model;

	public AdministratorController(AdministratorView views ,AdministratorModel model) {
		this.views = views;
		views.btnExportListener(new BtnExportListener());
		views.comboBxListener(new ComboBoxListener());
		
		this.model = model;
	}
	
	class ComboBoxListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			/*
			 * ComboBox
			 */
			views.setChart(model.ComboBox(views.getItemStatus()));

		}
	}
	
	class BtnExportListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			/*
			 * Export
			 */
			model.Export();
		}
	}
	
	public void clear() {
		views.clear();
	}
	

}
