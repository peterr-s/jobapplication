/**
 * 
 */
package com.example.app.jobapp.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author xiaobin
 *
 */
public class RecordInfoFailed extends Composite {

	private static RecordInfoFailedUiBinder uiBinder = GWT.create(RecordInfoFailedUiBinder.class);

	interface RecordInfoFailedUiBinder extends UiBinder<Widget, RecordInfoFailed> {
	}


	@UiField Label errorMsg;
	
	public RecordInfoFailed(String msg) {
		initWidget(uiBinder.createAndBindUi(this));
		
		errorMsg.setText(msg);
	}

}
