import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class CalculatorLogic implements ActionListener {

	class CalculatorLogicState {
		double dUpper, dLower, memory;
		String upper, lower, operator;
		boolean calculating, error;

		void clear () {
			dUpper = Double.NaN;
			dLower = 0;
			upper = "";
			lower = "0";
			operator = null;
			calculating = true;
			error = false;
		}
	}

	private ArrayList<String> numbers = new ArrayList<String> (Arrays.asList
			("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "00", ".")),
			operators = new ArrayList<String> (Arrays.asList
					("+", "-", "*", "÷", "=", "^", "mod", "y√x", "x^y"));

	private double dUpper, dLower, memory;
	private String upper, lower, operator;
	private boolean calculating, error;
	private CalculatorLogicState lastState;

	public CalculatorLogic () {
		clear ();
		memory = Double.NaN;
		lastState = new CalculatorLogicState ();
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

	private void save () {
		lastState.dUpper = dUpper;
		lastState.dLower = dLower;
		lastState.memory = memory;
		lastState.upper = upper;
		lastState.lower = lower;
		lastState.operator = operator;
		lastState.calculating = calculating;
		lastState.error = error;
	}

	private void restore () {
		dUpper = lastState.dUpper;
		dLower = lastState.dLower;
		memory = lastState.memory;
		upper = lastState.upper;
		lower = lastState.lower;
		operator = lastState.operator;
		calculating = lastState.calculating;
		error = lastState.error;
	}

	public void calculate () {
		if (!Double.isNaN (dUpper) && !Double.isNaN (dLower) && operator != null && !error) {
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
				if (dLower != 0)
					dLower = dUpper / dLower;
				else
					error = true;
			}
			else if (operator.equals ("mod")) {
				dLower = dUpper % dLower;
			}
			else if (operator.equals ("^") || operator.equals ("x^y")) {
				dLower = Math.pow (dUpper, dLower);
			}
			else if (operator.equals ("y√x")) {
				dLower = Math.pow (dUpper, 1/dLower);
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
		boolean isNum;
		try {
			dLower = Double.parseDouble (lower);
			isNum = true;
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
			error = false;
		}
		else if (operators.contains (cmd)) {
			if (cmd.equals ("-") && calculating) {
				lower = cmd;
				calculating = false;
				error = false;
			}
			else if (cmd.equals ("=")) {
				if (!error) {
					if (operator != null)
						upper = upper + " " + lower + " =";
					else {
						lower = Double.toString (dLower);
						upper = lower + " =";
					}
					calculate ();
					calculating = true;
				}
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
					upper = "MR: " + lower;
					calculating = true;
					error = false;
				}
				else
					error = true;
			}
			else if (cmd.equals ("MS")) {
				if (isNum) {
					memory = dLower;
					upper = "MS: " + lower;
				}
				else
					error = true;
			}
			else if (cmd.equals ("M+")) {
				if (!Double.isNaN (memory) && isNum) {
					memory += dLower;
					calculating = true;
				}
				else
					error = true;
			}
			else if (cmd.equals ("M-")) {
				if (!Double.isNaN (memory) && isNum) {
					memory -= dLower;
					calculating = true;
				}
				else
					error = true;
			}
			else if (cmd.equals ("←")) {
				if (lower.length () > 1 && !calculating)
					lower = lower.substring (0, lower.length () - 1);
				else {
					lower = "0";
					calculating = true;
				}
				error = false;
			}
			else if (cmd.equals ("CE")) {
				if (error) {
					restore ();
					error = false;
					calculating = true;
				}
				else
					clear ();
			}
			else if (cmd.equals ("C")) {
				clear ();
				error = false;
			}
			else if (cmd.equals ("±")) {
				if (isNum) {
					lower = Double.toString (-dLower);
					error = false;
				}
				error = true;
			}
			else if (cmd.equals ("1/X")) {
				if (isNum) {
					upper = "1 ÷ " + lower + " =";
					if (dLower != 0) {
						dLower = 1 / dLower;
						lower = Double.toString (dLower);
					}
					else
						error = true;
				}
			}
			else if (cmd.equals ("%")) {
				if (isNum) {
					dLower = dLower / 100;
					lower = Double.toString (dLower);
					error = false;
				}
				error = true;
			}
			else if (cmd.equals ("√") || cmd.equals ("√x")) {
				if (isNum) {
					upper = "√ " + lower + " =";
					if (dLower >= 0) {
						dLower = Math.sqrt (dLower);
						lower = Double.toString (dLower);
					}
					else
						error = true;
				}
			}
			else if (cmd.equals ("3√x")) {
				if (isNum) {
					upper = "3√ " + lower + " =";
					dLower = Math.pow (dLower, 1/3);
					lower = Double.toString (dLower);
				}
			}
			else if (cmd.equals ("y√x")) {
			}

			else if (cmd.equals ("x^2")) {
				if (isNum) {
					dLower = Math.pow (dLower, 2);
					upper = lower + " ^ 2 =";
					lower = Double.toString (dLower);
				}
			}
			else if (cmd.equals ("x^3")) {
				if (isNum) {
					dLower = Math.pow (dLower, 3);
					upper = lower + " ^ 3 =";
					lower = Double.toString (dLower);
				}
			}
			else if (cmd.equals ("n!")) {
				long n = Math.round (dLower);
				dLower = 1;
				for (int i = 1; i <= n; i++) {
					dLower *= i;
				}
				upper = lower + "! =";
				lower = Double.toString (dLower);
			}
			else if (cmd.equals ("sin")) {
				dLower = Math.sin (dLower);
				upper = "sin " + lower + " =";
				lower = Double.toString (dLower);
			}
			else if (cmd.equals ("cos")) {
				dLower = Math.cos (dLower);
				upper = "cos " + lower + " =";
				lower = Double.toString (dLower);
			}
			else if (cmd.equals ("tan")) {
				dLower = Math.tan (dLower);
				upper = "tan " + lower + " =";
				lower = Double.toString (dLower);
			}
		}
		if (!error) {
			save ();
		}
		else {
			lower = "Error";
		}
	}
}
