package ru.crystals.configuration;

import javax.swing.JRadioButton;

public class SettingsValuesChoose extends SettingsValues<JRadioButton> {

    private String value;

    public SettingsValuesChoose(JRadioButton ui, String key, String value) {
        super(ui, key, "", null);
        this.value = value;
        if (Settings.Instance.get(key, "").equalsIgnoreCase(value)) {
            ui.setSelected(true);
        }
    }

    @Override
    protected void save() {
        if (ui.isSelected()) {
            setValue(key, value);
        }
    }

}
