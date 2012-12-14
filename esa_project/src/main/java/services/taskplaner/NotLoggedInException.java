package services.taskplaner;

public class NotLoggedInException extends Exception {
	
	@Override
	public String getMessage() {
		return "User is not logged in";
	}

}
