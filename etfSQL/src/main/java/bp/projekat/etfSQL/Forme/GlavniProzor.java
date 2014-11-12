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

import java.awt.Image;
import java.awt.ScrollPane;
import java.awt.Scrollbar;
import java.awt.Color;

import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JSeparator;
import javax.swing.ImageIcon;

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
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		frmEtfSql.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		ImageIcon slika = new ImageIcon(getClass().getResource("/bp/projekat/etfSQL/Resursi/execute_icon.png"));
		Image sl = slika.getImage();
		Image temp = sl.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		
		JButton btnExecute = new JButton("Execute");
		btnExecute.setIcon(new ImageIcon(temp));
		btnExecute.setFont(new Font("Arial", Font.PLAIN, 11));
		btnExecute.setBounds(557, 304, 117, 31);
		panel.add(btnExecute);
		
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
					
					ParametriKonekcijeProzor pk = new ParametriKonekcijeProzor();
					kon = pk.dajKonekciju();
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
		statusTB.setForeground(Color.BLUE);
		statusTB.setFont(new Font("Arial", Font.PLAIN, 11));
		statusTB.setBackground(SystemColor.menu);
		statusTB.setBounds(14, 341, 660, 20);
		panel.add(statusTB);
		statusTB.setColumns(10);
		
		slika = new ImageIcon(getClass().getResource("/bp/projekat/etfSQL/Resursi/rollback_icon.png"));
		sl = slika.getImage();
		temp = sl.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		
		JButton btnRollback = new JButton("Rollback");
		btnRollback.setIcon(new ImageIcon(temp));
		btnRollback.setFont(new Font("Arial", Font.PLAIN, 11));
		btnRollback.setBounds(430, 304, 117, 31);
		panel.add(btnRollback);
		
		slika = new ImageIcon(getClass().getResource("/bp/projekat/etfSQL/Resursi/commit_icon.png"));
		sl = slika.getImage();
		temp = sl.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		
		JButton btnCommit = new JButton("Commit");
		btnCommit.setIcon(new ImageIcon(temp));
		btnCommit.setFont(new Font("Arial", Font.PLAIN, 11));
		btnCommit.setBounds(303, 304, 117, 31);
		panel.add(btnCommit);
		
		rezultatTable = new JTable();
		rezultatTable.setBounds(-15, 32, 660, 95);
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setBounds(14, 149, 660, 149);
		panel.add(scrollPane);
		
		scrollPane.add(rezultatTable);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setBounds(14, 32, 660, 98);
		panel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		panel_1.add(queryTB, BorderLayout.CENTER);
		queryTB.setFont(new Font("Arial", Font.PLAIN, 14));
		
		JSeparator separator = new JSeparator();
		separator.setBounds(14, 139, 660, 2);
		panel.add(separator);
		
		btnExecute.addActionListener(new ActionListener() {
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
