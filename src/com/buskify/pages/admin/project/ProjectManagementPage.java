package com.buskify.pages.admin.project;

import org.apache.wicket.markup.html.link.StatelessLink;
import org.apache.wicket.markup.html.panel.Panel;

import com.buskify.pages.admin.BaseAdminPage;

public class ProjectManagementPage extends BaseAdminPage {
	private final String PANEL_ID = "panel";
	public ProjectManagementPage(){
		addLinks();
		Panel panel = new ViewProjectPanel(PANEL_ID);
		panel.setOutputMarkupId(true);
	}
	
	private void addLinks(){
		
	}
	
	private class SwitchPanelLink extends StatelessLink{
		public SwitchPanelLink(String id){
			super(id);
		}

		@Override
		public void onClick() {
			
		}
	}
	
	private void switchPanel(Panel panel){
		
	}
}