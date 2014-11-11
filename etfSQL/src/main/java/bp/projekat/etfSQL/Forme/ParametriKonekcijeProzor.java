package bp.projekat.etfSQL.Forme;


import javax.swing.JFrame;

public class ParametriKonekcijeProzor {

	private JFrame frame;

	/**
	 * Create the application.
	 */
	public ParametriKonekcijeProzor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		initialize();
	}

}
