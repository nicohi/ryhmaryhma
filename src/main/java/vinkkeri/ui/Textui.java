package vinkkeri.ui;

import vinkkeri.ui.commands.Command;
import vinkkeri.ui.commands.TextUICommands;

import java.util.Scanner;
import java.util.Map;
import java.util.TreeMap;

//TODO extract interface from this class 
public class Textui {

    private IO io;
    private Controller controller;
	private static boolean running;

	/*
	 * This map associates words to commands.
	 * To add a new command, add a new class implementing the interface Command to TextUICommands.
	 * Then put a new instance of that class into this Map.
	 * The key determines what the user must type to access that command.
	 */
    private static Map<String, Command> commands;
    static
    {
        commands = new TreeMap<String, Command>();
        commands.put("help", new TextUICommands.Help());
        commands.put("list", new TextUICommands.ListTips());
        commands.put("new", new TextUICommands.NewTip());
        commands.put("quit", new TextUICommands.Quit());
    }

	/**
	 * 
	 * @param controller
	 */
	public Textui(Controller controller, IO io) {
        this.io = io;
        this.controller = controller;
    }

	/**
	 * Starts the ui loop.
	 * Commands are read from io
	 */
	public void run() {
		this.running = true;
		io.printLine("Welcome to vinkkeri.\n Enter \"help\" to view help.\n");
        while (running) {
			io.print("> ");
			//Read command from user. Split it by whitespace
			String[] command = this.io.readLine().split(" ");
			if (command.length > 0) {
				io.printLine("");
				//Run the command specified by the user.
				//If that command is not found in Map 'commands' then run command Unknown(). 
				commands.getOrDefault(command[0], new TextUICommands.Unknown()).run(this, command);
				io.printLine("");
			} 
        }
    }

	/**
	 * End the loop started by run().
	 */
	public void quit() {
		this.running = false;
	}

	/**
	 *
	 * @return 
	 */
	public IO getIo() {
		return io;
	}

	/**
	 *
	 * @return
	 */
	public Controller getController() {
		return controller;
	}

	/**
	 *
	 * @return
	 */
	public static Map<String, Command> getCommands() {
		return commands;
	}

}
