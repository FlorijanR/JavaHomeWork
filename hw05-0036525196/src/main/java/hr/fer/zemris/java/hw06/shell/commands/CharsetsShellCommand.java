package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Takes no arguments and lists names of supported charsets.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class CharsetsShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = Util.splitArguments(arguments);
		
		if (args.length > 0) {
			env.writeln("Invalid command argument.");
		} else {
			SortedMap<String, Charset> map = Charset.availableCharsets();
			for (String charset : map.keySet()) {
				System.out.println(charset);
			}
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		list.add("Takes no arguments and lists names of supported charsets.");

		return Collections.unmodifiableList(list);
	}

}
