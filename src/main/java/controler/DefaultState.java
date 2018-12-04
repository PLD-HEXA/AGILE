package controler;

import java.io.File;
import javax.swing.JFileChooser;
import entities.Map;
import entities.Reseau;
import view.MainWindow;


/**
 * The default state of the window.
 * @author PLD-HEXA-301
 *
 */
public class DefaultState implements State {

	@Override
	public void loadPlan(Controler controler, MainWindow mainWindow) {
		mainWindow.displayMessage("Load plan");
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("/"));
		chooser.changeToParentDirectory();
		mainWindow.add(chooser);
		int returnValue = chooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = chooser.getSelectedFile();
			// Ici rajouter l'appel a la methode qui traite l'xml
			Reseau reseau = controler.getParser().parseCityPlan(selectedFile.toString());
                        if (reseau != null) {
                            Map map = new Map();
                            map.fillMapIdAndCoordinate(reseau);
                            if (map.getMapId() != null && map.getCoordinates() != null) {
                                map.fillGraph(reseau);
                                if (map.getGraph() == null) {
                                    mainWindow.getTextualView().setItineraries(null);
                                    mainWindow.getTextualView().repaint();
                                    mainWindow.getGraphicalView().setItineraries(null);
                                    mainWindow.getGraphicalView().setMap(map);
                                    mainWindow.getGraphicalView().repaint();
                                    controler.setCurState(controler.planState);
                                }
                                else {
                                    // TODO : Prendre en compte le contenu du xml est incorrect
                                    // Ici, due à un tronçon dont l'un des attributs à une valeur incorrecte
                                }
                            }
                            else {
                                // TODO : Prendre en compte le cas ou le plan
                                // n'est pas valide (Ici, car un attribut est incorrect ou
                                // bien aucune infos n'est valable)
                            }
                        } 
                        else {
                            // TODO : Prendre en compte le cas ou le plan n'est pas valide (Ici, cas
                            // ou l'extension est incorrecte, une balise non connue est ajoutée,
                            // un attribut est rajoutée)
                        }
		}
	}

	@Override
	public void loadDeliveries(Controler controler, MainWindow mainWindow) {
	}

	@Override
	public void compute(Controler controler, MainWindow mainWindow) {

	}

}
