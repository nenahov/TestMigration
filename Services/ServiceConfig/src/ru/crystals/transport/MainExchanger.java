package ru.crystals.transport;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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

public class MainExchanger extends JPanel {

    public MainExchanger() {
        this("");
    }

    public MainExchanger(String defaultConfigNumber) {
        setLayout(new MigLayout("", "[][grow][]", "[][][][][][][][][][][grow]"));
        {
            JLabel lblIp = new JLabel("IP службы лицензирования:");
            add(lblIp, "cell 0 0,alignx trailing");
            JTextField edHaspRemoteHost = new JTextField();
            add(edHaspRemoteHost, "cell 1 0,growx");
            JButton btDefault = new JButton("");
            add(btDefault, "cell 2 0");
            SettingsValues.add(edHaspRemoteHost, btDefault, "HaspRemoteHost", "127.0.0.1");
        }

        JSeparator separator = new JSeparator();
        add(separator, "cell 0 1 3 1,grow");
        {
            JLabel label = new JLabel("Доступ к БД:");
            add(label, "cell 0 2,alignx trailing");
            CdbField cdbField = new CdbField();
            add(cdbField, "cell 1 2 2 1,grow");
            SettingsValues.add(cdbField, "CDB");
        }
        {
            JLabel label = new JLabel("Драйвер БД:");
            add(label, "cell 0 3,alignx trailing");
            JTextField edDrivers = new JTextField();
            add(edDrivers, "cell 1 3,growx");
            JButton btDefault = new JButton();
            add(btDefault, "flowx,cell 2 3");
            SettingsValues.add(edDrivers, btDefault, "drivers", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
            JButton btnOracle = new JButton();
            add(btnOracle, "cell 2 3");
            SettingsValues.add(edDrivers, btnOracle, "drivers", "com.microsoft.sqlserver.jdbc.SQLServerDriver oracle.jdbc.driver.OracleDriver");
            btnOracle.setText("+ Oracle");
        }
        {
            JLabel label = new JLabel("Номер конфигурации:");
            add(label, "cell 0 4,alignx trailing");
            JTextField edConfigNumber = new JTextField();
            add(edConfigNumber, "cell 1 4,growx");
            JButton btDefault = new JButton();
            add(btDefault, "cell 2 4");
            SettingsValues.add(edConfigNumber, btDefault, "ConfigNumber", defaultConfigNumber);
        }

        JSeparator separator_1 = new JSeparator();
        add(separator_1, "cell 0 5 3 1,grow");
        {
            JLabel label = new JLabel("Почтовые уведомления:");
            add(label, "cell 0 6,alignx trailing");
            JCheckBox chkAlarmer = new JCheckBox("");
            add(chkAlarmer, "cell 1 6");
            SettingsValues.add(chkAlarmer, "Alarmer", false);
        }

        JSeparator separator_2 = new JSeparator();
        add(separator_2, "cell 0 7 3 1,grow");
        {
            JLabel label = new JLabel("Уровень логирования:");
            add(label, "cell 0 8,alignx trailing");
            JComboBox cbDebugLevel = new JComboBox();
            add(cbDebugLevel, "cell 1 8,growx");
            cbDebugLevel.setModel(new DefaultComboBoxModel(new String[] {"ALL", "TRACE", "DEBUG", "INFO", "WARN", "ERROR", "FATAL", "OFF"}));
            JButton btDefault = new JButton();
            add(btDefault, "cell 2 8");
            SettingsValues.add(cbDebugLevel, btDefault, "DebugLevel", "DEBUG");
        }
        {
            JLabel label = new JLabel("Чтение настроек каждые, с:");
            add(label, "cell 0 9,alignx trailing");
            JTextField edRereadConfigTimeOut = new IntegerField();
            add(edRereadConfigTimeOut, "cell 1 9,growx");
            JButton btDefault = new JButton();
            add(btDefault, "cell 2 9");
            SettingsValues.add(edRereadConfigTimeOut, btDefault, "RereadConfigTimeOut", "10");
        }
        JPanel panel_1 = new PanelOther();
        add(panel_1, "cell 0 10 3 1,grow");

    }

    private static final long serialVersionUID = 1L;
}
