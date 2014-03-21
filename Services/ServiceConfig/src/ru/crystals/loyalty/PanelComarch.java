package ru.crystals.loyalty;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import ru.crystals.config.IntegerField;
import ru.crystals.config.PanelOther;
import ru.crystals.configuration.SettingsValues;

class PanelComarch extends JPanel {

    private static final long serialVersionUID = 1L;

    public PanelComarch() {
        setLayout(new MigLayout("", "[][grow][]", "[][][][][][][][grow]"));

        JLabel lblWsUrl = new JLabel("Адрес веб-сервиса:");
        add(lblWsUrl, "cell 0 0,alignx trailing");

        JTextField edUrl = new JTextField();
        add(edUrl, "cell 1 0,growx");
        edUrl.setColumns(10);

        JButton b1 = new JButton();
        add(b1, "cell 2 0");

        JLabel lblPartnerid = new JLabel("Параметр partnerId:");
        add(lblPartnerid, "cell 0 1,alignx trailing");

        JTextField edPartnerId = new IntegerField();
        add(edPartnerId, "cell 1 1,growx");
        edPartnerId.setColumns(10);

        JButton b2 = new JButton();
        add(b2, "cell 2 1");

        JLabel lblDepartmentid = new JLabel("Departmentid:");
        add(lblDepartmentid, "cell 0 2,alignx trailing");

        JTextField edDepartmentId = new JTextField();
        add(edDepartmentId, "cell 1 2,growx");
        edDepartmentId.setColumns(10);

        JButton b3 = new JButton();
        add(b3, "cell 2 2");

        JLabel label = new JLabel("Префиксы социальных карт:");
        add(label, "cell 0 3,alignx trailing");

        JTextField edSocCards = new JTextField();
        add(edSocCards, "cell 1 3,growx");
        edSocCards.setColumns(10);

        JButton b4 = new JButton();
        add(b4, "cell 2 3");

        JLabel label_1 = new JLabel("Префиксы карт, разрешенных для начисления:");
        add(label_1, "cell 0 4,alignx trailing");

        JTextField edIssuance = new JTextField();
        add(edIssuance, "cell 1 4,growx");
        edIssuance.setColumns(10);

        JButton b5 = new JButton();
        add(b5, "cell 2 4");

        JLabel label_2 = new JLabel("Префиксы карт, разрешенных для списания:");
        add(label_2, "cell 0 5,alignx trailing");

        JTextField edRedemption = new JTextField();
        edRedemption.setColumns(10);
        add(edRedemption, "cell 1 5,growx");

        JButton b6 = new JButton();
        add(b6, "cell 2 5");

        SettingsValues.add(edUrl, b1, "Comarch.urlclm", "http://193.201.136.99:8081/wscpstoclm/services/CLM");
        SettingsValues.add(edPartnerId, b2, "Comarch.partnerId", "73");
        // (если не указан, будет браться № магазина)
        SettingsValues.add(edDepartmentId, b3, "Comarch.departmentid", "");
        SettingsValues.add(edSocCards, b4, "Comarch.prefixSocCard", "429158");
        SettingsValues.add(edIssuance, b5, "Comarch.prefixAllowIssuance", "7789001, 7789002, 7789005, 7789006, 7789007, 7789008");
        SettingsValues.add(edRedemption, b6, "Comarch.prefixAllowRedemption", "7789001, 7789002, 7789005, 7789006, 7789007, 7789008");

        JLabel lblNewLabel = new JLabel("Двойной слип для соц. карт:");
        add(lblNewLabel, "cell 0 6,alignx trailing");

        JCheckBox chSocCardDoubleSlip = new JCheckBox("");
        add(chSocCardDoubleSlip, "cell 1 6");
        SettingsValues.add(chSocCardDoubleSlip, "Comarch.SocCardDoubleSlip", false);

        // comarch.prefixerror195=7789001, 7789002
        // comarch.prefixerror194=7789005, 7789006, 7789007, 7789008

        JPanel panel = new PanelOther("Comarch");
        add(panel, "cell 0 7 3 1,grow");
    }
}
