package ru.crystals.loyalty;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import ru.crystals.configuration.SettingsValues;

class PanelSet10 extends JPanel {

    private static final long serialVersionUID = 1L;

    public PanelSet10() {
        setLayout(new MigLayout("", "[][grow][]", "[][][][]"));
        JLabel lblWsUrl = new JLabel("Адрес веб-сервиса, отвечающего за расчет скидок:");
        add(lblWsUrl, "cell 0 0,alignx trailing");

        JTextField edDiscount = new JTextField();
        add(edDiscount, "cell 1 0,growx");
        edDiscount.setColumns(10);

        JButton b1 = new JButton();
        add(b1, "cell 2 0");

        JLabel lblPartnerid = new JLabel("Адрес веб-сервиса, отвечающего за авторизацию карт:");
        add(lblPartnerid, "cell 0 1,alignx trailing");

        JTextField edCards = new JTextField();
        add(edCards, "cell 1 1,growx");
        edCards.setColumns(10);

        JButton b2 = new JButton();
        add(b2, "cell 2 1");

        JLabel lblDepartmentid = new JLabel("Разделять позиции по наборам:");
        add(lblDepartmentid, "cell 0 2,alignx trailing");

        JCheckBox chPrintMixMach = new JCheckBox();
        add(chPrintMixMach, "cell 1 2 2 1,growx");

        SettingsValues.add(edDiscount, b1, "SETv10.ProcessingDiscount.ws_address",
            "http://127.0.0.1:8090/SET-ProcessingDiscount/ProcessingPurchaseWS");
        SettingsValues.add(edCards, b2, "SETv10.CardsManager.ws_address", "http://127.0.0.1:8090/SET-Cards/SET/Cards");
        SettingsValues.add(chPrintMixMach, "SETv10.PrintMixMach", false);

    }

}
