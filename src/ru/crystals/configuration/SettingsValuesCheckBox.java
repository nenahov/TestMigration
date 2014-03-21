package ru.crystals.configuration;

import javax.swing.JCheckBox;

public class SettingsValuesCheckBox extends SettingsValues<JCheckBox> {

    public SettingsValuesCheckBox(JCheckBox ui, String key, boolean defaultValue) {
        super(ui, key, defaultValue ? "1" : "0", null);
        String value = Settings.Instance.get(key, defaultValue ? "1" : "0");
        ui.setSelected(!value.equals("0") && !value.equalsIgnoreCase("false"));
    }

    @Override
    public void save() {
        setValue(key, ui.isSelected() ? "1" : "0");
    }

}
