/**
 *Hw2 - EventDate.java
 *@author kimpham
 *@since 10/04/2016
 */

import java.util.*;

/**
 * Uses to represents the names of months in a year
 */
enum MONTHS
{
	Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov, Dec;
}


/**
 * Uses to represents the names of days in a week
 */
enum DAYS
{
	Sunday, Monday, Tueday, Wednesday, Thursday, Friday, Saturday ;
}


/**
 * Represents a date with an event scheduled on
 */
public class EventDate {
	private int year;
	private int month;
	private int day;
	private ArrayList<Event> events = new ArrayList<>();
	private Calendar cal = Calendar.getInstance();
	private DAYS[] daysOfWeek = DAYS.values();
	private MONTHS[] monthsOfYear = MONTHS.values();
	
	
	/**
	 * Constructor
	 * @param year	the year of date that has an event
	 * @param month	the month of date that has an event
	 * @param day	the day of date that has an event
	 * @param event	the event that was scheduled on that date
	 */
	public EventDate(int year, int month, int day, Event event){
		this.year = year;
		this.month = month;
		this.day = day;
		events.add(event);
		cal.set(year, month-1, day);
	}
	
	/**
	 * Constructor
	 * @param year	the year of date that has an event
	 * @param month	the month of date that has an event
	 * @param day	the day of date that has an event
	 */
	public EventDate(int year, int month, int day){
		this.year = year;
		this.month = month;
		this.day = day;
		cal.set(year, month-1, day);
	}
	
	/**Checks to see if two EventDates are equal
	 * @return true if equals and false otherwise
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object anObject){
		if(this == anObject) return true;	//if this is the object return true
		if(anObject == null) return false;	//if object is null return false
		if(this.getClass()!=anObject.getClass()) return false;	//if not same class return false
		
		EventDate aDate = (EventDate) anObject;		//cast anObject to EventDate
		return year==aDate.year && month==aDate.month && day==aDate.day;	//compare by year, month, day
		
	}
	
	
	/**
	 * Add an event to a date
	 * @param event event to be added
	 * @return true if add successfully
	 */
	public boolean add(Event event){
		events.add(event);
		return true;
	}
	
	/**
	 * deletes all events on an EventDate
	 * @return true if deletes successfully
	 */
	public boolean deleteEvent(){
		int size = events.size();
		for(int i = 0; i < size; i++){
			events.remove(events.get(0));
		}
		return true;
	}
	
	/**
	 * Return EventDate's String representation in format
	 * MM/DD/YYY: event
	 * @return string representation of EventDate in number format
	 */
	public String toNumberString(){
		String append = "0"; //use for when day < 10 (eg. 1,2,3...)
		if(day > 10)
			append = "";
		
		//print out EventDate's date as mm/dd/yyyy:
		String line = month+"/"+append+day+"/"+year+": ";
		
		//print out events following the dates
		for(int i = 0; i < events.size(); i++){
			line+= events.get(i).toString();
		}
		return line;
	}
	
	
	/**Return EventDate's String representation in format
	 * Day of Week, Month Date
	 * Event
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		//print out EventDate's date as word (eg. Friday, Jun 30, 2016)
		String line=  daysOfWeek[cal.get(Calendar.DAY_OF_WEEK)-1] +", " + monthsOfYear[month-1] + " " + day +", "+year+" \n";
		
		//print out events under the date
		for(int i = 0; i < events.size(); i++){
			line+= events.get(i).toString();
		}
		return line;
	}
	
	/**
	 * Return EventDate's String representation in format that omits the year
	 * Day of Week, Month Date event
	 * @return
	 */
	public String toString2(){
		//print out EventDate's date as word (eg. Friday, Jun 30)
		String line=  daysOfWeek[cal.get(Calendar.DAY_OF_WEEK)-1] +", " + monthsOfYear[month-1] + " " + day +" ";
		
		//print out event following the date
		for(int i = 0; i < events.size(); i++){
			line+= events.get(i).toString();
		}
		return line;
	}
	
	/**
	 * Return the year of an EventDate
	 * @return year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Set the year of an EventDate
	 * @param year
	 * precondition: year is not 0
	 * postcondition: year is set to a number
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * Returns the month of an EventDate
	 * @return month
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * Sets the month of an EventDate
	 * @param month
	 * precondition: month is not 0
	 * postcondition: month is set to a number
	 */
	public void setMonth(int month) {
		this.month = month;
	}

	/**
	 * Returns the date of the month of an EventDate
	 * @return date
	 */
	public int getDay() {
		return day;
	}

	/**
	 * Sets date of a month of an EventDate
	 * @param day
	 * precondition: day is not 0
	 * postcondition: day is set to a number
	 */
	public void setDay(int day) {
		this.day = day;
	}	
}
