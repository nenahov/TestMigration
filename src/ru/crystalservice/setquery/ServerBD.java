package ru.crystalservice.setquery;

import org.w3c.dom.Element;

public class ServerBD {
	private String name;
	private String url;
	// private String user;
	// private String password;
	private String ip = "127.0.0.1";
	private String database;
	private String user;
	private String password;

	public ServerBD(String name, String url, String ip, String database, String user, String password) {
		super();
		this.name = name;
		this.url = url;
		this.ip = ip;
		this.database = database;
		this.user = user;
		this.password = password;
	}

	public ServerBD(Element element) {
		name = element.getAttribute("name");
		ip = element.getAttribute("ip");
		database = element.getAttribute("database");
		user = element.getAttribute("user");
		password = element.getAttribute("password");
		url = element.getAttribute("url");
		if ((url == null) || (url.trim().equals(""))) {
			url = "jdbc:sqlserver://" + ip + ";databasename=" + database;
		}
	}

	@Override
	public String toString() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public String getIp() {
		return ip;
	}

	public String getDatabase() {
		return database;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

}
