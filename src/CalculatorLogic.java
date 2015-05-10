import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class CalculatorLogic implements ActionListener {

	private ArrayList<String> numbers = new ArrayList<String> (
			Arrays.asList ("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "00", ".")),
			operators = new ArrayList<String> (Arrays.asList ("+", "-", "*", "÷", "=", "^", "mod"));

	private double dUpper, dLower, memory;
	private String upper, lower, operator;
	private boolean calculating, error;

	public CalculatorLogic () {
		clear ();
		memory = Double.NaN;
	}

	public void clear () {
		dUpper = Double.NaN;
		dLower = 0;
		upper = "";
		lower = "0";
		operator = null;
		calculating = true;
		error = false;
	}

	public String getUpper () {
		return upper;
	}

	public String getLower () {
		return lower;
	}

	public void calculate () {
		if (!Double.isNaN (dUpper) && !Double.isNaN (dLower) && operator != null) {
			if (operator.equals ("+")) {
				dLower = dUpper + dLower;
			}
			else if (operator.equals ("-")) {
				dLower = dUpper - dLower;
			}
			else if (operator.equals ("*")) {
				dLower = dUpper * dLower;
			}
			else if (operator.equals ("÷")) {
				dLower = dUpper / dLower;
			}
			else if (operator.equals ("mod")) {
				dLower = dUpper % dLower;
			}
			else if (operator.equals ("^")) {
				dLower = Math.pow (dUpper, dLower);
			}
			lower = Double.toString (dLower);
			operator = null;
		}
	}

	@Override
	public String toString () {
		return "CalculatorLogic[dUpper: " + dUpper + ", dLower: " + dLower +
				", memory: " + memory + ", upper: " + upper + ", lower: " + lower +
				", operator: " + operator + ", calculating: " + calculating;
	}

	@Override
	public void actionPerformed (ActionEvent event) {
		String cmd = event.getActionCommand ();
		boolean isNum = true;
		try {
			dLower = Double.parseDouble (lower);
		}
		catch (NumberFormatException exception) {
			dLower = Double.NaN;
			isNum = false;
		}

		if (numbers.contains (cmd)) {
			if (calculating)
				lower = cmd;
			else
				lower += cmd;
			calculating = false;
		}
		else if (operators.contains (cmd)) {
			if (cmd.equals ("-") && calculating) {
				lower = cmd;
				calculating = false;
			}
			else if (cmd.equals ("=")) {
				if (operator != null)
					upper = upper + " " + lower + " =";
				else {
					lower = Double.toString (dLower);
					upper = lower + " =";
				}
				calculate ();
				calculating = true;
			}
			else {
				if (!Double.isNaN (dUpper)) {
					calculate ();
				}
				operator = cmd;
				dUpper = dLower;
				upper = lower + " " + operator;
				calculating = true;
			}
		}
		else {
			if (cmd.equals ("MC")) {
				memory = Double.NaN;
			}
			else if (cmd.equals ("MR")) {
				if (!Double.isNaN (memory)) {
					lower = Double.toString (memory);
					calculating = true;
				}
			}
			else if (cmd.equals ("MS")) {
				memory = dLower;
			}
			else if (cmd.equals ("M+")) {
				if (!Double.isNaN (memory)) {
					memory += dLower;
					calculating = true;
				}
			}
			else if (cmd.equals ("M-")) {
				if (!Double.isNaN (memory)) {
					memory -= dLower;
					calculating = true;
				}
			}
			else if (cmd.equals ("←")) {
				if (lower.length () > 1 && !calculating)
					lower = lower.substring (0, lower.length () - 1);
				else {
					lower = "0";
					calculating = true;
				}
			}
			else if (cmd.equals ("CE")) {
				if (error) {
					clear ();
				}
			}
			else if (cmd.equals ("C")) {
				clear ();
			}
			else if (cmd.equals ("±")) {
				if (isNum)
					lower = Double.toString (-dLower);
			}
			else if (cmd.equals ("√")) {
				if (isNum)
					lower = Double.toString (Math.sqrt (dLower));
			}
			else if (cmd.equals ("1/X")) {
				if (isNum) {
					dLower = 1 / dLower;
					lower = Double.toString (dLower);
				}
			}
			else if (cmd.equals ("%")) {
				if (isNum) {
					dLower = dLower / 100;
					lower = Double.toString (dLower);
				}
			}
			//else if (cmd.equals ("mod")) {
			//	//
			//}
			//else if (cmd.equals ("+")) {
			//	operator = cmd;
			//}
			//else if (cmd.equals ("-")) {
			//	if (calculating) {
			//		lower = cmd;
			//		calculating = false;
			//	}
			//	else
			//		operator = cmd;
			//}
			//else if (cmd.equals ("÷")) {
			//	//
			//}
			//else if (cmd.equals ("*")) {
			//	//
			//}
			//else if (cmd.equals ("=")) {
			//	//
			//}
		}
		System.out.println (this);
	}
}
