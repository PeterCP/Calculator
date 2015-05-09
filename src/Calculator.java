import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JPanel implements ActionListener {

	private JTextField display;
	private double result = 0;
	private String operator = "=";
	private boolean calculating = true;
	private char[] characters = {'7', '8', '9', '/', '4', '5',
			'6', '*', '1', '2', '3', '-', '0', '.', '=', '+'};

	public Calculator () {
		setLayout (new BorderLayout ());

		display = new JTextField ("0");
		display.setEditable (false);
		add (display, "North");

		JPanel panel = new JPanel ();
		panel.setLayout (new GridLayout (4, 4));

		for (int i = 0; i < characters.length; i++) {
			JButton button = new JButton (String.valueOf (characters[i]));
			panel.add (button);
			button.addActionListener (this);

		}
		add (panel, "Center");
	}

	@Override
	public void actionPerformed (ActionEvent e) {
		String cmd = e.getActionCommand ();
		if ('0' <= cmd.charAt (0) && cmd.charAt (0) <= '9' || cmd.equals (".")) {
			if (calculating)
				display.setText (cmd);
			else
				display.setText (display.getText () + cmd);
			calculating = false;
		}
		else {
			if (calculating) {
				if (cmd.equals ("-")) {
					display.setText (cmd);
					calculating = false;
				}
				else
					operator = cmd;
			}
			else {
				double x = Double.parseDouble (display.getText ());
				calculate (x);
				operator = cmd;
				calculating = true;
			}
		}
	}

	private void calculate (double n) {
		if (operator.equals ("+"))
			result += n;
		else if (operator.equals ("-"))
			result -= n;
		else if (operator.equals ("*"))
			result *= n;
		else if (operator.equals ("/"))
			result /= n;
		else if (operator.equals ("="))
			result = n;
		display.setText (String.valueOf (result));
	}

	public static void main (String[] args) {
		//JFrame frame = new JFrame ();
		//frame.setTitle ("Calculator");
		//frame.setSize (200, 200);
		//frame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
		//
		//Container contentPane = frame.getContentPane ();
		//contentPane.add (new Calculator ());
		//frame.setVisible (true);

		//char radix = (char) 0x221A;
		//System.out.println (radix);
	}
}
