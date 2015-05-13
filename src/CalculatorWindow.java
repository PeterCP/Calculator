import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorWindow extends JFrame implements ActionListener {

	private static String[][] buttonLabels = {
			{"MC", "MR", "MS", "M+", "M-"},
			{"←", "CE", "C", "±", "√"},
			{"7", "8", "9", "÷", "^"},
			{"4", "5", "6", "*", "1/X"},
			{"1", "2", "3", "-", "mod"},
			{"0", ".", "%", "+", "="}
	};

	private JLabel displayUpper, displayLower;
	private CalculatorLogic logic;

	public CalculatorWindow () {
		super ("Calculator");
		setLayout (null);
		setResizable (false);
		setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
		setSize (325, 525);

		logic = new CalculatorLogic ();

		displayUpper = new JLabel (logic.getUpper ());
		displayUpper.setLocation (40, 40);
		displayUpper.setSize (260, 20);
		displayUpper.setFont (new Font ("Arial", Font.PLAIN, 17));
		displayUpper.setHorizontalAlignment (SwingConstants.RIGHT);

		getContentPane ().add (displayUpper);

		displayLower = new JLabel (logic.getLower ());
		displayLower.setLocation (12, 60);
		displayLower.setSize (290, 45);
		displayLower.setFont (new Font ("Arial", Font.PLAIN, 46));
		displayLower.setHorizontalAlignment (SwingConstants.RIGHT);
		getContentPane ().add (displayLower);

		Point btnPoint = new Point (15, 125);
		Dimension btnDim = new Dimension (55, 50);
		JButton btn;
		for (int y = 0; y < 6; y++) {
			for (int x = 0; x < 5; x++) {
				btn = new JButton (buttonLabels[y][x]);
				btn.setSize (btnDim);
				btn.setLocation (btnPoint);
				btn.setBorder (new LineBorder (Color.BLACK));
				btn.setBackground (Color.WHITE);
				btn.setOpaque (true);
				btn.setBorderPainted (true);
				btn.addActionListener (this);
				getContentPane ().add (btn);

				btnPoint.x += 60;
			}
			btnPoint.x = 15;
			btnPoint.y += 60;
		}
	}

	@Override
	public void actionPerformed (ActionEvent e) {
		logic.actionPerformed (e);
		displayUpper.setText (logic.getUpper ());
		displayLower.setText (logic.getLower ());
	}

	public static void main (String[] args) {
		CalculatorWindow c = new CalculatorWindow ();
		c.setVisible (true);
	}
}
