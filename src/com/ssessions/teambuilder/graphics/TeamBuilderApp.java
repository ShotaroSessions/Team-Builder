package com.ssessions.teambuilder.graphics;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.ssessions.teambuilder.exceptions.InvalidFileException;
import com.ssessions.teambuilder.systems.SystemInterface;

import java.awt.BorderLayout;
import java.awt.Cursor;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

/**
 * Class for the GUI, uses {@link systems.SystemInterface} to use the other 
 * classes within the application
 * 
 * @author Shotaro Sessions 
 *
 */
public class TeamBuilderApp {

	private SystemInterface systemIn;
	
	private JFrame frame;

	private JPanel panel;
	
	private JButton btnExit;
	private JButton btnReadFile;
	private JButton btnGroup;
	private JButton btnSort;
	
	private JLabel lblTeamBuilder;
	private JLabel lblStudents;
	private JLabel lblElements;
	private JLabel lblGroups;
	private JLabel lblPriorityElements;
	private JLabel lblElem_1;
	private JLabel lblElem_2;
	private JLabel lblElem_3;
	
	private JComboBox<String> cmbGroups;
	private JComboBox<String> cmbElem_1;
	private JComboBox<String> cmbElem_2;
	private JComboBox<String> cmbElem_3;
	
	private JCheckBox chkElem_1;
	private JCheckBox chkElem_2;
	private JCheckBox chkElem_3;
	
	public TeamBuilderApp(String link) throws InvalidFileException{
		systemIn = new SystemInterface(link);
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				//First Makes Sure that a TeamBuilderApp instance is successfully made
				TeamBuilderApp graphics = null;
				while(graphics == null){
					try {
						String file = fileSelect();
						if(file == null) System.exit(0);
						graphics = new TeamBuilderApp(file);
					} catch(InvalidFileException e) {
						e.printStackTrace();
						JOptionPane.showConfirmDialog(null, e.getLocalizedMessage(), "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
					}
				}
				
				//Initializes and shows the window
				try {
					graphics.initialize();
					graphics.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static String fileSelect() {
		
		JFileChooser chooser;
		int returnVal;
		
		//Creates file chooser
		chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
		chooser.setFileFilter(filter);
		chooser.setCurrentDirectory(new java.io.File("."));
			    
		//Choose read file
		chooser.setDialogTitle("Open File");
		System.out.println("Please choose a file to read from");
		returnVal = chooser.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) return chooser.getSelectedFile().getAbsolutePath();
		else return null;
				
	}
	
	private void writeToFile(String groupResult){
		
		JFileChooser chooser;
		int returnVal;
		String writeLocation;
		boolean choosing = true;
		
		while(choosing) {
			//Choose write file
			chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setCurrentDirectory(new java.io.File("."));
			chooser.setAcceptAllFileFilterUsed(false);
			chooser.setDialogTitle("Print to File");
			returnVal = chooser.showOpenDialog(null);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				writeLocation = chooser.getSelectedFile().getAbsolutePath();
				try {
					systemIn.writeString(groupResult, writeLocation);
					choosing = false;
				} catch(InvalidFileException e) {
					e.printStackTrace();
					JOptionPane.showConfirmDialog(null, e.getLocalizedMessage(), "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}
		
	}

	private void updateDisplay() {
		
		int index;
		
		lblStudents.setText("Students: " + systemIn.studentNum());
		lblElements.setText("Elements: " + systemIn.elemNum());
		
		String[] groupItems = new String[systemIn.studentNum()/3-1];
		index = 0;
		for(int i = 2; i <= systemIn.studentNum()/3; i++) groupItems[index++] = Integer.toString(i);
		cmbGroups.setModel(new DefaultComboBoxModel<String>(groupItems));
		String[] elemItems = new String[systemIn.elemNum()];
		index = 0;
		for(int i = 1; i < elemItems.length + 1; i++) elemItems[index++] = Integer.toString(i);
		cmbElem_1.setModel(new DefaultComboBoxModel<String>(elemItems));
		cmbElem_2.setModel(new DefaultComboBoxModel<String>(elemItems));
		cmbElem_3.setModel(new DefaultComboBoxModel<String>(elemItems));
		
	}


	/**
	 * Initialize the contents and action listeners of the frame as well as call some startup operations
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 400, 250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Team Builder App");
		
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnExit.setBounds(10, 10, 80, 25);
		panel.add(btnExit);
		
		btnReadFile = new JButton("Read File");
		btnReadFile.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnReadFile.setBounds(296, 10, 80, 25);
		panel.add(btnReadFile);
		
		lblTeamBuilder = new JLabel("Team Builder");
		lblTeamBuilder.setFont(new Font("Rockwell", Font.PLAIN, 24));
		lblTeamBuilder.setBounds(113, 0, 173, 46);
		panel.add(lblTeamBuilder);
		
		lblStudents = new JLabel("Students: 0");
		lblStudents.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblStudents.setBounds(28, 56, 116, 25);
		panel.add(lblStudents);
		
		lblElements = new JLabel("Elements: 0");
		lblElements.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblElements.setBounds(29, 91, 115, 25);
		panel.add(lblElements);
		
		lblGroups = new JLabel("Groups:");
		lblGroups.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblGroups.setBounds(28, 130, 61, 25);
		panel.add(lblGroups);
		
		cmbGroups = new JComboBox<String>();
		cmbGroups.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cmbGroups.setModel(new DefaultComboBoxModel<String>(new String[] {"0"}));
		cmbGroups.setBounds(89, 132, 80, 21);
		panel.add(cmbGroups);
		
		lblPriorityElements = new JLabel("Priority Elements:");
		lblPriorityElements.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPriorityElements.setBounds(177, 54, 126, 25);
		panel.add(lblPriorityElements);
		
		btnGroup = new JButton("Group");
		btnGroup.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnGroup.setBounds(10, 178, 80, 25);
		panel.add(btnGroup);
		
		btnSort = new JButton("Sort");
		btnSort.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnSort.setBounds(100, 178, 80, 25);
		panel.add(btnSort);
		
		chkElem_1 = new JCheckBox("");
		chkElem_1.setBounds(187, 85, 21, 21);
		panel.add(chkElem_1);
		
		cmbElem_1 = new JComboBox<String>();
		cmbElem_1.setModel(new DefaultComboBoxModel<String>(new String[] {"0"}));
		cmbElem_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cmbElem_1.setBounds(214, 85, 60, 21);
		panel.add(cmbElem_1);
		
		lblElem_1 = new JLabel("1st Priority");
		lblElem_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblElem_1.setBounds(276, 81, 89, 25);
		panel.add(lblElem_1);
		
		chkElem_2 = new JCheckBox("");
		chkElem_2.setBounds(187, 115, 21, 21);
		panel.add(chkElem_2);
		
		cmbElem_2 = new JComboBox<String>();
		cmbElem_2.setModel(new DefaultComboBoxModel<String>(new String[] {"0"}));
		cmbElem_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cmbElem_2.setBounds(214, 115, 60, 21);
		panel.add(cmbElem_2);
		
		lblElem_2 = new JLabel("2nd Priority");
		lblElem_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblElem_2.setBounds(276, 111, 89, 25);
		panel.add(lblElem_2);
		
		chkElem_3 = new JCheckBox("");
		chkElem_3.setBounds(187, 145, 21, 21);
		panel.add(chkElem_3);
		
		cmbElem_3 = new JComboBox<String>();
		cmbElem_3.setModel(new DefaultComboBoxModel<String>(new String[] {"0"}));
		cmbElem_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cmbElem_3.setBounds(214, 145, 60, 21);
		panel.add(cmbElem_3);
		
		lblElem_3 = new JLabel("3rd Priority");
		lblElem_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblElem_3.setBounds(276, 141, 89, 25);
		panel.add(lblElem_3);
		
		
		//Action listeners
		
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(0);
			}
		});
		
		btnReadFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					String file = fileSelect();
					if(file != null) systemIn = new SystemInterface(file);
				} catch(InvalidFileException err) {
					err.printStackTrace();
					JOptionPane.showConfirmDialog(null, err.getLocalizedMessage(), "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				}
				updateDisplay();
			}
		});
		
		btnGroup.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String groupResult = systemIn.groupRoster(Integer.parseInt((String)cmbGroups.getSelectedItem()));
				writeToFile(groupResult);
				JOptionPane.showConfirmDialog(null, "List Saved", "Save File", JOptionPane.DEFAULT_OPTION);
			}
		});
		
		btnSort.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String sortResult;
				int[] elements;
				
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				
				if(chkElem_1.isSelected() && chkElem_2.isSelected() && chkElem_3.isSelected()) {
					elements = new int[3];
					elements[0] = Integer.parseInt((String)cmbElem_1.getSelectedItem())-1;
					elements[1] = Integer.parseInt((String)cmbElem_2.getSelectedItem())-1;
					elements[2] = Integer.parseInt((String)cmbElem_3.getSelectedItem())-1;
					sortResult = systemIn.sortRoster(Integer.parseInt((String)cmbGroups.getSelectedItem()), elements);
				}
				else if(chkElem_1.isSelected() && chkElem_2.isSelected()) {
					elements = new int[2];
					elements[0] = Integer.parseInt((String)cmbElem_1.getSelectedItem())-1;
					elements[1] = Integer.parseInt((String)cmbElem_2.getSelectedItem())-1;
					sortResult = systemIn.sortRoster(Integer.parseInt((String)cmbGroups.getSelectedItem()), elements);
				}
				else if(chkElem_1.isSelected()) {
					elements = new int[1];
					elements[0] = Integer.parseInt((String)cmbElem_1.getSelectedItem())-1;
					sortResult = systemIn.sortRoster(Integer.parseInt((String)cmbGroups.getSelectedItem()), elements);
				}
				else {
					sortResult = systemIn.sortRoster(Integer.parseInt((String)cmbGroups.getSelectedItem()));
				}
				frame.setCursor(Cursor.getDefaultCursor());
				writeToFile(sortResult);
				JOptionPane.showConfirmDialog(null, "List Saved", "Save File", JOptionPane.DEFAULT_OPTION);
			}
		});
		
		chkElem_1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					cmbElem_2.setVisible(true);
					chkElem_2.setVisible(true);
					lblElem_2.setVisible(true);
					if(chkElem_2.isSelected()) {
						cmbElem_3.setVisible(true);
						chkElem_3.setVisible(true);
						lblElem_3.setVisible(true);
					}
				}
				else {
					cmbElem_2.setVisible(false);
					chkElem_2.setVisible(false);
					lblElem_2.setVisible(false);
					cmbElem_3.setVisible(false);
					chkElem_3.setVisible(false);
					lblElem_3.setVisible(false);
				}
			}
		});
		
		chkElem_2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					cmbElem_3.setVisible(true);
					chkElem_3.setVisible(true);
					lblElem_3.setVisible(true);
				}
				else {
					cmbElem_3.setVisible(false);
					chkElem_3.setVisible(false);
					lblElem_3.setVisible(false);
				}
			}
		});
		
		
		//Startup operations
		
		updateDisplay();
		
		cmbElem_2.setVisible(false);
		chkElem_2.setVisible(false);
		lblElem_2.setVisible(false);
		cmbElem_3.setVisible(false);
		chkElem_3.setVisible(false);
		lblElem_3.setVisible(false);
	}
}
