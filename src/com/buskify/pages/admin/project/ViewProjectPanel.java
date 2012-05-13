package com.buskify.pages.admin.project;

import java.util.Iterator;
import java.util.List;

import lombok.extern.log4j.Log4j;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.buskify.dao.ProjectDao;
import com.buskify.entity.Project;

@Log4j
public class ViewProjectPanel extends Panel {
	private final int ROW = 100;

	public ViewProjectPanel(String id) {
		super(id);

	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
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

		ProjectDao projectDao = new ProjectDao();
		List<Project> projectList = projectDao.findAll();
		ListView<Project> projectLV = new ListView<Project>("projectLV", projectList) {
			@Override
			protected void populateItem(ListItem<Project> item) {
				Project project = item.getModelObject();
				item.add(new Label("snos", Model.of(item.getIndex() +1)));
				item.add(new Label("title", Model.of(project.getTitle())));
				item.add(new Label("max", Model.of(project.getMax())));
			}
		};
		dataListContainer.add(projectLV);
		if (projectList != null && projectList.size() != 0) {
			dataListContainer.setVisible(true);
			emptyListMessageContainer.setVisible(false);
		}else{
			dataListContainer.setVisible(false);
			emptyListMessageContainer.setVisible(true);
		}
	}

	private void addProjectListView() {
		IDataProvider<Project> projectDataProvider = new IDataProvider<Project>() {
			@Override
			public void detach() {

			}

			@Override
			public Iterator<? extends Project> iterator(int first, int count) {
				Iterator iter = null;
				ProjectDao projectDao = new ProjectDao();
				try {
					List<Project> list = projectDao.findAll();
					iter = list.iterator();
				} catch (Exception ex) {
					log.error("Exception occured getting iterator for project",
							ex);
				}
				return iter;
			}

			@Override
			public IModel<Project> model(Project project) {
				return new Model<Project>(project);
			}

			@Override
			public int size() {
				return new ProjectDao().findAll().size();
			}

		};
		DataView<Project> projectDV = new DataView<Project>("projectDV",
				projectDataProvider) {
			@Override
			protected void populateItem(Item<Project> item) {

			}
		};
	}
}
