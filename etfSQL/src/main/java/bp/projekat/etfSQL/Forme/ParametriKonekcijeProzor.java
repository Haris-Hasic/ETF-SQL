package bp.projekat.etfSQL.Forme;


import javax.swing.JFrame;

import java.awt.Toolkit;

import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

import java.awt.Font;

import javax.swing.UIManager;
import javax.swing.JButton;

import bp.projekat.etfSQL.Baza.Konekcija;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;

public class ParametriKonekcijeProzor {

	private JFrame frmConnect;
	private JTextField textField_TAG;
	private JTextField textField_User;
	private JTextField textField_Pass;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTable table;
	private JTextField textField_6;
	private Konekcija kon;

	/**
	 * Create the application.
	 */
	public ParametriKonekcijeProzor() {
		kon = new Konekcija();
		initialize();
	}
	
	public Konekcija dajKonekciju() {
		return kon;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmConnect = new JFrame();
		frmConnect.setBackground(SystemColor.window);
		frmConnect.setResizable(false);
		frmConnect.setIconImage(Toolkit.getDefaultToolkit().getImage(ParametriKonekcijeProzor.class.getResource("/bp/projekat/etfSQL/Resursi/ETF-Logo.gif")));
		frmConnect.setTitle("Connect");
		frmConnect.setBounds(100, 100, 741, 406);
		frmConnect.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.window);
		frmConnect.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblTag = new JLabel("TAG :");
		lblTag.setFont(new Font("Arial", Font.PLAIN, 11));
		lblTag.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTag.setBounds(451, 24, 46, 14);
		panel.add(lblTag);
		
		JLabel lblUser = new JLabel("User :");
		lblUser.setFont(new Font("Arial", Font.PLAIN, 11));
		lblUser.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUser.setBounds(451, 55, 46, 14);
		panel.add(lblUser);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Arial", Font.PLAIN, 11));
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(411, 86, 86, 14);
		panel.add(lblPassword);
		
		JLabel lblTns = new JLabel("TNS :");
		lblTns.setFont(new Font("Arial", Font.PLAIN, 11));
		lblTns.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTns.setBounds(451, 184, 46, 14);
		panel.add(lblTns);
		
		JLabel lblHost = new JLabel("Host :");
		lblHost.setFont(new Font("Arial", Font.PLAIN, 11));
		lblHost.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHost.setBounds(451, 215, 46, 14);
		panel.add(lblHost);
		
		JLabel lblTcpPort = new JLabel("TCP Port :");
		lblTcpPort.setFont(new Font("Arial", Font.PLAIN, 11));
		lblTcpPort.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTcpPort.setBounds(395, 246, 102, 14);
		panel.add(lblTcpPort);
		
		JLabel lblSid = new JLabel("SID :");
		lblSid.setFont(new Font("Arial", Font.PLAIN, 11));
		lblSid.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSid.setBounds(451, 279, 46, 14);
		panel.add(lblSid);
		
		textField_TAG = new JTextField();
		textField_TAG.setBounds(507, 21, 197, 20);
		panel.add(textField_TAG);
		textField_TAG.setColumns(10);
		
		textField_User = new JTextField();
		textField_User.setColumns(10);
		textField_User.setBounds(507, 52, 197, 20);
		panel.add(textField_User);
		
		textField_Pass = new JTextField();
		textField_Pass.setColumns(10);
		textField_Pass.setBounds(507, 83, 197, 20);
		panel.add(textField_Pass);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(507, 212, 197, 20);
		panel.add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(507, 243, 197, 20);
		panel.add(textField_4);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.window);
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Tip baze podataka", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		panel_1.setBounds(451, 117, 253, 56);
		panel.add(panel_1);
		((javax.swing.border.TitledBorder) panel_1.getBorder()).setTitleFont(new Font("Arial", Font.PLAIN, 11));
		
		JRadioButton rdbtnOracle = new JRadioButton("Oracle");
		rdbtnOracle.setBackground(SystemColor.window);
		rdbtnOracle.setFont(new Font("Arial", Font.PLAIN, 11));
		panel_1.add(rdbtnOracle);
		
		JRadioButton rdbtnMysql = new JRadioButton("MySQL");
		rdbtnMysql.setBackground(SystemColor.window);
		rdbtnMysql.setFont(new Font("Arial", Font.PLAIN, 11));
		panel_1.add(rdbtnMysql);
		rdbtnMysql.setSelected(true);
		
		JRadioButton rdbtnPostgresql = new JRadioButton("PostgreSQL");
		rdbtnPostgresql.setBackground(SystemColor.window);
		rdbtnPostgresql.setFont(new Font("Arial", Font.PLAIN, 11));
		panel_1.add(rdbtnPostgresql);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(507, 181, 197, 20);
		panel.add(textField_5);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 427, 378);
		panel.add(scrollPane);
		
		DefaultTableModel model = new DefaultTableModel();
		table = new JTable(model);
		table.setBackground(SystemColor.window);
		scrollPane.setViewportView(table);
		model.addColumn("Tag");
		model.addColumn("User");
		model.addColumn("Alias");
		model.addColumn("Count");
		model.addColumn("Last Usage");
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(507, 276, 197, 20);
		panel.add(textField_6);
		
		JButton btnTest = new JButton("Test");
		btnTest.setFont(new Font("Arial", Font.PLAIN, 11));
		btnTest.setBounds(609, 307, 95, 28);
		panel.add(btnTest);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new Object[]{textField_TAG.getText(), textField_User.getText(), textField_TAG.getText(), 1, 1});
				
				kon.LoadDriver();
				kon.Connect();
				JOptionPane.showMessageDialog(null, "Uspješno ste konektovani !");
				
				
			}
		});
		btnConnect.setFont(new Font("Arial", Font.PLAIN, 11));
		btnConnect.setBounds(609, 338, 95, 28);
		panel.add(btnConnect);
		
		JButton btnSave = new JButton("Save");
		btnSave.setFont(new Font("Arial", Font.PLAIN, 11));
		btnSave.setBounds(507, 307, 95, 28);
		panel.add(btnSave);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Arial", Font.PLAIN, 11));
		btnDelete.setBounds(507, 338, 95, 28);
		panel.add(btnDelete);
		frmConnect.setVisible(true);
		
		//Group the radio buttons.
	    ButtonGroup group = new ButtonGroup();
	    group.add(rdbtnOracle);
	    group.add(rdbtnMysql);
	    group.add(rdbtnPostgresql);
		
	}

	//public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		//initialize();
	//}
}
