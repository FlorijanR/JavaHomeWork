package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * Interface used for modeling a shell command.
 * 
 * @author Florijan Rusac
 * @version 1.0
 */
public interface ShellCommand {
	/**
	 * Executes a command.
	 * 
	 * @param env in which the command is executed in
	 * @param arguments of the command
	 * @return CONTINUE or TERMINATE depending on the outcome 
	 * 		of the command execution
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * @return command name
	 */
	String getCommandName();

	/**
	 * @return a list of string with description of this command
	 */
	List<String> getCommandDescription();

}
