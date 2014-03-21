package ru.crystals.cashagent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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
            JLabel label_1 = new JLabel("Порт для запросов от кассы:");
            add(label_1, "cell 0 2,alignx trailing");
            JTextField edMonitorListenerPort = new IntegerField();
            add(edMonitorListenerPort, "cell 1 2,growx");
            JButton btDefault = new JButton();
            add(btDefault, "cell 2 2");
            SettingsValues.add(edMonitorListenerPort, btDefault, "AgentListenerPort", "6510");
        }
        {
            JLabel label_1 = new JLabel("Таймаут, с:");
            add(label_1, "cell 0 3,alignx trailing");
            JTextField edMonitorSocketTimeout = new IntegerField();
            add(edMonitorSocketTimeout, "cell 1 3,growx");
            JButton btDefault = new JButton();
            add(btDefault, "cell 2 3");
            SettingsValues.add(edMonitorSocketTimeout, btDefault, "AgentSocketTimeout", "5");
        }
        {
            JLabel label_3 = new JLabel("Кодировка данных для кассы:");
            add(label_3, "cell 0 4,alignx trailing");
            JComboBox cbWinCashEncoding = new JComboBox();
            cbWinCashEncoding.setEditable(true);
            cbWinCashEncoding.setModel(new DefaultComboBoxModel(new String[] {"", "cp866", "windows-1251", "UTF-8"}));
            add(cbWinCashEncoding, "cell 1 4,growx");
            JButton btDefault = new JButton("");
            add(btDefault, "cell 2 4");
            SettingsValues.add(cbWinCashEncoding, btDefault, "CashEncoding", "cp866");
        }
        JSeparator separator_1 = new JSeparator();
        add(separator_1, "cell 0 5 3 1,grow");
        {
            JLabel label = new JLabel("Дополнительное логирование для отладки:");
            add(label, "cell 0 6,alignx trailing");
            JCheckBox chLogTimeOut = new JCheckBox("");
            add(chLogTimeOut, "cell 1 6");
            SettingsValues.add(chLogTimeOut, "debugLog", false);
        }
    }
}
