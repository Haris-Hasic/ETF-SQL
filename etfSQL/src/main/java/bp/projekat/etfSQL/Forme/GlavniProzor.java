package bp.projekat.etfSQL.Forme;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Toolkit;
import java.awt.BorderLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;

import java.awt.Font;

import javax.swing.JTextField;

import java.awt.SystemColor;

import javax.swing.JMenuItem;

import bp.projekat.etfSQL.Baza.Konekcija;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

public class GlavniProzor {

	private JFrame frmEtfSql;
	private final JTextPane textPane = new JTextPane();
	private JTextField textField;
	private JTable table;
	
	private Konekcija kon;

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
		kon = null;
		
		frmEtfSql = new JFrame();
		frmEtfSql.setResizable(false);
		frmEtfSql.setTitle("ETF SQL");
		frmEtfSql.setIconImage(Toolkit.getDefaultToolkit().getImage(GlavniProzor.class.getResource("/bp/projekat/etfSQL/Resursi/ETF-Logo.gif")));
		frmEtfSql.setBounds(100, 100, 703, 500);
		frmEtfSql.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frmEtfSql.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frmEtfSql.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		textPane.setBounds(14, 98, 660, 219);
		panel.add(textPane);
		
		JButton executeButton = new JButton("Execute");
		executeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				
			}
		});
		executeButton.setFont(new Font("Arial", Font.PLAIN, 11));
		executeButton.setBounds(595, 328, 79, 23);
		panel.add(executeButton);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Arial", Font.PLAIN, 12));
		menuBar.setBounds(0, 0, 697, 21);
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
				try {
					kon.connect();
					JOptionPane.showMessageDialog(null, "Konektovan");
				}
				catch(Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}

			}
		});
		
		mntmConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					kon.connect();
					JOptionPane.showMessageDialog(null, "Konektovan");
				}
				catch(Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}

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
		textField.setBounds(14, 328, 571, 20);
		panel.add(textField);
		textField.setColumns(10);
		textField.setVisible(false);
		
		JButton btnRollback = new JButton("Rollback");
		btnRollback.setFont(new Font("Arial", Font.PLAIN, 11));
		btnRollback.setBounds(580, 32, 94, 47);
		panel.add(btnRollback);
		
		JButton btnCreateSavepoint = new JButton("Create Savepoint");
		btnCreateSavepoint.setFont(new Font("Arial", Font.PLAIN, 11));
		btnCreateSavepoint.setBounds(443, 32, 127, 47);
		panel.add(btnCreateSavepoint);
		
		JButton button_2 = new JButton("Commit");
		button_2.setFont(new Font("Arial", Font.PLAIN, 11));
		button_2.setBounds(346, 32, 87, 47);
		panel.add(button_2);
		
		table = new JTable();
		table.setBounds(14, 365, 660, 95);
		panel.add(table);
	}
}
