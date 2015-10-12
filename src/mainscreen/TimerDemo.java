package mainscreen;

import javax.swing.Timer;
import java.awt.event.*;
import javax.swing.JLabel;

public class TimerDemo implements ActionListener {
    private Timer timer = new Timer(1000,this);
    private int count = 0;
    private JLabel label;
 
    TimerDemo(JLabel countdown) {
        this.label = countdown;
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
                
        if (e.getSource() == timer) {
            label.setText("Je moet " + String.valueOf(count--) + " seconden wachten.");
        }
    }
}