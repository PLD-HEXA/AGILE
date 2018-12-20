package controler;

import java.util.LinkedList;

/**
 * List of user commands.
 *
 * @author PLD-HEXA-301
 */
public class CmdList {
	
    /**
     * List of user commands.
     */
    private LinkedList<Command> list;
    /**
     * Index of the current user command.
     */
    private int curIndex;

    /**
     * Constructor
     */
    public CmdList() {
        curIndex = -1;
        list = new LinkedList<>();
    }

    /**
     * Adds the command to the list then does the command.
     *
     * @param cmd
     */
    public void add(Command cmd) {
        int i = curIndex + 1;
        while (i < list.size()) {
            list.remove(i);
        }
        curIndex++;
        list.add(curIndex, cmd);
        cmd.doCmd();
    }

    /**
     * Undoes the command.
     */
    public void undo() {
        if (curIndex >= 0) {
            Command cmd = list.get(curIndex);
            curIndex--;
            cmd.undoCmd();
        }
    }

    /**
     * Redoes the current command.
     */
    public void redo() {
        if (curIndex < list.size() - 1) {
            curIndex++;
            Command cmd = list.get(curIndex);
            cmd.doCmd();
        }
    }

    /**
     * Resets the command list.
     */
    public void reset() {
        curIndex = -1;
        list.clear();
    }
}
