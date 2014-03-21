package ru.crystals.configuration;

import javax.swing.JButton;
import javax.swing.JTextField;

public class SettingsValuesString extends SettingsValues<JTextField> {

    public SettingsValuesString(JTextField ui, JButton btnDefault, String key, String defaultValue) {
        super(ui, key, defaultValue, btnDefault);
        ui.setText(Settings.Instance.get(key, ""));
    }

    @Override
    protected void setDefaultToEditor() {
        ui.setText(defaultValue);
    }

    @Override
    public void save() {
        String value = ui.getText().trim();
        setValue(key, value);
    }

}
