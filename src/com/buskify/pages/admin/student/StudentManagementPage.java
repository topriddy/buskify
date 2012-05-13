package com.buskify.pages.admin.student;

import org.apache.wicket.markup.html.link.StatelessLink;
import org.apache.wicket.markup.html.panel.Panel;

import com.buskify.pages.HelpPanel;
import com.buskify.pages.admin.BaseAdminPage;

public class StudentManagementPage extends BaseAdminPage {
private final String PANEL_ID = "PANEL";
	
	public StudentManagementPage(){
		Panel panel = new ViewStudentPanel(PANEL_ID);
		panel.setOutputMarkupId(true);
		panel.setOutputMarkupPlaceholderTag(true);
		add(panel);
		
		addLinks();
	}
	private void addLinks(){
		add(new SwitchPanelLink("viewAllLink", new ViewStudentPanel(PANEL_ID)));
		add(new SwitchPanelLink("newStudentLink", new NewStudentPanel(PANEL_ID)));
		add(new SwitchPanelLink("editLink", new EditStudentPanel(PANEL_ID)));
		add(new SwitchPanelLink("batchUploadLink", new UploadStudentPanel(PANEL_ID)));
		add(new SwitchPanelLink("helpLink", new HelpPanel(PANEL_ID)));
	}
	
	
	
	private void switchPanel(Panel panel){
		panel.setOutputMarkupId(true);
		panel.setOutputMarkupPlaceholderTag(true);
		this.replace(panel);
	}
	
	private class SwitchPanelLink extends StatelessLink{
		Panel panel;
		public SwitchPanelLink(String id, Panel panel){
			super(id);
			this.panel = panel;
		}

		@Override
		public void onClick() {
			switchPanel(panel);
		}
	}
}
