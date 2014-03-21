package ru.crystals.configuration;

import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JTextArea;

public class SettingsValuesOther extends SettingsValues<JTextArea> {

    public SettingsValuesOther(JTextArea ui, String... prefix) {
        super(ui, "", "", null);
        List<String> otherKeys = SettingsValues.getOtherKeys(prefix);
        StringBuilder sb = new StringBuilder();
        for (String key : otherKeys) {
            sb.append(key + "=" + Settings.Instance.get(key, "") + "\n");
        }
        ui.setText(sb.toString());
    }

    @Override
    protected void save() {
        StringTokenizer st = new StringTokenizer(ui.getText(), "\n\r");
        while (st.hasMoreTokens()) {
            String s = st.nextToken();
            int k = s.indexOf('=');
            if (k > -1) {
                String key = s.substring(0, k);
                String value = s.substring(k + 1);
                setValue(key.trim(), value.trim());
            }
        }
    }
}
