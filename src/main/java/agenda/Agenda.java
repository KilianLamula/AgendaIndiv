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
    
    
        //METHODES INDIV
    
    
    /**
     * Trouver les événements de l'agenda en fonction de leur titre
     * @param title le titre à rechercher
     * @return les événements qui ont le même titre
     */
    public List<Event> findByTitle(String title) {
        
        ArrayList<Event> findByTitle = new ArrayList();
        
        for(Event e : lesEvenements){
            if(e.getTitle().equals(title)){
                findByTitle.add(e);
            }
        }        
        return findByTitle;
    }
    
    /**
     * Déterminer s’il y a de la place dans l'agenda pour un événement
     * @param e L'événement à tester (on se limitera aux événements simples)
     * @return vrai s’il y a de la place dans l'agenda pour cet événement
     */
    public boolean isFreeFor(Event e) {
        
        for(Event event : lesEvenements){
            //Cas date de début prévue en même temps
            if(event.getStart().equals(e.getStart())) return false;
            
            //Cas date de fin prévue en même temps
            if(event.getStart().plus(event.getDuration()).equals(e.getStart().plus(e.getDuration()))) return false;
            
            //Cas date prévue pendant l'event (date de début pendant l'event)
            if(event.getStart().isAfter(e.getStart()) && event.getStart().isBefore(e.getStart().plus(e.getDuration()))) return false;
            
            //Cas d'un évènement prévu qui déborde sur l'évènement qu'on veut prévoir (date de fin pendant l'event)
            if(event.getStart().plus(event.getDuration()).isBefore(e.getStart().plus(e.getDuration())) 
                    && event.getStart().plus(event.getDuration()).isAfter(e.getStart())) return false;
        }
        
        //Sinon place dispo :
        return true;
    }
}
