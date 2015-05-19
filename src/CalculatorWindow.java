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
	private static String[][] scientificButtonLabels = {
			{"sin", "√x"},
			{"cos", "3√x"},
			{"tan", "y√x"},
			{"x^2", "x^3"},
			{"x^y", "n!"},
			{"log", "10^x"}
	};

	private JMenuBar menuBar;
	private JMenuItem toggleModeMenuItem, converterMenuItem;
	private JLabel displayUpper, displayLower;
	private JPanel basicPanel, scientificPanel;
	private CalculatorLogic logic;
	private boolean scientificModeActive;

	public CalculatorWindow () {
		super ("Calculator");
		setLayout (null);
		setResizable (false);
		setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
		setSize (325, 525);

		menuBar = new JMenuBar ();
		setJMenuBar (menuBar);

		toggleModeMenuItem = new JMenuItem ("Scientific Mode");
		toggleModeMenuItem.setActionCommand ("toggleMode");
		toggleModeMenuItem.addActionListener (this);
		menuBar.add (toggleModeMenuItem);

		converterMenuItem = new JMenuItem ("Unit Converter");
		converterMenuItem.setActionCommand ("converter");
		converterMenuItem.addActionListener (this);
		menuBar.add (converterMenuItem);

		logic = new CalculatorLogic ();
		scientificModeActive = false;

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

		Point btnPoint;
		Dimension btnDim = new Dimension (55, 50);
		JButton btn;

		basicPanel = new JPanel (null);

		btnPoint = new Point ();
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
				basicPanel.add (btn);

				btnPoint.x += 60;
			}
			btnPoint.x = 0;
			btnPoint.y += 60;
		}
		basicPanel.setSize (60*5, 60*6);
		basicPanel.setLocation (15, 125);
		getContentPane ().add (basicPanel);

		scientificPanel = new JPanel (null);
		btnPoint = new Point ();
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 2; x++) {
				btn = new JButton (scientificButtonLabels[y][x]);
				btn.setSize (btnDim);
				btn.setLocation (btnPoint);
				btn.setBorder (new LineBorder (Color.BLACK));
				btn.setBackground (Color.WHITE);
				btn.setOpaque (true);
				btn.setBorderPainted (true);
				btn.addActionListener (this);
				scientificPanel.add (btn);

				btnPoint.x += 60;
			}
			btnPoint.x = 0;
			btnPoint.y += 60;
		}
		scientificPanel.setSize (60*2, 60*5);
		scientificPanel.setLocation (325, 125);
		scientificPanel.setVisible (false);
		getContentPane ().add (scientificPanel);
	}

	@Override
	public void actionPerformed (ActionEvent e) {
		String cmd = e.getActionCommand ();
		if (cmd.equals ("toggleMode")) {
			if (scientificModeActive) {
				setSize (325, 525);
				scientificPanel.setVisible (false);
				toggleModeMenuItem.setText ("Scientific Mode");
			}
			else {
				setSize (455, 525);
				scientificPanel.setVisible (true);
				toggleModeMenuItem.setText ("Basic Mode");
			}
			scientificModeActive = !scientificModeActive;
		}
		else if (cmd.equals ("converter")) {
			UnitConverter uc = new UnitConverter ();
			uc.setVisible (true);
		}
		else {
			logic.actionPerformed (e);
			displayUpper.setText (logic.getUpper ());
			displayLower.setText (logic.getLower ());
		}
	}

	public static void main (String[] args) {
		CalculatorWindow c = new CalculatorWindow ();
		c.setVisible (true);
	}
}
