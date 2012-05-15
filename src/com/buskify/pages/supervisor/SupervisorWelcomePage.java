package com.buskify.pages.supervisor;

import org.apache.wicket.markup.html.link.StatelessLink;
import org.apache.wicket.markup.html.panel.Panel;

import com.buskify.pages.HelpPanel;
import com.buskify.pages.student.ProjectSelectionPanel;


public class SupervisorWelcomePage extends BaseSupervisorPage {
private final String PANEL_ID = "PANEL";
	
	
	public SupervisorWelcomePage(){
		Panel panel = new SupervisorProjectPanel(PANEL_ID);
		panel.setOutputMarkupId(true);
		panel.setOutputMarkupPlaceholderTag(true);
		add(panel);
		
		addLinks();
	}
	
	private void addLinks(){
		add(new SwitchPanelLink("profileInfoLink",new SupervisorProfilePanel(PANEL_ID)));
		add(new SwitchPanelLink("assignedProjectsLink",new SupervisorProjectPanel(PANEL_ID)));
		add(new SwitchPanelLink("assignedStudentsLink",new SupervisorStudentPanel(PANEL_ID)));
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
