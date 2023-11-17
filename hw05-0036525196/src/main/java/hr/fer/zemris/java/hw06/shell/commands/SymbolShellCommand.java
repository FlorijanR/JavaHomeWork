package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Displays current symbol values and allows
 * changing of symbol values.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class SymbolShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = Util.splitArguments(arguments);
		
		if (args.length == 1) {
			if (args[0].equals("PROMPT")) {
				env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'");
			} else if (args[0].equals("MULTILINE")) {
				env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol() + "'");
			} else if (args[0].equals("MORELINES")) {
				env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol() + "'");
			} else {
				env.writeln("Invalid command argument.");
			}
		} else if (args.length == 2) {
			if (args[1].length() != 1) {
				env.writeln("Invalid command argument.");
				return ShellStatus.CONTINUE;
			}

			if (args[0].equals("PROMPT")) {
				Character saved = env.getPromptSymbol();
				Character newSymbol = args[1].charAt(0);
				env.setPromptSymbol(newSymbol);
				env.writeln("Symbol for PROMPT changed from '" + saved + "' to '" + env.getPromptSymbol() + "'");
			} else if (args[0].equals("MULTILINE")) {
				Character saved = env.getMultilineSymbol();
				Character newSymbol = args[1].charAt(0);
				env.setMultilineSymbol(newSymbol);
				env.writeln("Symbol for MULTILINE changed from '" + saved + "' to '" + env.getMultilineSymbol() + "'");
			} else if (args[0].equals("MORELINES")) {
				Character saved = env.getMorelinesSymbol();
				Character newSymbol = args[1].charAt(0);
				env.setMorelinesSymbol(newSymbol);
				env.writeln("Symbol for MORELINES changed from '" + saved + "' to '" + env.getMorelinesSymbol() + "'");
			} else {
				env.writeln("Invalid command argument.");
			}
		} else {
			env.writeln("Invalid command argument.");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		list.add("Displays current symbol values and allows");
		list.add("changing of symbol values.");

		return Collections.unmodifiableList(list);
	}

}










