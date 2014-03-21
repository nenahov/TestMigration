package ru.crystals.config;

import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class IntegerField extends JTextField implements FocusListener {

    private static final long serialVersionUID = 1L;

    public static IntegerField selected = null;

    public IntegerField() {
        super();
        setInt(null);
        addFocusListener(this);
    }

    /**
     * Retrieve the contents of this field as an <TT>int</TT>.
     * 
     * @return the contents of this field as an <TT>int</TT>.
     */
    public Integer getInt() {
        final String text = getText();
        if (text == null || text.length() == 0) {
            return null;
        }
        return Integer.parseInt(text);
    }

    /**
     * Set the contents of this field to the passed <TT>int</TT>.
     * 
     * @param value
     *            The new value for this field.
     */
    public void setInt(Integer value) {
        if (value == null) {
            setText("");
        } else {
            setText("" + value);
        }
    }

    /**
     * Create a new document model for this control that only accepts integral values.
     * 
     * @return The new document model.
     */
    @Override
    protected Document createDefaultModel() {
        return new IntegerDocument();
    }

    @Override
    public void focusGained(FocusEvent e) {
        // Draw the component with a red border
        // indicating that it has focus.
        // this.repaint();
        selected = this;
    }

    @Override
    public void focusLost(FocusEvent e) {
        // Draw the component with a black border
        // indicating that it doesn't have focus.
        // this.repaint();
        if (selected == this) {
            selected = null;
        }
    }

    /**
     * This document only allows integral values to be added to it.
     */
    static class IntegerDocument extends PlainDocument {

        /**
		 * 
		 */
        private static final long serialVersionUID = 1L;

        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            boolean isValid = false;
            if (str != null) {
                if (str.equals("")) {
                    isValid = true;
                } else {
                    try {
                        Integer value = Integer.parseInt(str);
                        if (value >= 0) {
                            if ((getLength() + str.length()) <= 9) {
                                isValid = true;
                            }
                        }
                    } catch (NumberFormatException ex) {
                        //
                    }
                }
            }
            if (isValid) {
                super.insertString(offs, str, a);
            } else {
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }

}
