package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
 * Expects two arguments, a file path and a directory
 * or a file path. Copies the first file to the second
 * file or directory.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public class CopyShellCommand implements ShellCommand {
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = null;
		try {
			args = Util.splitArgumentsEscape(arguments);
		} catch (IllegalArgumentException e) {
			env.writeln("Invalid command argument.");
			return ShellStatus.CONTINUE;
		}

		Path p1;
		Path p2;
		if (args.length == 2) {
			try {
				p1 = Paths.get(args[0]);
				p2 = Paths.get(args[1]);
			} catch (InvalidPathException e) {
				env.writeln("Invalid command argument.");
				return ShellStatus.CONTINUE;
			}

			if (p1.toFile().isFile()) {

				if (p2.toFile().isDirectory()) {
					p2 = Paths.get(p2.toString() + "/" + p1.toFile().getName());
				}

				if (p1.toString().equals(p2.toString())) {
					env.writeln("Given paths are equal.");
					return ShellStatus.CONTINUE;
				}

				boolean overwrite = false;
				String line;
				if (p2.toFile().exists()) {
					do {
						env.write("File already exists, do you want to overwrite it? (y/n) > ");
						line = env.readLine();
						if (line.equals("y") || line.equals("n")) {
							if (line.equals("y"))
								overwrite = true;
							break;
						}
					} while (true);
				}

				if (overwrite || !p2.toFile().exists()) {
					try (InputStream is = Files.newInputStream(p1); OutputStream os = Files.newOutputStream(p2)) {
						byte[] buff = new byte[4096];
						while (true) {
							int r = is.read(buff);
							if (r < 1) {
								break;
							}
							os.write(buff, 0, r); // obradi samo buff[0] do buff[r-1]
						}
					} catch (IOException ex) {
						env.writeln("I/O error occured.");
						return ShellStatus.CONTINUE;
					}
				} else {
					env.writeln("File was not copied.");
				}
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
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		list.add("Expects two arguments, a file path and a directory");
		list.add("or a file path. Copies the first file to the second");
		list.add("file or directory.");
		
		return Collections.unmodifiableList(list);
	}

}
