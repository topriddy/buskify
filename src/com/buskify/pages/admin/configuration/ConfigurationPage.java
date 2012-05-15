package com.buskify.pages.admin.configuration;

import lombok.extern.log4j.Log4j;

import org.apache.wicket.markup.html.link.StatelessLink;
import org.apache.wicket.markup.html.panel.Panel;

import com.buskify.pages.HelpPanel;
import com.buskify.pages.admin.BaseAdminPage;
import com.buskify.pages.admin.project.ViewProjectPanel;
@Log4j
public class ConfigurationPage extends BaseAdminPage{
private final String PANEL_ID = "PANEL";
	
	public ConfigurationPage(){
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
		add(new SwitchPanelLink("allocationLink", new AllocationPanel(PANEL_ID)));
		add(new SwitchPanelLink("resetAllocationLink", new ResetAllocationPanel(PANEL_ID)));
		add(new SwitchPanelLink("settingsLink", new SettingsPanel(PANEL_ID)));
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
