package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * If no arguments are given, it lists names of all
 * supported commands. If started with single argument,
 * it prints a name and a description of given command.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class HelpShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = Util.splitArguments(arguments);
		SortedMap<String, ShellCommand> commands = env.commands();

		if (args.length == 0) {
			for (String charset : commands.keySet()) {
				System.out.println(charset);
			}
		} else if (args.length == 1) {
			if (commands.get(args[0]) == null) {
				env.writeln("Invalid command argument.");
			} else {
				List<String> desc = commands.get(args[0]).getCommandDescription();
				int len = commands.get(args[0]).getCommandName().length();

				env.write(commands.get(args[0]).getCommandName());

				boolean first = true;
				for (var line : desc) {
					if (!first) {
						for (int i = 0; i < len; i++) {
							env.write(" ");
						}
					}
					if (first) first = false;

					env.writeln("     " + line);
				}
			}
		} else {
			env.writeln("Invalid command argument.");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "help";
	}
	
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		list.add("If no arguments are given, it lists names of all");
		list.add("supported commands. If started with single argument,");
		list.add("it prints a name and a description of given command.");
		
		return Collections.unmodifiableList(list);
	}

}
