/**
 * Hw2 - CalendarManager.java
 * @author kimpham
 */


import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * Represents short name of days of a week
 */
enum DAYS_SHORT
{
	Sun, Mon, Tue, Wed, Thu, Fri, Sat ;
}
/**
 * Represents the month name of a year
 */
enum MONTHS_FULL{
	January, February, March, April, May, June, July, August, September, October, November, December;
}

/**
 * Manages display and navigation of calendar
 */
public class CalendarManager {
	
	private DAYS_SHORT[] dayOfWeek = DAYS_SHORT.values();
	private MONTHS_FULL[] months_full = MONTHS_FULL.values();
	private Calendar cal;
	private int dateNow, monthNow, yearNow;
	private int monthsFromCurrent;
	private int daysFromCurrent;
	
	/**
	 * Constructor
	 */
	public CalendarManager(){
		cal = Calendar.getInstance();
		dateNow = cal.get(Calendar.DAY_OF_MONTH);
		monthNow = cal.get(Calendar.MONTH);
	    yearNow = cal.get(Calendar.YEAR);
	    monthsFromCurrent = 0;
	    daysFromCurrent = 0;
	}
	
	/**
	 * Returns the current date on the calendar
	 * @return current date on calendar, if an event is scheduled, then an EventDate with event is returned or an
	 * EventDate without event otherwise
	 */
	public EventDate currentDay(){
		daysFromCurrent = 0;
		return this.returnRequestedDay();
	}
	
	/**
	 * Returns the previous date on the calendar
	 * @return previous date on calendar, if an event is scheduled, then an EventDate with event is returned or an
	 * EventDate without event otherwise
	 */
	public EventDate previousDay(){
		daysFromCurrent --;
		return this.returnRequestedDay();
	}
	
	/**
	 * Returns the next date on the calendar
	 * @return next date on calendar, if an event is scheduled, then an EventDate with event is returned or an
	 * EventDate without event otherwise
	 */
	public EventDate nextDay(){
		daysFromCurrent ++;
		return this.returnRequestedDay();
	}
	
	/**
	 * Displays the next month on the calendar
	 * @param eventMan An eventManager that manages events for this calendar
	 */
	public void nextMonth(EventManager eventMan){
		monthsFromCurrent  = 1;
		this.displayMonth(eventMan);
	}
	
	/**
	 * Displays the current month on the calendar
	 * @param eventMan An eventManager that manages events for this calendar
	 */
	public void currentMonth(EventManager eventMan){
		monthsFromCurrent = 0;
		this.displayMonth(eventMan);
	}
	
	/**
	 * Displays the previous month on the calendar
	 * @param eventMan An eventManager that manages events for this calendar
	 */
	public void previousMonth(EventManager eventMan){
		monthsFromCurrent = -1;
		this.displayMonth(eventMan);
	}
	
	/**
	 * Return the date requested (previous, next or current day)
	 * @return date requested
	 */
	private EventDate returnRequestedDay(){
		cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, daysFromCurrent);
		int requestedYear = cal.get(Calendar.YEAR);
		int requestedMonth = cal.get(Calendar.MONTH);
		int requestedDay = cal.get(Calendar.DAY_OF_MONTH);
	
		return new EventDate(requestedYear,requestedMonth+1,requestedDay);	
	}
	
	
	/**
	 * Displays a month
	 * @param eventMan
	 */
	private void displayMonth(EventManager eventMan){
		cal.add(Calendar.MONTH, monthsFromCurrent);
		
		//set calendar to current time
		cal = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),1);
		
		//get other important dates
		int firstDayOfMonth = cal.get(Calendar.DAY_OF_WEEK);
		int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int weeksInMonth = cal.getActualMaximum(Calendar.WEEK_OF_MONTH);
		
		//print MONTH YEAR HEADING
		System.out.println(months_full[cal.get(Calendar.MONTH)] + " " + cal.get(Calendar.YEAR));
		//print days of weeks heading
		for(int i = 0; i < dayOfWeek.length; i++){
				System.out.printf("%5s ", dayOfWeek[i]);
		}
		System.out.println();
		int day = 1;
		
		for(int i = 0; i < weeksInMonth*7; i++){
			if(i < firstDayOfMonth - 1){
				System.out.printf("      ");
			}
			else if(i > firstDayOfMonth-2 + daysInMonth){
				System.out.printf("    ");
			}else{
				if(day==dateNow && monthNow==cal.get(Calendar.MONTH) && yearNow==cal.get(Calendar.YEAR)){
					System.out.printf(" [[%d]]",day);
					day++;
				}else{
					EventDate date = new EventDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, day);
					if(eventMan.search(date)!=null){
						System.out.printf("   [%d]",day);
						day++;
					}else
						System.out.printf("%5d ", day++);
				}
					
				if((i+1)%7==0)
					System.out.println();
				}
				
		}
		System.out.println();
	}
	
		
		
		
	
	
}
