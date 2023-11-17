package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Takes a single argument: directory name, and
 * creates the appropriate directory structure.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class MkdirShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = null;
		try {
			args = Util.splitArgumentsEscape(arguments);
		} catch (IllegalArgumentException e) {
			env.writeln("Invalid command argument.");
			return ShellStatus.CONTINUE;
		}

		Path p;
		if (args.length == 1) {
			try {
				p = Paths.get(args[0]);
			} catch (InvalidPathException e) {
				env.writeln("Invalid command argument.");
				return ShellStatus.CONTINUE;
			}

			p.toFile().mkdirs();
		}  else {
			env.writeln("Invalid command argument.");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		list.add("Takes a single argument: directory name, and");
		list.add("creates the appropriate directory structure.");
		
		return Collections.unmodifiableList(list);
	}

}
