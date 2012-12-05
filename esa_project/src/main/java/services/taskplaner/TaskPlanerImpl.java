package services.taskplaner;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateful(name = "TaskPlaner")
public class TaskPlanerImpl implements TaskPlaner {

	private String username = null;

	@PersistenceContext
	EntityManager entitymanager;

	@Override
	public Task addNewTask(String text, int year, int month, int day, int hour,
			int minute) throws NotLoggedInException {
		if (!isLoggedIn())
			throw new NotLoggedInException();

		final Task t = new Task(username, text, year, month, day, hour, minute);
		entitymanager.persist(t);
		return t;
	}

	@Override
	public List<Task> getAllTasks() {
		final TypedQuery<Task> query = entitymanager.createQuery(
				"select t from Task as t", Task.class);
		return query.getResultList();
	}

	@Override
	public List<Task> getTasksFromUser() throws NotLoggedInException {
		if (!isLoggedIn())
			throw new NotLoggedInException();

		final TypedQuery<Task> query = entitymanager.createQuery(
				"select t from Task as t where t.username='" + username + "'",
				Task.class);
		return query.getResultList();
	}

	@Override
	public boolean login(String username) {
		if (isLoggedIn())
			return false;

		this.username = username;

		return true;
	}

	private boolean isLoggedIn() {
		return username != null;
	}

	@Override
	public boolean logout() {
		if (!isLoggedIn())
			return false;

		username = null;

		return true;
	}

	@Override
	public List<Task> getTasksForToday() throws NotLoggedInException {
		if (!isLoggedIn())
			throw new NotLoggedInException();

		int[] today = getTodayAsArray();
		final TypedQuery<Task> query = entitymanager.createQuery(
				"select t from Task as t where t.username='" + username
						+ "' and t.yyyy=" + today[0] + " and t.mm=" + today[1]
						+ " and t.dd=" + today[2], Task.class);
		return query.getResultList();
	}

	// TODO change date to calendar
	@SuppressWarnings("deprecation")
	private int[] getTodayAsArray() {
		int[] date = new int[5];
		Date today = Calendar.getInstance().getTime();
		date[0] = today.getYear() + 1900;
		date[1] = today.getMonth() + 1;
		date[2] = today.getDate();
		date[3] = today.getHours();
		date[4] = today.getMinutes();
		return date;
	}

}
