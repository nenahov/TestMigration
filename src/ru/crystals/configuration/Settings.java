package ru.crystals.configuration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public enum Settings {
    Instance;

    private ExchangeProperties prop = null;
    private File file = new File("Config.conf").getAbsoluteFile();
    private String encoding = "UTF-8";

    public void loadProperties() {
        loadProperties(file, encoding);
    }

    public void loadProperties(File file, String encoding) {
        System.setProperty("user.dir", file.getParentFile().getPath());
        this.file = file;
        this.encoding = encoding;
        prop = new ExchangeProperties(file, encoding);
        SettingsValues.clearParams();
    }

    public void save() {
        File dest = new File(file.getParent(), file.getName() + "." + new SimpleDateFormat("yyMMddHHmmss").format(new Date()));
        dest.getAbsolutePath();
        file.renameTo(dest);
        try {
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), encoding);
            try {
                prop.getAllProperies().store(writer, "Settings");
            } finally {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Settings() {
        loadProperties();
    }

    public ExchangeProperties getProp() {
        return prop;
    }

    /**
     * Получаем значение параметра.
     * 
     * @param key
     *            - ключ
     * @return - возвращаемое значение
     */
    public String get(String key) {
        return prop.get(key);
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
        return prop.get(key, defaultValue);
    }

    public void put(String key, String value) {
        prop.put(key, value);
    }

    public void remove(String key) {
        prop.getAllProperies().remove(key.toLowerCase());
    }

}
