/**
 * Hw2 - MyCalendar.java
 * @author kimpham
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


/**
 * Manages input/output of the program
 */
public class MyCalendar {
	private EventManager eventMan;
	private CalendarManager calMan;
	private String filename;
	
	/**
	 * Constructor
	 * @param filename filename to be written to when program quits and saves data
	 */
	public MyCalendar(String filename){
		eventMan = new EventManager();
		calMan = new CalendarManager();
		this.filename = filename;
	}
	
	/**
	 * Reads any event from file to program if file exists
	 */
	public void read()
	{
		try{
			File file = new File(filename);
			if(file.exists()){
			String line = "";
			Scanner in = new Scanner(file);
			while(in.hasNextLine()){
				line = in.nextLine();
				EventDate eventDate = this.createEventDate(line);
				eventMan.add(eventDate);
			}
			}
			else{
			System.out.println("This is the first run");
			}
		}catch(IOException ex){
			System.out.println("Error opening file");
		}
		
	}
	
	/**
	 * Saves events to file 
	 */
	public void save(){
		try{
			File file = new File(filename);
			FileWriter writer = new FileWriter(file.getAbsoluteFile());
			BufferedWriter buffer = new BufferedWriter(writer);
			String[] list = eventMan.eventDateList();
			for(int i = 0; i< list.length; i++){
				buffer.write(list[i]);
			}
			buffer.close();

			
		}catch(IOException ex){
			System.out.println("Error writing to file");
		}
	}
	
	/**
	 * Displays menu for user interface
	 */
	public void menu(){
		String choice = "";
		Scanner scanner = new Scanner(System.in);
		calMan.currentMonth(eventMan);
		do{
			System.out.printf("[Load] ");
			System.out.printf("[V]iew by ");
			System.out.printf("[C]reate ");
			System.out.printf("[G]oto ");
			System.out.printf("[E]vent List ");
			System.out.printf("[D]elete ");
			System.out.printf("[Q]uit\n");
			choice = scanner.nextLine().toLowerCase().trim();
			executeChoice(choice, scanner);
			
		}while(!choice.equals("q"));
		scanner.close();
	}
	
	/**
	 * Executes user choice
	 * @param choice choice entered by user
	 * @param in Scanner used to collect input
	 */
	public void executeChoice(String choice, Scanner in){
		switch(choice){
		case "l":	//load 
			read();
			break;
		case "v":	//view 
			System.out.println("[D]ay view or [M]onth view?");
			String viewChoice = in.nextLine();
			
			if(viewChoice.equalsIgnoreCase("d")) //view by day
				dayView(in);	//displays day view
			else
				monthView(in);	//display month view
			break;
		case "c":	//create
			createView(in);	//display view that handles creating new event
			break;
		case "g":	//go to 
			System.out.println("Enter date MM/DD/YYYY: ");
			String date = in.nextLine();
			String[] field = date.split("/");
			EventDate go_to = new EventDate(Integer.parseInt(field[2]),
					Integer.parseInt(field[0]), Integer.parseInt(field[1]));
			printAnEventDate(go_to);	//search for and print out go_to
			break;
		case "e":	//event list
			eventMan.printEventsList();
			break;
		case "d":	//delete
			System.out.println("[S]elected or [A]ll?");
			String deleteChoice = in.nextLine();
			
			if(deleteChoice.equalsIgnoreCase("a")) //delete all
				eventMan.deleteAllEvent();
			else if(deleteChoice.equalsIgnoreCase("s")){ //delete selected
				System.out.println("Enter date MM/DD/YYYY to delete: ");
				String deleteDate = in.nextLine();
				
				String[] deleteField = deleteDate.split("/");
				EventDate toDelete = new EventDate(Integer.parseInt(deleteField[2]),
					Integer.parseInt(deleteField[0]), Integer.parseInt(deleteField[1]));
				
				boolean result = eventMan.deleteDateEvent(toDelete); //attempt to delete
				if(!result)
					System.out.println("Event is not in system");
			}
			break;
		case "q":	//quit
			save();	//save data to file
				
		}
	}
	
	/**
	 * Search for an EventDate in EventManager by date to see if the date has an event
	 * @param date date to search for
	 */
	public void printAnEventDate(EventDate date){
		EventDate lookup = eventMan.search(date); 
		
		if(lookup == null)	// if not found print the date without events
			System.out.println(date.toString());
		else
			System.out.println(lookup.toString()); //if found print out the found EventDate
	}
	
	/**
	 * Show a date in Day View
	 * @param in Scanner used to read input
	 */
	public void dayView(Scanner in){
		EventDate today = calMan.currentDay();
		printAnEventDate(today);
		
		System.out.println("[P]revious or [N]ext or [M]ain menu");
		String navigate = in.nextLine();
		
		while(!navigate.equalsIgnoreCase("m")){
			
			if(navigate.equalsIgnoreCase("p"))
				today = calMan.previousDay();
			else if(navigate.equalsIgnoreCase("n"))
				today = calMan.nextDay();
			printAnEventDate(today);
			System.out.println("[P]revious or [N]ext or [M]ain menu");
			navigate = in.nextLine();
		}
	}
	
	/**
	 * Show a month in Month View
	 * @param in Scanner used to read input
	 */
	public void monthView(Scanner in){
		calMan.currentMonth(eventMan);
		System.out.println("[P]revious or [N]ext or [M]ain menu");
		String navigate = in.nextLine();
		
		while(!navigate.equalsIgnoreCase("m")){
			if(navigate.equalsIgnoreCase("p"))
				calMan.previousMonth(eventMan);
			else if(navigate.equalsIgnoreCase("n"))
				calMan.nextMonth(eventMan);
			System.out.println("[P]revious or [N]ext or [M]ain menu");
			navigate = in.nextLine();
		}
	}
	
	/**
	 * Displays and collect input for Creating new event
	 * @param in Scanner to collect input
	 */
	public void createView(Scanner in){
		String title, date, starttime, endtime;
		System.out.println("Create Event");
		System.out.println("Title: ");
		title = in.nextLine();
		System.out.println("Date in MM/DD/YYYY: ");
		date = in.nextLine();
		System.out.println("Start time (HH:MM): ");
		starttime = in.nextLine();
		System.out.println("End time (HH:MM) or [Enter] for no endtime:");
		endtime = in.nextLine();
		Event event = new Event(title, starttime+" - "+ endtime);
		//m d y
		String[] dayField = date.split("/");
		EventDate eventDate = new EventDate(Integer.parseInt(dayField[2]),
				Integer.parseInt(dayField[0]), Integer.parseInt(dayField[1]),event);
		eventMan.add(eventDate);
		
	}
	
	/**
	 * Creates EventDate from file input
	 * @param line a line read from file containing initializing info
	 * @return an EventDate
	 */
	public EventDate createEventDate(String line){
		String date = "";
		String time = "";
		String title = "";
		int colon = line.indexOf(":");
		date = line.substring(0, colon);
		int dash = line.indexOf("-");
		int timeEnd = 0;
		if(dash != -1){
			//there is start time and endtime
			timeEnd = line.indexOf(" ", dash+2);
		}else{
			//there is not end time
			timeEnd = line.indexOf(" ", colon+2);
		}
		time = line.substring(colon+1, timeEnd).trim();
		title = line.substring(timeEnd+1);
		Event event = new Event(title, time);
		
		String[] field = date.substring(0, date.length()).split("/");
		EventDate eventDate = new EventDate(Integer.parseInt(field[2]),Integer.parseInt(field[0]),
				Integer.parseInt(field[1]), event);
		
		return eventDate;
	}
	
	
}
