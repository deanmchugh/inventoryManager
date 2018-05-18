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
	
	public String fileSelect(MouseEvent e) {
		String filePath = null;
		Object source = e.getSource();
		JButton open = new JButton();
		JButton save = new JButton();
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new java.io.File("."));
        fc.setDialogTitle("Select CVS File");
        if(source == GUI.output) {
        	fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        	if(fc.showSaveDialog(save) == JFileChooser.APPROVE_OPTION) {
            	filePath = fc.getCurrentDirectory().getAbsolutePath();
            }
        }else if(source == GUI.sales) {
        	fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        	if(fc.showOpenDialog(open) == JFileChooser.APPROVE_OPTION) {
            	filePath = fc.getSelectedFile().getAbsolutePath();
            }
        }else {
        	filePath = null;
        }
        return filePath;
    }
}
