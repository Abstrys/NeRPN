//  NeRPN: A minimalistic RPN Calculator in Java.
//  Copyright (C) 2007 Eron J. Hennessey
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU General Public License for more details.
//
//  You should have received a copy of the GNU General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.
package abstrys.NeRPN;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Stack;
import java.text.DecimalFormat;

/**
 * Contains a scrollable, resizable view of a stack, consisting
 * of text elements and labels
 */
public class RPNViewPanel extends JPanel
{

    final int PANEL_SIZE = 4;
    JPanel row_value_panel;
    Stack stack;
    DecimalFormat format = null;

    public RPNViewPanel()
    {
        BorderLayout layout = new BorderLayout();
        layout.setHgap(2);
        setLayout(layout);
        add(createRowLabelPanel(), BorderLayout.WEST);
        add(createRowValuePanel(), BorderLayout.CENTER);

        // TODO: set up a number of different formats that can be selected.
        format = new DecimalFormat("0.######");

        setBorder(new EmptyBorder(4, 4, 2, 4));
    }

    private JPanel createRowLabelPanel()
    {
        final String ROW_LABELS[] =
        {
            "w", "z", "y", "x"
        };

        JPanel panel = new JPanel();
        GridLayout layout = new GridLayout(PANEL_SIZE, 0);
        layout.setVgap(2);
        panel.setLayout(layout);

        for (int i = 0; i < PANEL_SIZE; i++)
        {
            JLabel label = new JLabel(ROW_LABELS[i]);
            label.setHorizontalAlignment(JLabel.CENTER);
            panel.add(label);
        }
        return panel;
    }

    private JPanel createRowValuePanel()
    {
        row_value_panel = new JPanel();
        GridLayout layout = new GridLayout(PANEL_SIZE, 0);
        layout.setVgap(2);
        row_value_panel.setLayout(layout);

        for (int i = 0; i < PANEL_SIZE; i++)
        {
            JTextField field = new JTextField();
            field.setEditable(false);
            field.setHorizontalAlignment(JTextField.RIGHT);
            if (i == (PANEL_SIZE - 1))
            {
                Font bold_font = field.getFont().deriveFont(Font.BOLD);
                field.setFont(bold_font);
                field.setBackground(new Color(255, 255, 204));
            }
            row_value_panel.add(field);
        }
        return row_value_panel;
    }

    /**
     * Updates the stack view using the first four elements of a
     * passed-in Stack object.
     * @param stack the Stack to use.
     */
    public void updateView(RPNCalc calc)
    {
        final int s = calc.getStackHeight();
        for (int i = 0; (i < PANEL_SIZE); i++)
        {
            JTextField val_field = (JTextField) row_value_panel.getComponent(PANEL_SIZE - 1 - i);
            if (i < s)
            {
                val_field.setText(calc.getStackEntry(s - 1 - i));
            }
            else
            {
                val_field.setText(null);
            }
        }
        repaint();
    }
}

