package ru.crystals.loyalty;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import ru.crystals.config.PanelOther;

public class MainLoyalty extends JPanel {

    private static final long serialVersionUID = 1L;

    public MainLoyalty() {

        setLayout(new BorderLayout(0, 0));

        JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
        add(tabbedPane, BorderLayout.CENTER);

        JPanel pnMain = new PanelMain();
        tabbedPane.addTab("Основные", null, pnMain, null);

        JPanel pnSet5 = new PanelSet5();
        tabbedPane.addTab("Set 5", null, pnSet5, null);

        JPanel pnSet10 = new PanelSet10();
        tabbedPane.addTab("Set 10", null, pnSet10, null);

        JPanel pnManzana = new PanelOther("Manzana");
        tabbedPane.addTab("Manzana", null, pnManzana, null);

        JPanel pnSiebel = new PanelSiebel();
        tabbedPane.addTab("Siebel", null, pnSiebel, null);

        JPanel pnBrandLoyalty = new PanelBrandLoyalty();
        tabbedPane.addTab("BrandLoyalty", null, pnBrandLoyalty, null);

        JPanel pnRetalix = new PanelRetalix();
        tabbedPane.addTab("Retalix", null, pnRetalix, null);

        JPanel pnComarch = new PanelComarch();
        tabbedPane.addTab("Comarch", null, pnComarch, null);

        JPanel pnOther = new PanelOther();
        tabbedPane.addTab("Остальные", null, pnOther, null);
    }
    // v SETv5
    // v SETv10
    // v Manzana
    // v Siebel
    // v BrandLoyalty
    // v Retalix
    // v Comarch
    // Promo
    // MSSQL

}
