package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Lists all files and directories in a given directory.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class LsShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = null;
		try {
			args = Util.splitArgumentsEscape(arguments);
		} catch (IllegalArgumentException e) {
			env.writeln("Invalid command argument.");
			return ShellStatus.CONTINUE;
		}

		if (args.length == 1) {

			File dir = new File(args[0]);
			if (dir.isDirectory()) {
				File[] directoryListing = dir.listFiles();
				Arrays.sort(directoryListing);

				for (File f : directoryListing) {
					try {
						printFile(f, env);
					} catch (IOException e) {
						env.writeln("I/O error occured.");
						return ShellStatus.CONTINUE;
					}
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
	 * Prints a name of a file or a directory and its properties.
	 * 
	 * @param f File to be printed
	 * @param env used to print to standard output
	 * @throws IOException if reading attributes fails
	 */
	private void printFile(File f, Environment env) throws IOException {
		if (f.isDirectory()) {
			env.write("d");
		} else {
			env.write("-");
		}
		if (f.canRead()) {
			env.write("r");
		} else {
			env.write("-");
		}
		if (f.canWrite()) {
			env.write("w");
		} else {
			env.write("-");
		}
		if (f.canExecute()) {
			env.write("e ");
		} else {
			env.write("- ");
		}

		env.write(String.format("%10d ", f.length()));

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Path path = f.toPath();
		BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class,
				LinkOption.NOFOLLOW_LINKS);
		BasicFileAttributes attributes = faView.readAttributes();
		FileTime fileTime = attributes.creationTime();
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
		env.writeln(formattedDateTime + " " + f.getName());
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		list.add("Lists all files and directories in a given directory.");

		return Collections.unmodifiableList(list);
	}

}
