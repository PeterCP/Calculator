import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class UnitConverter extends JFrame implements ActionListener {

	private class ConversionType {

		private final String name;
		private Vector<ConversionUnit> options;

		ConversionType (String name) {
			super ();
			this.name = name;
			options = new Vector<ConversionUnit> ();
		}

		public void add (ConversionUnit e) {
			options.add (e);
		}

		public DefaultComboBoxModel<ConversionUnit> model () {
			return new DefaultComboBoxModel<ConversionUnit> (options);
		}

		@Override
		public String toString () {
			return name;
		}
	}

	private class ConversionUnit {

		public final String name;
		public final double ratio;

		ConversionUnit (String name, double ratio) {
			this.name = name;
			this.ratio = ratio;
		}

		@Override
		public String toString () {
			return name;
		}
	}

	private JLabel typeLabel, fromLabel, toLabel;
	private JComboBox<ConversionType> typeComboBox;
	private JComboBox<ConversionUnit> fromComboBox, toComboBox;
	private JTextField fromTextField, toTextField;
	private JPanel fromPanel, toPanel;
	private Vector<ConversionType> conversionTypes;

	public UnitConverter () {
		super ("Unit Converter");
		setLayout (new BoxLayout (getContentPane (), BoxLayout.Y_AXIS));
		setSize (400, 200);
		setResizable (false);
		setDefaultCloseOperation (DISPOSE_ON_CLOSE);

		conversionTypes = new Vector<ConversionType> ();

		ConversionType length = new ConversionType ("Length");
		conversionTypes.add (length);
		length.add (new ConversionUnit ("Meter", 1000));
		length.add (new ConversionUnit ("Millimiter", 1));
		length.add (new ConversionUnit ("Centimeter", 10));
		length.add (new ConversionUnit ("Kilometer", 1000000));
		length.add (new ConversionUnit ("Inch", 25.4));
		length.add (new ConversionUnit ("Foot", 304.8));
		length.add (new ConversionUnit ("Yard", 914.4));
		length.add (new ConversionUnit ("Mile", 1609340));

		ConversionType mass = new ConversionType ("Mass");
		conversionTypes.add (mass);
		mass.add (new ConversionUnit ("Gram", 1000));
		mass.add (new ConversionUnit ("Milligram", 1));
		mass.add (new ConversionUnit ("Gram", 1000));
		mass.add (new ConversionUnit ("Kilogram", 1000000));
		mass.add (new ConversionUnit ("Metric Ton", 1000000000));
		mass.add (new ConversionUnit ("Pound", 453592));
		mass.add (new ConversionUnit ("Ounce", 28349.5));

		ConversionType speed = new ConversionType ("Speed");
		conversionTypes.add (speed);
		speed.add (new ConversionUnit ("Km / Hour", 1));
		speed.add (new ConversionUnit ("Meters / Sec", 3.6));
		speed.add (new ConversionUnit ("Miles / Hour", 1.60934));
		speed.add (new ConversionUnit ("Feet / Sec", 1.09728));
		speed.add (new ConversionUnit ("Knot", 1.852));

		ConversionType volume = new ConversionType ("Volume");
		conversionTypes.add (volume);
		volume.add (new ConversionUnit ("Liter", 1000));
		volume.add (new ConversionUnit ("Milliliter", 1));
		volume.add (new ConversionUnit ("Cubic Meter", 1000000));
		volume.add (new ConversionUnit ("US Gallon", 3785.41));
		volume.add (new ConversionUnit ("US Ounce", 29.5735));
		volume.add (new ConversionUnit ("Cubic Foot", 28316.8));
		volume.add (new ConversionUnit ("Cubic Inch", 16.3871));

		ConversionType area = new ConversionType ("Area");
		conversionTypes.add (area);
		area.add (new ConversionUnit ("Square Meter", 1));
		area.add (new ConversionUnit ("Square Kilometer", 1000000));
		area.add (new ConversionUnit ("Hectare", 10000));
		area.add (new ConversionUnit ("Square Mile", 2590000));
		area.add (new ConversionUnit ("Acre", 4046.86));

		ConversionType time = new ConversionType ("Time");
		conversionTypes.add (time);
		time.add (new ConversionUnit ("Second", 1));
		time.add (new ConversionUnit ("Nanosecond", 0.000000001));
		time.add (new ConversionUnit ("Microsecond", 0.000001));
		time.add (new ConversionUnit ("Millisecond", 0.001));
		time.add (new ConversionUnit ("Minute", 60));
		time.add (new ConversionUnit ("Hour", 3600));
		time.add (new ConversionUnit ("Day", 86400));
		time.add (new ConversionUnit ("Week", 604800));
		time.add (new ConversionUnit ("Month", 2630000));
		time.add (new ConversionUnit ("Year", 31560000));

		typeLabel = new JLabel ("Select the type of unit to convert.",
				SwingConstants.LEFT);
		getContentPane ().add (typeLabel);

		typeComboBox = new JComboBox<ConversionType>
				(new DefaultComboBoxModel<ConversionType>
						(conversionTypes));
		typeComboBox.setActionCommand ("typeComboBoxChanged");
		typeComboBox.addActionListener (this);
		getContentPane ().add (typeComboBox);

		fromPanel = new JPanel (new FlowLayout(FlowLayout.LEFT));
		getContentPane ().add (fromPanel);

		fromLabel = new JLabel ("From:");
		fromPanel.add (fromLabel);

		fromComboBox = new JComboBox<ConversionUnit>
				(conversionTypes.get (0).model ());
		fromComboBox.setActionCommand ("unitComboBoxChanged");
		fromComboBox.addActionListener (this);
		fromPanel.add (fromComboBox);

		fromTextField = new JTextField ();
		fromTextField.setActionCommand ("textFieldChanged");
		fromTextField.addActionListener (this);
		getContentPane ().add (fromTextField);

		toPanel = new JPanel (new FlowLayout (FlowLayout.LEFT));
		getContentPane ().add (toPanel);

		toLabel = new JLabel ("To:");
		toPanel.add (toLabel);

		toComboBox = new JComboBox<ConversionUnit>
				(conversionTypes.get (0).model ());
		toComboBox.setActionCommand ("unitComboBoxChanged");
		toComboBox.addActionListener (this);
		toPanel.add (toComboBox);

		toTextField = new JTextField ();
		toTextField.setEnabled (false);
		getContentPane ().add (toTextField);
	}

	private void update () {
		double from, to, fromRatio, toRatio;
		try {
			from = Double.parseDouble (fromTextField.getText ());

			fromRatio = ((ConversionUnit)(fromComboBox.getSelectedItem ())).ratio;
			toRatio = ((ConversionUnit)(toComboBox.getSelectedItem ())).ratio;

			to = from * fromRatio / toRatio;
			toTextField.setText (Double.toString (to));
		}
		catch (NumberFormatException e) {
			toTextField.setText ("Not a number");
		}
	}

	@Override
	public void actionPerformed (ActionEvent e) {
		String cmd = e.getActionCommand ();
		if (cmd.equals ("typeComboBoxChanged")) {
			ConversionType types = (ConversionType) typeComboBox.getSelectedItem ();

			fromComboBox.setModel (types.model ());
			toComboBox.setModel (types.model ());

			fromComboBox.updateUI ();
			toComboBox.updateUI ();
		}
		update ();
	}
}
