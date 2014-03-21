package ru.crystals.scheduler;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import ru.crystals.config.CdbField;
import ru.crystals.config.IntegerField;
import ru.crystals.config.PanelOther;
import ru.crystals.configuration.SettingsValues;

public class MainScheduler extends JPanel {

    public MainScheduler() {
        setLayout(new MigLayout("", "[][grow][]", "[][][][][][grow]"));

        {
            JLabel label = new JLabel("Доступ к БД:");
            add(label, "cell 0 0,alignx trailing");
            CdbField cdbField = new CdbField();
            add(cdbField, "cell 1 0 2 1,grow");
            SettingsValues.add(cdbField, "CDB");
        }
        {
            JLabel label = new JLabel("Драйвер БД:");
            add(label, "cell 0 1,alignx trailing");
            JTextField edDrivers = new JTextField();
            add(edDrivers, "cell 1 1,growx");
            JButton btDefault = new JButton();
            add(btDefault, "cell 2 1");
            SettingsValues.add(edDrivers, btDefault, "drivers", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
        }

        JSeparator separator = new JSeparator();
        add(separator, "cell 0 2 3 1,grow");
        {
            JLabel label = new JLabel("Уровень логирования:");
            add(label, "cell 0 3,alignx trailing");
            JComboBox cbDebugLevel = new JComboBox();
            add(cbDebugLevel, "cell 1 3,growx");
            cbDebugLevel.setModel(new DefaultComboBoxModel(new String[] {"ALL", "TRACE", "DEBUG", "INFO", "WARN", "ERROR", "FATAL", "OFF"}));
            JButton btDefault = new JButton();
            add(btDefault, "cell 2 3");
            SettingsValues.add(cbDebugLevel, btDefault, "DebugLevel", "DEBUG");
        }
        {
            JLabel label = new JLabel("Чтение настроек каждые, с:");
            add(label, "cell 0 4,alignx trailing");
            JTextField edRereadConfigTimeOut = new IntegerField();
            add(edRereadConfigTimeOut, "cell 1 4,growx");
            JButton btDefault = new JButton();
            add(btDefault, "cell 2 4");
            SettingsValues.add(edRereadConfigTimeOut, btDefault, "RereadConfigTimeOut", "10");
        }
        JPanel panel_1 = new PanelOther();
        add(panel_1, "cell 0 5 3 1,grow");
    }

    private static final long serialVersionUID = 1L;
}
