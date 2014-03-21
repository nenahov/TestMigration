package ru.crystals.loyalty;

import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import ru.crystals.config.IntegerField;
import ru.crystals.configuration.SettingsValues;

class PanelMain extends JPanel {

    private static final String BONUS_PROCESSING = "BonusProcessing";
    private static final String TYPE_LOYALTY_SECOND = "TypeLoyaltySecond";
    private static final String TYPE_LOYALTY = "TypeLoyalty";
    private static final long serialVersionUID = 1L;
    private final ButtonGroup bgFirstLoyalty = new ButtonGroup();
    private final ButtonGroup bgSecondLoyalty = new ButtonGroup();
    private final ButtonGroup bgBonus = new ButtonGroup();

    public PanelMain() {
        setLayout(new MigLayout("", "[][][]", "[][][][][]"));

        JPanel panel_2 = new JPanel();
        add(panel_2, "cell 0 0 3 1,grow");
        panel_2.setLayout(new GridLayout(1, 0, 0, 0));

        JPanel panel = new JPanel();
        panel_2.add(panel);
        panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
            "\u0422\u0438\u043F \u043F\u0435\u0440\u0432\u043E\u0439 \u043B\u043E\u044F\u043B\u044C\u043D\u043E\u0441\u0442\u0438",
            TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel.setLayout(new GridLayout(0, 1, 0, 0));

        JRadioButton rdbtnNewRadioButton = new JRadioButton("Нет");
        bgFirstLoyalty.add(rdbtnNewRadioButton);
        panel.add(rdbtnNewRadioButton);

        JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Set 5");
        bgFirstLoyalty.add(rdbtnNewRadioButton_1);
        panel.add(rdbtnNewRadioButton_1);

        JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Set 10");
        bgFirstLoyalty.add(rdbtnNewRadioButton_2);
        panel.add(rdbtnNewRadioButton_2);

        JRadioButton rdbtnNewRadioButton_4 = new JRadioButton("Manzana");
        bgFirstLoyalty.add(rdbtnNewRadioButton_4);
        panel.add(rdbtnNewRadioButton_4);

        JRadioButton rdbtnNewRadioButton_3 = new JRadioButton("Siebel");
        bgFirstLoyalty.add(rdbtnNewRadioButton_3);
        panel.add(rdbtnNewRadioButton_3);

        JRadioButton rdbtnBrandloyalty = new JRadioButton("BrandLoyalty");
        bgFirstLoyalty.add(rdbtnBrandloyalty);
        panel.add(rdbtnBrandloyalty);

        JRadioButton rdbtnNewRadioButton_5 = new JRadioButton("Retalix");
        bgFirstLoyalty.add(rdbtnNewRadioButton_5);
        panel.add(rdbtnNewRadioButton_5);

        JRadioButton rdbtnNewRadioButton_6 = new JRadioButton("Comarch");
        bgFirstLoyalty.add(rdbtnNewRadioButton_6);
        panel.add(rdbtnNewRadioButton_6);

        JRadioButton rdbtnNewRadioButton_7 = new JRadioButton("Promo");
        bgFirstLoyalty.add(rdbtnNewRadioButton_7);
        panel.add(rdbtnNewRadioButton_7);

        JRadioButton rdbtnNewRadioButton_8 = new JRadioButton("Stored Procedures");
        bgFirstLoyalty.add(rdbtnNewRadioButton_8);
        panel.add(rdbtnNewRadioButton_8);

        JPanel panel_1 = new JPanel();
        panel_2.add(panel_1);
        panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
            "\u0422\u0438\u043F \u0432\u0442\u043E\u0440\u043E\u0439 \u043B\u043E\u044F\u043B\u044C\u043D\u043E\u0441\u0442\u0438",
            TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_1.setLayout(new GridLayout(0, 1, 0, 0));

        JRadioButton radioButton = new JRadioButton("Нет");
        bgSecondLoyalty.add(radioButton);
        panel_1.add(radioButton);

        JRadioButton radioButton_1 = new JRadioButton("Set 5");
        bgSecondLoyalty.add(radioButton_1);
        panel_1.add(radioButton_1);

        JRadioButton radioButton_2 = new JRadioButton("Set 10");
        bgSecondLoyalty.add(radioButton_2);
        panel_1.add(radioButton_2);

        JRadioButton radioButton_3 = new JRadioButton("Manzana");
        bgSecondLoyalty.add(radioButton_3);
        panel_1.add(radioButton_3);

        JRadioButton radioButton_4 = new JRadioButton("Siebel");
        bgSecondLoyalty.add(radioButton_4);
        panel_1.add(radioButton_4);

        JRadioButton radioButton_5 = new JRadioButton("BrandLoyalty");
        bgSecondLoyalty.add(radioButton_5);
        panel_1.add(radioButton_5);

        JRadioButton radioButton_6 = new JRadioButton("Retalix");
        bgSecondLoyalty.add(radioButton_6);
        panel_1.add(radioButton_6);

        JRadioButton radioButton_7 = new JRadioButton("Comarch");
        bgSecondLoyalty.add(radioButton_7);
        panel_1.add(radioButton_7);

        JRadioButton radioButton_8 = new JRadioButton("Promo");
        bgSecondLoyalty.add(radioButton_8);
        panel_1.add(radioButton_8);

        JRadioButton radioButton_9 = new JRadioButton("Stored Procedures");
        bgSecondLoyalty.add(radioButton_9);
        panel_1.add(radioButton_9);
        SettingsValues.add(rdbtnNewRadioButton, TYPE_LOYALTY, "NONE");
        SettingsValues.add(rdbtnNewRadioButton_1, TYPE_LOYALTY, "SETv5");
        SettingsValues.add(rdbtnNewRadioButton_2, TYPE_LOYALTY, "SETv10");
        SettingsValues.add(rdbtnNewRadioButton_4, TYPE_LOYALTY, "Manzana");
        SettingsValues.add(rdbtnNewRadioButton_3, TYPE_LOYALTY, "Siebel");
        SettingsValues.add(rdbtnBrandloyalty, TYPE_LOYALTY, "BrandLoyalty");
        SettingsValues.add(rdbtnNewRadioButton_5, TYPE_LOYALTY, "Retalix");
        SettingsValues.add(rdbtnNewRadioButton_6, TYPE_LOYALTY, "Comarch");
        SettingsValues.add(rdbtnNewRadioButton_7, TYPE_LOYALTY, "Promo");
        SettingsValues.add(rdbtnNewRadioButton_8, TYPE_LOYALTY, "MSSQL");
        SettingsValues.add(radioButton, TYPE_LOYALTY_SECOND, "NONE");
        SettingsValues.add(radioButton_1, TYPE_LOYALTY_SECOND, "SETv5");
        SettingsValues.add(radioButton_2, TYPE_LOYALTY_SECOND, "SETv10");
        SettingsValues.add(radioButton_3, TYPE_LOYALTY_SECOND, "Manzana");
        SettingsValues.add(radioButton_4, TYPE_LOYALTY_SECOND, "Siebel");
        SettingsValues.add(radioButton_5, TYPE_LOYALTY_SECOND, "BrandLoyalty");
        SettingsValues.add(radioButton_6, TYPE_LOYALTY_SECOND, "Retalix");
        SettingsValues.add(radioButton_7, TYPE_LOYALTY_SECOND, "Comarch");
        SettingsValues.add(radioButton_8, TYPE_LOYALTY_SECOND, "Promo");
        SettingsValues.add(radioButton_9, TYPE_LOYALTY_SECOND, "MSSQL");

        JPanel panel_3 = new JPanel();
        panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
            "\u0411\u043E\u043D\u0443\u0441\u043D\u0430\u044F \u0441\u0438\u0441\u0442\u0435\u043C\u0430", TitledBorder.LEADING, TitledBorder.TOP,
            null, null));
        panel_2.add(panel_3);
        panel_3.setLayout(new GridLayout(0, 1, 0, 0));

        JRadioButton radioButton_10 = new JRadioButton("Нет");
        bgBonus.add(radioButton_10);
        panel_3.add(radioButton_10);

        JRadioButton radioButton_11 = new JRadioButton("Set 5");
        bgBonus.add(radioButton_11);
        panel_3.add(radioButton_11);

        JRadioButton radioButton_12 = new JRadioButton("Set 10");
        bgBonus.add(radioButton_12);
        panel_3.add(radioButton_12);

        JRadioButton radioButton_14 = new JRadioButton("Siebel");
        bgBonus.add(radioButton_14);
        panel_3.add(radioButton_14);

        JRadioButton radioButton_17 = new JRadioButton("Comarch");
        bgBonus.add(radioButton_17);
        panel_3.add(radioButton_17);
        SettingsValues.add(rdbtnNewRadioButton, TYPE_LOYALTY, "NONE");
        SettingsValues.add(rdbtnNewRadioButton_1, TYPE_LOYALTY, "SETv5");
        SettingsValues.add(rdbtnNewRadioButton_2, TYPE_LOYALTY, "SETv10");
        SettingsValues.add(rdbtnNewRadioButton_4, TYPE_LOYALTY, "Manzana");
        SettingsValues.add(rdbtnNewRadioButton_3, TYPE_LOYALTY, "Siebel");
        SettingsValues.add(rdbtnBrandloyalty, TYPE_LOYALTY, "BrandLoyalty");
        SettingsValues.add(rdbtnNewRadioButton_5, TYPE_LOYALTY, "Retalix");
        SettingsValues.add(rdbtnNewRadioButton_6, TYPE_LOYALTY, "Comarch");
        SettingsValues.add(rdbtnNewRadioButton_7, TYPE_LOYALTY, "Promo");
        SettingsValues.add(rdbtnNewRadioButton_8, TYPE_LOYALTY, "MSSQL");
        SettingsValues.add(radioButton, TYPE_LOYALTY_SECOND, "NONE");
        SettingsValues.add(radioButton_1, TYPE_LOYALTY_SECOND, "SETv5");
        SettingsValues.add(radioButton_2, TYPE_LOYALTY_SECOND, "SETv10");
        SettingsValues.add(radioButton_3, TYPE_LOYALTY_SECOND, "Manzana");
        SettingsValues.add(radioButton_4, TYPE_LOYALTY_SECOND, "Siebel");
        SettingsValues.add(radioButton_5, TYPE_LOYALTY_SECOND, "BrandLoyalty");
        SettingsValues.add(radioButton_6, TYPE_LOYALTY_SECOND, "Retalix");
        SettingsValues.add(radioButton_7, TYPE_LOYALTY_SECOND, "Comarch");
        SettingsValues.add(radioButton_8, TYPE_LOYALTY_SECOND, "Promo");
        SettingsValues.add(radioButton_9, TYPE_LOYALTY_SECOND, "MSSQL");

        SettingsValues.add(radioButton_10, BONUS_PROCESSING, "NONE");
        SettingsValues.add(radioButton_11, BONUS_PROCESSING, "SETv5");
        SettingsValues.add(radioButton_12, BONUS_PROCESSING, "SETv10");
        SettingsValues.add(radioButton_14, BONUS_PROCESSING, "Siebel");
        SettingsValues.add(radioButton_17, BONUS_PROCESSING, "Comarch");

        JLabel label = new JLabel("Порт для запросов от кассы:");
        add(label, "cell 0 1,alignx trailing");

        JTextField edCashPort = new IntegerField();

        add(edCashPort, "cell 1 1,growx");
        edCashPort.setColumns(10);

        JButton dfCashPort = new JButton();

        add(dfCashPort, "cell 2 1");

        JLabel lblYjvthRfccs = new JLabel("Номер кассы (Если служба установлена на WinCash):");
        add(lblYjvthRfccs, "cell 0 2,alignx trailing");

        JTextField edCashNumber = new IntegerField();
        add(edCashNumber, "cell 1 2,growx");
        edCashNumber.setColumns(10);

        JButton dfCashNumber = new JButton();
        add(dfCashNumber, "cell 2 2");

        SettingsValues.add(edCashPort, dfCashPort, "CashPort", "6515");
        SettingsValues.add(edCashNumber, dfCashNumber, "CashNumber", "");

        {
            JLabel lll = new JLabel("Дополнительное логирование для отладки:");
            add(lll, "cell 0 3,alignx trailing");
            JCheckBox chLogTimeOut = new JCheckBox("");
            add(chLogTimeOut, "cell 1 3");
            SettingsValues.add(chLogTimeOut, "LogProcessingDiscount", false);
        }

    }

}
