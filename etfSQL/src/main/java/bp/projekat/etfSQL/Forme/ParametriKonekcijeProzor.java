package bp.projekat.etfSQL.Forme;


import javax.swing.JFrame;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
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
import java.awt.Color;

public class ParametriKonekcijeProzor {

	private JFrame frmConnect;
	private JTextField textField_User;
	private JTextField textField_Pass;
	private JTable table;
	private Konekcija kon;
	private JTextField txtEtflab;
	private JTextField textField_Host;
	private JTextField textField_dbName;
	private JTextField textField_Port;

	/**
	 * Create the application.
	 */
	public ParametriKonekcijeProzor(final GlavniProzor k) {
		initialize(k);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(final GlavniProzor k) {
		
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
		
		JLabel lblUser = new JLabel("User :");
		lblUser.setFont(new Font("Arial", Font.PLAIN, 11));
		lblUser.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUser.setBounds(435, 243, 46, 14);
		panel.add(lblUser);
		
		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setFont(new Font("Arial", Font.PLAIN, 11));
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(394, 271, 86, 14);
		panel.add(lblPassword);
		
		textField_User = new JTextField();
		textField_User.setColumns(10);
		textField_User.setBounds(490, 240, 235, 20);
		panel.add(textField_User);
		
		textField_Pass = new JTextField();
		textField_Pass.setColumns(10);
		textField_Pass.setBounds(490, 268, 235, 20);
		panel.add(textField_Pass);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.window);
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Tip baze podataka", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		panel_1.setBounds(421, 11, 304, 109);
		panel.add(panel_1);
		((javax.swing.border.TitledBorder) panel_1.getBorder()).setTitleFont(new Font("Arial", Font.PLAIN, 11));
		panel_1.setLayout(null);
		
		final JRadioButton rdbtnOracle = new JRadioButton("");
		rdbtnOracle.setBounds(40, 21, 21, 23);
		rdbtnOracle.setBackground(SystemColor.window);
		rdbtnOracle.setFont(new Font("Arial", Font.PLAIN, 11));
		panel_1.add(rdbtnOracle);
		
		final JRadioButton rdbtnMysql = new JRadioButton("");
		rdbtnMysql.setBounds(113, 21, 21, 23);
		rdbtnMysql.setBackground(SystemColor.window);
		rdbtnMysql.setFont(new Font("Arial", Font.PLAIN, 11));
		panel_1.add(rdbtnMysql);
		rdbtnMysql.setSelected(true);
		
		final JRadioButton rdbtnPostgresql = new JRadioButton("");
		rdbtnPostgresql.setBounds(183, 21, 21, 23);
		rdbtnPostgresql.setBackground(SystemColor.window);
		rdbtnPostgresql.setFont(new Font("Arial", Font.PLAIN, 11));
		panel_1.add(rdbtnPostgresql);
		
		final JRadioButton rdbtnMssql = new JRadioButton("");
	    rdbtnMssql.setBackground(Color.WHITE);
	    rdbtnMssql.setBounds(252, 21, 21, 23);
	    panel_1.add(rdbtnMssql);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 401, 356);
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
		
		JButton btnTest = new JButton("Test");
		btnTest.setFont(new Font("Arial", Font.PLAIN, 11));
		btnTest.setBounds(630, 303, 95, 28);
		panel.add(btnTest);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					String tip;
					
					if(rdbtnOracle.isSelected()) 
					{
						tip = "oracle";
					}
					
					else if(rdbtnPostgresql.isSelected()) 
					{
						tip = "postgresql";
					}
					
					else if(rdbtnMssql.isSelected()) 
					{
						tip = "sqlserver";
					}
					
					else
					{
						tip = "mysql";
					}
					
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					model.addRow(new Object[]{txtEtflab.getText(), textField_User.getText(), "-", "-", "-"});
					
					kon = new Konekcija(tip, textField_Host.getText(), textField_Port.getText(), textField_dbName.getText(), textField_User.getText(), textField_Pass.getText());						
					JOptionPane.showMessageDialog(null, "Uspješno ste konektovani !");
					kon.LoadDriver();
					kon.Connect();
					
					k.setKonekcija(kon);
				}
				catch(Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
		btnConnect.setFont(new Font("Arial", Font.PLAIN, 11));
		btnConnect.setBounds(630, 339, 95, 28);
		panel.add(btnConnect);
		
		JButton btnSave = new JButton("Save");
		btnSave.setFont(new Font("Arial", Font.PLAIN, 11));
		btnSave.setBounds(526, 339, 95, 28);
		panel.add(btnSave);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Arial", Font.PLAIN, 11));
		btnDelete.setBounds(421, 339, 95, 28);
		panel.add(btnDelete);
		frmConnect.setVisible(true);
		
		//Group the radio buttons.
	    ButtonGroup group = new ButtonGroup();
	    group.add(rdbtnOracle);
	    group.add(rdbtnMysql);
	    group.add(rdbtnPostgresql);
	    group.add(rdbtnMssql);
	    
	    ImageIcon slika = new ImageIcon(getClass().getResource("/bp/projekat/etfSQL/Resursi/oracle-logo.jpg"));
		Image sl = slika.getImage();
		Image temp = sl.getScaledInstance(70, 50, java.awt.Image.SCALE_SMOOTH);
		
	    JLabel lbl_slika1 = new JLabel("");
	    lbl_slika1.setBounds(17, 52, 70, 34);
	    lbl_slika1.setIcon(new ImageIcon(temp));
	    panel_1.add(lbl_slika1);
	    
	    slika = new ImageIcon(getClass().getResource("/bp/projekat/etfSQL/Resursi/mysql_logo.png"));
		sl = slika.getImage();
		temp = sl.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		
	    JLabel lbl_slika2 = new JLabel("");
	    lbl_slika2.setBounds(105, 50, 40, 40);
	    lbl_slika2.setIcon(new ImageIcon(temp));
	    panel_1.add(lbl_slika2);
	    
	    slika = new ImageIcon(getClass().getResource("/bp/projekat/etfSQL/Resursi/postgresql_logo.png"));
		sl = slika.getImage();
		temp = sl.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
	    
	    JLabel lbl_slika3 = new JLabel("");
	    lbl_slika3.setBounds(168, 44, 50, 50);
	    lbl_slika3.setIcon(new ImageIcon(temp));
	    panel_1.add(lbl_slika3);
	    
	    slika = new ImageIcon(getClass().getResource("/bp/projekat/etfSQL/Resursi/mssql_logo.png"));
		sl = slika.getImage();
		temp = sl.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
	    
	    JLabel lbl_slika4 = new JLabel("");
	    lbl_slika4.setBounds(243, 51, 40, 40);
	    lbl_slika4.setIcon(new ImageIcon(temp));
	    panel_1.add(lbl_slika4);
	    
	    txtEtflab = new JTextField();
	    txtEtflab.setText("ETFLAB");
	    txtEtflab.setColumns(10);
	    txtEtflab.setBounds(490, 129, 235, 20);
	    panel.add(txtEtflab);
	    
	    JLabel labelTAG = new JLabel("TAG :");
	    labelTAG.setHorizontalAlignment(SwingConstants.RIGHT);
	    labelTAG.setFont(new Font("Arial", Font.PLAIN, 11));
	    labelTAG.setBounds(435, 131, 46, 14);
	    panel.add(labelTAG);
	    
	    textField_Host = new JTextField();
	    textField_Host.setColumns(10);
	    textField_Host.setBounds(490, 156, 235, 20);
	    panel.add(textField_Host);
	    
	    JLabel labelHost = new JLabel("Host :");
	    labelHost.setHorizontalAlignment(SwingConstants.RIGHT);
	    labelHost.setFont(new Font("Arial", Font.PLAIN, 11));
	    labelHost.setBounds(435, 159, 46, 14);
	    panel.add(labelHost);
	    
	    textField_dbName = new JTextField();
	    textField_dbName.setColumns(10);
	    textField_dbName.setBounds(490, 184, 235, 20);
	    panel.add(textField_dbName);
	    
	    JLabel lblDbName = new JLabel("DB name :");
	    lblDbName.setHorizontalAlignment(SwingConstants.RIGHT);
	    lblDbName.setFont(new Font("Arial", Font.PLAIN, 11));
	    lblDbName.setBounds(411, 187, 70, 14);
	    panel.add(lblDbName);
	    
	    JLabel lblPort = new JLabel("Port :");
	    lblPort.setHorizontalAlignment(SwingConstants.RIGHT);
	    lblPort.setFont(new Font("Arial", Font.PLAIN, 11));
	    lblPort.setBounds(435, 215, 46, 14);
	    panel.add(lblPort);
	    
	    textField_Port = new JTextField();
	    textField_Port.setColumns(10);
	    textField_Port.setBounds(490, 212, 235, 20);
	    panel.add(textField_Port);
		
	}
}
