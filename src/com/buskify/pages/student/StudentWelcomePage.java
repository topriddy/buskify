package com.buskify.pages.student;

import org.apache.wicket.markup.html.link.StatelessLink;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;

import com.buskify.pages.HelpPanel;
import com.buskify.pages.admin.project.ViewProjectPanel;

public class StudentWelcomePage extends BaseStudentPage{
	private final String PANEL_ID = "PANEL";
	
	
	public StudentWelcomePage(){
		Panel panel = new ProjectSelectionPanel(PANEL_ID);
		panel.setOutputMarkupId(true);
		panel.setOutputMarkupPlaceholderTag(true);
		add(panel);
		
		addLinks();
	}
	
	private void addLinks(){
		add(new SwitchPanelLink("profileInfo",new StudentProfilePanel(PANEL_ID)));
		add(new SwitchPanelLink("projectSelection",new ProjectSelectionPanel(PANEL_ID)));
		add(new SwitchPanelLink("apdfForm",new StudentApdfPanel(PANEL_ID)));
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
