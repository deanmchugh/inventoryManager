package GUI;

import java.awt.event.*;
import javax.swing.*;

public class ButtonListener implements MouseListener{

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		fileSelect(e);
	}
	
	public void fileSelect(MouseEvent e) {
		Object source = e.getSource();
		JButton open = new JButton();
		JButton save = new JButton();
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new java.io.File("."));
        fc.setDialogTitle("Select CVS File");
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        if(source == GUI.output) {
        	if(fc.showSaveDialog(save) == JFileChooser.SAVE_DIALOG) {
            	//create cvs file for manifest 
            }
        }else if(source == GUI.sales) {
        	if(fc.showOpenDialog(open) == JFileChooser.APPROVE_OPTION) {
            	//read cvs file and create stock
            }
        }      
    }
}
