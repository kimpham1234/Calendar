/**
 *Hw2 - Event.java
 *@author kimpham
 *@since 10/04/2016
 */


/**
 * Represent an event schedule on a date
 */
public class Event {
	private String title;
	private String time;
	
	/**
	 * Constructor
	 * @param title title of event
	 * @param time start and end time of event
	 */
	public Event(String title, String time){
		this.title = title;
		int dash = time.indexOf("-");
		if(time.substring(dash+1).length()==1)
			this.time = time.substring(0, dash-1);
		else
			this.time = time;
	}
	
	/**
	 * Returns title of an event
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets title of an event
	 * @param title title to be set
	 * precondition: title not empty
	 * postcondition: title is set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * returns the time of an event
	 * @return time of event
	 */
	public String getTime() {
		return time;
	}

	/**
	 * Sets time of an event
	 * @param time
	 * precondition: time is not empty
	 * postcondition: time is set
	 */
	public void setTime(String time) {
		this.time = time;
	}
	
	/**Returns string representation of event
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return time + " " + title+"\n";
	}
}
