package com.example.app.jobapp.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class RecordInfoSuccess extends Composite {

	private static RecordInfoSuccessUiBinder uiBinder = GWT.create(RecordInfoSuccessUiBinder.class);

	interface RecordInfoSuccessUiBinder extends UiBinder<Widget, RecordInfoSuccess> {
	}

	public RecordInfoSuccess() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
