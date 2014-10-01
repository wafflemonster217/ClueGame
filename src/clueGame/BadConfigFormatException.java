package clueGame;

public class BadConfigFormatException extends Exception{
	String message;
	public BadConfigFormatException() {
		message = "Error in the formatting of a config file";
		logErr();
	}
	
	public BadConfigFormatException(String badFile) {
		this.message = "Error in the formatting of config file: " + badFile;
		logErr();
	}

	@Override
	public String toString() {
		return "BadConfigFormatException [" + message + "]";
	}
	
	public void logErr() {
		//Write error to logfile here
	}
	
}
