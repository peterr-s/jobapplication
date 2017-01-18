/**
 * 
 */
package com.example.app.jobapp.shared;

import java.io.Serializable;

/**
 * Exception thrown when applicant info passed to server invalid.
 * @author xiaobin
 *
 */
public class ApplicantInfoException extends Exception implements Serializable {
	String msg;
	
	public ApplicantInfoException() {
		
	}
	
	public ApplicantInfoException(String msg) {
		this.msg = msg;
	}
	
	@Override
	public String getMessage() {
		return msg;
	}
}
