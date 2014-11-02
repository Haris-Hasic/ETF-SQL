package Prozori;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Toolkit;
import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;

import java.awt.Font;

import javax.swing.JTextField;

import java.awt.SystemColor;

import javax.swing.JMenuItem;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GlavniProzor {

	private JFrame frmEtfSql;
	private final JTextPane textPane = new JTextPane();
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GlavniProzor window = new GlavniProzor();
					window.frmEtfSql.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GlavniProzor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEtfSql = new JFrame();
		frmEtfSql.setResizable(false);
		frmEtfSql.setTitle("ETF SQL");
		frmEtfSql.setIconImage(Toolkit.getDefaultToolkit().getImage(GlavniProzor.class.getResource("/Resursi/ETF_Logo.jpg")));
		frmEtfSql.setBounds(100, 100, 703, 500);
		frmEtfSql.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frmEtfSql.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frmEtfSql.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		textPane.setBounds(14, 161, 660, 255);
		panel.add(textPane);
		
		JButton btnNewButton = new JButton("Execute");
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 11));
		btnNewButton.setBounds(595, 428, 79, 23);
		panel.add(btnNewButton);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Arial", Font.PLAIN, 12));
		menuBar.setBounds(0, 0, 684, 21);
		panel.add(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setFont(new Font("Arial", Font.PLAIN, 12));
		menuBar.add(mnFile);
		
		JMenu mnEdit = new JMenu("Edit");
		mnEdit.setFont(new Font("Arial", Font.PLAIN, 12));
		menuBar.add(mnEdit);
		
		JMenu mnView = new JMenu("View");
		mnView.setFont(new Font("Arial", Font.PLAIN, 12));
		menuBar.add(mnView);
		
		JMenu mnSearch = new JMenu("Search");
		mnSearch.setFont(new Font("Arial", Font.PLAIN, 12));
		menuBar.add(mnSearch);
		
		JMenu mnText = new JMenu("Text");
		mnText.setFont(new Font("Arial", Font.PLAIN, 12));
		menuBar.add(mnText);
		
		JMenu mnSession = new JMenu("Session");
		mnSession.setFont(new Font("Arial", Font.PLAIN, 12));
		menuBar.add(mnSession);
		
		JMenuItem mntmConnect = new JMenuItem("Connect...");
		mntmConnect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

			}
		});
		mntmConnect.setFont(new Font("Arial", Font.PLAIN, 12));
		mnSession.add(mntmConnect);
		
		JMenu mnScript = new JMenu("Script");
		mnScript.setFont(new Font("Arial", Font.PLAIN, 12));
		menuBar.add(mnScript);
		
		JMenu mnTools = new JMenu("Tools");
		mnTools.setFont(new Font("Arial", Font.PLAIN, 12));
		menuBar.add(mnTools);
		
		JMenu mnWindow = new JMenu("Window");
		mnWindow.setFont(new Font("Arial", Font.PLAIN, 12));
		menuBar.add(mnWindow);
		
		JMenu mnHelp = new JMenu("Help");
		mnHelp.setFont(new Font("Arial", Font.PLAIN, 12));
		menuBar.add(mnHelp);
		
		textField = new JTextField();
		textField.setBackground(SystemColor.menu);
		textField.setBounds(14, 429, 571, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnRollback = new JButton("Rollback");
		btnRollback.setFont(new Font("Arial", Font.PLAIN, 11));
		btnRollback.setBounds(559, 86, 115, 63);
		panel.add(btnRollback);
		
		JButton btnCreateSavepoint = new JButton("Create Savepoint");
		btnCreateSavepoint.setFont(new Font("Arial", Font.PLAIN, 11));
		btnCreateSavepoint.setBounds(434, 86, 115, 63);
		panel.add(btnCreateSavepoint);
		
		JButton button_2 = new JButton("Commit");
		button_2.setFont(new Font("Arial", Font.PLAIN, 11));
		button_2.setBounds(309, 86, 115, 63);
		panel.add(button_2);
	}
}
