package com.buskify.pages.admin.project;

import org.apache.wicket.markup.html.link.StatelessLink;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;

import com.buskify.pages.HelpPanel;
import com.buskify.pages.admin.BaseAdminPage;

public class ProjectManagementPage extends BaseAdminPage {
	private final String PANEL_ID = "PANEL";
	
	public ProjectManagementPage(){
		Panel panel = new ViewProjectPanel(PANEL_ID);
		panel.setOutputMarkupId(true);
		panel.setOutputMarkupPlaceholderTag(true);
		add(panel);
		
		addLinks();
	}
	
	private void switchPanel(Panel panel){
		panel.setOutputMarkupId(true);
		panel.setOutputMarkupPlaceholderTag(true);
		this.replace(panel);
	}
	
	private void addLinks(){
		add(new SwitchPanelLink("viewAllLink", new ViewProjectPanel(PANEL_ID)));
		add(new SwitchPanelLink("newProjectLink", new NewProjectPanel(PANEL_ID)));
		add(new SwitchPanelLink("editLink", new EditProjectPanel(PANEL_ID)));
		add(new SwitchPanelLink("batchUploadLink", new UploadProjectPanel(PANEL_ID)));
		add(new SwitchPanelLink("helpLink", new HelpPanel(PANEL_ID)));
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