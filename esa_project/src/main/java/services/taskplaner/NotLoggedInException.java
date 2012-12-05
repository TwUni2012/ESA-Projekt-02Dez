package services.taskplaner;

public class NotLoggedInException extends Exception {
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "User is not logged in";
	}
}
