package controler;

import java.util.LinkedList;

public class CmdList {

	private LinkedList<Command> liste;
	private int curIndex;

	public CmdList() {
		curIndex = -1;
		liste = new LinkedList<Command>();
	}

	public void add(Command cmd) {
		int i = curIndex + 1;
		while (i < liste.size()) {
			liste.remove(i);
		}
		curIndex++;
		liste.add(curIndex, cmd);
		cmd.doCmd();
	}

	public void undo() {
		if (curIndex >= 0) {
			Command cmd = liste.get(curIndex);
			curIndex--;
			cmd.undoCmd();
		}
	}

	public void cancel() {
		if (curIndex >= 0) {
			Command cmd = liste.get(curIndex);
			liste.remove(curIndex);
			curIndex--;
			cmd.undoCmd();
		}
	}

	public void redo() {
		if (curIndex < liste.size() - 1) {
			curIndex++;
			Command cmd = liste.get(curIndex);
			cmd.doCmd();
		}
	}

	public void reset() {
		curIndex = -1;
		liste.clear();
	}

}
