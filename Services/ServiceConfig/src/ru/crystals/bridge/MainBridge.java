package ru.crystals.bridge;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import ru.crystals.config.PanelOther;

public class MainBridge extends JPanel {

    private static final long serialVersionUID = 1L;

    public MainBridge() {

        setLayout(new BorderLayout(0, 0));

        JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
        add(tabbedPane, BorderLayout.CENTER);

        JPanel pnMain = new PanelMain();
        tabbedPane.addTab("Основные", null, pnMain, null);

        JPanel pnSet5 = new PanelDB();
        tabbedPane.addTab("База данных", null, pnSet5, null);

        JPanel pnOther = new PanelOther();
        tabbedPane.addTab("Остальные", null, pnOther, null);
    }

}
