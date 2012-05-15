package com.buskify.pages.admin.configuration;

import lombok.extern.log4j.Log4j;

import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;

import com.buskify.components.ConfirmationLink;
import com.buskify.components.MyFeedbackPanel;
import com.buskify.util.Util;

@Log4j
public class AllocationPanel extends Panel{
	public AllocationPanel(String id){
		super(id);
		
		FeedbackPanel feedback = new MyFeedbackPanel("feedback", new ComponentFeedbackMessageFilter(this));
		add(feedback);
		
		add(new ConfirmationLink("runHSMLink", "Are you sure you want to run Algorithm?"){
			@Override
			public void onClick() {
				log.debug("Ran HSM Algorithm Successfully");
				AllocationPanel.this.info("Sucess!! Ran Hybrid Stable Marriage Algorithm Successfully");
			}
		});
		
		add(new ConfirmationLink("runRandomLink", "Are you sure you want to run Algorithm?"){
			@Override
			public void onClick() {
				System.out.println("Beginning to Reset Allocation");
				Util.resetAllocation();
				log.debug("Reset Allocation Successfully");
				AllocationPanel.this.info("Sucess!! Ran Random Allocation Algorithm Successfully");
			}
		});
	}
}
