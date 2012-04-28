package ru.crystalservice.setquery;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Configuration {

	public static List<ServerBD> getServers() throws ConfigurationException {
		File f = null;

		f = new File("config.xml");
		if (f.exists()) {
			return getServersFromFile(f);
		}

		return null;
	}

	private static List<ServerBD> getServersFromFile(File f) throws ConfigurationException {
		try {
			if (f.exists()) {
				FileInputStream fis = new FileInputStream(f);

				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(fis);
				return getServersFromXML(doc);
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new ConfigurationException("Ошибка чтения настроек из файла config.xml", e);
		}
	}

	public static List<ServerBD> getServersFromXML(String line) throws ParserConfigurationException, SAXException, IOException, ConfigurationException {

		ByteArrayInputStream mem = new ByteArrayInputStream(line.getBytes("WINDOWS-1251"));
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		mem.reset();
		Document doc = db.parse(mem);

		return getServersFromXML(doc);
	}

	private static List<ServerBD> getServersFromXML(Document doc) throws ParserConfigurationException, SAXException, IOException, ConfigurationException {

		List<ServerBD> serverArray = new ArrayList<ServerBD>();

		Element rootConfig = (Element) doc.getDocumentElement();
		if (!rootConfig.getTagName().equals("ROOT")) {
			throw new ConfigurationException("Bad root element");
		} else {

			NodeList nl = rootConfig.getChildNodes();
			for (int i = 0; i < nl.getLength(); i++) {
				Node exe = nl.item(i);
				if (exe instanceof Element) {
					if (exe.getNodeName().equalsIgnoreCase("Params")) {
						// TODO
					}

					if (exe.getNodeName().equalsIgnoreCase("Servers")) {
						NodeList nl2 = ((Element) exe).getChildNodes();
						for (int j = 0; j < nl2.getLength(); j++) {
							Node exes = nl2.item(j);
							if (exes instanceof Element) {
								if (exes.getNodeName().equalsIgnoreCase("Server")) {
									ServerBD server = new ServerBD((Element) exes);
									serverArray.add(server);
								}
							}
						}
					}
				}
			}
		}
		return serverArray;
	}
}
