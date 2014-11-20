package bp.projekat.etfSQL.Forme;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Toolkit;
import java.awt.BorderLayout;

import javax.swing.BorderFactory;
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

public class GlavniProzor {

	private JFrame frmEtfSql;
	private final static JTextPane queryTB = new JTextPane();
	private JTextField statusTB;
	private JTable rezultatTable;
	
	private Konekcija kon;
	private JTable historyTable;
	static List<CommandLogger> listaLogger;
	private static Charset UTF8 = Charset.forName("UTF-8");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					listaLogger = new ArrayList<CommandLogger>();
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
		
		JButton btnRollback = new JButton("Rollback");
		btnRollback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Nije još implementirano !");
			}
		});
		btnRollback.setIcon(new ImageIcon(temp));
		btnRollback.setFont(new Font("Arial", Font.PLAIN, 11));
		btnRollback.setBounds(430, 304, 117, 31);
		panel.add(btnRollback);
		
		slika = new ImageIcon(getClass().getResource("/bp/projekat/etfSQL/Resursi/commit_icon.png"));
		sl = slika.getImage();
		temp = sl.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		
		JButton btnCommit = new JButton("Commit");
		btnCommit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Nije još implementirano !");
			}
		});
		btnCommit.setIcon(new ImageIcon(temp));
		btnCommit.setFont(new Font("Arial", Font.PLAIN, 11));
		btnCommit.setBounds(303, 304, 117, 31);
		panel.add(btnCommit);
		
		rezultatTable = new JTable();
		rezultatTable.setBounds(-15, 32, 660, 95);
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setBounds(14, 178, 405, 120);
		panel.add(scrollPane);
		
		scrollPane.add(rezultatTable);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setBounds(14, 65, 660, 98);
		panel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		panel_1.add(queryTB, BorderLayout.CENTER);
		queryTB.setFont(new Font("Arial", Font.PLAIN, 14));
		
		JSeparator separator = new JSeparator();
		separator.setBounds(14, 170, 660, 2);
		panel.add(separator);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EmptyBorder(0, 0, 0, 0));
		panel_2.setBounds(-1, 345, 697, 31);
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
		
		Border emptyBorder = BorderFactory.createEmptyBorder();
		
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
		btnConnect.setBounds(14, 28, 30, 30);
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
					JOptionPane.showMessageDialog(null, "Uspješno ste diskonektovani !");
				}
				catch(Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		btnDisconnect.setToolTipText("Disconnect.");
		btnDisconnect.setIcon(new ImageIcon(temp));
		btnDisconnect.setBounds(45, 28, 30, 30);
		panel.add(btnDisconnect);
		
		historyTable = new JTable();
		historyTable.setBounds(190, 304, 66, 47);
		
		ScrollPane scrollPane_Log = new ScrollPane();
		scrollPane_Log.setSize(249, 120);
		scrollPane_Log.setLocation(425, 178);
		scrollPane.setBounds(14, 178, 405, 120);
		panel.add(scrollPane_Log);
		
		scrollPane_Log.add(historyTable);
		
		historyTable.addMouseListener(new MouseAdapter() {
			  public void mouseClicked(MouseEvent e) {
			     if (e.getClickCount() == 2) { // check if a double click
			    	 
			    	 int row = historyTable.rowAtPoint(e.getPoint());
			    	 queryTB.setText((String) historyTable.getValueAt(row, 2));
			     }
			   }
			});
		
		btnExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					String s = queryTB.getText();
					long tStart = System.currentTimeMillis();
					ResultSet rs = kon.createResultSet(s);
					int br = brojRedova(rs);
					prikaziUTabeli(rs);
					long tEnd = System.currentTimeMillis();
					long tms = tEnd - tStart;
					statusTB.setText(" >> " + rezultatTable.getRowCount() + " rows fetched. (Elapsed time: " + tms + " ms)");
					
					DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					Date date = new Date();
					System.out.println(dateFormat.format(date));
					CommandLogger c = new CommandLogger(kon.getKorisnik(), s, date);
					
					popuniLoggerTabelu(c);
					writeFile("./test.txt");
			        //readFile("./test.txt");
					
				}
				catch(Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
	}
	
	public void uspostaviKonekciju() {
		
		try {
			
			ParametriKonekcijeProzor pk = new ParametriKonekcijeProzor();
			kon = pk.dajKonekciju();
		}
		catch(Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}
	
	public void prikaziUTabeli(ResultSet rs) throws SQLException {
		
		ListTableModel modelTabele = ListTableModel.createModelFromResultSet(rs);
		rezultatTable.setModel(modelTabele);
	}
	
	public void popuniLoggerTabelu(CommandLogger c) {
		
		listaLogger.add(c);
		
		LoggerTableModel model = new LoggerTableModel(listaLogger);
		historyTable.setModel(model);
	}
	
	public int brojRedova(ResultSet rs) throws SQLException {
		
		return rs.getFetchSize();
	}
	
	//Datoteke
    public static void writeFile(String path) throws IOException {
        Writer writer = new OutputStreamWriter(new FileOutputStream(path), UTF8);
        try {
        	for(CommandLogger c : listaLogger) {
        		String output = String.format(c.getVrijeme() + "," + c.getUser() + "," + c.getIzvrsenaKomanda() + "%s",System.getProperty("line.separator"));
        		writer.write(output);
        	}
        } finally {
            writer.close();
        }
    }

    public static void readFile(String path) throws IOException {
        Reader reader = new InputStreamReader(new FileInputStream(path), UTF8);
        try {
            int c = reader.read();
            System.out.println(c);
        } finally {
            reader.close();
        }
    }
}
