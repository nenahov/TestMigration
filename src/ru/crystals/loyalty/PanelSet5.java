package ru.crystals.loyalty;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import ru.crystals.config.CdbField;
import ru.crystals.config.PanelOther;
import ru.crystals.configuration.SettingsValues;

public class PanelSet5 extends JPanel {

    private static final long serialVersionUID = 1L;

    public PanelSet5() {
        setLayout(new MigLayout("", "[][grow]", "[][][grow]"));

        JLabel label = new JLabel("Доступ к БД:");
        add(label, "cell 0 0,alignx trailing");

        CdbField cdbField = new CdbField();
        add(cdbField, "cell 1 0,grow");
        SettingsValues.add(cdbField, "SET.CDB");

        JLabel label_1 = new JLabel("Доступ к БД по картам:");
        label_1.setToolTipText("(Если не указан, то используется верхний параметр)");
        add(label_1, "cell 0 1");

        CdbField cdbField_1 = new CdbField();
        add(cdbField_1, "cell 1 1,grow");
        SettingsValues.add(cdbField_1, "CARDS.CDB");

        JPanel panel = new PanelOther("Set5.", "setv5.", "set.", "cards.");
        add(panel, "cell 0 2 2 1,grow");
    }

}
