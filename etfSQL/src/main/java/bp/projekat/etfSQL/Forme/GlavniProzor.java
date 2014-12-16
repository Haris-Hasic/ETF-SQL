package bp.projekat.etfSQL.Forme;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Toolkit;
import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
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
import bp.projekat.etfSQL.Klase.Command;
import bp.projekat.etfSQL.Klase.CommandLogger;
import bp.projekat.etfSQL.Klase.ListTableModel;
import bp.projekat.etfSQL.Klase.LoggerTableModel;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JTable;

import java.awt.Image;
import java.awt.ScrollPane;
import java.awt.Scrollbar;
import java.awt.Color;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JSeparator;
import javax.swing.ImageIcon;
import javax.swing.border.CompoundBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.EmptyBorder;

import java.io.*;
import java.nio.charset.*;

import javax.swing.JCheckBox;
import javax.swing.JLabel;


public class GlavniProzor {

	private JFrame frmEtfSql;
	private final static JTextPane queryTB = new JTextPane();
	private JTextField statusTB;
	private Konekcija kon;
	private JTable historyTable;
	static CommandLogger commandLogger;
	private JTable rezultatTable;
	private JTextField textFieldAktivniUser;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GlavniProzor window = new GlavniProzor();
					window.frmEtfSql.setVisible(true);
					queryTB.requestFocus();
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
	
	public void setKonekcija(Konekcija k)
	{
		kon = k;
	}
	
	public void postaviKorisnika(String k, String b)
	{
		textFieldAktivniUser.setText("  " + k + "@" + b);
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		kon = null;
		commandLogger = new CommandLogger();
		try {
			commandLogger.ucitajIzDatoteke("./test.txt");
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
		frmEtfSql = new JFrame();
		frmEtfSql.setResizable(false);
		frmEtfSql.setTitle("ETF SQL");
		frmEtfSql.setIconImage(Toolkit.getDefaultToolkit().getImage(GlavniProzor.class.getResource("/bp/projekat/etfSQL/Resursi/ETF-Logo.gif")));
		frmEtfSql.setBounds(100, 100, 800, 553);
		frmEtfSql.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frmEtfSql.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.menu);
		frmEtfSql.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JButton btnCommit2 = new JButton("");
		btnCommit2.setToolTipText("Commit changes.");
		btnCommit2.setBackground(SystemColor.menu);
		btnCommit2.setBounds(14, 70, 30, 30);
		panel.add(btnCommit2);
		
		JButton btnRollback2 = new JButton("");
		btnRollback2.setToolTipText("Rollback uncommited changes.");
		btnRollback2.setBackground(SystemColor.menu);
		btnRollback2.setBounds(46, 70, 30, 30);
		panel.add(btnRollback2);
		
		final JCheckBox chckbxLogirajUBazu = new JCheckBox("Logiraj u Bazu");
		chckbxLogirajUBazu.setBounds(685, 36, 95, 23);
		chckbxLogirajUBazu.setFont(new Font("Arial", Font.PLAIN, 11));
		panel.add(chckbxLogirajUBazu);
		
		JButton btnExecute2 = new JButton("");
		btnExecute2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					String s = queryTB.getText();
					long tStart = System.currentTimeMillis();
					ResultSet rs = kon.createResultSet(s);
					prikaziUTabeli(rs);
					long tEnd = System.currentTimeMillis();
					long tms = tEnd - tStart;
					statusTB.setText(" >> " + rezultatTable.getRowCount() + " rows fetched. (Elapsed time: " + tms + " ms)");
					
					DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					Date date = new Date();
					System.out.println(dateFormat.format(date));
					Command c = new Command(Konekcija.getKorisnik(), s, date);
					commandLogger.dodajKomandu(c);
					
					if(!chckbxLogirajUBazu.isSelected())
						commandLogger.spasiUDatoteku("./test.txt");
					
					else {
						kon.createLogTable();
						kon.logiraj(Konekcija.getKorisnik(), s, date);
					}
						
					popuniLoggerTabelu();					
				}
				catch(Exception e) {
					System.out.println(e);
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
		btnExecute2.setToolTipText("Execute query.");
		btnExecute2.setBackground(SystemColor.menu);
		btnExecute2.setBounds(78, 70, 30, 30);
		panel.add(btnExecute2);
		
		ImageIcon slika = new ImageIcon(getClass().getResource("/bp/projekat/etfSQL/Resursi/execute_icon.png"));
		Image sl = slika.getImage();
		Image temp = sl.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		
		Border emptyBorder = BorderFactory.createEmptyBorder();
		
		btnExecute2.setIcon(new ImageIcon(temp));
		btnExecute2.setBorder(emptyBorder);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Arial", Font.PLAIN, 12));
		menuBar.setBounds(0, 0, 791, 21);
		panel.add(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setFont(new Font("Arial", Font.PLAIN, 12));
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mnFile.add(mntmNew);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);
		
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
		
		slika = new ImageIcon(getClass().getResource("/bp/projekat/etfSQL/Resursi/connect_icon.png"));
		sl = slika.getImage();
		temp = sl.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		
		JMenuItem mntmConnect = new JMenuItem("Connect...");
		mntmConnect.setIcon(new ImageIcon(temp));
		
		mntmConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				uspostaviKonekciju();
				
			}
		});
		
		mntmConnect.setFont(new Font("Arial", Font.PLAIN, 12));
		mnSession.add(mntmConnect);
		
		slika = new ImageIcon(getClass().getResource("/bp/projekat/etfSQL/Resursi/disconnect_icon.png"));
		sl = slika.getImage();
		temp = sl.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		
		JMenuItem mntmDisconnect = new JMenuItem("Disconnect");
		mntmDisconnect.setFont(new Font("Arial", Font.PLAIN, 12));
		mntmDisconnect.setIcon(new ImageIcon(temp));
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
		
		slika = new ImageIcon(getClass().getResource("/bp/projekat/etfSQL/Resursi/rollback_icon.png"));
		sl = slika.getImage();
		temp = sl.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		
		btnRollback2.setIcon(new ImageIcon(temp));
		btnRollback2.setBorder(emptyBorder);
		
		slika = new ImageIcon(getClass().getResource("/bp/projekat/etfSQL/Resursi/commit_icon.png"));
		sl = slika.getImage();
		temp = sl.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		
		btnCommit2.setIcon(new ImageIcon(temp));
		btnCommit2.setBorder(emptyBorder);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setBounds(14, 103, 529, 170);
		panel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		panel_1.add(queryTB, BorderLayout.CENTER);
		queryTB.setFont(new Font("Arial", Font.PLAIN, 14));
		
		JSeparator separator = new JSeparator();
		separator.setBounds(14, 284, 766, 2);
		panel.add(separator);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EmptyBorder(0, 0, 0, 0));
		panel_2.setBounds(14, 485, 766, 33);
		panel.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		statusTB = new JTextField();
		panel_2.add(statusTB, BorderLayout.CENTER);
		statusTB.setForeground(Color.BLUE);
		statusTB.setFont(new Font("Arial", Font.PLAIN, 11));
		statusTB.setBackground(SystemColor.menu);
		statusTB.setColumns(10);
		
		slika = new ImageIcon(getClass().getResource("/bp/projekat/etfSQL/Resursi/connect_icon.png"));
		sl = slika.getImage();
		temp = sl.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		
		JButton btnConnect = new JButton("");
		btnConnect.setBorder(emptyBorder);
		btnConnect.setBackground(SystemColor.menu);
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				uspostaviKonekciju();
			}
		});
		btnConnect.setToolTipText("Connect to a database.");
		btnConnect.setIcon(new ImageIcon(temp));
		btnConnect.setBounds(14, 32, 30, 30);
		panel.add(btnConnect);
		
		slika = new ImageIcon(getClass().getResource("/bp/projekat/etfSQL/Resursi/disconnect_icon.png"));
		sl = slika.getImage();
		temp = sl.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		
		JButton btnDisconnect = new JButton("");
		btnDisconnect.setBorder(emptyBorder);
		btnDisconnect.setBackground(SystemColor.menu);
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					
					kon.Disconnect();
					textFieldAktivniUser.setText("  Not Connected");
					JOptionPane.showMessageDialog(null, "Uspješno ste diskonektovani !");
				}
				catch(Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		btnDisconnect.setToolTipText("Disconnect.");
		btnDisconnect.setIcon(new ImageIcon(temp));
		btnDisconnect.setBounds(46, 32, 30, 30);
		panel.add(btnDisconnect);
		
		historyTable = new JTable();
		historyTable.setBounds(190, 304, 66, 47);
		historyTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		JScrollPane scrollPane_Log = new JScrollPane();
		scrollPane_Log.setSize(227, 152);
		scrollPane_Log.setLocation(553, 121);
		panel.add(scrollPane_Log);
		
		scrollPane_Log.setViewportView(historyTable);
		
		rezultatTable = new JTable();
		JScrollPane scrollPane_1 = new JScrollPane(rezultatTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane_1.setBounds(14, 297, 766, 177);
		panel.add(scrollPane_1);
		rezultatTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane_1.setViewportView(rezultatTable);
		
		JLabel lblRecentlyExecutedQuerys = new JLabel("Recently executed querys :");
		lblRecentlyExecutedQuerys.setFont(new Font("Arial", Font.PLAIN, 11));
		lblRecentlyExecutedQuerys.setBounds(553, 103, 227, 14);
		panel.add(lblRecentlyExecutedQuerys);
		
		slika = new ImageIcon(getClass().getResource("/bp/projekat/etfSQL/Resursi/script_icon.png"));
		sl = slika.getImage();
		temp = sl.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		
		JButton buttonExecuteAsScript = new JButton("");
		buttonExecuteAsScript.setToolTipText("Execute query text as script.");
		buttonExecuteAsScript.setBackground(SystemColor.menu);
		buttonExecuteAsScript.setBounds(111, 70, 30, 30);
		buttonExecuteAsScript.setIcon(new ImageIcon(temp));
		buttonExecuteAsScript.setBorder(emptyBorder);
		panel.add(buttonExecuteAsScript);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(14, 66, 766, 2);
		panel.add(separator_2);
		
		textFieldAktivniUser = new JTextField();
		textFieldAktivniUser.setEditable(false);
		textFieldAktivniUser.setText("  Not Connected");
		textFieldAktivniUser.setFont(new Font("Arial", Font.PLAIN, 12));
		textFieldAktivniUser.setBounds(86, 34, 176, 27);
		panel.add(textFieldAktivniUser);
		textFieldAktivniUser.setColumns(10);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(14, 27, 766, 2);
		panel.add(separator_1);
		
		historyTable.addMouseListener(new MouseAdapter() {
			  public void mouseClicked(MouseEvent e) {
			     if (e.getClickCount() == 2) { // check if a double click
			    	 
			    	 int row = historyTable.rowAtPoint(e.getPoint());
			    	 queryTB.setText((String) historyTable.getValueAt(row, 2));
			     }
			   }
			});
		popuniLoggerTabelu();
	}
	
	public void uspostaviKonekciju() {
		
		try {

			ParametriKonekcijeProzor pk = new ParametriKonekcijeProzor(this);
		}
		catch(Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}
	
	public void prikaziUTabeli(ResultSet rs) throws SQLException {
		
		ListTableModel modelTabele = ListTableModel.createModelFromResultSet(rs);
		rezultatTable.setModel(modelTabele);
	}
	
	public void popuniLoggerTabelu() {
		LoggerTableModel model = new LoggerTableModel(commandLogger.dajListuKomandi());
		historyTable.setModel(model);
	}
	
	public int brojRedova(ResultSet rs) throws SQLException {
		
		return rs.getFetchSize();
	}
}