package ru.crystalservice.setquery;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

@SuppressWarnings("serial")
public class MainQuery {

	private JProgressBar progressBar;
	private JTabbedPane tpBottom;
	private JPanel pnResult;
	private Settings pnSettings;
	
	private File dir = new File(".");

	private class ActSelectAll extends AbstractAction {
		ActSelectAll() {
			super("Выделить все", null);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (textField != null) {
				textField.selectAll();
			}
		}
	}

	private ActSelectAll actSelectAll = new ActSelectAll();

	private class ActCutText extends AbstractAction {
		ActCutText() {
			super("Вырезать", null);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (textField != null) {
				textField.cut();
			}
		}
	}

	private ActCutText actCutText = new ActCutText();

	private class ActCopyText extends AbstractAction {
		ActCopyText() {
			super("Копировать", null);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (textField != null) {
				textField.copy();
			}
		}
	}

	private ActCopyText actCopyText = new ActCopyText();

	private class ActPasteText extends AbstractAction {
		ActPasteText() {
			super("Вставить", null);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (textField != null) {
				textField.paste();
			}
		}
	}

	private ActPasteText actPasteText = new ActPasteText();

	private class ActStop extends AbstractAction {
		ActStop() {
			super("Stop", null);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			stopQuery();
		}
	}

	private ActStop actStop = new ActStop();

	private class ActOpen extends AbstractAction {
		ActOpen() {
			super("Open...", null);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser(dir);
			jfc.setDialogTitle("Open sql-скрипт");
			if (jfc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
				File f = jfc.getSelectedFile();
				dir = f.getParentFile();
				if (f.exists()) {
					try {
						taCommand.setText(FileHelper.getContents(f, ""));
					} catch (Exception e1) {
						taCommand.setText(e1.getMessage());
					}
				} else
					taCommand.setText("");
			}
		}
	}

	private ActOpen actOpen = new ActOpen();
	
	private class ActSaveScript extends AbstractAction {
		ActSaveScript() {
			super("Save...", null);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser(dir);
			jfc.setDialogTitle("Save sql-script");
			if (jfc.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
				File f = jfc.getSelectedFile();
				dir = f.getParentFile();
				try {
					FileHelper.setContents(f, taCommand.getText(), "");
				} catch (Exception e1) {
					
				}

			}
		}
	}

	private ActSaveScript actSaveScript = new ActSaveScript();

	private class ActSaveResult extends AbstractAction {
		ActSaveResult() {
			super("Save Result...", null);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser(dir);
			jfc.setDialogTitle("Save Result");
			if (jfc.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
				File f = jfc.getSelectedFile();
				dir = f.getParentFile();				
				try {
					FileHelper.setContents(f, taResult.getText(), "");
				} catch (Exception e1) {
					
				}

			}
		}
	}

	private ActSaveResult actSaveResult = new ActSaveResult();

	private class ActRun extends AbstractAction {
		ActRun() {
			super("Run", null);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			runQuery();
		}
	}

	private ActRun actRun = new ActRun();

	private ExecQuery execQuery = null;
	private JButton runButton;
	private JList list;
	private JTextArea taResult;
	private JTextArea taCommand;
	private JTextArea textField = null;
	private JFrame frame;
	private List<ServerBD> servers = new ArrayList<ServerBD>();
	private String currentShop = "";

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
		}
		Locale.setDefault(new Locale("ru", "RU"));
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MainQuery window = new MainQuery();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application
	 */
	public MainQuery() {
		createContents();
	}

	private boolean isMakePaste() {
		String s = null;
		Transferable clipData = frame.getToolkit().getSystemClipboard().getContents(null);
		try {
			s = (String) clipData.getTransferData(DataFlavor.stringFlavor);
		} catch (Exception ex) {
		}
		return (s != null && !s.equals(""));
	}

	private void setTextArea(JTextArea ta) {
		textField = ta;
		actCutText.setEnabled((textField.getSelectedText() != null) && (!textField.getSelectedText().equals("")));
		actCopyText.setEnabled((textField.getSelectedText() != null) && (!textField.getSelectedText().equals("")));
		actPasteText.setEnabled(isMakePaste());
	}

	/**
	 * Initialize the contents of the frame
	 */
	private void createContents() {
		frame = new JFrame();
		frame.setTitle("SET Query (v.1.7)");
		frame.setSize(new Dimension(700, 500));
		frame.setLocationRelativeTo(null);
		frame.setMinimumSize(new Dimension(500, 300));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JSplitPane splitPane = new JSplitPane();
		splitPane.setOneTouchExpandable(true);
		splitPane.setContinuousLayout(true);
		splitPane.setAutoscrolls(true);
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);

		final JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setContinuousLayout(true);
		splitPane_1.setOneTouchExpandable(true);
		splitPane_1.setAutoscrolls(true);
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setRightComponent(splitPane_1);

		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		splitPane_1.setTopComponent(panel);

		final JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setMinimumSize(new Dimension(0, 120));
		panel.add(scrollPane_1);

		taCommand = new JTextArea();
		final UndoManager undo = new UndoManager();
		Document doc = taCommand.getDocument();

		// The next two lines should be in one line.
		doc.addUndoableEditListener(new UndoableEditListener() {
			// The next two lines should be in one line.
			@Override
			public void undoableEditHappened(UndoableEditEvent evt) {
				undo.addEdit(evt.getEdit());
			}
		});

		taCommand.getActionMap().put("Undo", new AbstractAction("Undo") {
			@Override
			public void actionPerformed(ActionEvent evt) {
				try {
					if (undo.canUndo()) {
						undo.undo();
					}
				} catch (CannotUndoException e) {
				}
			}
		});
		taCommand.getInputMap().put(KeyStroke.getKeyStroke("control Z"), "Undo");

		taCommand.getActionMap().put("Redo", new AbstractAction("Redo") {
			@Override
			public void actionPerformed(ActionEvent evt) {
				try {
					if (undo.canRedo()) {
						undo.redo();
					}
				} catch (CannotRedoException e) {
				}
			}
		});
		taCommand.getInputMap().put(KeyStroke.getKeyStroke("control Y"), "Redo");

		taCommand.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(final MouseEvent e) {
				setTextArea(taCommand);
			}
		});
		taCommand.setDragEnabled(true);
		taCommand.setCaret(new SelectionPreservingCaret());
		scrollPane_1.setViewportView(taCommand);

		final JPopupMenu pmCommand = new JPopupMenu();
		addPopup(taCommand, pmCommand);

		final JMenuItem newItemMenuItem = new JMenuItem();
		newItemMenuItem.setAction(actRun);
		pmCommand.add(newItemMenuItem);

		final JMenuItem newItemMenuItem_1 = new JMenuItem();
		newItemMenuItem_1.setAction(actStop);
		pmCommand.add(newItemMenuItem_1);

		pmCommand.addSeparator();

		final JMenuItem newItemMenuItem_4 = new JMenuItem();
		newItemMenuItem_4.setAction(actCutText);
		pmCommand.add(newItemMenuItem_4);

		final JMenuItem newItemMenuItem_5 = new JMenuItem();
		newItemMenuItem_5.setAction(actCopyText);
		pmCommand.add(newItemMenuItem_5);

		final JMenuItem newItemMenuItem_6 = new JMenuItem();
		newItemMenuItem_6.setAction(actPasteText);
		pmCommand.add(newItemMenuItem_6);

		pmCommand.addSeparator();

		final JMenuItem newItemMenuItem_2 = new JMenuItem();
		newItemMenuItem_2.setAction(actSelectAll);
		pmCommand.add(newItemMenuItem_2);

		final JPanel panel_2 = new JPanel();
		panel_2.setLayout(new GridLayout(1, 0));
		panel.add(panel_2, BorderLayout.NORTH);

		actStop.setEnabled(false);

		runButton = new JButton();
		panel_2.add(runButton);
		runButton.setAction(actRun);
		runButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F5"), "actRun");
		runButton.getActionMap().put("actRun", actRun);

		final JButton openButton = new JButton();
		panel_2.add(openButton);
		openButton.setAction(actOpen);
		openButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F2"), "actOpen");
		openButton.getActionMap().put("actOpen", actOpen);

		final JButton saveButton_1 = new JButton();
		panel_2.add(saveButton_1);
		saveButton_1.setAction(actSaveScript);

		final JPanel panel_5 = new JPanel();
		panel_5.setLayout(new GridLayout(0, 1));
		panel.add(panel_5, BorderLayout.SOUTH);

		final JLabel label_1 = new JLabel();
		label_1.setText("<name> => 'имя магазина'; <ip> => 'ip-адрес'");
		panel_5.add(label_1);

		progressBar = new JProgressBar();
		progressBar.setString("");
		progressBar.setStringPainted(true);
		panel_5.add(progressBar);

		tpBottom = new JTabbedPane();
		splitPane_1.setBottomComponent(tpBottom);

		pnResult = new JPanel();
		pnResult.setLayout(new BorderLayout());
		tpBottom.addTab("Результат", null, pnResult, null);

		final JScrollPane scrollPane_2 = new JScrollPane();
		pnResult.add(scrollPane_2, BorderLayout.CENTER);

		taResult = new JTextArea();
		taResult.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(final MouseEvent e) {
				setTextArea(taResult);
			}
		});
		taResult.setDragEnabled(true);
		scrollPane_2.setViewportView(taResult);

		final JPopupMenu pmResult = new JPopupMenu();
		addPopup(taResult, pmResult);

		final JMenuItem newItemMenuItem_7 = new JMenuItem();
		newItemMenuItem_7.setAction(actCutText);
		pmResult.add(newItemMenuItem_7);

		final JMenuItem newItemMenuItem_8 = new JMenuItem();
		newItemMenuItem_8.setAction(actCopyText);
		pmResult.add(newItemMenuItem_8);

		final JMenuItem newItemMenuItem_9 = new JMenuItem();
		newItemMenuItem_9.setAction(actPasteText);
		pmResult.add(newItemMenuItem_9);

		pmResult.addSeparator();

		final JMenuItem newItemMenuItem_3 = new JMenuItem();
		newItemMenuItem_3.setAction(actSelectAll);
		pmResult.add(newItemMenuItem_3);

		final JPanel panel_4 = new JPanel();
		final FlowLayout flowLayout_1 = new FlowLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel_4.setLayout(flowLayout_1);
		pnResult.add(panel_4, BorderLayout.SOUTH);

		final JButton saveButton = new JButton();
		saveButton.setAction(actSaveResult);
		panel_4.add(saveButton);

		final JButton clearButton = new JButton();
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				taResult.setText("");
			}
		});
		clearButton.setText("Clear");
		panel_4.add(clearButton);

		pnSettings = new Settings();
		tpBottom.addTab("Настройки", null, pnSettings, null);

		final JPanel panel_3 = new JPanel();
		panel_3.setLayout(new BorderLayout());
		panel_3.setMinimumSize(new Dimension(200, 0));
		splitPane.setLeftComponent(panel_3);

		final JPanel panel_6 = new JPanel();
		panel_3.add(panel_6, BorderLayout.NORTH);

		final JLabel label = new JLabel();
		label.setText("Список БД");
		panel_6.add(label);

		final JPanel panel_7 = new JPanel();
		panel_3.add(panel_7, BorderLayout.SOUTH);

		final JButton button = new JButton();
		button.setText("+");
		panel_7.add(button);

		final JButton button_1 = new JButton();
		button_1.setText("-");
		panel_7.add(button_1);

		final JButton button_2 = new JButton();
		button_2.setText("^");
		panel_7.add(button_2);

		final JScrollPane scrollPane_3 = new JScrollPane();
		panel_3.add(scrollPane_3, BorderLayout.CENTER);

		list = new JList();
		list.setDropMode(DropMode.ON_OR_INSERT);
		list.setModel(new DefaultListModel());
		scrollPane_3.setViewportView(list);

		// Clipboard clipboard = frame.getToolkit().getSystemClipboard();
		// clipboard.addFlavorListener(new FlavorListener()
		// {
		// public void flavorsChanged(FlavorEvent e)
		// {
		// System.out.println(e);
		// }
		// });

		Informer informer = new Informer();
		informer.Init();
		try {
			((DefaultListModel) list.getModel()).removeAllElements();
			servers = Configuration.getServers();
			for (ServerBD serverBD : servers) {
				((DefaultListModel) list.getModel()).addElement(serverBD);
			}
			list.updateUI();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frame, e.getMessage(), "Список серверов", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void runQuery() {
		progressBar.setString("");
		progressBar.setIndeterminate(true);
		tpBottom.setSelectedComponent(pnResult);
		actStop.setEnabled(true);
		runButton.setAction(actStop);
		actRun.setEnabled(false);
		execQuery = new ExecQuery();
		execQuery.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if ("progress".equals(evt.getPropertyName())) {
					progressBar.setIndeterminate(false);
					progressBar.setValue((Integer) evt.getNewValue());
					progressBar.setString(currentShop);
				}
			}
		});
		execQuery.execute();
	}

	private void stopQuery() {
		actRun.setEnabled(true);
		runButton.setAction(actRun);
		actStop.setEnabled(false);
		if (execQuery != null) {
			execQuery.cancel(false);
			execQuery = null;
		}
		System.gc();
	}

	private class ExecQuery extends SwingWorker<Void, RowMessage> implements IWriterMessages {
		private List<ServerBD> selected = new ArrayList<ServerBD>();
		private String sCommand = "";
		private Informer informer = new Informer();

		@Override
		protected Void doInBackground() {

			// Random random = new Random();
			StringBuilder s = null;
			int k = 0;

			for (ServerBD serverBD : selected) {
				if (!isCancelled()) {
					currentShop = serverBD.toString();
					setProgress((100 * selected.indexOf(serverBD) + 5) / selected.size());
					s = new StringBuilder(sCommand);
					k = 0;
					while ((k = s.indexOf("<name>", k)) > 0) {
						s = s.replace(k, k + 6, "'" + serverBD.toString() + "'");
					}
					k = 0;
					while ((k = s.indexOf("<ip>", k)) > 0) {
						s = s.replace(k, k + 4, "'" + serverBD.getIp() + "'");
					}
					// taResult.append(s.toString() + "\n");
					informer.execQuery(serverBD, s.toString());
				}
			}
			return null;
		}

		@Override
		public boolean putMsg(RowMessage msg) {
			if (!isCancelled())
				publish(msg);
			return !isCancelled();
		}

		public ExecQuery() {
			super();
			Object[] sv = list.getSelectedValues();
			for (Object sel : sv) {
				if (sel instanceof ServerBD) {
					selected.add((ServerBD) sel);
				}
			}
			sCommand = taCommand.getSelectedText();
			if ((sCommand == null) || sCommand.equals("")) {
				sCommand = taCommand.getText();
			}
			informer.setResultComponent(this);
			informer.setShowTitleShopName(sCommand.toLowerCase().indexOf("<name>") < 0);
			informer.setSeparator(pnSettings.getSeparator());
		}

		@Override
		protected void process(List<RowMessage> pairs) {
			for (RowMessage rowMessage : pairs) {
				taResult.append(rowMessage.getMsg());
			}
		}

		@Override
		protected void done() {
			if (!isCancelled()) {
				currentShop = "Done!";
				setProgress(100);
				stopQuery();
			}
		}
	}

	/**
	 * WindowBuilder generated method.<br>
	 * Please don't remove this method or its invocations.<br>
	 * It used by WindowBuilder to associate the {@link javax.swing.JPopupMenu}
	 * with parent.
	 */
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger())
					showMenu(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger())
					showMenu(e);
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
