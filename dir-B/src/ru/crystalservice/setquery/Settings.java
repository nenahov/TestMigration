package ru.crystalservice.setquery;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class Settings extends JPanel {

	private JCheckBox cbOther;
	private JCheckBox cbTab;
	private JCheckBox cbTz;
	private ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField edOther;

	/**
	 * Create the panel
	 */
	public Settings() {
		super();
		setLayout(new BorderLayout());

		final JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Символом-разделителем является:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
		panel.setLayout(new GridLayout(0, 2));
		add(panel, BorderLayout.NORTH);

		cbTz = new JCheckBox();
		buttonGroup.add(cbTz);
		cbTz.setText("точка с запятой");
		panel.add(cbTz);

		cbTab = new JCheckBox();
		cbTab.setSelected(true);
		buttonGroup.add(cbTab);
		panel.add(cbTab);
		cbTab.setText("знак табуляции");

		cbOther = new JCheckBox();
		buttonGroup.add(cbOther);
		cbOther.setText("другой:");
		panel.add(cbOther);

		edOther = new JTextField();
		edOther.setText("qwerty");
		panel.add(edOther);
		//
	}

	public String getSeparator() {
		if (cbTz.isSelected())
			return ";";
		else if (cbTab.isSelected())
			return "\t";
		else
			return edOther.getText();
	}

}
