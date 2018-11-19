package vinkkeri.ui.commands;

import java.util.ArrayList;
import java.util.List;
import vinkkeri.objects.Tip;
import vinkkeri.ui.IO;
import vinkkeri.ui.Textui;

public class TextUICommands {

	public static class Unknown implements Command {

		@Override
		public void run(Textui ui, String ... args) {
			ui.getIo().printLine("Unknown command " + args[0]);
		}

		@Override
		public String help() {
			return "unknown - unknown command";
		}

	}

	public static class Quit implements Command {

		@Override
		public void run(Textui ui, String ... args) {
			ui.getIo().printLine("Quitting vinkkeri...");
			ui.quit();
		}

		@Override
		public String help() {
			return "quit - exits application";
		}

	}

	public static class ListTips implements Command {

		@Override
		public void run(Textui ui, String ... args) {
			ui.getController().getTips().stream().forEach(tip ->
					ui.getIo().printLine(tip.toString()+"\n"));
		}

		@Override
		public String help() {
			return "list - lists tips";
		}

	}

	public static class Help implements Command {

		@Override
		public void run(Textui ui, String ... args) {
			ui.getCommands().values().stream().forEach(command -> 
					ui.getIo().printLine(command.help()));
		}

		@Override
		public String help() {
			return "help - view help";
		}

	}

	public static class NewTip implements Command {

		@Override
		public void run(Textui ui, String ... args) {
			IO io = ui.getIo();
			io.print("Title: ");
			String title = io.readLine();
			io.print("Author: ");
			String author = io.readLine();
			io.print("Comment: ");
			String summary = io.readLine();
			io.print("ISBN (Can be empty):");
			String isbn = io.readLine();
			String type = "book";
			String url = "";
			boolean read = false;
			Tip tip = new Tip(type, title, author, summary, isbn, url, read);
			ui.getController().newTip(tip);
		}

		@Override
		public String help() {
			return "new  - add a new tip";
		}

	}

}
