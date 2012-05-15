package com.buskify.pages.admin.configuration;

import lombok.extern.log4j.Log4j;

import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;

import com.buskify.components.ConfirmationLink;
import com.buskify.components.MyFeedbackPanel;
import com.buskify.util.Util;

@Log4j
public class ResetAllocationPanel extends Panel {
	public ResetAllocationPanel(String id){
		super(id);
		FeedbackPanel feedback = new MyFeedbackPanel("feedback", new ComponentFeedbackMessageFilter(this));
		add(feedback);
		
		add(new ConfirmationLink("resetAllocation", "Are you sure you want to perform this irreversibleaction"){
			@Override
			public void onClick() {
				System.out.println("Beginning to Reset Allocation");
				Util.resetAllocation();
				log.debug("Reset Allocation Successfully");
				ResetAllocationPanel.this.info("Sucess!! All Students have been de-allocated!!");
			}
		});
	}
}
