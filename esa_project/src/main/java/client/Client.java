package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Vector;

import javax.naming.InitialContext;

import services.taskplaner.Task;
import services.taskplaner.TaskPlaner;
import webfrontend.restful_newsticker.Article;
import webfrontend.restful_newsticker.ArticleFactory;


public class Client {

	public static void main(final String[] args) {
				runTaskPlaner();
//		runRESTfulNewsTicker();
	}

	private static void runRESTfulNewsTicker() {
		final String serviceURL = "http://content.guardianapis.com/world";
		final String applicationURL = "?format=xml&order-by=newest&date-id=date%2Ftoday";
		try {
			//connect to the api
			final HttpURLConnection connection = (HttpURLConnection) (new URL(serviceURL+applicationURL)).openConnection();
			connection.setRequestMethod("GET");
			connection.connect();

			// read the response from the restful service
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String response=reader.readLine();

			//generate list of articles from the xml-response
			Vector<Article> articles = ArticleFactory.analyzeResponse(response);

			System.out.println("Result of request " + serviceURL + applicationURL + "\n");
			
			for(Article article : articles)
			{
				System.out.println(article + "\n");
			}

		} catch (UnknownHostException uHE) {
			System.err.println("Couldn't establish connection to " + serviceURL);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void runTaskPlaner() {
		try {
			final InitialContext context = new InitialContext();
			final TaskPlaner taskplaner = (TaskPlaner) context.lookup("java:global/services/TaskPlaner");

			List<Task> tasks = taskplaner.getAllTasks();
			if(tasks!=null) {
				System.out.println("\nAll Tasks (" + tasks.size() + "):");
				for(Task t:tasks) {
					System.out.println(t);
				}
			}

			String user = "user2";
			if(taskplaner.login(user)) {
				System.out.println("\nLogged in as '" + user + "'");
				tasks = taskplaner.getTasksFromUser();
				if(tasks != null) {
					System.out.println("\nAll Tasks (" + tasks.size() + "):");
					for(Task t : tasks) {
						System.out.println(t);
					}
				}

				tasks = taskplaner.getTasksForToday();
				if(tasks != null) {
					System.out.println("\nTasks for today (" + tasks.size() + "):");
					for(Task t : tasks) {
						System.out.println(t);
					}
				}
			}

			if(taskplaner.logout())
				System.out.println("\n'" + user + "' logged out.");

			user = "user1";
			if(taskplaner.login(user)) {
				System.out.println("\nLogged in as '" + user + "'");
				tasks=taskplaner.getTasksFromUser();
				if(tasks != null) {
					System.out.println("All Tasks (" + tasks.size() + "):");
					for(Task t : tasks) {
						System.out.println(t);
					}
				}
			}

			user = "user3";
			
			if(taskplaner.login(user)) {
				System.out.println("\nLogged in as '" + user + "'");
				tasks = taskplaner.getTasksFromUser();
				if(tasks != null) {
					System.out.println("All Tasks (" + tasks.size() + "):");
					for(Task t : tasks) {
						System.out.println(t);
					}
				}
			} else {
				System.err.println("\n'" + user + "' couldn't log in.");
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
