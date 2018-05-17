package GUI;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame{
	
	public static JButton sales, output;
	
	public GUI() {
		super("Shop Assistant");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setLayout(new FlowLayout());
        
        String[] tempDataList = {"apple","orange","fruit"};
        
        JPanel p1 = new JPanel();
        p1.add(new JLabel("Stock"));
        JList<String> list = new JList<String>(tempDataList);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(-1);
        p1.add(new JScrollPane(list));
                
        JPanel p2 = new JPanel();
        p2.add(new JLabel("Create Manifest"));
        output = new JButton("Create Manifest");
        output.addMouseListener(new ButtonListener());
        p2.add(output);
        
        JPanel p3 = new JPanel();
        p3.add(new JLabel("Import Sales"));
        sales = new JButton("Import Sales");
        sales.addMouseListener(new ButtonListener());
        p3.add(sales);
        
        JTabbedPane tp = new JTabbedPane();
        tp.setBounds(150, 200, 500, 500);
        tp.add("Stock", p1);
        tp.add("Manifest", p2);
        tp.add("Sales", p3);
        getContentPane().add(tp);
         
        setPreferredSize(new Dimension(500, 500));
        setLocation(new Point(200, 200));
        pack(); 
        setVisible(true); 
	}
}
