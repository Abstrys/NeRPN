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

import javax.swing.JFrame;
import java.awt.Container;
import java.awt.event.*;

// Current issues:
// 0001: Need to make the input panel get keyboard focus every time the frame
//       has focus.
// 0004: A menu should be added, with items to get help, about, and set
//       preferences for the application
// 0005: A keypad should be added, that can be shown or hidden as desired
//       (selectable via settings in the menu).
public class NeRPN extends JFrame
{

    RPNPanel rpn_panel = null;

    public NeRPN()
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("NeRPN - v1.0");

        Container cp = getContentPane();
        rpn_panel = new RPNPanel();
        cp.add(rpn_panel);

        addWindowFocusListener(new WindowAdapter()
        {
            // TODO: this may not be necessary.

            public void WindowGainedFocus(WindowEvent e)
            {
                rpn_panel.requestFocusInWindow();
            }
        });

        pack();
        setVisible(true);
    }

    static public void main(String[] args)
    {
        NeRPN app = new NeRPN();
    }
}

