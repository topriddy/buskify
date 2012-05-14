package com.buskify.pages.admin.student;


import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import lombok.extern.log4j.Log4j;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.lang.Bytes;

import com.buskify.dao.ProjectDao;
import com.buskify.entity.Project;
import com.buskify.pages.admin.project.UploadProjectPanel;
import com.buskify.util.ExcelUtil;

@Log4j
public class UploadStudentPanel extends Panel {

	
	public UploadStudentPanel(String id) {
		super(id);
		add(new UploadStudentForm("form"));
	}

	private class UploadStudentForm extends Form {
		private FileUploadField fileUploadField;

		private final List<String> uploadOptions = Arrays.asList(new String[] {
				"Overwrite Existing Data", "Append to Existing Data" });
		private String uploadChoice = uploadOptions.get(0);

		
		public UploadStudentForm(String id) {
			super(id);
			setMultiPart(true);
			setMaxSize(Bytes.kilobytes(300));


			add(fileUploadField = new FileUploadField("fileUploadField"){
				
			});

			RadioChoice uploadRadioChoice = new RadioChoice("uploadChoice",
					Model.of(uploadChoice), uploadOptions).setSuffix("").setPrefix("");
			add(uploadRadioChoice);
			add(new Button("upload") {

				@Override
				public void onError() {
					log.error("Error creating project");
				}

				@Override
				public void onSubmit() {
//					final FileUpload upload = fileUploadField
//							.getFileUpload();
//					boolean overwrite = false;
//					if (upload != null) {
//						overwrite = (uploadChoice == uploadOptions.get(0));
//					}
//					log.debug("Overwrite: " + overwrite);
//					processFileUpload(upload, overwrite);
					UploadStudentForm.this.info("Upload Successfully");
				}
			});
			add(getDownloadLink());
		}
		private void processFileUpload(FileUpload fileUpload,boolean overwrite){
			log.debug("Overwrite: " + overwrite);
			ProjectDao projectDao = new ProjectDao();
			if(overwrite){
				projectDao.deleteAll();
				log.debug("Deleted Existing Projects");
			}
			try{
				List<Project> projectList = ExcelUtil.extractProjects(fileUpload.getInputStream());
				projectDao.saveAll(projectList);
				log.debug("Saved Successfully :");
			}catch(Exception ex){
				log.error(ex);
			}
		}
		
		private DownloadLink getDownloadLink(){
			File file = new File(UploadStudentPanel.class.getResource("student_upload_template.xls").getFile());
			String uuid = UUID.randomUUID().toString();
			String fileName = String.format("student_upload_template%s.xls",uuid);
			DownloadLink downloadLink = new DownloadLink("download", file,fileName);
			return downloadLink;
		}
	}
}
