package grovpackage;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manager between player and some elements of GUI pattern observer
 * It is a Singleton object
 * @author Elf
 *
 */
public class PlayerGUIManager extends Observable implements Observer {
	
	private static PlayerGUIManager playerGUIManager = new PlayerGUIManager();
	
	private PlayerGUIManager() {
		super();
	}
	
	public static PlayerGUIManager getInstance(){
		return playerGUIManager;
	}
	
	@Override
	public void update(Observable o, Object source) {
		//Player state change so we must notify observers
		if(source instanceof GroooovPlayer){
			setChanged();
			notifyObservers(source);
			return;
		}
		Logger.getGlobal().log(Level.SEVERE, "Error while managing player with GUI elements");
	}
}
