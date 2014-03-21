package ru.crystals.configuration;

import ru.crystals.config.CdbField;

public class SettingsValuesCDB extends SettingsValues<CdbField> {

    public SettingsValuesCDB(CdbField ui, String paramName) {
        super(ui, paramName, "", null);
        ui.setCDB(Settings.Instance.get(key, ""));
    }

    @Override
    protected void save() {
        setValue(key, ui.getCDB());
    }

}
