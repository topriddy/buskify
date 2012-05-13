package com.buskify.pages.admin.project;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;

public class ViewProjectPanel extends Panel {
	public ViewProjectPanel(String id){
		super(id);
		
		WebMarkupContainer emptyListMessageContainer = new WebMarkupContainer("emptyListMessage");
		emptyListMessageContainer.setOutputMarkupPlaceholderTag(true);
		add(emptyListMessageContainer);
		
		WebMarkupContainer dataListContainer = new WebMarkupContainer("dataListContainer");
		dataListContainer.setOutputMarkupPlaceholderTag(true);
		add(dataListContainer);
	}
}
