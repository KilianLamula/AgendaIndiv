package agenda;

import java.time.*;

public class Event {

    /**
     * The myTitle of this event
     */
    private String myTitle;
    
    /**
     * The starting time of the event
     */
    private LocalDateTime myStart;

    /**
     * The durarion of the event 
     */
    private Duration myDuration;


    /**
     * Constructs an event
     *
     * @param title the title of this event
     * @param start the start time of this event
     * @param duration the duration of this event
     */
    public Event(String title, LocalDateTime start, Duration duration) {
        this.myTitle = title;
        this.myStart = start;
        this.myDuration = duration;
        
    }

    /**
     * Tests if an event occurs on a given day
     *
     * @param aDay the day to test
     * @return true if the event occurs on that day, false otherwise
     */
    public boolean isInDay(LocalDate aDay) {
        
        //Si aucune n'est condition ci-dessous ne correspond, res = false
        boolean res=false;
       
        LocalDate dateStart= this.myStart.toLocalDate();
        LocalDate dateFin=this.myStart.plusMinutes(this.myDuration.toMinutes()).toLocalDate();
        
        //si date debut ou date de fin égale date en paramètre, res = true
        if(dateStart.equals(aDay)|| dateFin.equals(aDay)){
                res=true;      
        }

        //si date en paramètre est comprise entre la date de debut et la date de fin, res = true
        if(dateStart.isBefore(aDay) && dateFin.isAfter(aDay)){
                res=true;
            }
            return res;
        }     
   
    @Override
    public String toString() {
        return "Event [myDuration=" + myDuration + ", myStart=" + myStart + ", myTitle=" + myTitle + "]";
    }

    /**
     * @return the myTitle
     */
    public String getTitle() {
        return myTitle;
    }

    /**
     * @return the myStart
     */
    public LocalDateTime getStart() {
        return myStart;
    }

    /**
     * @return the myDuration
     */
    public Duration getDuration() {
        return myDuration;
    } 
}
