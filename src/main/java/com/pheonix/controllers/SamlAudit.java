package com.pheonix.controllers;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="saml_audit")
public class SamlAudit {

	@Column(name = "submissionDate")
	private String submissionDate;
	
	@Id
	@Column(name = "requestid")
	private String requestid;
	
	@Column(name = "eID")
	private String eID;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "service")
	private String service;
	
	@Column(name = "authContext")
	private String authContext;
	
	@Column(name = "ipaddresss")
	private String ipaddresss;
	
	
	
	public String getSubmissionDate() {
		return submissionDate;
	}
	public void setSubmissionDate(String submissionDate) {
		this.submissionDate = submissionDate;
	}
	public String getRequestid() {
		return requestid;
	}
	public void setRequestid(String requestid) {
		this.requestid = requestid;
	}
	public String geteID() {
		return eID;
	}
	public void seteID(String eID) {
		this.eID = eID;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	
	
	
	public String getAuthContext() {
		return authContext;
	}
	public void setAuthContext(String authContext) {
		this.authContext = authContext;
	}
	public String getIpaddresss() {
		return ipaddresss;
	}
	public void setIpaddresss(String ipaddresss) {
		this.ipaddresss = ipaddresss;
	}
	
	
	
}
