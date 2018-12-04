package controler;

import java.util.LinkedList;

/**
 * List of user commands.
 * @author PLD-HEXA-301
 *
 */
/**
 * @author PLD-HEXA-301
 *
 */
public class CmdList {

	
	private LinkedList<Command> liste;
	private int curIndex;

	/**
	 * Constructor 
	 */
	public CmdList() {
		curIndex = -1;
		liste = new LinkedList<Command>();
	}

	
	/**
	 * Adds the command to the list then does the command.
	 * @param cmd
	 */
	public void add(Command cmd) {
		int i = curIndex + 1;
		while (i < liste.size()) {
			liste.remove(i);
		}
		curIndex++;
		liste.add(curIndex, cmd);
		cmd.doCmd();
	}

	/**
	 * Undoes the command.
	 */
	public void undo() {
		if (curIndex >= 0) {
			Command cmd = liste.get(curIndex);
			curIndex--;
			cmd.undoCmd();
		}
	}
	
	
	/**
	 * Cancels the last command. 
	 */
	public void cancel() {
		if (curIndex >= 0) {
			Command cmd = liste.get(curIndex);
			liste.remove(curIndex);
			curIndex--;
			cmd.undoCmd();
		}
	}

	
	/**
	 * Redoes the current command.
	 */
	public void redo() {
		if (curIndex < liste.size() - 1) {
			curIndex++;
			Command cmd = liste.get(curIndex);
			cmd.doCmd();
		}
	}
	
	
	/**
	 * Resets the command list.
	 */
	public void reset() {
		curIndex = -1;
		liste.clear();
	}

}
