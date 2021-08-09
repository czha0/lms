package ui;

/******************************************************************************
 *  Compilation:  javac GUI.java
 *  Execution:    java GUI
 *
 *  A minimal Java program with a graphical user interface. The
 *  GUI prints out the number of times the user clicks a button.
 *
 *  % java GUI
 *
 * https://introcs.cs.princeton.edu/java/15inout/GUI.java.html
 ******************************************************************************/

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JPanel
        implements ActionListener {
    private int clicks = 0;
    private JTable table;
    private JLabel label = new JLabel("Number of clicks:  0 ");
    private JFrame frame = new JFrame();
    private JTextArea output;

    public GUI() {


        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        table = new JTable(new MyTableModel());
        table.setPreferredScrollableViewportSize(new Dimension(500, 100));
        table.setFillsViewportHeight(true);
        table.getSelectionModel().addListSelectionListener(new RowListener());
        table.getColumnModel().getSelectionModel().addListSelectionListener(new ColumnListener());
        add(new JScrollPane(table));

        // the clickable button
        JButton button = new JButton("Click Me");
        button.addActionListener(this);

        // the panel with the button and text
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(500, 300, 10, 300));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(button);
        panel.add(label);

        // display output
        output = new JTextArea(5, 40);
        output.setEditable(false);
        add(new JScrollPane(output));

        // set up the frame and display it
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Lease Management System");
        frame.pack();
        frame.setVisible(true);
    }

    // process the button clicks
    public void actionPerformed(ActionEvent e) {
        clicks++;
        label.setText("Number of clicks:  " + clicks);
    }


    /*
    Manifest-Version: 1.0
    X-COMMENT: Main-Class will be added automatically by build
    Codebase: *.oracle.com
    Permissions: sandbox
    Application-Name: Table Selection Demo from Oracle library
     */
    class MyTableModel extends AbstractTableModel {
        private String[] columnNames = {"Lease ID",
                "Property ID",
                "Lease Starts",
                "Lease End",
                "ActiveLease?"};
        private Object[][] data = {
                {"1", "1",
                        "Snowboarding", new Integer(5), new Boolean(false)},
                {"2", "2",
                        "Rowing", new Integer(3), new Boolean(true)},
                {"3", "3",
                        "Pool", new Integer(10), new Boolean(false)}
        };

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col < 2) {
                return false;
            } else {
                return true;
            }
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }

    }

    private class RowListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
            if (event.getValueIsAdjusting()) {
                return;
            }
            output.append("ROW SELECTION EVENT. ");
            outputSelection();
        }
    }

    private class ColumnListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
            if (event.getValueIsAdjusting()) {
                return;
            }
            output.append("COLUMN SELECTION EVENT. ");
            outputSelection();
        }
    }

    private void outputSelection() {
        output.append(String.format("Lead: %d, %d. ",
                table.getSelectionModel().getLeadSelectionIndex(),
                table.getColumnModel().getSelectionModel().
                        getLeadSelectionIndex()));
        output.append("Rows:");
        for (int c : table.getSelectedRows()) {
            output.append(String.format(" %d", c));
        }
        output.append(". Columns:");
        for (int c : table.getSelectedColumns()) {
            output.append(String.format(" %d", c));
        }
        output.append(".\n");
    }

    // create one Frame
    public static void main(String[] args) {
        new GUI();
    }
}
