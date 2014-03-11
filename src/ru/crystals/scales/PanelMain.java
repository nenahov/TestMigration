package ru.crystals.scales;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import ru.crystals.config.IntegerField;
import ru.crystals.configuration.SettingsValues;

class PanelMain extends JPanel {

    private static final long serialVersionUID = 1L;

    public PanelMain() {
        setLayout(new MigLayout("", "[][grow][]", "[][][][][][][]"));
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
            JLabel label = new JLabel("Невесовые префиксы:");
            add(label, "cell 0 2,alignx trailing");
            JTextField edNonWeightPrefixes = new JTextField();
            add(edNonWeightPrefixes, "cell 1 2,growx");
            JButton btDefault = new JButton();
            add(btDefault, "cell 2 2");
            SettingsValues.add(edNonWeightPrefixes, btDefault, "NonWeightPrefixes", "5");
        }
        JSeparator separator_ = new JSeparator();
        add(separator_, "cell 0 3 3 1,grow");
        {
            JLabel label_1 = new JLabel("Порт мониторинга:");
            add(label_1, "cell 0 4,alignx trailing");
            JTextField edMonitorListenerPort = new IntegerField();
            add(edMonitorListenerPort, "cell 1 4,growx");
            JButton btDefault = new JButton();
            add(btDefault, "cell 2 4");
            SettingsValues.add(edMonitorListenerPort, btDefault, "MonitorListenerPort", "6500");
        }
        {
            JLabel label_1 = new JLabel("Таймаут мониторинга, мс:");
            add(label_1, "cell 0 5,alignx trailing");
            JTextField edMonitorSocketTimeout = new IntegerField();
            add(edMonitorSocketTimeout, "cell 1 5,growx");
            JButton btDefault = new JButton();
            add(btDefault, "cell 2 5");
            SettingsValues.add(edMonitorSocketTimeout, btDefault, "MonitorSocketTimeout", "20000");
        }

        JSeparator separator_1 = new JSeparator();
        add(separator_1, "cell 0 6 3 1,grow");
    }
}
