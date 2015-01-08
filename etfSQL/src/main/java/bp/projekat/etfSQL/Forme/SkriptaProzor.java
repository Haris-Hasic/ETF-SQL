package bp.projekat.etfSQL.Forme;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Image;

import javax.swing.JFrame;
import java.awt.BorderLayout;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JTextField;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

public class SkriptaProzor {

	private JFrame frmKreirajSkriptu;
	private JTextField status_textBox;

	/**
	 * Create the application.
	 */
	public SkriptaProzor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmKreirajSkriptu = new JFrame();
		frmKreirajSkriptu.setIconImage(Toolkit.getDefaultToolkit().getImage(SkriptaProzor.class.getResource("/bp/projekat/etfSQL/Resursi/script_icon.png")));
		frmKreirajSkriptu.setTitle("Write Script - Untitled");
		frmKreirajSkriptu.setBounds(100, 100, 450, 300);
		frmKreirajSkriptu.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frmKreirajSkriptu.setMinimumSize(new Dimension(500, 500));
		frmKreirajSkriptu.setLocationRelativeTo(null); 
		
		final JTextArea skripta_txtBox = new JTextArea();
		frmKreirajSkriptu.getContentPane().add(skripta_txtBox, BorderLayout.CENTER);
		
		JToolBar toolBar = new JToolBar();
		frmKreirajSkriptu.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		status_textBox = new JTextField();
		status_textBox.setEditable(false);
		toolBar.add(status_textBox);
		status_textBox.setColumns(10);
		
		JMenuBar menuBar = new JMenuBar();
		frmKreirajSkriptu.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("Script");
		mnFile.setFont(new Font("SansSerif", Font.PLAIN, 12));
		menuBar.add(mnFile);
		
		ImageIcon slika = new ImageIcon(getClass().getResource("/bp/projekat/etfSQL/Resursi/ExecuteAsScript_Icon.png"));
		Image sl = slika.getImage();
		Image tempSl = sl.getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
		
		JMenuItem mntmExecute = new JMenuItem("Execute");
		mntmExecute.setIcon(new ImageIcon(tempSl));
		mntmExecute.setFont(new Font("SansSerif", Font.PLAIN, 12));
		mnFile.add(mntmExecute);
		
		mnFile.addSeparator();
		
		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.setFont(new Font("SansSerif", Font.PLAIN, 12));
		mnFile.add(mntmNew);
		
		JMenuItem mntmOpen = new JMenuItem("Open...");
		mntmOpen.setFont(new Font("SansSerif", Font.PLAIN, 12));
		mnFile.add(mntmOpen);
		
		slika = new ImageIcon(getClass().getResource("/bp/projekat/etfSQL/Resursi/save_icon.png"));
		sl = slika.getImage();
		tempSl = sl.getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.setIcon(new ImageIcon(tempSl));
		mntmSave.setFont(new Font("SansSerif", Font.PLAIN, 12));
		mnFile.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save as...");
		mntmSaveAs.setIcon(new ImageIcon(tempSl));
		mntmSaveAs.setFont(new Font("SansSerif", Font.PLAIN, 12));
		mnFile.add(mntmSaveAs);
		
		mnFile.addSeparator();
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setFont(new Font("SansSerif", Font.PLAIN, 12));
		mnFile.add(mntmExit);
		
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
		
		frmKreirajSkriptu.setVisible(true);
		
		//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// Eventi menu opcija
		//--------------------------------------------------------------------------------------------------------------------------
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					FileDialog fd = new FileDialog(frmKreirajSkriptu, "Open File...", FileDialog.LOAD);
					fd.setVisible(true);
					String path = fd.getDirectory() + fd.getFile();
					FileReader fileReader = new FileReader(path);
					BufferedReader buffReader = new BufferedReader(fileReader);
					skripta_txtBox.read(buffReader, fd.getFile());
					frmKreirajSkriptu.setTitle("Write Script - " + fd.getFile());
				}
				catch(Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
		//--------------------------------------------------------------------------------------------------------------------------
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    
				int reply = JOptionPane.showConfirmDialog(null, "All unsaved changes will be lost. Are you sure ?", "Open New File", JOptionPane.YES_NO_OPTION);
				
		        if (reply == JOptionPane.YES_OPTION) {
		        	skripta_txtBox.setText("");
		        	frmKreirajSkriptu.setTitle("Write Script - Untitled");
		        }
			}
		});
		//--------------------------------------------------------------------------------------------------------------------------
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmKreirajSkriptu.dispose();
			}
		});
		//--------------------------------------------------------------------------------------------------------------------------
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String s = skripta_txtBox.getText();
				
				if(s.length()>0)
				{
					try {
						FileDialog fd = new FileDialog(frmKreirajSkriptu, "Save File As", FileDialog.SAVE);
						fd.setFile("Script");
						fd.setVisible(true);
						String path = fd.getDirectory() + fd.getFile();
		
						FileOutputStream fos = new FileOutputStream(path);
						byte[] b = s.getBytes();
						fos.write(b);
						fos.close();
						
						frmKreirajSkriptu.setTitle("Write Script - " + fd.getFile());
					} 
					catch (Exception e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
				}
			}
		});
		//--------------------------------------------------------------------------------------------------------------------------
		mntmSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String s = skripta_txtBox.getText();
				
				if(s.length()>0)
				{
					try {
						FileDialog fd = new FileDialog(frmKreirajSkriptu, "Save File As", FileDialog.SAVE);
						fd.setFile("Script");
						fd.setVisible(true);
						String path = fd.getDirectory() + fd.getFile();
		
						FileOutputStream fos = new FileOutputStream(path);
						byte[] b = s.getBytes();
						fos.write(b);
						fos.close();
						
						frmKreirajSkriptu.setTitle("Write Script - " + fd.getFile());
					} 
					catch (Exception e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
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
		skripta_txtBox.addCaretListener(new CaretListener() {
			
			public void caretUpdate(CaretEvent arg0) {
				
                int linenum = 1;
                int columnnum = 1;

                try {
                	
                    int caretpos = skripta_txtBox.getCaretPosition();
                    linenum = skripta_txtBox.getLineOfOffset(caretpos);

                    columnnum = caretpos - skripta_txtBox.getLineStartOffset(linenum);

                    linenum += 1;
                }
                catch(Exception ex) { }
                
                updateStatus(linenum, columnnum);
            }
        });

        updateStatus(1,1);
    }

	
    private void updateStatus(int linenumber, int columnnumber) {
    	status_textBox.setText("Line: " + linenumber + "; Column: " + columnnumber + ";");
    }

}
