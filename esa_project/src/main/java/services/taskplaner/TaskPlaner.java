package services.taskplaner;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface TaskPlaner {
	public boolean login(String username);
	public boolean logout();
	public Task addNewTask(String text, int year, int month, int day, int hour, int minute) throws NotLoggedInException;
	public List<Task> getAllTasks();
	public List<Task> getTasksFromUser() throws NotLoggedInException;
	public List<Task> getTasksForToday() throws NotLoggedInException;
}
