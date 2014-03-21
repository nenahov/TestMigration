package ru.crystals.bridge;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import ru.crystals.config.IntegerField;
import ru.crystals.config.PanelOther;
import ru.crystals.configuration.SettingsValues;

class PanelMain extends JPanel {

    private static final long serialVersionUID = 1L;

    public PanelMain() {
        setLayout(new MigLayout("", "[][grow][]", "[][][][][grow][][]"));

        {
            JLabel lblNewLabel = new JLabel("Товары:");
            add(lblNewLabel, "cell 0 0,alignx trailing");

            JTextField ui = new JTextField();
            add(ui, "cell 1 0,growx");
            ui.setColumns(10);

            JButton b = new JButton("");
            add(b, "cell 2 0");

            SettingsValues.add(ui, b, "SETv10.goods.ws_address", "http://127.0.0.1:8090/SET-ERPIntegration/SET/WSGoodsCatalogImport");
        }
        {
            JLabel label_4 = new JLabel("Карты:");
            add(label_4, "cell 0 1,alignx trailing");

            JTextField ui = new JTextField();
            ui.setColumns(10);
            add(ui, "cell 1 1,growx");

            JButton b = new JButton("");
            add(b, "cell 2 1");

            SettingsValues.add(ui, b, "SETv10.cards.ws_address", "http://127.0.0.1:8090/SET-ERPIntegration/SET/WSCardsCatalogImport");
        }
        {
            JLabel label_6 = new JLabel("Скидки:");
            add(label_6, "cell 0 2,alignx trailing");

            JTextField ui = new JTextField();
            ui.setColumns(10);
            add(ui, "cell 1 2,growx");

            JButton b = new JButton("");
            add(b, "cell 2 2");

            SettingsValues.add(ui, b, "SETv10.discounts.ws_address", "http://127.0.0.1:8090/SET-ERPIntegration/AdvertisingActionsImport");
        }
        {
            JLabel label_5 = new JLabel("Кассиры:");
            add(label_5, "cell 0 3,alignx trailing");

            JTextField ui = new JTextField();
            ui.setColumns(10);
            add(ui, "cell 1 3,growx");

            JButton b = new JButton("");
            add(b, "cell 2 3");

            SettingsValues.add(ui, b, "SETv10.cashiers.ws_address", "http://127.0.0.1:8090/SET-ERPIntegration/CashiersImport");
        }

        {
            JTextField textField = new JTextField();
            textField.setBackground(new Color(211, 211, 211));
            textField.setEditable(false);
            textField.setText("Порт веб-сервиса для маппинга скидок http://127.0.0.1:8080/WS/AdvertiseMappingService?wsdl");
            add(textField, "cell 0 5 3 1,growx");
            textField.setColumns(10);

            JLabel label = new JLabel("Порт:");
            add(label, "cell 0 6,alignx trailing");

            JTextField ui = new IntegerField();
            add(ui, "cell 1 6,growx");

            JButton b = new JButton();
            add(b, "cell 2 6");
            SettingsValues.add(ui, b, "WSPort", "8080");
        }
        {
            JPanel panel = new PanelOther("SETv5.goods", "SETv5.cards", "SETv5.cashiers", "SETv5.discounts");
            add(panel, "cell 0 4 3 1,grow");
        }

    }
}
