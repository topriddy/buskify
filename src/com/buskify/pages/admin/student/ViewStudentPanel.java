package com.buskify.pages.admin.student;

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
import com.buskify.dao.StudentDao;
import com.buskify.entity.Student;

@Log4j
public class ViewStudentPanel extends Panel {
	private final int ROW = 10;

	public ViewStudentPanel(String id) {
		super(id);

	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		boolean isEmptyList = new StudentDao().findAll().isEmpty();

		WebMarkupContainer dataListContainer = new WebMarkupContainer(
				"dataListContainer");
		dataListContainer.setOutputMarkupPlaceholderTag(true);
		dataListContainer.setVisible(!isEmptyList);
		add(dataListContainer);

		IModel<ArrayList<Student>> studentListModel = new Model<ArrayList<Student>>() {
			@Override
			public ArrayList<Student> getObject() {
				// TODO Auto-generated method stub
				return (ArrayList<Student>) new StudentDao().findAll();
			}

		};

		PageableListView<Student> studentLV = new PageableListView<Student>(
				"studentLV", studentListModel, ROW) {
			@Override
			protected void populateItem(ListItem<Student> item) {
				final Student student = item.getModelObject();
				item.setModel(new CompoundPropertyModel(student));
				item.add(new Label("snos", Model.of(item.getIndex() + 1)));
				item.add(new Label("stream"));
				item.add(new Label("course"));
				item.add(new Label("number"));
				item.add(new Link("editLink") {
					@Override
					public void onClick() {
						log.info("EditLink clicked");
					}
				});
				item.add(new ConfirmationLink("deleteLink", "Are you sure you want to delete Student?") {
					@Override
					public void onClick() {
						new StudentDao().delete(student);
						log.info("Student Deleted Successfully");
					}
				});
			}
		};
		studentLV.setOutputMarkupPlaceholderTag(true);
		dataListContainer.add(studentLV);
		dataListContainer.add(new PagingNavigator("pagingNavigator", studentLV));

		WebMarkupContainer emptyListMessageContainer = new WebMarkupContainer(
				"emptyListMessage");
		emptyListMessageContainer.setOutputMarkupPlaceholderTag(true);
		emptyListMessageContainer.setVisible(isEmptyList);
		add(emptyListMessageContainer);
	}
}
