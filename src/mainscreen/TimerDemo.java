package mainscreen;

import javax.swing.Timer;
import java.awt.event.*;

public class TimerDemo implements ActionListener {
  Timer timer = new Timer(1000,this);
  
  int count = 100;
  
  TimerDemo() {
    timer.start();
    }
  
  public static void main(int args) {
    TimerDemo td = new TimerDemo();
    
    }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == timer) {
        System.out.println(count);
        count--;
      }
    }
}