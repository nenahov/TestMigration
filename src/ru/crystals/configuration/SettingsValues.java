package ru.crystals.configuration;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ru.crystals.config.CdbField;

public abstract class SettingsValues<T extends JComponent> {

    private static List<SettingsValues< ? >> values = new ArrayList<SettingsValues< ? >>();

    private static List<String> excluded = new ArrayList<String>();

    protected T ui;
    protected String key;
    protected String defaultValue;

    public static void add(JTextField ui, JButton btnDefault, String paramName, String defaultValue) {
        values.add(new SettingsValuesString(ui, btnDefault, paramName, defaultValue));
    }

    public static void add(JComboBox ui, JButton btnDefault, String paramName, String defaultValue) {
        values.add(new SettingsValuesComboBox(ui, btnDefault, paramName, defaultValue));
    }

    public static void add(JRadioButton ui, String paramName, String value) {
        values.add(new SettingsValuesChoose(ui, paramName, value));
    }

    public static void add(JTextArea ui, String... prefix) {
        values.add(new SettingsValuesOther(ui, prefix));
    }

    public static void add(JCheckBox ui, String paramName, boolean defaultValue) {
        values.add(new SettingsValuesCheckBox(ui, paramName, defaultValue));
    }

    public static void add(CdbField ui, String paramName) {
        values.add(new SettingsValuesCDB(ui, paramName));
    }

    public SettingsValues(T ui, String key, String defaultValue, JButton btnDefault) {
        super();
        this.ui = ui;
        this.key = key;
        this.defaultValue = defaultValue;
        if (key != null && !key.isEmpty()) {
            ui.setToolTipText(key);
        }
        if (btnDefault != null) {
            btnDefault.setToolTipText(key + "=" + defaultValue);
            setBtnDefault(btnDefault);
        }
    }

    private void setBtnDefault(JButton btnDefault) {
        btnDefault.setText("Default");
        btnDefault.setIcon(new ImageIcon(SettingsValues.class.getResource("/res/Default.png")));
        btnDefault.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setDefaultToEditor();
            }
        });
    }

    protected void setDefaultToEditor() {
        // Must ovverride
    }

    protected void setValue(String key, String value) {
        if (value != null && !value.isEmpty() && !value.equalsIgnoreCase("null")) {
            Settings.Instance.put(key, value);
        } else {
            Settings.Instance.remove(key);
        }
    }

    public static void clearParams() {
        values = new ArrayList<SettingsValues< ? >>();
        excluded = new ArrayList<String>();
    }

    public static void saveAll() {
        for (SettingsValues< ? > param : values) {
            param.save();
        }
        Settings.Instance.save();
    }

    protected abstract void save();

    protected static List<String> getOtherKeys(String... prefix) {
        List<String> result = new ArrayList<String>();
        Set<Object> keySet = Settings.Instance.getProp().getAllProperies().keySet();
        for (Object keyO : keySet) {
            String key = ((String) keyO).trim();
            if (prefix != null && prefix.length > 0) {
                for (String pr : prefix) {
                    if (key.startsWith(pr.toLowerCase())) {
                        result.add(key);
                        break;
                    }
                }
            } else {
                result.add(key);
            }
        }
        for (SettingsValues< ? > param : values) {
            result.remove(param.key.toLowerCase());
        }
        result.removeAll(excluded);
        excluded.addAll(result);
        Collections.sort(result);
        return result;
    }
}
