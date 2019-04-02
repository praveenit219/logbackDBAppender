package com.pheonix.logging;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ch.qos.logback.classic.db.DBAppender;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class PheonixAuditDBAppender extends DBAppender {

	final static Logger log = LoggerFactory.getLogger(PheonixAuditDBAppender.class);

	protected String insertPropertiesSQL; 
	protected String insertExceptionSQL; 
	protected String insertSQL; 
	protected static final Method GET_GENERATED_KEYS_METHOD; 

	private PheonixDBNameResolver dbNameResolver; 


	@Autowired
	DataSource dataSource;

	public Connection getConnection() throws SQLException {
		if(dataSource!=null)
			return	dataSource.getConnection();
		return getConnectionSource().getConnection();
	}


	static { 
		Method getGeneratedKeysMethod; 
		try { 
			getGeneratedKeysMethod = PreparedStatement.class.getMethod("getGeneratedKeys", (Class[]) null); 
		} catch (Exception ex) { 
			getGeneratedKeysMethod = null; 
		} 
		GET_GENERATED_KEYS_METHOD = getGeneratedKeysMethod; 
	} 


	public PheonixAuditDBAppender(){}


	public PheonixDBNameResolver getDbNameResolver() {
		return dbNameResolver;
	}


	public void setDbNameResolver(PheonixDBNameResolver dbNameResolver) {
		this.dbNameResolver = dbNameResolver;
	}

	@Override 
	public void start() { 

		super.start();	 
		if (dbNameResolver == null) 
			dbNameResolver = new PheonixDBNameResolver(); 
		insertSQL = PheonixSQLBuilder.buildInsertSQL(dbNameResolver); 
	} 

	@Override 
	protected void subAppend(ILoggingEvent event, Connection connection, PreparedStatement insertStatement) throws Throwable { 

		
		boolean isAuditable = checkForAudit(event);
		
		if(isAuditable) {
			bindLoggingEventWithInsertStatement(insertStatement, event);
			System.out.println("===      INSERT STATEMENT      ===    " + insertStatement);
			int updateCount = -1;
			try {
				updateCount = insertStatement.executeUpdate();
			} catch (Exception e) {
				log.error(" ERROR   ");
				e.printStackTrace();
			} 
			
			System.out.println(" updateCount = "+ updateCount);

			if (updateCount != 1) { 
				log.error(" ERROR  IT IS ");
				addWarn("Failed to insert loggingEvent"); 
			} 
		}
		
	} 

	private boolean checkForAudit(ILoggingEvent event) {
		String logdata = event.getFormattedMessage();
		if(logdata.contains("AUDIT#")) {
			return true;
		}
		return false;
	}


	protected void secondarySubAppend(ILoggingEvent event, 
			Connection connection, long eventId) throws Throwable { 
	} 

	private void bindLoggingEventWithInsertStatement(PreparedStatement stmt, ILoggingEvent event) throws SQLException { 
		String logdata = event.getFormattedMessage();
		log.debug(" logdata   = "+ logdata);	

		parseLogData(logdata.substring(logdata.indexOf("#")+1), stmt);


	} 

	private void parseLogData(String logdata, PreparedStatement stmt) throws SQLException {

		String requestid = null;
		String submission_date = null;
		String eID = null;
		String status = null;
		String service = null;
		String authnContextClassRef = null;
		String ipaddresss = null;


		try {
			if(logdata.contains(",")) {


				String[] entries = logdata.split(",");
				if (null != entries && entries.length > 0) {
					submission_date = entries[0]; 
					requestid = entries[1];
					eID = entries[2];
					status = entries[3];
					service = entries[4];
					authnContextClassRef = entries[5];
					ipaddresss = entries[6];

				}

				stmt.setString(1, submission_date); 
				stmt.setString(2, requestid); 
				stmt.setString(3, eID); 
				stmt.setString(4, status); 
				stmt.setString(5, service);
				stmt.setString(6, authnContextClassRef); 
				stmt.setString(7, ipaddresss); 
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			requestid = null;
			submission_date = null;
			eID = null;
			status = null;
			service = null;
			authnContextClassRef = null;
			ipaddresss = null;

		}

	}



	@Override 
	protected Method getGeneratedKeysMethod() { 
		return GET_GENERATED_KEYS_METHOD; 
	} 

	@Override 
	protected String getInsertSQL() { 
		return insertSQL; 
	} 

	protected void insertProperties(Map<String, String> mergedMap, 
			Connection connection, long eventId) throws SQLException {

	} 

}
