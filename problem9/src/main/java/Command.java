import java.io.Serializable;

/**
 * @author Dejan Kostadinovski
 *
 */
public class Command implements Serializable {

	private static final long serialVersionUID = 2334595196329179652L;
	
	private String commandName;
	private String[] commandParameters;
	
	
	public Command(String commandName, String[] commandParamters) {
		this.commandName = commandName;
		this.commandParameters = commandParamters;
	}
	
	public String getName() {
		return commandName;
	}
	
	public String[] getParamters() {
		return commandParameters;
	}

}