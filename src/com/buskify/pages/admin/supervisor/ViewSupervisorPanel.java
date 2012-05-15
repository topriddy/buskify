package com.buskify.pages.admin.supervisor;

import java.util.ArrayList;

import lombok.extern.log4j.Log4j;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.buskify.components.ConfirmationLink;
import com.buskify.dao.SupervisorDao;
import com.buskify.entity.Supervisor;

@Log4j
public class ViewSupervisorPanel extends Panel {
	private final int ROW = 15;
	public ViewSupervisorPanel(String id){
		super(id);
	}


	@Override
	protected void onInitialize() {
		super.onInitialize();

		boolean isEmptyList = new SupervisorDao().findAll().isEmpty();

		WebMarkupContainer dataListContainer = new WebMarkupContainer(
				"dataListContainer");
		dataListContainer.setOutputMarkupPlaceholderTag(true);
		dataListContainer.setVisible(!isEmptyList);
		add(dataListContainer);

		IModel<ArrayList<Supervisor>> supervisorListModel = new Model<ArrayList<Supervisor>>() {
			@Override
			public ArrayList<Supervisor> getObject() {
				// TODO Auto-generated method stub
				return (ArrayList<Supervisor>) new SupervisorDao().findAll();
			}

		};

		PageableListView<Supervisor> supervisorLV = new PageableListView<Supervisor>(
				"supervisorLV", supervisorListModel, ROW) {
			@Override
			protected void populateItem(ListItem<Supervisor> item) {
				final Supervisor supervisor = item.getModelObject();
				item.setModel(new CompoundPropertyModel(supervisor));
				item.add(new Label("snos", Model.of(item.getIndex() + 1)));
				item.add(new Label("fullName"));
				item.add(new Label("username"));
				item.add(new Link("editLink") {
					@Override
					public void onClick() {
						log.info("EditLink clicked");
					}
				});
				item.add(new ConfirmationLink("deleteLink", "Are you sure you want to delete supervisor") {
					@Override
					public void onClick() {
						new SupervisorDao().delete(supervisor);
						log.info("Supervisor Deleted Successfully");
					}
				});
			}
		};
		supervisorLV.setOutputMarkupPlaceholderTag(true);
		dataListContainer.add(supervisorLV);
		dataListContainer.add(new PagingNavigator("pagingNavigator", supervisorLV));

		WebMarkupContainer emptyListMessageContainer = new WebMarkupContainer(
				"emptyListMessage");
		emptyListMessageContainer.setOutputMarkupPlaceholderTag(true);
		emptyListMessageContainer.setVisible(isEmptyList);
		add(emptyListMessageContainer);
	}
}
