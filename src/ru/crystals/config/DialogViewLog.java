package ru.crystals.config;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class DialogViewLog extends JDialog {

    private static final long serialVersionUID = 1L;

    private BufferedReader bufferedReader = null;
    private static StyleContext cont = StyleContext.getDefaultStyleContext();
    private static AttributeSet attrRed = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.RED);
    private static AttributeSet attrRedDark = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.RED.darker());
    private static AttributeSet attrORANGE = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.ORANGE.darker());
    private static AttributeSet attrBlue = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLUE.darker());
    private static AttributeSet attrBlack = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLACK);

    private StyledDocument doc;
    private JTextPane textPane;

    private long limit = 0;

    public DialogViewLog(Component parent, File file) {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/res/ico.png")));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(new Dimension(800, 500));
        setModal(true);
        setLocationRelativeTo(parent);

        setTitle("Просмотр лога");
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane = new JScrollPane();
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        textPane = new JTextPane();
        textPane.addCaretListener(new CaretListener() {

            @Override
            public void caretUpdate(CaretEvent e) {

                if (e.getDot() == e.getMark())
                    return;

                highlight(selectHighlightPainter, textPane.getSelectedText());
            }
        });
        // textPane.setAutoscrolls(true);
        doc = textPane.getStyledDocument();

        scrollPane.setViewportView(textPane);

        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.SOUTH);

        edSearch = new JTextField();
        edSearch.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                highlight(searchHighlightPainter, edSearch.getText());
            }
        });
        panel.add(edSearch);
        edSearch.setColumns(20);

        JButton btnNewButton = new JButton("Выделить в тексте");
        btnNewButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                highlight(searchHighlightPainter, edSearch.getText());
            }
        });
        panel.add(btnNewButton);
        final LogWorker worker = new LogWorker();
        worker.execute();
        if (file.exists()) {
            try {
                limit = Math.max(file.length() - 10000, file.length() / 10);
                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "windows-1251"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        setAlwaysOnTop(true);
        addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
                //
            }

            @Override
            public void windowIconified(WindowEvent e) {
                //
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                //
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                //
            }

            @Override
            public void windowClosing(WindowEvent e) {
                worker.cancel(false);
            }

            @Override
            public void windowClosed(WindowEvent e) {
                //
            }

            @Override
            public void windowActivated(WindowEvent e) {
                //
            }
        });

    }
    private HighlightWorker selWorker = null;

    protected void highlight(MyHighlightPainter painter, String pattern) {
        if (selWorker != null) {
            selWorker.cancel(false);
        }
        selWorker = new HighlightWorker(textPane, pattern, painter);
        selWorker.execute();
    }

    class LogWorker extends SwingWorker<Void, String> {

        private static final int MAX_SIZE_LOG = 10000000;
        private AttributeSet attr = attrBlack;

        @Override
        protected Void doInBackground() throws Exception {
            while (!isCancelled()) {
                if (bufferedReader != null) {
                    String line = bufferedReader.readLine();
                    if (line != null) {
                        publish(line);
                    } else {
                        Thread.sleep(10);
                        limit = 0;
                    }
                } else {
                    Thread.sleep(1000);
                }
            }
            bufferedReader.close();
            return null;
        }

        @Override
        protected void process(List<String> chunks) {
            if (chunks != null) {
                for (String line : chunks) {
                    limit = limit - line.length() - 2;
                    if (limit <= 0) {
                        try {
                            String lll = line.toLowerCase();
                            if (lll.contains("info :") || lll.contains("command = ")) {
                                attr = attrBlue;
                            } else if (lll.contains("trace:") || lll.contains("debug:")) {
                                attr = attrBlack;
                            } else if (lll.contains("warn:")) {
                                attr = attrORANGE;
                            } else if (lll.contains("error:")) {
                                attr = attrRed;
                            } else if (lll.contains("fatal:")) {
                                attr = attrRedDark;

                            }
                            doc.insertString(doc.getLength(), line + "\n", attr);
                            if (doc.getLength() > MAX_SIZE_LOG) {
                                doc.remove(0, MAX_SIZE_LOG / 3);
                                String s = doc.getText(0, Math.min(1000, MAX_SIZE_LOG / 5));
                                int k = s.indexOf('\n');
                                if (k > -1) {
                                    doc.remove(0, k + 1);
                                }
                            }
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

    }

    class HighlightWorker extends SwingWorker<Void, Integer> {

        private JTextComponent textComp;
        private MyHighlightPainter painter;
        private String pattern;
        private Highlighter hilite;

        // Removes only our private highlights
        public HighlightWorker(JTextComponent textComp, String pattern, MyHighlightPainter painter) {
            this.textComp = textComp;
            this.pattern = pattern.toUpperCase();
            this.painter = painter;
            Highlighter hilite = textComp.getHighlighter();
            Highlighter.Highlight[] hilites = hilite.getHighlights();
            for (int i = 0; i < hilites.length; i++) {
                if (hilites[i].getPainter() == painter) {
                    hilite.removeHighlight(hilites[i]);
                }
            }
        }

        @Override
        protected Void doInBackground() throws Exception {
            if (pattern.length() > 2) {
                try {
                    hilite = textComp.getHighlighter();
                    Document doc = textComp.getDocument();
                    String text = doc.getText(0, doc.getLength());
                    int pos = 0;

                    // Search for pattern
                    // see I have updated now its not case sensitive
                    String allText = text.toUpperCase();
                    while (!isCancelled() && (pos = allText.indexOf(pattern, pos)) >= 0) {
                        // Create highlighter using private painter and apply around pattern
                        publish(pos);
                        pos += pattern.length();
                    }
                } catch (BadLocationException e) {
                    //
                }
            }
            return null;
        }

        @Override
        protected void process(List<Integer> chunks) {
            for (Integer pos : chunks) {
                if (!isCancelled()) {
                    try {
                        hilite.addHighlight(pos, pos + pattern.length(), painter);
                    } catch (BadLocationException e) {
                        //
                    }
                }
            }
        }

    }
    // An instance of the private subclass of the default highlight painter
    private MyHighlightPainter selectHighlightPainter = new MyHighlightPainter(Color.YELLOW.darker());
    private MyHighlightPainter searchHighlightPainter = new MyHighlightPainter(Color.YELLOW);
    private JTextField edSearch;

    // A private subclass of the default highlight painter
    class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter {

        public MyHighlightPainter(Color color) {
            super(color);
        }
    }

}
