package com.buskify.pages.admin.project;

import java.io.File;
import java.util.Arrays;
import java.util.List;

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
import com.buskify.util.ExcelUtil;

@Log4j
public class UploadProjectPanel extends Panel {
	public UploadProjectPanel(String id) {
		super(id);
		add(new UploadProjectForm("form"));
	}

	private class UploadProjectForm extends Form {
		private FileUploadField fileUploadField;

		private final List<String> uploadOptions = Arrays.asList(new String[] {
				"Overwrite Existing Data", "Append to Existing Data" });
		private String uploadChoice = uploadOptions.get(0);

		
		public UploadProjectForm(String id) {
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
					final FileUpload upload = fileUploadField
							.getFileUpload();
					boolean overwrite = false;
					if (upload != null) {
						overwrite = (uploadChoice == uploadOptions.get(0));
//						processFileUpload(uploads.get(0), overwrite);
					}
					log.debug("Overwrite: " + overwrite);
					UploadProjectForm.this.info("Upload Successfully");
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
			File file = new File(UploadProjectPanel.class.getResource("project_upload_template.xls").getFile());
			DownloadLink downloadLink = new DownloadLink("download", file);
			return downloadLink;
		}
	}
}
