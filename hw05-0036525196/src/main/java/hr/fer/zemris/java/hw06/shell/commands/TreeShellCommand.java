package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Expects a single argument: directory name
 * and prints a tree starting from that directory.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class TreeShellCommand implements ShellCommand{
	/**
	 * Used to visit all files/directories in a 
	 * directory and print them as a tree.
	 * 
	 * @author Florijan Rusac
	 * @version 1.0
	 */
	private static class MyVisitor extends SimpleFileVisitor<Path> {
		/**
		 * Environment used for writing to standard output.
		 */
		private Environment env;
		/**
		 * Current depth of tree.
		 */
		private int level = 0;
		
		/**
		 * Constructor that initializes Environment.
		 * 
		 * @param env to initialize Environment.
		 */
		public MyVisitor(Environment env) {
			this.env = env;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			level -= 2;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			printFile(dir.toFile().getName());
			level += 2;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			printFile(file.toFile().getName());
			return FileVisitResult.CONTINUE;
		}

		/**
		 * Prints current depth with spaces and a name of
		 * a directory or file.
		 * 
		 * @param name of file or directory to be printed
		 */
		private void printFile(String name) {
			for (int i = 0; i < level; i++) {
				env.write(" ");
			}
			env.writeln(name);
		}
	}

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

			if (p.toFile().exists() && p.toFile().isDirectory()) {
				
				try {
					Files.walkFileTree(p, new MyVisitor(env));
				} catch (IOException e) {
					env.writeln("I/O error occured.");
					return ShellStatus.CONTINUE;
				}
				
			} else {
				env.writeln("Invalid command argument.");
			}
		}  else {
			env.writeln("Invalid command argument.");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		list.add("Expects a single argument: directory name");
		list.add("and prints a tree starting from that directory.");
		
		return Collections.unmodifiableList(list);
	}

}
