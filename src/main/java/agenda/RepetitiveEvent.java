package agenda;

import java.util.*;
import java.time.*;
import java.time.temporal.ChronoUnit;

/**
 * Description : A repetitive Event
 */
public class RepetitiveEvent extends Event {
    /**
     * Constructs a repetitive event
     *
     * @param title the title of this event
     * @param start the start of this event
     * @param duration myDuration in seconds
     * @param frequency one of :
     * <UL>
     * <LI>ChronoUnit.DAYS for daily repetitions</LI>
     * <LI>ChronoUnit.WEEKS for weekly repetitions</LI>
     * <LI>ChronoUnit.MONTHS for monthly repetitions</LI>
     * </UL>
     */
    
    protected ChronoUnit frequency;
    protected ArrayList<LocalDate> lesExceptions;
    
    public RepetitiveEvent(String title, LocalDateTime start, Duration duration, ChronoUnit frequency) {
        super(title, start, duration);
        this.frequency = frequency;
        this.lesExceptions = new ArrayList<LocalDate>();
    }

    /**
     * Adds an exception to the occurrence of this repetitive event
     *
     * @param date the event will not occur at this date
     */
    public void addException(LocalDate date) {
        lesExceptions.add(date);
    }

    /**
     *
     * @return the type of repetition
     */
    public ChronoUnit getFrequency() {
        return this.frequency;
    }
    
    //On redéfinit la méthode isInDay dans cette classe car il peut y avoir des exceptions à présent et de plus il y a une certaine fréquence    
    
    public boolean isInDay(LocalDate aDay) {
        
        //Si le jour est une exception on retourne false
        if(lesExceptions.contains(aDay)) return false;
        
        //Cas jour
        if(frequency == ChronoUnit.DAYS){
            //Dans le cas où le jour correspond à la date de début : retourne true
            if(this.getStart().toLocalDate().equals(aDay)) return true;
            //Si le jour est après la date de début : forcément true car l'event est tous les jours
            if(this.getStart().toLocalDate().isBefore(aDay)) return true; 
        }
        
        //Cas semaine
        if(frequency == ChronoUnit.WEEKS){
            //Il y a 53 jours dans une année, on est obligé de toutes les vérifier
            for(int i = 0; i < 53; i++){
                //On vérifie à chaque fois en ajoutant une semaine si le jour correspond
                if(this.getStart().toLocalDate().plus(i, ChronoUnit.WEEKS).equals(aDay)) return true;
            }
            //Sinon false
            return false;
        }
        
        //Cas mois
        if(frequency == ChronoUnit.MONTHS){
            //De la même façon on vérifie tous les mois de l'année (x12)
            for(int i = 0; i < 12; i++){
                //On vérifie à chaque fois en ajoutant un mois si le jour correspond
                if (this.getStart().toLocalDate().plus(i, ChronoUnit.MONTHS).equals(aDay)) return true;
            }
            //Sinon false
            return false;
        }
        //Dans le cas où aucune des conditions ci-dessus correspond, on vérifie si le jour correspond à la date de début :
        return this.getStart().toLocalDate().equals(aDay);
    }

    @Override
    public String toString() {
        return "RepetitiveEvent{" + "frequency=" + frequency + ", lesExceptions=" + lesExceptions + '}';
    }
}
