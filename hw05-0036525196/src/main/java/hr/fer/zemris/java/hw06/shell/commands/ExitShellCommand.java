package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Used to terminate MyShell.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class ExitShellCommand implements ShellCommand {
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = Util.splitArguments(arguments);
		if (args.length != 0) {
			env.writeln("Invalid command argument.");
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return "exit";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		list.add("Used to terminate MyShell.");
		
		return Collections.unmodifiableList(list);
	}

}
