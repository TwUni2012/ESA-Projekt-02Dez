package webfrontend.exceptions;

public class ParsingException extends Exception {
	
	private String line;

	public ParsingException(String line) {
		this.line=line;
	}

	@Override
	public String getMessage() {
		return line;
	}
}
