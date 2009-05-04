package ru.crystalservice.setquery;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class Informer {

	private Properties prop = new Properties();
	private Connection conn;
	private CallableStatement cs;
	// private ResultSet rs;
	private IWriterMessages iWriterMessages = null;
	private String separator = "\t";
	private ServerBD lastServerBD = null;
	private ServerBD curServerBD = null;
	private boolean showTitleShopName = true;

	private void showError(String title, Exception e) {
		/*
		 * if (iWriterMessages != null) { //iWriterMessages.
		 * iWriterMessages.append(title + ": " + e.getMessage() + "\n"); }
		 */
		if (curServerBD != lastServerBD) {
			lastServerBD = curServerBD;
			iWriterMessages.putMsg(new RowMessage("================================================\n"));
			iWriterMessages.putMsg(new RowMessage(curServerBD.toString() + "\n"));
		}
		showResultMessage(title + ": " + e.getMessage());
	}

	private void showResultMessage(String msg) {
		if (iWriterMessages != null) {
			if (isShowTitleShopName() && (curServerBD != lastServerBD)) {
				lastServerBD = curServerBD;
				iWriterMessages.putMsg(new RowMessage("================================================\n"));
				iWriterMessages.putMsg(new RowMessage(curServerBD.toString() + "\n"));
			}
			iWriterMessages.putMsg(new RowMessage(msg + "\n"));
		}
	}

	public boolean Init() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public void connect() throws Exception {
		Properties properties = new Properties();
		properties.setProperty("user", prop.getProperty("user"));
		properties.setProperty("password", prop.getProperty("password"));
		properties.setProperty("autoReconnect", "true");
		properties.setProperty("SetBigStringTryClob", "true");
		try {
			DriverManager.setLoginTimeout(5);
			conn = DriverManager.getConnection(prop.getProperty("jdbcUrl"), properties);
		} catch (Exception e) {
			// TODO вывод сообщения об ошибке
			showError("Подключение к БД", e);
			throw e;
		}
	}

	public void disconnect() {// throws Exception {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				conn = null;
				// throw e;
			}
		}
		conn = null;
	}

	public String getInfo() {
		return "info";
	}

	public synchronized void execQuery(List<ServerBD> servers, String cmdCommand) {
		lastServerBD = null;
		for (ServerBD serverBD : servers) {
			execQuery(serverBD, cmdCommand);
		}
	}

	public synchronized void execQuery(ServerBD serverBD, String cmdCommand) {
		lastServerBD = null;
		prop.setProperty("jdbcUrl", serverBD.getUrl());
		prop.setProperty("user", serverBD.getUser());
		prop.setProperty("password", serverBD.getPassword());
		curServerBD = serverBD;
		fillList(cmdCommand);
	}

	private void showResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				// showResultMessage("-----");
				ResultSetMetaData md = rs.getMetaData();
				StringBuilder s = new StringBuilder("");
				for (int i = 1; i <= md.getColumnCount(); i++) {
					s.append(md.getColumnName(i) + getSeparator());
				}
				showResultMessage(s.toString());
				while (rs.next()) {
					s.setLength(0);
					for (int i = 1; i <= md.getColumnCount(); i++) {
						if (rs.getString(i) != null) {
							s.append(rs.getString(i) + getSeparator());
						} else {
							s.append("NULL" + getSeparator());
						}
					}
					showResultMessage(s.toString());
				}
			} catch (Exception e) {
				showError("Error", e);
			}
		}
	}

	private synchronized void fillList(String cmdCommand) {
		try {
			connect();
			// заполнение списка
			String sGo = "\nGO\n" + cmdCommand.toUpperCase() + "\nGO\n";
			cmdCommand = cmdCommand + "\n";
			int k1 = sGo.indexOf("\nGO\n");
			int k2 = sGo.indexOf("\nGO\n", k1 + "\nGO".length());
			String s = null;
			while (k2 > -1) {
				s = cmdCommand.substring(k1, k2 - "\nGO".length()).trim();
				k1 = k2;
				k2 = sGo.indexOf("\nGO\n", k1 + "\nGO".length());
				if (s != null && !s.equals("")) {
					cs = conn.prepareCall(s);
					try {
						cs.execute();
					} catch (Exception e) {
						showError("Ошибка", e);
						cs.getMoreResults();
					}

					showResultSet(cs.getResultSet());

					while (cs.getMoreResults()) {
						showResultSet(cs.getResultSet());
					}
				}
			}
		} catch (Exception e) {
			showError("Ошибка", e);
		} finally {
			disconnect();
		}
	}

	public void setResultComponent(IWriterMessages taResult) {
		this.iWriterMessages = taResult;
	}

	public boolean isShowTitleShopName() {
		return showTitleShopName;
	}

	public void setShowTitleShopName(boolean showTitleShopName) {
		this.showTitleShopName = showTitleShopName;
	}
}
