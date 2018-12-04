package controler;

/**
 * Represents a user command.
 * @author PLD-HEXA-301
 *
 */
public interface Command {
	
	/**
	 * Does the command.
	 */
	void doCmd();
	/**
	 * Undoes the command.
	 */
	void undoCmd();

}
