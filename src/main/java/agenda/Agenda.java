package agenda;

import java.time.LocalDate;
import java.util.*;

/**
 * Description : An agenda that stores events
 */
public class Agenda {
    
    HashSet<Event> lesEvenements = new HashSet<Event>();
    
    /**
     * Adds an event to this agenda
     *
     * @param e the event to add
     */
    public void addEvent(Event e) {
        lesEvenements.add(e);
    }

    /**
     * Computes the events that occur on a given day
     *
     * @param day the day toi test
     * @return and iteraror to the events that occur on that day
     */
    public List<Event> eventsInDay(LocalDate day) {
        ArrayList<Event> thisDay = new ArrayList<Event>();
        
        for(Event e : lesEvenements){
            if(e.isInDay(day)){
                thisDay.add(e);
            }
        }
        return thisDay;
    }
}
