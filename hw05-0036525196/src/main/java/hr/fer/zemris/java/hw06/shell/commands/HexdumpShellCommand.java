package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Expects a single argument: file name, and produces
 * a hex-output.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class HexdumpShellCommand implements ShellCommand {
	/**
	 * List that contains last 16 or less read
	 * bytes in an integer format.
	 */
	List<Integer> list = new ArrayList<>();
	/**
	 * Counts the number of printed bytes.
	 */
	long numberOfPrinted = 0;

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

			if (p.toFile().isFile()) {

				list.clear();
				numberOfPrinted = 0;
				try (InputStream is = Files.newInputStream(p)) {
					byte[] buff = new byte[4096];
					while (true) {
						int r = is.read(buff);
						if (r < 1) {
							if (list.size() > 0) {
								printChars(env);
							}
							break;
						}
						printHex(buff, r, env);
					}
				} catch (IOException ex) {
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
	 * Prints the number of printed bytes and hex values of read
	 * bytes.
	 * 
	 * @param buff contains bytes which will be printed
	 * @param r number of new bytes in buff
	 * @param env used to write to standard output
	 */
	private void printHex(byte[] buff, int r, Environment env) {
		if (buff.length > 0) {
			for (int i = 0; i < r; i++) {
				if (list.size() == 0) {
					env.write(String.format("%08X: ", numberOfPrinted));
				}
				
				if (list.size() == 7) {
					env.write(String.format("%02X|", buff[i]));
					numberOfPrinted++;
					list.add((int) buff[i]);
				} else if (list.size() == 15) {
					env.write(String.format("%02X | ", buff[i]));
					numberOfPrinted++;
					list.add((int) buff[i]);
					printChars(env);
					list.clear();
				} else {
					env.write(String.format("%02X ", buff[i]));
					numberOfPrinted++;
					list.add((int) buff[i]);
				}
			}
		}
	}

	/**
	 * Adds padding if list contains less than 16
	 * integers and prints characters with values of those
	 * integers if they are between 32 and 123, otherwise
	 * prints a ".".
	 * 
	 * @param env used to write to standard output
	 */
	private void printChars(Environment env) {
		int blank = 0;
		if (list.size() < 8) {
			blank = 8 - list.size();
			for (int i = 0; i < blank; i++) {
				if (i == blank - 1) {
					env.write("  |");
				} else {
					env.write("   ");
				}
			}
		}
		if (list.size() < 16) {
			if (list.size() < 9) {
				blank = 8;
			} else {
				blank = 8 - (list.size() - 8);
			}
			for (int i = 0; i < blank; i++) {
				env.write("   ");
			}
			env.write("| ");
		}

		for (var item : list) {
			if (item < 32 || item > 123) {
				env.write(".");
			} else {
				env.write(String.format("%c", item));
			}
		}
		env.writeln("");
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		list.add("Expects a single argument: file name, and produces");
		list.add("a hex-output.");
		
		return Collections.unmodifiableList(list);
	}

}
