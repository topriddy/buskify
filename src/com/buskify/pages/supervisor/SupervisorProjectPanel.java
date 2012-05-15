package com.buskify.pages.supervisor;



import java.util.ArrayList;
import java.util.List;

import lombok.extern.log4j.Log4j;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.buskify.UserSession;
import com.buskify.dao.ProjectDao;
import com.buskify.entity.Project;
import com.buskify.entity.Supervisor;

@Log4j
public class SupervisorProjectPanel extends Panel {
	private final int ROW = 15;

	public SupervisorProjectPanel(String id) {
		super(id);

	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		final Supervisor supervisor = (Supervisor)UserSession.get().getAppUser();
		
		IModel<ArrayList<Project>> projectListModel = new Model<ArrayList<Project>>() {
			@Override
			public ArrayList<Project> getObject() {
				// TODO Auto-generated method stub
				return (ArrayList<Project>) new ProjectDao().findBySupervisor(supervisor);
			}
		};
		
		WebMarkupContainer emptyListMessageContainer = new WebMarkupContainer(
				"emptyListMessage");
		emptyListMessageContainer.setOutputMarkupPlaceholderTag(true);
		emptyListMessageContainer.setVisible(false);
		add(emptyListMessageContainer);

		WebMarkupContainer dataListContainer = new WebMarkupContainer(
				"dataListContainer");
		dataListContainer.setOutputMarkupPlaceholderTag(true);
		dataListContainer.setVisible(false);
		add(dataListContainer);

		List<Project> projectList = new ProjectDao().findBySupervisor(supervisor);
		PageableListView<Project> projectLV = new PageableListView<Project>("projectLV", projectListModel, ROW) {

			@Override
			protected void populateItem(ListItem<Project> item) {
				final Project project = item.getModelObject();
				item.add(new Label("snos", Model.of(item.getIndex() +1)));
				item.add(new Label("title", Model.of(project.getTitle())));
				item.add(new Label("max", Model.of(project.getMax())));
			}
			
		};
		dataListContainer.add(projectLV);
		dataListContainer.add(new PagingNavigator("pagingNavigator", projectLV));
		if (projectList != null && projectList.size() != 0) {
			dataListContainer.setVisible(true);
			emptyListMessageContainer.setVisible(false);
		}else{
			dataListContainer.setVisible(false);
			emptyListMessageContainer.setVisible(true);
		}
	}

}
