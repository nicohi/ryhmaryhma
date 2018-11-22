package vinkkeri.ui.commands;

import vinkkeri.ui.tui.Textui;

/**
 * Interface for commands.
 */
public interface Command {

	/**
	 * Runs command with arguments in an array of strings
	 * A command can access IO and Controller classes through the Textui class.
	 * @param ui
	 * @param args
	 */
	void run(Textui ui, String ... args);

	/**
	 * Returns the help string for the command explaining what it does
	 * @return
	 */
	String help();
}
