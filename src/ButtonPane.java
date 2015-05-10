import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by PeterCP on 5/8/15.
 */
public class ButtonPane extends JPanel implements ActionListener {

	//private String[][] buttonLabels = {
	//		{"MC", "MR", "MS", "M+", "M-"},
	//		{"<-", "CE", "C", "±", "√"},
	//		{"7", "8", "9", "/", "%"},
	//		{"4", "5", "6", "*", "1/x"},
	//		{"1", "2", "3", "-", "Ans"},
	//		{"0", "00", ".", "+", "="}
	//};

	private String[][] buttonLabels = {
			{"MC", "MR", "MS", "M+", "M-"},
			{"<-", "CE", "C", "+-", "sqrt"},
			{"7", "8", "9", "/", "%"},
			{"4", "5", "6", "*", "1/x"},
			{"1", "2", "3", "-", "Ans"},
			{"0", "00", ".", "+", "="}
	};

	public ButtonPane () {
		super (new GridLayout (6, 5));

		JButton btn;
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 6; y++) {
				btn = new JButton (x + "," + y);
				btn.setSize (50, 50);
				btn.addActionListener (this);
				add (btn);
			}
		}
		updateUI ();
		setSize (50*6, 50*6);
	}

	@Override
	public void actionPerformed (ActionEvent e) {
		String cmd = e.getActionCommand ();
		System.out.println (cmd);
	}
}
