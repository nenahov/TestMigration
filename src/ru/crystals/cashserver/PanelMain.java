package ru.crystals.cashserver;

import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import ru.crystals.config.IntegerField;
import ru.crystals.configuration.SettingsValues;

class PanelMain extends JPanel {

    private static final long serialVersionUID = 1L;
    private final ButtonGroup buttonGroup = new ButtonGroup();

    public PanelMain() {
        setLayout(new MigLayout("", "[][grow][]", "[][][][][][][][][][][][][][][][][][][]"));
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
            JLabel label = new JLabel("Порт для запросов от DOS кассы:");
            add(label, "cell 0 2,alignx trailing");
            JTextField edDosCashPort = new IntegerField();
            add(edDosCashPort, "cell 1 2,growx");
            JButton btDefault = new JButton();
            add(btDefault, "cell 2 2");
            SettingsValues.add(edDosCashPort, btDefault, "DosCashPort", "6668");
        }
        {
            JLabel label_4 = new JLabel("Порция данных для DOS кассы:");
            add(label_4, "cell 0 3,alignx trailing");

            JTextField edCashDataCount = new IntegerField();
            add(edCashDataCount, "cell 1 3,growx");
            edCashDataCount.setColumns(10);

            JButton btDefault = new JButton();
            add(btDefault, "cell 2 3");
            SettingsValues.add(edCashDataCount, btDefault, "CashDataCount", "100");
        }
        JSeparator separator_ = new JSeparator();
        add(separator_, "cell 0 4 3 1,grow");
        {
            JLabel label_1 = new JLabel("Порт для запросов от WIN кассы:");
            add(label_1, "cell 0 5,alignx trailing");
            JTextField edWinCashPort = new IntegerField();
            add(edWinCashPort, "cell 1 5,growx");
            JButton btDefault = new JButton();
            add(btDefault, "cell 2 5");
            SettingsValues.add(edWinCashPort, btDefault, "WinCashPort", "9999");
        }
        {
            JLabel label_3 = new JLabel("Кодировка данных для Win-кассы:");
            add(label_3, "cell 0 6,alignx trailing");
            JComboBox cbWinCashEncoding = new JComboBox();
            cbWinCashEncoding.setEditable(true);
            cbWinCashEncoding.setModel(new DefaultComboBoxModel(new String[] {"", "windows-1251", "UTF-8"}));
            add(cbWinCashEncoding, "cell 1 6,growx");
            JButton btDefault = new JButton("");
            add(btDefault, "cell 2 6");
            SettingsValues.add(cbWinCashEncoding, btDefault, "WinCashEncoding", "windows-1251");
        }
        JSeparator separator_1 = new JSeparator();
        add(separator_1, "cell 0 7 3 1,grow");
        {
            JLabel label_2 = new JLabel("Порт для запросов от мониторинга:");
            add(label_2, "cell 0 8,alignx trailing");
            JTextField edMonitorPort = new IntegerField();
            add(edMonitorPort, "cell 1 8,growx");
            JButton btDefault = new JButton();
            add(btDefault, "cell 2 8");
            SettingsValues.add(edMonitorPort, btDefault, "MonitorPort", "6501");
        }
        {
            JLabel label = new JLabel("Кодировка данных для мониторинга:");
            add(label, "cell 0 9,alignx trailing");
            JComboBox cbMonitorEncoding = new JComboBox();
            cbMonitorEncoding.setEditable(true);
            add(cbMonitorEncoding, "cell 1 9,growx");
            JButton btDefault = new JButton("");
            add(btDefault, "cell 2 9");
            SettingsValues.add(cbMonitorEncoding, btDefault, "MonitorEncoding", "windows-1251");
        }
        JSeparator separator_2 = new JSeparator();
        add(separator_2, "cell 0 10 3 1,grow");

        {
            JLabel label_1 = new JLabel("Таймаут потока пересчета смен, с:");
            add(label_1, "cell 0 12,alignx trailing");
            JTextField edOpergangCalculateTimeout = new IntegerField();
            add(edOpergangCalculateTimeout, "cell 1 12,growx");
            JButton btDefault = new JButton();
            add(btDefault, "cell 2 12");
            SettingsValues.add(edOpergangCalculateTimeout, btDefault, "OpergangCalculateTimeout", "60");
        }
        JSeparator separator_3 = new JSeparator();
        add(separator_3, "cell 0 13 3 1,grow");
        {
            JLabel lblNewLabel = new JLabel("Как сохранять историю загрузки:");
            add(lblNewLabel, "cell 0 14,alignx trailing");

            JPanel panel_1 = new JPanel();
            add(panel_1, "cell 1 14,alignx left");
            panel_1.setLayout(new GridLayout(0, 4, 0, 0));

            JRadioButton rdbtnNewRadioButton = new JRadioButton("Off");
            buttonGroup.add(rdbtnNewRadioButton);
            panel_1.add(rdbtnNewRadioButton);

            JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("DB");
            buttonGroup.add(rdbtnNewRadioButton_1);
            panel_1.add(rdbtnNewRadioButton_1);

            JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("File");
            buttonGroup.add(rdbtnNewRadioButton_2);
            panel_1.add(rdbtnNewRadioButton_2);

            JRadioButton rdbtnNewRadioButton_3 = new JRadioButton("DB & File");
            buttonGroup.add(rdbtnNewRadioButton_3);
            panel_1.add(rdbtnNewRadioButton_3);

            SettingsValues.add(rdbtnNewRadioButton, "SaveXmlToDbAndFile", "0");
            SettingsValues.add(rdbtnNewRadioButton_1, "SaveXmlToDbAndFile", "1");
            SettingsValues.add(rdbtnNewRadioButton_2, "SaveXmlToDbAndFile", "2");
            SettingsValues.add(rdbtnNewRadioButton_3, "SaveXmlToDbAndFile", "3");

        }
        {
            JLabel label = new JLabel("Каталог для сохранения истории:");
            add(label, "cell 0 15,alignx trailing");
            JTextField edHistoryLoadDir = new JTextField();
            add(edHistoryLoadDir, "cell 1 15,growx");
            JButton btDefault = new JButton("");
            add(btDefault, "cell 2 15");
            SettingsValues.add(edHistoryLoadDir, btDefault, "HistoryLoadDir", "load");
        }
        JSeparator separator_4 = new JSeparator();
        add(separator_4, "cell 0 16 3 1,grow");
        {
            JLabel label = new JLabel("Уровень логирования:");
            add(label, "cell 0 17,alignx trailing");
            JComboBox cbDebugLevel = new JComboBox();
            add(cbDebugLevel, "cell 1 17,growx");
            cbDebugLevel.setModel(new DefaultComboBoxModel(new String[] {"ALL", "TRACE", "DEBUG", "INFO", "WARN", "ERROR", "FATAL", "OFF"}));
            JButton btDefault = new JButton("");
            add(btDefault, "cell 2 17");
            SettingsValues.add(cbDebugLevel, btDefault, "DebugLevel", "ALL");
        }
        {
            JLabel label = new JLabel("Дополнительное логирование для отладки:");
            add(label, "cell 0 18,alignx trailing");
            JCheckBox chLogTimeOut = new JCheckBox("");
            add(chLogTimeOut, "cell 1 18");
            SettingsValues.add(chLogTimeOut, "logTimeOut", false);
        }
    }
}
