/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

/**
 * TextInputDemo.java uses these additional files:
 *   SpringUtilities.java
 */
public class TextInputGUI extends JPanel
                                          implements ActionListener,
                                                     FocusListener {
    JTextField propertyIDField;
    JTextField leaseIDField;
    JFormattedTextField rentField;
    JSpinner categorySpinner;
    boolean recordSet = false;
    Font regularFont;
    Font italicFont;
    JLabel recordDisplay;
    private final int gap = 10;

    public TextInputGUI() {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        JPanel leftHalf = new JPanel() {
            //Don't allow us to stretch vertically.
            public Dimension getMaximumSize() {
                Dimension pref = getPreferredSize();
                return new Dimension(Integer.MAX_VALUE,
                                     pref.height);
            }
        };
        leftHalf.setLayout(new BoxLayout(leftHalf,
                                         BoxLayout.PAGE_AXIS));
        leftHalf.add(createEntryFields());
        leftHalf.add(createButtons());

        add(leftHalf);
        add(createRecordDisplay());
    }

    protected JComponent createButtons() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.TRAILING));

        JButton button = new JButton("Add lease record");
        button.addActionListener(this);
        panel.add(button);

        button = new JButton("Clear");
        button.addActionListener(this);
        button.setActionCommand("clear");
        panel.add(button);

        //Match the SpringLayout's gap, subtracting 5 to make
        //up for the default gap FlowLayout provides.
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0,
                                                gap - 5,gap - 5));
        return panel;
    }

    /**
     * Called when the user clicks the button or presses
     * Enter in a text field.
     */
    public void actionPerformed(ActionEvent e) {
        if ("clear".equals(e.getActionCommand())) {
            recordSet = false;
            propertyIDField.setText("");
            leaseIDField.setText("");

            //We can't just setText on the formatted text
            //field, since its value will remain set.
            rentField.setValue(null);
        } else {
            recordSet = true;
        }
        updateDisplays();
    }

    protected void updateDisplays() {
//        recordDisplay.setText(formatAddress());
        if (recordSet) {
            recordDisplay.setFont(regularFont);
        } else {
            recordDisplay.setFont(italicFont);
        }
    }

    protected JComponent createRecordDisplay() {
        JPanel panel = new JPanel(new BorderLayout());
        recordDisplay = new JLabel();
        recordDisplay.setHorizontalAlignment(JLabel.CENTER);
        regularFont = recordDisplay.getFont().deriveFont(Font.PLAIN,
                                                            16.0f);
        italicFont = regularFont.deriveFont(Font.ITALIC);
        updateDisplays();

        //Lay out the panel.
        panel.setBorder(BorderFactory.createEmptyBorder(
                                gap / 2, //top
                                0,     //left
                                gap / 2,//bottom
                                0));   //right
        panel.add(new JSeparator(JSeparator.VERTICAL),
                  BorderLayout.LINE_START);
        panel.add(recordDisplay,
                  BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(200, 150));

        return panel;
    }

//    protected String formatAddress() {
//        if (!recordSet) {
//            return "No record set.";
//        }
//
//        String propertyID = propertyIDField.getText();
//        String leaseID = leaseIDField.getText();
//        String propertyCategory = (String) categorySpinner.getValue();
//        String monthlyRent = rentField.getText();
//        String empty = "";
//
//
//        if ((leaseID == null) || empty.equals(leaseID)) {
//            leaseID = "<em>(no leaseID specified)</em>";
//        }
//        if ((propertyCategory == null) || empty.equals(propertyCategory)) {
//            propertyCategory = "<em>(no propertyCategory specified)</em>";
//        } else {
//            int abbrevIndex = propertyCategory.indexOf('(') + 1;
//            propertyCategory = propertyCategory.substring(abbrevIndex,
//                                    abbrevIndex + 2);
//        }
//        if ((monthlyRent == null) || empty.equals(monthlyRent)) {
//            monthlyRent = "";
//        }
//
//        StringBuffer sb = setSpringBuffer(propertyID, leaseID, propertyCategory, monthlyRent);
//
//        return sb.toString();
//    }

    private StringBuffer setSpringBuffer(String propertyID, String leaseID,
                                         String propertyCategory, String monthlyRent) {
        StringBuffer sb = new StringBuffer();
        sb.append("<html><p align=center>");
        sb.append(propertyID);
        sb.append("<br>");
        sb.append(leaseID);
        sb.append(" ");
        sb.append(propertyCategory); //should format
        sb.append(" ");
        sb.append(monthlyRent);
        sb.append("</p></html>");
        return sb;
    }

    //A convenience method for creating a MaskFormatter.
    protected MaskFormatter createFormatter(String s) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);
        } catch (java.text.ParseException exc) {
            System.err.println("formatter is bad: " + exc.getMessage());
            System.exit(-1);
        }
        return formatter;
    }

    /**
     * Called when one of the fields gets the focus so that
     * we can select the focused field.
     */
    public void focusGained(FocusEvent e) {
        Component c = e.getComponent();
        if (c instanceof JFormattedTextField) {
            selectItLater(c);
        } else if (c instanceof JTextField) {
            ((JTextField)c).selectAll();
        }
    }

    //Workaround for formatted text field focus side effects.
    protected void selectItLater(Component c) {
        if (c instanceof JFormattedTextField) {
            final JFormattedTextField ftf = (JFormattedTextField)c;
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    ftf.selectAll();
                }
            });
        }
    }

    //Needed for FocusListener interface.
    public void focusLost(FocusEvent e) { } //ignore

    protected JComponent createEntryFields() {
        JPanel panel = new JPanel(new SpringLayout());

        String[] labelStrings = {
            "Property ID: ", "City: ", "State: ", "Zip code: "
        };

        JLabel[] labels = new JLabel[labelStrings.length];
        JComponent[] fields = new JComponent[labelStrings.length];
        int fieldNum = 0;

        //Create the text field and set it up.
        propertyIDField = new JTextField();
        propertyIDField.setColumns(20);
        fields[fieldNum++] = propertyIDField;

        leaseIDField = new JTextField();
        leaseIDField.setColumns(20);
        fields[fieldNum++] = leaseIDField;

        String[] stateStrings = getCategoryStrings();
        categorySpinner = new JSpinner(new SpinnerListModel(stateStrings));
        fields[fieldNum++] = categorySpinner;

        rentField = new JFormattedTextField(createFormatter("#####"));
        fields[fieldNum++] = rentField;

        layoutLabels(panel, labelStrings, labels, fields);
        SpringUtilities.makeCompactGrid(panel, labelStrings.length, 2,
                gap, gap, //init x,y
                gap, gap / 2);//xpad, ypad
        return panel;
    }

    private void layoutLabels(JPanel panel, String[] labelStrings, JLabel[] labels, JComponent[] fields) {
        //Associate label/field pairs, add everything,
        //and lay it out.
        for (int i = 0; i < labelStrings.length; i++) {
            labels[i] = new JLabel(labelStrings[i],
                                   JLabel.TRAILING);
            labels[i].setLabelFor(fields[i]);
            panel.add(labels[i]);
            panel.add(fields[i]);

            //Add listeners to each field.
            JTextField tf = null;
            if (fields[i] instanceof JSpinner) {
                tf = getTextField((JSpinner) fields[i]);
            } else {
                tf = (JTextField) fields[i];
            }
            tf.addActionListener(this);
            tf.addFocusListener(this);
        }
    }

    public String[] getCategoryStrings() {
        String[] categoryStrings = {
            "COMMERCIAL",
            "RESIDENTIAL",
            "OFFICE",
            "RETAIL",
            "MIX"
        };
        return categoryStrings;
    }

    public JFormattedTextField getTextField(JSpinner spinner) {
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            return ((JSpinner.DefaultEditor)editor).getTextField();
        } else {
            System.err.println("Unexpected editor type: "
                               + spinner.getEditor().getClass()
                               + " isn't a descendant of DefaultEditor");
            return null;
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("TextInputDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add contents to the window.
        frame.add(new TextInputGUI());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
	        UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
    }
}
