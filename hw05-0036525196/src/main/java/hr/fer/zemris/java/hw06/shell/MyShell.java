package hr.fer.zemris.java.hw06.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeShellCommand;

/**
 * Emulates a shell and implements Environment.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class MyShell implements Environment {
	/**
	 * Multiline symbol.
	 */
	private Character MultilineSymbol = '|';
	/**
	 * Prompt symbol.
	 */
	private Character PromptSymbol = '>';
	/**
	 * Morelines symbol.
	 */
	private Character MorelinesSymbol = '\\';
	/**
	 * Map of supported commands.
	 */
	private SortedMap<String, ShellCommand> commands = new TreeMap<>();
	/**
	 * Used to read from standard input.
	 */
	private Scanner sc;

	/**
	 * Starts the shell and executes commands.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		Environment en = new MyShell();
		String commandName;
		ShellCommand command;
		String arguments;
		String input = null;
		ShellStatus status = ShellStatus.CONTINUE;

		try {
			en.writeln("Welcome to MyShell v 1.0");
		} catch (ShellIOException e) {
			System.out.println("ShellIOException, terminating...");
			System.exit(1);
		}

		do {
			try {
				en.write(String.format("%s ", en.getPromptSymbol()));
				input = en.readLine();
				while (input.endsWith(String.valueOf(en.getMorelinesSymbol()))) {
					en.write(String.format("%s ", en.getMultilineSymbol()));
					input = input.substring(0, input.length() - 1);
					input += en.readLine();
				}
			} catch (ShellIOException e) {
				System.out.println("ShellIOException, terminating...");
				System.exit(1);
			}

			commandName = input.trim().split(" ")[0];
			if (input.trim().length() > commandName.length() + 1) {
				arguments = input.trim().substring(commandName.length() + 1);
			} else {
				arguments = "";
			}
			command = null;
			command = en.commands().get(commandName);

			if (command == null) {
				try {
					en.writeln("Invalid command.");
				} catch (ShellIOException e) {
					System.out.println("ShellIOException, terminating...");
					System.exit(1);
				}
				continue;
			}

			try {
				status = command.executeCommand(en, arguments);
			} catch (ShellIOException e) {
				System.out.println("ShellIOException, terminating...");
				System.exit(1);
			}
		} while (status != ShellStatus.TERMINATE);

	}

	/**
	 * Constructor that fills commands map with supported commands
	 * and initializes a scanner.
	 */
	public MyShell() {
		fillMap();
		sc = new Scanner(System.in);
	}

	/**
	 * Fills commands map with supported commands.
	 */
	private void fillMap() {
		commands.put("exit", new ExitShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("help", new HelpShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
	}

	@Override
	public String readLine() throws ShellIOException {
		try {
			String line = sc.nextLine();
			return line;
		} catch (Exception e) {
			throw new ShellIOException();
		}
	}

	@Override
	public void write(String text) throws ShellIOException {
		try {
			System.out.printf(text);
		} catch (Exception e) {
			throw new ShellIOException();
		}
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		try {
			System.out.println(text);
		} catch (Exception e) {
			throw new ShellIOException();
		}
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(commands);
	}

	@Override
	public Character getMultilineSymbol() {
		return MultilineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		MultilineSymbol = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return PromptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		PromptSymbol = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return MorelinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		MorelinesSymbol = symbol;
	}

}
