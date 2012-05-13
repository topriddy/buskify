package com.buskify.pages.admin.supervisor;

import org.apache.wicket.markup.html.link.StatelessLink;
import org.apache.wicket.markup.html.panel.Panel;

import com.buskify.pages.HelpPanel;
import com.buskify.pages.admin.BaseAdminPage;
import com.buskify.pages.admin.project.ViewProjectPanel;


public class SupervisorManagementPage extends BaseAdminPage {
	private final String PANEL_ID = "PANEL";
	
	public SupervisorManagementPage(){
		Panel panel = new ViewSupervisorPanel(PANEL_ID);
		panel.setOutputMarkupId(true);
		panel.setOutputMarkupPlaceholderTag(true);
		add(panel);
		
		addLinks();
	}
	private void addLinks(){
		add(new SwitchPanelLink("viewAllLink", new ViewSupervisorPanel(PANEL_ID)));
		add(new SwitchPanelLink("newSupervisorLink", new NewSupervisorPanel(PANEL_ID)));
		add(new SwitchPanelLink("editLink", new EditSupervisorPanel(PANEL_ID)));
		add(new SwitchPanelLink("batchUploadLink", new UploadSupervisorPanel(PANEL_ID)));
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
