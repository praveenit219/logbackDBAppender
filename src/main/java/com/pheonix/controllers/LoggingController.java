package com.pheonix.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoggingController {

	private static final Logger log = LoggerFactory.getLogger(LoggingController.class);

	
	@GetMapping(path = "/logback/test",  produces = "application/json")
	public ResponseEntity<String> processArtifactRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		log.info("entering api /logback/test");
		SamlAudit samlAudit = new SamlAudit();
		samlAudit.seteID("Enterprise APP ID");
		samlAudit.setIpaddresss(request.getRemoteAddr());
		samlAudit.setRequestid(UUID.randomUUID().toString());
		samlAudit.setService("Enterprise ServiceID ");
		samlAudit.setStatus("Processing");
		samlAudit.setAuthContext("authContext");
		samlAudit.setSubmissionDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		
		// helps to check for warn and then AUDIT# for retreiving details and use the datasource to insert into DB.
		log.warn("AUDIT# " + samlAudit.getSubmissionDate() + "," + samlAudit.getRequestid() + "," + samlAudit.geteID() + "," + samlAudit.getStatus() + "," + samlAudit.getService() + "," + samlAudit.getAuthContext() + "," + samlAudit.getIpaddresss());
		
		return null;
	}

}
