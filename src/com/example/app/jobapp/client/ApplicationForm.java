package com.example.app.jobapp.client;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import com.example.app.jobapp.shared.ApplicantInfo;
import com.example.app.jobapp.shared.ApplicantInfoException;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ApplicationForm extends Composite {

	private static ApplicationFormUiBinder uiBinder = GWT.create(ApplicationFormUiBinder.class);

	interface ApplicationFormUiBinder extends UiBinder<Widget, ApplicationForm> {
	}

	@UiField TextBox name;
	@UiField Label nameFeedback;

	@UiField RadioButton genderMale;
	@UiField RadioButton genderFemale;
	@UiField RadioButton genderOther;
	@UiField Label genderFeedback;

	@UiField TextBox email;
	@UiField Label emailFeedback;

	@UiField TextBox phoneNumber;
	@UiField Label phoneNumberFeedback;

	@UiField CheckBox positionDeveloper;
	@UiField CheckBox positionDesigner;
	@UiField CheckBox positionMarketing;
	@UiField CheckBox positionAnalyst;
	@UiField Label positionFeedback;
	@UiField Label degreeFeedback;

	@UiField ListBox degree;
	@UiField TextArea cv;
	@UiField Button submit;
	@UiField Button hackedSubmit;

	ApplicantInfo applicantInfo = new ApplicantInfo();

	public ApplicationForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("submit")
	void onSubmitClick(ClickEvent e) {
		//check required fields
		if(!checkRequireFields()) {
			Window.alert("Some questions are not answered correctly. Please check the highlighted items.");
			return;
		}

		//get non-required fields data
		applicantInfo.setDegree(degree.getSelectedValue());
		applicantInfo.setCv(cv.getText());

		//submit the form
		submitForm();

	}

	private boolean checkRequireFields() {
		boolean isOK = true;
		//check name field
		String nameStr = name.getText();
		if(nameStr.length() < 5) {
			showFeedbackLabel(nameFeedback);
			isOK = false;
		} else {
			hideFeedbackLabel(nameFeedback);
			applicantInfo.setName(nameStr);
		}

		//check gender field
		if(!genderMale.getValue() && !genderFemale.getValue() && !genderOther.getValue()) {
			showFeedbackLabel(genderFeedback);
			isOK = false;
		} else {
			hideFeedbackLabel(genderFeedback);
			if(genderMale.getValue()) {
				applicantInfo.setGender("male");
			} else if(genderFemale.getValue()) {
				applicantInfo.setGender("female");
			} else if(genderOther.getValue()) {
				applicantInfo.setGender("other");
			}
		}

		//check email field
		String emailStr = email.getText();
		if(emailStr.isEmpty() || 
				!emailStr.matches("^([a-zA-Z0-9_.\\-+])+@(([a-zA-Z0-9\\-])+\\.)+[a-zA-Z0-9]{2,4}$")) {
			showFeedbackLabel(emailFeedback);
			isOK = false;
		} else {
			hideFeedbackLabel(emailFeedback);
			applicantInfo.setEmail(emailStr);
		}

		//check phone number field
		String phoneNumberStr = phoneNumber.getText();
		if(phoneNumberStr.isEmpty() || 
				RegExp.compile("[^0-9]").exec(phoneNumberStr) != null ) {
			showFeedbackLabel(phoneNumberFeedback);
			isOK = false;
		} else {
			hideFeedbackLabel(phoneNumberFeedback);
			applicantInfo.setPhone(phoneNumberStr);
		}


		//check position field
		if(!positionDeveloper.getValue() && !positionDesigner.getValue() &&
				!positionMarketing.getValue() && !positionAnalyst.getValue()) {//none is checked
			showFeedbackLabel(positionFeedback);
			isOK = false;
		} else {
			hideFeedbackLabel(positionFeedback);
			Set<String> positions = new HashSet<>();
			if(positionAnalyst.getValue()) {
				positions.add("Business Analyst");
			} 
			if (positionDesigner.getValue()) {
				positions.add("UI Designer");
			}
			if (positionDeveloper.getValue()) {
				positions.add("Java Developer");
			}
			if (positionMarketing.getValue()) {
				positions.add("Marketing");
			}

			applicantInfo.setPositions(positions);
		}

		return isOK;
	}

	private void showFeedbackLabel(Label label) {
		label.setVisible(true);
	}

	private void hideFeedbackLabel(Label label) {
		label.setVisible(false);
	}

	private void submitForm() {
		Window.alert("The following infomation will be submitted:\n"
				+ "Name: " + applicantInfo.getName() + "\n" 
				+ "Gender: " + applicantInfo.getGender() + "\n"
				+ "Email: " + applicantInfo.getEmail() + "\n"
				+ "Phone number: " + applicantInfo.getPhone() + "\n"
				+ "Interested position: " + applicantInfo.getPositions() + "\n"
				+ "Degree: " + applicantInfo.getDegree() + "\n"
				+ "CV: " + applicantInfo.getCv()
				);

		//actual submission code goes here
		//create a remote service proxy 
		RecordInfoServiceAsync recordInfoService = GWT.create(RecordInfoService.class);

		//call the remote method to record applicant info
		recordInfoService.recordInfo(applicantInfo, new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				//applicant information recorded successfully, show the success page.
				RootPanel.get().clear();
				RootPanel.get().add(new RecordInfoSuccess());
			}

			@Override
			public void onFailure(Throwable caught) {
				//errors occur, check error types and show feedback accordingly
				RootPanel.get().clear();
				if (caught instanceof ApplicantInfoException) {
					ApplicantInfoException e = (ApplicantInfoException) caught;
					RootPanel.get().add(new RecordInfoFailed(e.getMessage()));
				} else {
					RootPanel.get().add(
							new RecordInfoFailed("Unexpected errors occured! Please contact administrator."));
				}

			}
		});

	}

	@UiHandler("hackedSubmit")
	void onHackedSubmitClick(ClickEvent e)	{
		//create an ApplicantInfo object with invalid info
		Set<String> interestedPosition = new HashSet<>();
		interestedPosition.add("Boss");

		ApplicantInfo info = new ApplicantInfo(
				"abcd", //name too short
				"not valid gender", 
				"myemail AT gmail DOT com",  //invalid email address
				"1234abcd", //phone number contains non-number characters
				interestedPosition, //not one of the positions listed
				"High school diploma", // not one of the options
				"My CV");

		//create a remote service proxy 
		RecordInfoServiceAsync recordInfoService = GWT.create(RecordInfoService.class);
		recordInfoService.recordInfo(info, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				//errors occur, check error types and show feedback accordingly
				RootPanel.get().clear();
				if (caught instanceof ApplicantInfoException) {
					ApplicantInfoException e = (ApplicantInfoException) caught;
					RootPanel.get().add(new RecordInfoFailed(e.getMessage()));
				} else {
					RootPanel.get().add(
							new RecordInfoFailed("Unexpected errors occured! Please contact administrator."));
				}
			}

			@Override
			public void onSuccess(Void result) {
				RootPanel.get().clear();
				RootPanel.get().add(new RecordInfoSuccess());
			}
		});

	}

}
