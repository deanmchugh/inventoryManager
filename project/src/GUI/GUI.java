package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import exceptions.CSVFormatException;
import exceptions.DeliveryException;
import exceptions.StockException;
import objects.Item;

/**
 * Class to implement Gui 
 * @author Dean McHugh
 *
 */
@SuppressWarnings("serial")
public class GUI extends JFrame implements ActionListener, Runnable{
	
	public static final int WIDTH = 500;
	public static final int HEIGHT = 500;
	
	private JTable table;
	private DefaultTableModel tm;
	private JScrollPane sPane;
	private JTabbedPane tPane;
	private JPanel pnlDisplay, pnlItems;
	private JTextArea messages;
	private JButton btnCapital, btnProperties, btnManifestIn, btnManifestOut, btnSalesIn; 
	private JLabel labStock, labCapital, labProperties, labManifestIn, labManifestOut, labSalesIn;
	
	public GUI(String arg0) {
		super(arg0);
	}
	
	/**
	 * Method to implement GUI JTable, JScrollPane, JTabbedPane, JPanel, JTextArea
	 * JButton and JLabel.
	 * @throws StockException if stock is below 0.
	 */
	private void createGUI() throws StockException {
		setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setLayout(new BorderLayout());
        pnlDisplay = createPanel(Color.white);
        pnlItems = createPanel(Color.white);
        
        btnCapital = createButton("Capital");
	    btnProperties = createButton("Properties");
	    btnManifestIn = createButton("ManifestIn");
	    btnManifestOut = createButton("ManifestOut");
	    btnSalesIn = createButton("Sales");
	    
	    labCapital = createLabel("Show Store Capital");
	    labProperties = createLabel("Import Item Properties");
	    labManifestIn = createLabel("Import Current Manifest");
	    labManifestOut = createLabel("Export Current Manifest");
	    labSalesIn = createLabel("Import Sales Log");
	    labStock = createLabel("Current Items in Stock");
	    
	    tPane = createTabbedPane();
	    
	    messages = createTextArea(500,50);
	    
	    table = createTable();
	    
	    sPane = createScrollPane(table);
	    
	    tPane.add("Operations", pnlDisplay);
	    tPane.add("Stock", pnlItems);
	    
	    pnlItems.add(labStock, BorderLayout.NORTH);
	    pnlItems.add(sPane, BorderLayout.SOUTH);
	    
	    pnlDisplay.setLayout(new BorderLayout());
	    
	    layoutButtonPanelAndLabel();
	    this.getContentPane().add(tPane, BorderLayout.CENTER);

	    btnManifestIn.setEnabled(false);
	    btnManifestOut.setEnabled(false);
	    btnSalesIn.setEnabled(false);
	    
	    repaint();
        setVisible(true); 
	}
	
	/**
	 * Method to create a colored JPanel.
	 * @param c, the color you want the panel to be.
	 * @return a JPanel of chosen color.
	 */
	private JPanel createPanel(Color c) {
		JPanel jp = new JPanel();
		jp.setBackground(c);
		return jp;
	}
	
	/**
	 * Method to create a JButton.
	 * @param str, the text you want on the button.
	 * @return a button with the chosen text.
	 */
	private JButton createButton(String str) {
		JButton jb = new JButton(str); 
		jb.addActionListener(this);
		return jb; 
	}
	
	/**
	 * Method to create a JTable and fill it with the store item information.
	 * @return a table of item information.
	 * @throws StockException if stock is below 0.
	 */
	private JTable createTable() throws StockException {
		double temp;
		String[] columnNames = {"Quantity", "Name", "Cost", "Sell Price", "Reorder Point",
				"Reorder Amount", "Temperature"};
		tm = new DefaultTableModel(columnNames, 0);
		table = new JTable(tm);
		for(Item item : EntryPoint.shopFront.getInventory()) {
			int quantity = EntryPoint.shopFront.getInventory().getQuantity(item);
			String name = item.getName();
			double cost = item.getCost();
			double sell = item.getSellPrice();
			int reorderP = item.getReorderPoint();
			int reorderA = item.getReorderAmount();
			if(item.isTempControlled()) {
				temp = item.getTemp();
			} else {
				temp = 0;
			}
			Object[] data = {new Integer(quantity), name, new Double(cost), new Double(sell), new Integer(reorderP), new Integer(reorderA), new Double(temp)};
			tm.addRow(data);
		}
		table.setPreferredScrollableViewportSize(new Dimension(400,200));
		table.setFillsViewportHeight(true);
		return table;
	}
	
	/**
	 * Method to create a JScrollPane.
	 * @param jt, the table that you want to be placed in the scroll pane.
	 * @return the JScrollPane with a table of content.
	 */
	private JScrollPane createScrollPane(JTable jt) {
		JScrollPane sp = new JScrollPane(jt);
		return sp;
	}
	
	/**
	 * Method to create a JTabbedPane.
	 * @return a tabbed pane.
	 */
	private JTabbedPane createTabbedPane() {
		JTabbedPane jtp = new JTabbedPane();
		return jtp;
	}
	
	/**
	 * Method to create a text area of set width and height.
	 * @param width, how wide the text area should be.
	 * @param height, how high the text area should be.
	 * @return a text area of chosen width and height.
	 */
	private JTextArea createTextArea(int width, int height) {
		JTextArea jta = new JTextArea(); 
		jta.setEditable(false);
		jta.setLineWrap(false);
		jta.setFont(new Font("Arial",Font.BOLD,20));
		jta.setBorder(BorderFactory.createEtchedBorder());
		return jta;
	}
	
	/**
	 * Method to create a JLabel.
	 * @param str, the text to appear in the label.
	 * @return a label with the chosen text inside.
	 */
	private JLabel createLabel(String str) {
		JLabel jl = new JLabel(str);
		return jl;
	}
	
	/**
	 * Method to place elements into a grid inside a panel.
	 */
	private void layoutButtonPanelAndLabel() {
		GridBagLayout layout = new GridBagLayout();
	    pnlDisplay.setLayout(layout);
	    
	    //add components to grid
	    GridBagConstraints constraints = new GridBagConstraints(); 
	    
	    //Defaults
	    constraints.fill = GridBagConstraints.NONE;
	    constraints.anchor = GridBagConstraints.CENTER;
	    constraints.weightx = 100;
	    constraints.weighty = 100;

	    addToPanel(pnlDisplay, labCapital,constraints,0,0,1,1);
	    addToPanel(pnlDisplay, btnCapital,constraints,1,0,1,1);
	    addToPanel(pnlDisplay, labProperties,constraints,0,1,1,1); 
	    addToPanel(pnlDisplay, btnProperties,constraints,1,1,1,1); 
	    addToPanel(pnlDisplay, labManifestIn,constraints,0,2,1,1); 
	    addToPanel(pnlDisplay, btnManifestIn,constraints,1,2,1,1);
	    addToPanel(pnlDisplay, labManifestOut,constraints,0,3,1,1); 
	    addToPanel(pnlDisplay, btnManifestOut,constraints,1,3,1,1);
	    addToPanel(pnlDisplay, labSalesIn,constraints,0,4,1,1); 
	    addToPanel(pnlDisplay, btnSalesIn,constraints,1,4,1,1);
	    addToPanel(pnlDisplay, messages,constraints, 0,5,5,5);
	}
	
	/**
	 * Method to place an element in a panel. 
	 * @param jp, the panel that you want the element to go into.
	 * @param c, the component to be put into the panel.
	 * @param constraints, gives the constraints to the elements.
	 * @param x, the x location inside the grid.
	 * @param y, the y location inside the grid.
	 * @param w, the width of the element grid.
	 * @param h, the height of the element grid.
	 */
	private void addToPanel(JPanel jp,Component c, GridBagConstraints constraints, int x, int y, int w, int h) {  
	      constraints.gridx = x;
	      constraints.gridy = y;
	      constraints.gridwidth = w;
	      constraints.gridheight = h;
	      jp.add(c, constraints);
	}
	
	/**
	 * Event listener for buttons being clicked and calls methods to perform the event.
	 */
	public void actionPerformed(ActionEvent e){
		Object src=e.getSource();  
		
		if (src == btnProperties) {
			try {
				EntryPoint.importItemProperties();
				EntryPoint.exportManifest();
				EntryPoint.importManifest();
			} catch (CSVFormatException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "CSV Exception", JOptionPane.PLAIN_MESSAGE);
			} catch (StockException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Stock Exception", JOptionPane.PLAIN_MESSAGE);
			} catch (DeliveryException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Delivery Exception", JOptionPane.PLAIN_MESSAGE);
			}
			//fileSelect("In");
			btnProperties.setText("Imported");
			btnProperties.setEnabled(false);
		    btnSalesIn.setEnabled(true);
			messages.setText("You have imported item Properties");
		} else if (src == btnManifestIn) {
			try {
				EntryPoint.importManifest();
			} catch (CSVFormatException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "CSV Exception", JOptionPane.PLAIN_MESSAGE);
			} catch (DeliveryException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Delivery Exception", JOptionPane.PLAIN_MESSAGE);
			} catch (StockException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Stock Exception", JOptionPane.PLAIN_MESSAGE);
			}
			//fileSelect("In");
			messages.setText("You have imported the manifest");
			btnManifestIn.setEnabled(false);
			btnSalesIn.setEnabled(true);
		} else if (src == btnManifestOut) {
			try {
				EntryPoint.exportManifest();
			} catch (DeliveryException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Delivery Exception", JOptionPane.PLAIN_MESSAGE);
			} catch (StockException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Stock Exception", JOptionPane.PLAIN_MESSAGE);
			} catch (CSVFormatException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "CSV Exception", JOptionPane.PLAIN_MESSAGE);
			}
			//fileSelect("Out");
			messages.setText("You have exported the manifest");
			btnManifestOut.setEnabled(false);
			btnManifestIn.setEnabled(true);
		} else if (src == btnSalesIn) {
			try {
				EntryPoint.importSales();
			} catch (StockException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Stock Exception", JOptionPane.PLAIN_MESSAGE);
			} catch (CSVFormatException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "CSV Exception", JOptionPane.PLAIN_MESSAGE);
				if (e1.getMessage().contains("not found!")) {
					return;
				}
			}
			//fileSelect("In");
			messages.setText("You have imported the sales");
			btnSalesIn.setEnabled(false);
			btnManifestOut.setEnabled(true);
		} else if (src == btnCapital) {
			messages.setText(String.format("Current Capital: $%.2f", EntryPoint.shopFront.getCapital()));
		}
		try {
			newTable();
		} catch (StockException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Method to update table content after change in store inventory.
	 * @throws StockException if stock below 0.
	 */
	public void newTable() throws StockException {
		for(int i = tm.getRowCount() - 1; i > -1; i--) {
			tm.removeRow(i);
		}
		double temp = 0;
		for(Item item : EntryPoint.shopFront.getInventory()) {
			int quantity = EntryPoint.shopFront.getInventory().getQuantity(item);
			String name = item.getName();
			double cost = item.getCost();
			double sell = item.getSellPrice();
			int reorderP = item.getReorderPoint();
			int reorderA = item.getReorderAmount();
			if(item.isTempControlled()) {
				temp = item.getTemp();
			} else {
				temp = 0;
			}
			Object[] data = {new Integer(quantity), name, new Double(cost), new Double(sell), new Integer(reorderP), new Integer(reorderA), new Double(temp)};
			tm.addRow(data);
		}
		table.setModel(tm);
	}
	
	/**
	 * Method to return the file path of required files. 
	 * @param str, string to tell which button is pressed.
	 * @return a string of the absolute file path of chosen file.
	 */
	public String fileSelect(String str) {
		String filePath = null;
		JButton open = new JButton();
		JButton save = new JButton();
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new java.io.File("."));
        fc.setDialogTitle("Select CVS File");
        if(str == "Out") {
        	fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        	if(fc.showSaveDialog(save) == JFileChooser.APPROVE_OPTION) {
            	filePath = fc.getCurrentDirectory().getAbsolutePath();
            }
        }else if (str == "In"){
        	fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        	if(fc.showOpenDialog(open) == JFileChooser.APPROVE_OPTION) {
            	filePath = fc.getSelectedFile().getAbsolutePath();
            }
        }else {
        	filePath = null;
        }
        return filePath;
    }

	@Override
	public void run() {
		try {
			createGUI();
		} catch (StockException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
}
