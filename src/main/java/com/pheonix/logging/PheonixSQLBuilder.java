package com.pheonix.logging;

public class PheonixSQLBuilder {

	static String buildInsertSQL(PheonixDBNameResolver dbNameResolver) {
		StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ");
		sqlBuilder.append(dbNameResolver.getTableName(PheonixTableName.pheonix_audit)).append(" (");
		sqlBuilder.append(dbNameResolver.getColumnName(PhoenixColumnName.submissionDate)).append(", ");
		sqlBuilder.append(dbNameResolver.getColumnName(PhoenixColumnName.requestid)).append(", ");
		sqlBuilder.append(dbNameResolver.getColumnName(PhoenixColumnName.eID)).append(", ");
		sqlBuilder.append(dbNameResolver.getColumnName(PhoenixColumnName.status)).append(", ");
		sqlBuilder.append(dbNameResolver.getColumnName(PhoenixColumnName.service)).append(", ");
		sqlBuilder.append(dbNameResolver.getColumnName(PhoenixColumnName.authContext)).append(", ");
		sqlBuilder.append(dbNameResolver.getColumnName(PhoenixColumnName.ipaddresss)).append(") ");
		sqlBuilder.append("VALUES (?,?,?,?,?,?,?)");
		return sqlBuilder.toString();
	}


}
