package ru.crystals.configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map.Entry;
import java.util.Properties;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.Attributes;

/**
 * Обновляемый список параметров, используемых при обмене и при работе с данными.
 * 
 * @author Владимир Ненахов
 */
public class ExchangeProperties {

    private Properties properties = null;

    public ExchangeProperties() {
        properties = new Properties();
    }

    public ExchangeProperties(Properties defaults) {
        properties = new Properties(defaults);
    }

    public ExchangeProperties(Element element) {
        properties = new Properties();
        updateDataFromElement(element);
    }

    public ExchangeProperties(ExchangeProperties defaults) {
        properties = new Properties(defaults.getAllProperies());
    }

    public ExchangeProperties(Attributes attrs) {
        properties = new Properties();
        if (attrs != null) {
            for (int i = 0; i < attrs.getLength(); i++) {
                this.put(attrs.getQName(i), attrs.getValue(i));
            }
        }
    }

    public ExchangeProperties(File file, String encoding) {
        this.properties = new Properties();
        if (file.exists()) {
            Properties pTempCaseSensitive = new Properties();
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
                pTempCaseSensitive.load(br);
            } catch (Exception e) {
                throw new RuntimeException("Error Loading Properties File '" + file.getName() + "'!  Unable to continue Operation.");
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            for (Entry<Object, Object> en : pTempCaseSensitive.entrySet()) {
                put(((String) en.getKey()).toLowerCase(), (String) en.getValue());
            }
        }
    }

    private String getKey(String key) {
        if (key == null)
            key = "";
        StringBuilder s = new StringBuilder(key.toLowerCase());
        if (key.length() > 0 && ((key.charAt(0) == ':') || (key.charAt(0) == '_'))) {
            s = s.deleteCharAt(0);
        }
        return s.toString();
    }

    /**
     * Изменяем значение параметра.
     * 
     * @param key
     *            - ключ
     * @param value
     *            - значение
     */
    public void put(String key, String value) {
        if (value == null)
            value = "null";
        properties.setProperty(getKey(key), value);
    }

    public boolean existsKey(String key) {
        return properties.getProperty(getKey(key), null) != null;
    }

    /**
     * Меняем параметр, если такой уже есть в списке.
     * 
     * @param key
     * @param value
     */
    public void putIfExists(String key, String value) {
        if (existsKey(key)) {
            put(key, value);
        }
    }

    /**
     * Меняем (добавляем) параметр, только если его еще нет в списке.
     * 
     * @param key
     * @param value
     */
    public void putIfNotExists(String key, String value) {
        if (!existsKey(key)) {
            put(key, value);
        }
    }

    /**
     * Получаем значение параметра.
     * 
     * @param key
     *            - ключ
     * @return - возвращаемое значение
     */
    public String get(String key) {
        String value = properties.getProperty(getKey(key));
        if ((value != null) && (value.equalsIgnoreCase("null"))) {
            value = null;
        }
        return value;
    }

    /**
     * Получаем значение параметра.
     * 
     * @param key
     *            - ключ
     * @param defaultValue
     *            - значение по умолчанию
     * @return - возвращаемое значение
     */
    public String get(String key, String defaultValue) {
        // return properties.getProperty(getKey(key), defaultValue);
        // так нельзя, если будем использовать defaultProperties
        String s = get(key);
        if (s == null) {
            s = defaultValue;
        }
        return s;
    }

    public Properties getAllProperies() {
        return properties;
    }

    public void updateDataFromExchangeProperties(ExchangeProperties update) {
        if (update != null) {
            for (Entry<Object, Object> en : update.getAllProperies().entrySet()) {
                put((String) en.getKey(), (String) en.getValue());
            }
        }
    }

    public void updateDataFromElement(Element element) {
        if (element != null) {
            NamedNodeMap nnm = element.getAttributes();
            for (int i = 0; i < nnm.getLength(); i++) {
                this.put(nnm.item(i).getNodeName(), nnm.item(i).getNodeValue());
            }
        }
    }

    public void fillElement(Element element) {
        if (element != null) {
            for (Entry<Object, Object> en : getAllProperies().entrySet()) {
                element.setAttribute((String) en.getKey(), (String) en.getValue());
            }
        }
    }

    public void fillElementUpperCaseKeys(Element element) {
        if (element != null) {
            for (Entry<Object, Object> en : getAllProperies().entrySet()) {
                element.setAttribute(((String) en.getKey()).toUpperCase(), (String) en.getValue());
            }
        }
    }

    public ExchangeProperties createChildPropFromElement(Element element) {
        ExchangeProperties prop = new ExchangeProperties(getAllProperies());
        prop.updateDataFromElement(element);
        return prop;
    }
}
