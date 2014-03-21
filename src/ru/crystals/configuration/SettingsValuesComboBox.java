package ru.crystals.configuration;

import javax.swing.JButton;
import javax.swing.JComboBox;

public class SettingsValuesComboBox extends SettingsValues<JComboBox> {

    public SettingsValuesComboBox(JComboBox ui, JButton btnDefault, String key, String defaultValue) {
        super(ui, key, defaultValue, btnDefault);
        ui.setSelectedItem(Settings.Instance.get(key, ""));
    }

    @Override
    protected void setDefaultToEditor() {
        ui.setSelectedItem(defaultValue);
    }

    @Override
    public void save() {
        String value = ui.getSelectedItem().toString().trim();
        setValue(key, value);
    }

}
