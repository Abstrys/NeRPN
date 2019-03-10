//  NeRPN: A minimalistic RPN Calculator in Java.
//  Copyright © 2007 Eron J. Hennessey
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
import java.awt.*;
import java.awt.event.*;

class RPNPanel extends JPanel implements ActionListener {

    private JButton enter_button = null;
    private JButton back_button = null;
    private JTextField input_field = null;
    private RPNViewPanel view_panel = null;
    private RPNCalc calc = null;

    RPNPanel() {
        calc = new RPNCalc();

        BorderLayout layout = new BorderLayout();
        layout.setVgap(2);
        setLayout(layout);

        view_panel = new RPNViewPanel();
        add(view_panel, BorderLayout.CENTER);
        add(createInputPanel(), BorderLayout.SOUTH);
        input_field.requestFocusInWindow();
        input_field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                final char char_typed = e.getKeyChar();
                final String key_ops = "+-*/!^%";
                if (key_ops.indexOf((int) char_typed) != -1) {
                    String text = input_field.getText();
                    if ((text != null) && (text.length() > 0)) {
                        if (addToStack(text)) {
                            input_field.setText("");
                        }
                    }
                    addToStack(String.valueOf(char_typed));
                    e.consume();
                }
            }
        });
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);

        input_field = new JTextField(12);
        input_field.setHorizontalAlignment(JTextField.RIGHT);
        input_field.addActionListener(this);
        panel.add(input_field);

        back_button = new JButton("<");
        back_button.setMargin(new Insets(0, 1, 0, 1));
        back_button.addActionListener(this);
        panel.add(back_button);

        enter_button = new JButton("Enter");
        enter_button.setMargin(new Insets(0, 2, 0, 2));
        enter_button.addActionListener(this);
        panel.add(enter_button);
        input_field.requestFocusInWindow();

        return panel;
    }

    /**
     * Clears the input box
     */
    public void clearInput() {
        if (input_field != null) {
            input_field.setText("");
        }
    }

    /**
     * Returns the text that's in the input box
     */
    public String getInputText() {
        if (input_field != null) {
            return input_field.getText();
        }
        return null;
    }

    // Implementation of the ActionListener interface method.
    public void actionPerformed(ActionEvent e)
    {
        Object src = e.getSource();
        if (src == back_button) {
            String field_text = input_field.getText();
            if (!field_text.equals("")) {
                field_text = field_text.substring(0, field_text.length() - 1);
                input_field.setText(field_text);
            }
            else if (calc.getStackHeight() > 0) {
                addToStack("del");
            }
        } else if ((src == enter_button) || (src == input_field)) {
            String input_text = input_field.getText();
            if ((input_text == null) || (input_text.equals(""))) {
                addToStack("dup");
            } else if (addToStack(input_text)) {
                input_field.setText("");
            }
        }
        input_field.requestFocusInWindow();
    }

    /**
     * Adds the value from the input box to the stack.
     * @param text the text to add to the stack.
     * @return true if the value could be added, or false if an error occured
     * or validation of the text failed.
     */
    private boolean addToStack(String text) {
        try {
            calc.push(text);
            view_panel.updateView(calc);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                null, "\"" + text + "\" is not a valid value!",
                "NeRPN ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (ArithmeticException e) {
            JOptionPane.showMessageDialog(
                null, e.getMessage(), "NeRPN ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}

