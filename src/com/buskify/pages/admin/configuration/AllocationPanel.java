package com.buskify.pages.admin.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.buskify.allocation.algo.SMAlgorithm;
import com.buskify.components.ConfirmationLink;
import com.buskify.components.MyFeedbackPanel;
import com.buskify.dao.ProjectDao;
import com.buskify.dao.StudentDao;
import com.buskify.dao.SupervisorDao;
import com.buskify.entity.Project;
import com.buskify.entity.Student;
import com.buskify.entity.Supervisor;

@Log4j
public class AllocationPanel extends Panel {

	private List<AllocationResult> allocationResult;
	@Getter
	@Setter
	private int nosOfIters;
	@Getter
	@Setter
	private long timeTaken;
	@Getter
	@Setter
	private boolean allocationCompleted;

	private WebMarkupContainer resultContainer = null;
	private final int ROW = 10;

	public AllocationPanel(String id) {
		super(id);

		FeedbackPanel feedback = new MyFeedbackPanel("feedback",
				new ComponentFeedbackMessageFilter(this));
		add(feedback);

		add(new ConfirmationLink("runHSMLink",
				"Are you sure you want to run Algorithm?") {
			@Override
			public void onClick() {
				log.debug("Ran HSM Algorithm Successfully");
				doSMAlgorithm();
				AllocationPanel.this
						.info("Sucess!! Ran Hybrid Stable Marriage Algorithm Successfully");
			}
		});
		addResultView();
		add(new AjaxFallbackLink("toggleLink") {

			@Override
			public void onClick(AjaxRequestTarget target) {
				boolean visible = !resultContainer.isVisible();
				resultContainer.setVisible(visible);
				if (target != null) {
					target.add(resultContainer);
				}
			}
		});
	}

	private void addResultView() {
		resultContainer = new WebMarkupContainer("resultContainer");
		resultContainer.setOutputMarkupId(true);
		resultContainer.setOutputMarkupPlaceholderTag(true);
		resultContainer.setVisible(false);
		add(resultContainer);

		WebMarkupContainer emptyListMessageContainer = new WebMarkupContainer(
				"emptyListMessage") {
			@Override
			protected void onConfigure() {
				super.onConfigure();
				if (allocationResult == null || allocationResult.size() == 0) {
					setVisible(true);
				} else {
					setVisible(false);
				}
			}
		};
		emptyListMessageContainer.setOutputMarkupId(true);
		emptyListMessageContainer.setOutputMarkupPlaceholderTag(true);
		resultContainer.add(emptyListMessageContainer);

		IModel<ArrayList<AllocationResult>> allocationListModel = new Model<ArrayList<AllocationResult>>() {
			@Override
			public ArrayList<AllocationResult> getObject() {
				return (ArrayList<AllocationResult>) allocationResult;
			}
		};

		WebMarkupContainer dataListContainer = new WebMarkupContainer(
				"dataListContainer") {
			@Override
			protected void onConfigure() {
				super.onConfigure();
				if (allocationResult == null || allocationResult.size() == 0) {
					setVisible(false);
				} else {
					setVisible(true);
				}
			}
		};
		dataListContainer.setOutputMarkupPlaceholderTag(true);
		resultContainer.add(dataListContainer);

		dataListContainer.add(new Label("nosOfIters",
				new PropertyModel<Integer>(this, "nosOfIters")));
		dataListContainer.add(new Label("timeTaken",
				new PropertyModel<Integer>(this, "timeTaken")));
		dataListContainer.add(new Label("allocateCompleted",
				new PropertyModel<Integer>(this, "allocationCompleted")));

		PageableListView<AllocationResult> allocationLV = new PageableListView<AllocationResult>(
				"allocationLV", allocationListModel, ROW) {

			@Override
			protected void populateItem(ListItem<AllocationResult> item) {
				final AllocationResult result = item.getModelObject();
				item.add(new Label("snos", Model.of(item.getIndex() + 1)));
				item.add(new Label("number", Model.of(result.getNumber())));
				item.add(new Label("projectTitle", Model.of(result
						.getProjectTitle())));
				item.add(new Label("supervisor", Model.of(result
						.getSupervisorFullName())));
			}

			@Override
			protected void onConfigure() {
				super.onConfigure();
				if (allocationResult == null || allocationResult.size() == 0) {
					setVisible(false);
				} else {
					setVisible(true);
				}
			}
		};

		dataListContainer.add(allocationLV);
		dataListContainer.add(new PagingNavigator("pagingNavigator",
				allocationLV));
	}

	private void doSMAlgorithm() {
		SMAlgorithm algo = new SMAlgorithm();
		algo.doWork();

		// some stats
		nosOfIters = algo.getIterations();
		timeTaken = algo.getTimeTaken();
		allocationCompleted = algo.checkIfAllStudentIsAssigned();

		// build List for view
		ProjectDao projectDao = new ProjectDao();
		StudentDao studentDao = new StudentDao();
		SupervisorDao supervisorDao = new SupervisorDao();

		allocationResult = new ArrayList<AllocationResult>();
		List<Student> students = algo.getStudents();

		for (Student student : students) {
			Project project = null;
			Supervisor supervisor = null;
			if (student.getAssignedProject() != null) {
				project = projectDao.load(student.getAssignedProject().getId());
				if (project.getSupervisor() != null) {
					supervisor = supervisorDao.load(project.getSupervisor()
							.getId());
				}
			}

			String number = student.getNumber();
			String projectTitle = (project != null ? project.getTitle() : "N/A");
			String supervisorFullName = (supervisor != null ? supervisor
					.getFullName() : "N/A");

			AllocationResult result = new AllocationResult(number,
					projectTitle, supervisorFullName);
			allocationResult.add(result);
		}
		log.debug("Allocation Result Size is: " + allocationResult.size());

		// save allocation result to db
		studentDao.saveAll(students);
	}

	@Data
	@AllArgsConstructor
	private class AllocationResult implements Serializable {
		private String number;
		private String projectTitle;
		private String supervisorFullName;
	}

}