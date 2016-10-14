/**
 * Hw2 - EventManager.java
 * @author kimpham
 */


import java.util.*;

/**
 * Manages dates that events are scheduled on including adding, deleting, searching and printing out
 * EventDates as multiple format
 */
public class EventManager {
	private ArrayList<EventDate> dayWithEvents;  // a list of any EventDate that was created
	
	/**
	 * Constructor
	 */
	public EventManager(){
		dayWithEvents = new ArrayList<>();
	}
	
	/**
	 * Add a new EventDate to the list
	 * @param newDay
	 * @return true if add successful
	 */
	public boolean add(EventDate newDay){
		dayWithEvents.add(newDay);
		return true;
	}
	
	/**
	 * Search for an EventDate
	 * @param date
	 * @return the EventDate if found or null otherwise
	 */
	public EventDate search(EventDate date){
		for(int i = 0; i < dayWithEvents.size(); i++){
			if(dayWithEvents.get(i).equals(date))
				return dayWithEvents.get(i);
		}
		return null;
	}
	
	/**
	 * Print list of EventDate in format Date, Month Day Event
	 */
	public void printEventsList(){
		//if no event print "no events"
		if(dayWithEvents.size()==0){
			System.out.println("No events");
			return;
		}
		
		//sort the list before printing to maintain order
		dayWithEvents.sort((date1, date2)->{
			if(date1.getYear()!=date2.getYear()) return date1.getYear()-date2.getYear();
			if(date1.getMonth()!=date2.getMonth()) return date1.getMonth()-date2.getMonth();
			if(date1.getDay()!=date2.getDay())return date1.getDay()-date2.getDay();
			return 0;
		});
		
		//get the year
		int yearHeading = dayWithEvents.get(0).getYear();
		boolean first = true;
		
		//print the list with 1 year headings and the the events
		for(int i = 0; i < dayWithEvents.size(); i++){
			if(dayWithEvents.get(i).getYear()==yearHeading && first){
				System.out.println(yearHeading);
				first = false;
			}else if(dayWithEvents.get(i).getYear()!=yearHeading ){
				yearHeading = dayWithEvents.get(i).getYear();
				System.out.println(yearHeading);
			}
			System.out.println("  "+dayWithEvents.get(i).toString2());
		}
	}
	
	/**
	 * Delete an EventDate and all events on that date
	 * @param date
	 * @return true if delete successfully false otherwise
	 */
	public boolean deleteDateEvent(EventDate date){
		EventDate toDelete = search(date);
		if(toDelete!=null){
			toDelete.deleteEvent();
			dayWithEvents.remove(toDelete);
			return true;
		}else
			return false;
	}
	
	/**
	 * Delete all existing EventDates in the list and all its events
	 * @return
	 */
	public boolean deleteAllEvent(){
		int size = dayWithEvents.size();
		for(int i = 0; i < size ; i++){
			dayWithEvents.get(0).deleteEvent();
			dayWithEvents.remove(0);
		}
		
		return true;
	}
	
	
	/**
	 * Return the String array containing info of EventDates in the list, with each element in the array 
	 * follow the format MM/DD/YYYY: event
	 * @return arrays of EventDate's String representation
	 */
	public String[] eventDateList(){  //this method is used for writing to file to follow the required format
		if(dayWithEvents.size()!=0){
			
			//sort before saving info to String[]
			dayWithEvents.sort((date1, date2)->{
				if(date1.getYear()!=date2.getYear()) return date1.getYear()-date2.getYear();
				if(date1.getMonth()!=date2.getMonth()) return date1.getMonth()-date2.getMonth();
				if(date1.getDay()!=date2.getDay())return date1.getDay()-date2.getDay();
				return 0;
			});
			
			String[] list = new String[dayWithEvents.size()];
			
			for(int i = 0; i < dayWithEvents.size(); i++){
				list[i] = dayWithEvents.get(i).toNumberString();
			}
			return list;
		}
		return new String[0];
	}
}
