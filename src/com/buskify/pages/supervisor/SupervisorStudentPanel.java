package com.buskify.pages.supervisor;

import java.util.ArrayList;
import java.util.List;

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
import com.buskify.dao.StudentDao;
import com.buskify.entity.Project;
import com.buskify.entity.Student;
import com.buskify.entity.Supervisor;

public class SupervisorStudentPanel extends Panel {
	private final int ROW = 15;

	public SupervisorStudentPanel(String id) {
		super(id);

	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		final Supervisor supervisor = (Supervisor)UserSession.get().getAppUser();
		
		IModel<ArrayList<Student>> studentListModel = new Model<ArrayList<Student>>() {
			@Override
			public ArrayList<Student> getObject() {
				// TODO Auto-generated method stub
				return (ArrayList<Student>) new StudentDao().findBySupervisor(supervisor);
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

		List<Student> projectList = new StudentDao().findBySupervisor(supervisor);
		PageableListView<Student> projectLV = new PageableListView<Student>("studentLV", studentListModel, ROW) {

			@Override
			protected void populateItem(ListItem<Student> item) {
				final Student student = item.getModelObject();
				item.add(new Label("snos", Model.of(item.getIndex() +1)));
				item.add(new Label("number", Model.of(student.getNumber())));
				item.add(new Label("course", Model.of(student.getCourse())));
				item.add(new Label("stream", Model.of(student.getStream())));
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