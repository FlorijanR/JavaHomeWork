package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
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
 * This command opens given file and writes
 * its content to console. The first argument
 * is path. The second argument is a charset name
 * that should be used to interpret chars from
 * bytes. If it is not provided, a default
 * platform charset is used.
 *
 * @author Florijan Rusac
 * @version 1.0
 */
public class CatShellCommand implements ShellCommand {

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

			if (p.toFile().exists() && p.toFile().isFile()) {
				try {
					readAndPrint(p, Charset.defaultCharset(), env);
				} catch (IOException e) {
					env.writeln("I/O error occured.");
					return ShellStatus.CONTINUE;
				}
			} else {
				env.writeln("Invalid command argument.");
			}
		} else if (args.length == 2) {
			try {
				p = Paths.get(args[0]);
			} catch (InvalidPathException e) {
				env.writeln("Invalid command argument.");
				return ShellStatus.CONTINUE;
			}

			Charset c;
			try {
				c = Charset.forName(args[1]);
			} catch (IllegalCharsetNameException | UnsupportedCharsetException e) {
				env.writeln("Invalid command argument.");
				return ShellStatus.CONTINUE;
			}

			if (p.toFile().exists() && p.toFile().isFile()) {
				try {
					readAndPrint(p, c, env);
				} catch (IOException e) {
					env.writeln("I/O error occured.");
					return ShellStatus.CONTINUE;
				}
			} else {
				env.writeln("Invalid command argument.");
			}
		} else {
			env.writeln("Invalid command argument.");
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Reads and prints a passed path, with passed charset.
	 * 
	 * @param p path to be read and printed
	 * @param c charset to be used
	 * @param env to be used for writing to standard output
	 * @throws IOException if reading fails
	 */
	private void readAndPrint(Path p, Charset c, Environment env) throws IOException {
		BufferedReader br = Files.newBufferedReader(p, c);
		String line;
		do {
			line = br.readLine();
			if (line != null) {
				env.writeln(line);
			}
		} while (line != null);
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		list.add("This command opens given file and writes");
		list.add("its content to console. The first argument");
		list.add("is path. The second argument is a charset name");
		list.add("that should be used to interpret chars from");
		list.add("bytes. If it is not provided, a default");
		list.add("platform charset is used.");
				
		return Collections.unmodifiableList(list);
	}

}
