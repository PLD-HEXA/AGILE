package controler;

import java.io.File;

import javax.swing.JFileChooser;

import view.MainWindow;

public class PlanState extends DefaultState {
	
	@Override
	public void loadDeliveries(Controler controler, MainWindow mainWindow) {
		mainWindow.displayMessage("Load deliveries");
		mainWindow.displayMessage("Load plan");
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("/"));
		chooser.changeToParentDirectory();
		mainWindow.add(chooser);
		int returnValue = chooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = chooser.getSelectedFile();
			controler.setCurState(controler.planState);
			System.out.println(selectedFile.getAbsolutePath());
		}
		controler.setCurState(controler.deliveriesState);
	}


}
