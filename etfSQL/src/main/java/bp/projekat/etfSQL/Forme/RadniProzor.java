package bp.projekat.etfSQL.Forme;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JScrollPane;

import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.SwingConstants;

import java.awt.Desktop;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Window.Type;
import java.awt.Toolkit;

import javax.swing.JTree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import bp.projekat.etfSQL.Baza.Konekcija;
import bp.projekat.etfSQL.Baza.ScriptRunner;
import bp.projekat.etfSQL.Klase.Command;
import bp.projekat.etfSQL.Klase.CommandLogger;
import bp.projekat.etfSQL.Klase.ListTableModel;
import bp.projekat.etfSQL.Klase.LoggerTableModel;

import javax.swing.JCheckBox;
import javax.swing.JTabbedPane;

public class RadniProzor {

	private JFrame frmEtfSql; // Forma
	private Konekcija kon;  // Konekcija na bazu podataka
	private static CommandLogger commandLogger;
	private DefaultMutableTreeNode korijen;
	//-----------------------
	private ImageIcon slika;
	private Image sl;          // Ovaj kombo često koristim za postavljanje slike na dugmiće
	private Image tempSl;
	private Border emptyBorder;
	//------------------------------------------------------------------------------------------------------------------------------
	// Važnije kontrole sa forme
	private JTextArea query_txtBox; // Tekstualno polje za unos upita
	private JTable rezultatTable;   // Tabela u kojoj se prikazuju rezultati
	private JTextField statusBar_txtBox; // Polje u kome se prikazuju informacije o izvršenju
	JTextField aktivniUser_txtBox; // Polje u kome se prikazuju informacije o aktivnoj konekciji
	private JTable historyTable; // Tabela gdje se prikazuju komande koje su često korištene
	private JTree drvo; // Drvo u kojem se prikazuje struktura baze podataka 
	//------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RadniProzor window = new RadniProzor();
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
	public RadniProzor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Postavke forme
		frmEtfSql = new JFrame();
		frmEtfSql.setIconImage(Toolkit.getDefaultToolkit().getImage(RadniProzor.class.getResource("/bp/projekat/etfSQL/Resursi/ETF-Logo.gif")));
		frmEtfSql.setTitle("ETF SQL");
		frmEtfSql.setType(Type.POPUP);
		frmEtfSql.setBounds(100, 100, 900, 550);
		frmEtfSql.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEtfSql.setMinimumSize(new Dimension(900, 550));
		frmEtfSql.setLocationRelativeTo(null); 
		
		kon = null;
		commandLogger = new CommandLogger();
		try {
			commandLogger.ucitajIzDatoteke("./test.txt");
		}
		catch (Exception e) {
			System.out.println(e);
		}
		//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		//Inicijalizacija kontrola
		JMenuBar menuBar = new JMenuBar();
		frmEtfSql.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setFont(new Font("SansSerif", Font.PLAIN, 12));
		menuBar.add(mnFile);
		
		slika = new ImageIcon(getClass().getResource("/bp/projekat/etfSQL/Resursi/close_icon.png"));
		sl = slika.getImage();
		tempSl = sl.getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.setFont(new Font("SansSerif", Font.PLAIN, 12));
		mnFile.add(mntmNew);
		
		mnFile.addSeparator();
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setIcon(new ImageIcon(tempSl));
		mntmExit.setFont(new Font("SansSerif", Font.PLAIN, 12));
		mnFile.add(mntmExit);
		
		JMenu mnEdit = new JMenu("Edit");
		mnEdit.setFont(new Font("SansSerif", Font.PLAIN, 12));
		menuBar.add(mnEdit);
		
		JMenu mnView = new JMenu("View");
		mnView.setFont(new Font("SansSerif", Font.PLAIN, 12));
		menuBar.add(mnView);
		
		JMenu mnSearch = new JMenu("Search");
		mnSearch.setFont(new Font("SansSerif", Font.PLAIN, 12));
		menuBar.add(mnSearch);
		
		JMenu mnText = new JMenu("Text");
		mnText.setFont(new Font("SansSerif", Font.PLAIN, 12));
		menuBar.add(mnText);
		
		JMenu mnSession = new JMenu("Session");
		mnSession.setFont(new Font("SansSerif", Font.PLAIN, 12));
		menuBar.add(mnSession);
		
		JMenuItem mntmConnect = new JMenuItem("Connect...");
		mntmConnect.setFont(new Font("SansSerif", Font.PLAIN, 12));
		mnSession.add(mntmConnect);
		
		JMenuItem mntmDisconnect = new JMenuItem("Disconnect");
		mntmDisconnect.setFont(new Font("SansSerif", Font.PLAIN, 12));
		mnSession.add(mntmDisconnect);
		
		JMenu mnScript = new JMenu("Script");
		mnScript.setFont(new Font("SansSerif", Font.PLAIN, 12));
		menuBar.add(mnScript);
		
		JMenu mnTools = new JMenu("Tools");
		mnTools.setFont(new Font("SansSerif", Font.PLAIN, 12));
		menuBar.add(mnTools);
		
		JMenu mnWindow = new JMenu("Window");
		mnWindow.setFont(new Font("SansSerif", Font.PLAIN, 12));
		menuBar.add(mnWindow);
		
		JMenu mnHelp = new JMenu("Help");
		mnHelp.setFont(new Font("SansSerif", Font.PLAIN, 12));
		menuBar.add(mnHelp);
		
		slika = new ImageIcon(getClass().getResource("/bp/projekat/etfSQL/Resursi/help_icon.png"));
		sl = slika.getImage();
		tempSl = sl.getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
		
		JMenuItem mntmViewDocumentation = new JMenuItem("View Documentation");
		mntmViewDocumentation.setIcon(new ImageIcon(tempSl));
		mntmViewDocumentation.setFont(new Font("SansSerif", Font.PLAIN, 12));
		mnHelp.add(mntmViewDocumentation);
		
		mnHelp.addSeparator();
		
		JMenuItem mntmAboutEtfSql = new JMenuItem("About ETF SQL...");
		mntmAboutEtfSql.setFont(new Font("SansSerif", Font.PLAIN, 12));
		mnHelp.add(mntmAboutEtfSql);
		frmEtfSql.getContentPane().setLayout(new BorderLayout(0, 0));
		
		//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Važnije kontenjerske kontrole (paneli, scroll_paneli itd)
		//--------------------------------------------------------------------------------------------------------------------------
		JPanel panel = new JPanel();
		frmEtfSql.getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		//--------------------------------------------------------------------------------------------------------------------------
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridheight = 9;
		gbc_scrollPane_1.gridwidth = 18;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 5;
		gbc_scrollPane_1.gridy = 0;
		panel.add(scrollPane_1, gbc_scrollPane_1);
		
		query_txtBox = new JTextArea();
		query_txtBox.setFont(new Font("SansSerif", Font.PLAIN, 15));
		scrollPane_1.setViewportView(query_txtBox);
		//--------------------------------------------------------------------------------------------------------------------------
		
		//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Dodavanje elemenata na gornji toolbar
		JToolBar toolBar = new JToolBar();
		toolBar.setBackground(SystemColor.menu);
		frmEtfSql.getContentPane().add(toolBar, BorderLayout.NORTH);
		
		//--------------------------------------------------------------------------------------------------------------------------
		slika = new ImageIcon(getClass().getResource("/bp/projekat/etfSQL/Resursi/connect_icon.png"));
		sl = slika.getImage();
		tempSl = sl.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		
		JButton btnConnect = new JButton("");
		btnConnect.setToolTipText("Connect to a database.");
		btnConnect.setBounds(78, 70, 30, 30);
		btnConnect.setIcon(new ImageIcon(tempSl));
		emptyBorder = BorderFactory.createEmptyBorder();
		btnConnect.setBorder(emptyBorder);
		
		toolBar.add(btnConnect);
		toolBar.add(new JLabel("  ")); //Simuliranje razmaka radi boljeg izgleda
		//--------------------------------------------------------------------------------------------------------------------------
		slika = new ImageIcon(getClass().getResource("/bp/projekat/etfSQL/Resursi/disconnect_icon.png"));
		sl = slika.getImage();
		tempSl = sl.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		
		JButton btnDisconnect = new JButton("");
		btnDisconnect.setToolTipText("Disconnect from a database..");
		btnDisconnect.setBounds(78, 70, 30, 30);
		btnDisconnect.setIcon(new ImageIcon(tempSl));
		emptyBorder = BorderFactory.createEmptyBorder();
		btnDisconnect.setBorder(emptyBorder);
		
		toolBar.add(btnDisconnect);
		toolBar.add(new JLabel("  ")); //Simuliranje razmaka radi boljeg izgleda
		//--------------------------------------------------------------------------------------------------------------------------
		aktivniUser_txtBox = new JTextField();
		aktivniUser_txtBox.setEditable(false);
		toolBar.add(aktivniUser_txtBox);
		toolBar.add(new JLabel("  "));
		toolBar.add(new JSeparator(SwingConstants.VERTICAL));
		toolBar.add(new JLabel("  "));
		//--------------------------------------------------------------------------------------------------------------------------
		slika = new ImageIcon(getClass().getResource("/bp/projekat/etfSQL/Resursi/script_icon.png"));
		sl = slika.getImage();
		tempSl = sl.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
				
		JButton btnCreateScript = new JButton("");
		btnCreateScript.setToolTipText("Write new script.");
		btnCreateScript.setBounds(78, 70, 30, 30);
		btnCreateScript.setIcon(new ImageIcon(tempSl));
		emptyBorder = BorderFactory.createEmptyBorder();
		btnCreateScript.setBorder(emptyBorder);
		
		toolBar.add(btnCreateScript);
		toolBar.add(new JLabel("  "));
		//--------------------------------------------------------------------------------------------------------------------------
		slika = new ImageIcon(getClass().getResource("/bp/projekat/etfSQL/Resursi/ExecuteAsScript_icon.png"));
		sl = slika.getImage();
		tempSl = sl.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
						
		JButton btnExecuteScript = new JButton("");
		btnExecuteScript.setToolTipText("Execute written text as script.");
		btnExecuteScript.setBounds(78, 70, 30, 30);
		btnExecuteScript.setIcon(new ImageIcon(tempSl));
		emptyBorder = BorderFactory.createEmptyBorder();
		btnExecuteScript.setBorder(emptyBorder);
				
		toolBar.add(btnExecuteScript);
		toolBar.add(new JLabel("  "));
		//--------------------------------------------------------------------------------------------------------------------------
		slika = new ImageIcon(getClass().getResource("/bp/projekat/etfSQL/Resursi/execute_script_icon.png"));
		sl = slika.getImage();
		tempSl = sl.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
						
		JButton btnExecuteAsScript = new JButton("");
		btnExecuteAsScript.setToolTipText("Execute selected script.");
		btnExecuteAsScript.setBounds(78, 70, 30, 30);
		btnExecuteAsScript.setIcon(new ImageIcon(tempSl));
		emptyBorder = BorderFactory.createEmptyBorder();
		btnExecuteAsScript.setBorder(emptyBorder);
				
		toolBar.add(btnExecuteAsScript);
		toolBar.add(new JLabel("  "));
		toolBar.add(new JSeparator(SwingConstants.VERTICAL));
		toolBar.add(new JLabel("  "));
		//--------------------------------------------------------------------------------------------------------------------------
		slika = new ImageIcon(getClass().getResource("/bp/projekat/etfSQL/Resursi/execute_icon.png"));
		sl = slika.getImage();
		tempSl = sl.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		
		JButton btnExecute = new JButton("");
		btnExecute.setToolTipText("Execute query.");
		btnExecute.setBounds(78, 70, 30, 30);
		btnExecute.setIcon(new ImageIcon(tempSl));
		emptyBorder = BorderFactory.createEmptyBorder();
		btnExecute.setBorder(emptyBorder);
		
		toolBar.add(btnExecute);
		toolBar.add(new JLabel("  "));
		//--------------------------------------------------------------------------------------------------------------------------
		slika = new ImageIcon(getClass().getResource("/bp/projekat/etfSQL/Resursi/commit_icon.png"));
		sl = slika.getImage();
		tempSl = sl.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		
		JButton btnCommit = new JButton("");
		btnCommit.setToolTipText("Commit changes to database.");
		btnCommit.setBounds(78, 70, 30, 30);
		btnCommit.setIcon(new ImageIcon(tempSl));
		emptyBorder = BorderFactory.createEmptyBorder();
		btnCommit.setBorder(emptyBorder);
		
		toolBar.add(btnCommit);
		toolBar.add(new JLabel("  "));
		//--------------------------------------------------------------------------------------------------------------------------
		slika = new ImageIcon(getClass().getResource("/bp/projekat/etfSQL/Resursi/rollback_icon.png"));
		sl = slika.getImage();
		tempSl = sl.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		
		JButton btnRollback = new JButton("");
		btnRollback.setToolTipText("Rollback uncommited changes.");
		btnRollback.setBounds(78, 70, 30, 30);
		btnRollback.setIcon(new ImageIcon(tempSl));
		emptyBorder = BorderFactory.createEmptyBorder();
		btnRollback.setBorder(emptyBorder);
		
		toolBar.add(btnRollback);
		toolBar.add(new JLabel("  "));
		toolBar.add(new JSeparator(SwingConstants.VERTICAL));
		toolBar.add(new JLabel(" "));
		toolBar.add(new JLabel(" "));
		//--------------------------------------------------------------------------------------------------------------------------
		final JCheckBox logirajUBazu_checkBox = new JCheckBox("Logiraj u Bazu");
		logirajUBazu_checkBox.setFont(new Font("SansSerif", Font.PLAIN, 11));
		toolBar.add(logirajUBazu_checkBox);
		toolBar.add(new JLabel(" "));
		//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		
		JToolBar toolBar_1 = new JToolBar();
		frmEtfSql.getContentPane().add(toolBar_1, BorderLayout.SOUTH);
		
		statusBar_txtBox = new JTextField();
		toolBar_1.add(statusBar_txtBox);
		statusBar_txtBox.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 15;
		gbc_scrollPane.gridwidth = 5;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panel.add(scrollPane, gbc_scrollPane);

		JScrollPane scrollPane_3 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_3 = new GridBagConstraints();
		gbc_scrollPane_3.gridheight = 9;
		gbc_scrollPane_3.gridwidth = 6;
		gbc_scrollPane_3.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_3.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_3.gridx = 23;
		gbc_scrollPane_3.gridy = 0;
		panel.add(scrollPane_3, gbc_scrollPane_3);
		
		historyTable = new JTable();
		scrollPane_3.setViewportView(historyTable);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.gridheight = 6;
		gbc_scrollPane_2.gridwidth = 24;
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.gridx = 5;
		gbc_scrollPane_2.gridy = 9;
		panel.add(scrollPane_2, gbc_scrollPane_2);
		
		rezultatTable = new JTable();
		scrollPane_2.setViewportView(rezultatTable);
		
		//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Događaji
		//--------------------------------------------------------------------------------------------------------------------------
		// Connect dugme
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				uspostaviKonekciju();
			}
		});
		//--------------------------------------------------------------------------------------------------------------------------
		// Execute dugme 
		btnExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {

					String s = query_txtBox.getText();
					long tStart = System.currentTimeMillis();
					ResultSet rs = kon.createResultSet(s);
					prikaziUTabeli(rs);
					long tEnd = System.currentTimeMillis();
					long tms = tEnd - tStart;
					statusBar_txtBox.setText(" >> " + rezultatTable.getRowCount() + " rows fetched. (Elapsed time: " + tms + " ms)");

					DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					Date date = new Date();
					System.out.println(dateFormat.format(date));
					Command c = new Command(Konekcija.getKorisnik(), s, date);
					commandLogger.dodajKomandu(c);

					if(!logirajUBazu_checkBox.isSelected())
						commandLogger.spasiUDatoteku("./test.txt");

					else {
						
						kon.createLogTable();
						kon.logiraj(Konekcija.getKorisnik(), s, date);
					}

					popuniLoggerTabelu();					
				}
				
				catch(Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
		//--------------------------------------------------------------------------------------------------------------------------
		// Disconnect dugme
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					kon.Disconnect();
					aktivniUser_txtBox.setText("  Not Connected");
					pocistiDrvo();
					JOptionPane.showMessageDialog(null, "Uspješno ste diskonektovani !");
				}
				catch(Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		//--------------------------------------------------------------------------------------------------------------------------
		// Commit dugme
		btnCommit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {

					int reply = JOptionPane.showConfirmDialog(null, "Sve izvršene promjene će biti trajno zapisane na disk.\n Jeste li sigurni da želite uraditi commit ?", "Commit Changes To Database ?", JOptionPane.YES_NO_OPTION);
					
			        if (reply == JOptionPane.YES_OPTION) {
			        	kon.Commit();
			        	JOptionPane.showMessageDialog(null, "Promjene su uspješno zapisane u bazu.");
			        }
				}
				catch (Exception e) {
					
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
		//--------------------------------------------------------------------------------------------------------------------------
		// Rollback dugme
		btnRollback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					int reply = JOptionPane.showConfirmDialog(null, "Sve izvršene promjene nakon zadnjeg konzistentnog stanja će biti izgubljene.\n Jeste li sigurni da želite uraditi rollback ?", "Rollback Changes ?", JOptionPane.YES_NO_OPTION);
					
			        if (reply == JOptionPane.YES_OPTION) {
			        	kon.Rollback();
			        	JOptionPane.showMessageDialog(null, "Napravljene promjene su uspješno poništene.");
			        }
				}
				catch (Exception e) {
					
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
		//--------------------------------------------------------------------------------------------------------------------------
		// Execute Script dugme
		btnExecuteScript.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					FileDialog fd = new FileDialog(frmEtfSql, "Open File...", FileDialog.LOAD);
					fd.setVisible(true);
					String path = fd.getDirectory() + fd.getFile();

					ScriptRunner runner = new ScriptRunner(kon.getKonekcija(), true, true);//konekcija, [booleanAutoCommit], [booleanStopOnerror]
					runner.runScript(new BufferedReader(new FileReader(path)));
					JOptionPane.showMessageDialog(null, "Skripta uspješno izvršena.");
				}
				catch (Exception e) {
					
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
		//--------------------------------------------------------------------------------------------------------------------------
		// Create Script dugme
		btnCreateScript.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {

					SkriptaProzor sp = new SkriptaProzor();
				}
				catch(Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		//--------------------------------------------------------------------------------------------------------------------------
		mntmViewDocumentation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {

					File pdfFile = new File(getClass().getResource("/bp/projekat/etfSQL/Resursi/Help.pdf").getFile());
					
					if (pdfFile.exists()) {

						if (Desktop.isDesktopSupported()) {
							Desktop.getDesktop().open(pdfFile);
						} else {
							JOptionPane.showMessageDialog(null, "Awt Desktop is not supported!");
						}
					}

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
		//--------------------------------------------------------------------------------------------------------------------------
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmEtfSql.dispose();
			}
		});
		//--------------------------------------------------------------------------------------------------------------------------

		//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		
		korijen = new DefaultMutableTreeNode("Items");
		DefaultTreeModel model = new DefaultTreeModel(korijen);
		drvo = new JTree();
		drvo.setBorder(null);
		drvo.setModel(model);
		scrollPane.setViewportView(drvo);

		historyTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) { // check if a double click

					int row = historyTable.rowAtPoint(e.getPoint());
					query_txtBox.setText((String) historyTable.getValueAt(row, 2));
				}
			}
		});
		
		popuniLoggerTabelu();
	}
	
	//Pomoćne funkcije
	//------------------------------------------------------------------------------------------------------------------------------
	
	public void setKonekcija(Konekcija k)
	{
		kon = k;
	}

	public void postaviKorisnika(String k, String b)
	{
		aktivniUser_txtBox.setText("  " + k + "@" + b);
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
	
	public void populirajDrvo(String baza) throws Exception
	{
		DefaultMutableTreeNode tab = new DefaultMutableTreeNode("Tables");
		DefaultMutableTreeNode vie = new DefaultMutableTreeNode("Views");
		DefaultMutableTreeNode sys = new DefaultMutableTreeNode("System Tables");
		DefaultMutableTreeNode syn = new DefaultMutableTreeNode("Synonyms");
		
		for(DefaultMutableTreeNode c : populirajTabele(tab, vie, sys, syn, baza))
			korijen.add(c);
				
		DefaultMutableTreeNode proc = new DefaultMutableTreeNode("Procedures");
		korijen.add(populirajProcedure(proc, baza));

		DefaultTreeModel model = new DefaultTreeModel(korijen);
		drvo.setModel(model);
	}
	
	public void pocistiDrvo() throws Exception
	{
		korijen = new DefaultMutableTreeNode("Items");
		DefaultTreeModel model = new DefaultTreeModel(korijen);
		drvo.setModel(model);
	}
	
	public List<DefaultMutableTreeNode> populirajTabele(DefaultMutableTreeNode tab, DefaultMutableTreeNode vie, DefaultMutableTreeNode sys, DefaultMutableTreeNode syn, String baza) 
	{
		List<DefaultMutableTreeNode> listaCvorova = new ArrayList<DefaultMutableTreeNode>();
		
		try {
			
			String[] tipoviObjekataTab = {"TABLE"};
			String[] tipoviObjekataVie = {"VIEW"};
			String[] tipoviObjekataSys = {"SYSTEM TABLE"};
			String[] tipoviObjekataSyn = {"SYNONYM"};

			DatabaseMetaData mp = Konekcija.getKonekcija().getMetaData();
			ResultSet rsTab = mp.getTables(baza, "%", "%", tipoviObjekataTab);
			ResultSet rsVie = mp.getTables(baza, "%", "%", tipoviObjekataVie);
			ResultSet rsSys = mp.getTables(baza, "%", "%", tipoviObjekataSys);
			ResultSet rsSyn = mp.getTables(baza, "%", "%", tipoviObjekataSyn);
			
			int i = 0; // Kupljenje samo 200 slogova
			
			while (rsTab.next()) {
				
				DefaultMutableTreeNode cvor = new DefaultMutableTreeNode(rsTab.getString("TABLE_NAME"));
				tab.add(cvor);
				i++;
				if(i>200)
					break;
			}
			i = 0;
			while (rsVie.next()) {

				DefaultMutableTreeNode cvor = new DefaultMutableTreeNode(rsVie.getString("TABLE_NAME"));
				vie.add(cvor);
				i++;
				if(i>200)
					break;
			}
			i = 0;
			while (rsSys.next()) {

				DefaultMutableTreeNode cvor = new DefaultMutableTreeNode(rsSys.getString("TABLE_NAME"));
				sys.add(cvor);
				i++;
				if(i>200)
					break;
			}
			i = 0;
			while (rsSyn.next()) {

				DefaultMutableTreeNode cvor = new DefaultMutableTreeNode(rsSyn.getString("TABLE_NAME"));
				syn.add(cvor);
				i++;
				if(i>200)
					break;
			}
			
			listaCvorova.add(tab);
			listaCvorova.add(vie);
			listaCvorova.add(sys);
			listaCvorova.add(syn);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			System.out.println(e);
		}
		
		return listaCvorova;
	}
	
	public DefaultMutableTreeNode populirajProcedure(DefaultMutableTreeNode top, String baza) 
	{
		try {

			DatabaseMetaData mp = Konekcija.getKonekcija().getMetaData();
			ResultSet rs = mp.getProcedures(baza, null, "%");
			
			int i = 0;
			while (rs.next()) {

				DefaultMutableTreeNode cvor = new DefaultMutableTreeNode(rs.getString("PROCEDURE_NAME"));
				top.add(cvor);
				i++;
				if(i>200)
					break;
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			System.out.println(e);
		}

		return top;
	}
}
