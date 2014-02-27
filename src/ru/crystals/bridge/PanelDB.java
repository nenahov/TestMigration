package ru.crystals.bridge;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import ru.crystals.config.CdbField;
import ru.crystals.config.PanelOther;
import ru.crystals.configuration.SettingsValues;

class PanelDB extends JPanel {

    private static final long serialVersionUID = 1L;

    public PanelDB() {
        setLayout(new MigLayout("", "[][grow]", "[][grow]"));

        JLabel label = new JLabel("Доступ к БД:");
        add(label, "cell 0 0,alignx trailing");

        CdbField cdbField = new CdbField();
        add(cdbField, "cell 1 0,grow");
        SettingsValues.add(cdbField, "SETv5.CDB");

        JPanel panel = new PanelOther("SETv5");
        add(panel, "cell 0 1 2 1,grow");
    }

}
