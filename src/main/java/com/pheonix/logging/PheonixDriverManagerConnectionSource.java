package com.pheonix.logging;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Lazy;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import ch.qos.logback.core.db.ConnectionSourceBase;

@Lazy
public class PheonixDriverManagerConnectionSource extends ConnectionSourceBase {
   
	private String driverClass = null;
    private String url = null;

    private HikariConfig hikariConfig = null;
    private HikariDataSource dataSource = null;
    
    public void start() {
        try {
            if (driverClass != null) {
                Class.forName(driverClass);
                hikariConfig = new HikariConfig();
                createHikariConfigObject();
                discoverConnectionProperties();
            } else {
                addError("WARNING: No JDBC driver specified for logback DriverManagerConnectionSource.");
            }
        } catch (final ClassNotFoundException cnfe) {
            addError("Could not load JDBC driver class: " + driverClass, cnfe);
        } finally {
        	/*hikariConfig = null;
        	if(null!=dataSource) {
        		dataSource.close();
        	}*/
        }
    }

    
    private void createHikariConfigObject() {
    	hikariConfig.setJdbcUrl(url);
    	hikariConfig.setDriverClassName(driverClass);
    	hikariConfig.setUsername(getUser());
    	hikariConfig.setPassword(getPassword());    	
	}



    public DataSourceProperties dataSourceProperties() {
    	return new DataSourceProperties();
    }
    
    
  
    public DataSource dataSource() {
    	HikariDataSource dataSource = new HikariDataSource(hikariConfig);
    	
    	
    	return dataSource;
    	 
    }
    
    
    /**
     * @see ch.qos.logback.core.db.ConnectionSource#getConnection()
     */
    
  
    public Connection getConnection() throws SQLException {
        /*if (getUser() == null) {
            return DriverManager.getConnection(url);
        } else {
            return DriverManager.getConnection(url, getUser(), getPassword());
        }*/
    	
    	return dataSource().getConnection();
    }

    /**
     * Returns the url.
     * 
     * @return String
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the url.
     * 
     * @param url
     *          The url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Returns the name of the driver class.
     * 
     * @return String
     */
    public String getDriverClass() {
        return driverClass;
    }

    /**
     * Sets the driver class.
     * 
     * @param driverClass
     *          The driver class to set
     */
    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

}
