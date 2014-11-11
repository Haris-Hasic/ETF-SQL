package bp.projekat.etfSQL.Forme;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Toolkit;
import java.awt.BorderLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;

import java.awt.Font;

import javax.swing.JTextField;

import java.awt.SystemColor;

import javax.swing.JMenuItem;

import bp.projekat.etfSQL.Baza.Konekcija;
import bp.projekat.etfSQL.Klase.ListTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTable;

import java.awt.ScrollPane;
import java.awt.Scrollbar;
import java.awt.Color;

public class GlavniProzor {

	private JFrame frmEtfSql;
	private final JTextPane queryTB = new JTextPane();
	private JTextField statusTB;
	private JTable rezultatTable;
	
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
		frmEtfSql.setBounds(100, 100, 693, 402);
		frmEtfSql.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frmEtfSql.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frmEtfSql.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		queryTB.setFont(new Font("Arial", Font.PLAIN, 14));
		queryTB.setBounds(14, 74, 660, 103);
		panel.add(queryTB);
		
		JButton executeButton = new JButton("Execute");

		executeButton.setFont(new Font("Arial", Font.PLAIN, 11));
		executeButton.setBounds(595, 188, 79, 31);
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
		
		mntmConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					kon = new Konekcija();
					kon.LoadDriver();
					kon.Connect();
					JOptionPane.showMessageDialog(null, "Uspješno ste konektovani !");
				}
				catch(Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}

			}
		});
		
		mntmConnect.setFont(new Font("Arial", Font.PLAIN, 12));
		mnSession.add(mntmConnect);
		
		JMenuItem mntmDisconnect = new JMenuItem("Disconnect");
		mntmDisconnect.setFont(new Font("Arial", Font.PLAIN, 12));
		mntmDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					kon.Disconnect();
					JOptionPane.showMessageDialog(null, "Uspješno ste diskonektovani !");
				}
				catch(Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		mnSession.add(mntmDisconnect);
		
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
		
		statusTB = new JTextField();
		statusTB.setForeground(Color.RED);
		statusTB.setFont(new Font("Arial", Font.PLAIN, 11));
		statusTB.setBackground(SystemColor.menu);
		statusTB.setBounds(14, 341, 660, 20);
		panel.add(statusTB);
		statusTB.setColumns(10);
		
		JButton btnRollback = new JButton("Rollback");
		btnRollback.setFont(new Font("Arial", Font.PLAIN, 11));
		btnRollback.setBounds(580, 32, 94, 31);
		panel.add(btnRollback);
		
		JButton btnCreateSavepoint = new JButton("Create Savepoint");
		btnCreateSavepoint.setFont(new Font("Arial", Font.PLAIN, 11));
		btnCreateSavepoint.setBounds(443, 32, 127, 31);
		panel.add(btnCreateSavepoint);
		
		JButton button_2 = new JButton("Commit");
		button_2.setFont(new Font("Arial", Font.PLAIN, 11));
		button_2.setBounds(346, 32, 87, 31);
		panel.add(button_2);
		
		rezultatTable = new JTable();
		rezultatTable.setBounds(-15, 32, 660, 95);
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setBounds(14, 235, 660, 95);
		panel.add(scrollPane);
		
		scrollPane.add(rezultatTable);
		
		executeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
															
					String s = queryTB.getText();
					ResultSet rs = kon.createResultSet(s);
					int br = brojRedova(rs);
					prikaziUTabeli(rs);
					statusTB.setText(br + " rows fetched.");
					
				}
				catch(Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
	}
	
	public void prikaziUTabeli(ResultSet rs) throws SQLException {
		
		ListTableModel modelTabele = ListTableModel.createModelFromResultSet(rs);
		rezultatTable.setModel(modelTabele);
	}
	
	public int brojRedova(ResultSet rs) throws SQLException {
		
		return rs.getFetchSize();
	}
}
