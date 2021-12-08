package agenda;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import java.util.*;

/**
 * Description : A repetitive event that terminates after a given date, or after
 * a given number of occurrences
 */
public class FixedTerminationEvent extends RepetitiveEvent {

    protected LocalDate terminationInclusive;
    protected long numberOfOccurrences;
    /**
     * Constructs a fixed terminationInclusive event ending at a given date
     *
     * @param title the title of this event
     * @param start the start time of this event
     * @param duration the duration of this event
     * @param frequency one of :
     * <UL>
     * <LI>ChronoUnit.DAYS for daily repetitions</LI>
     * <LI>ChronoUnit.WEEKS for weekly repetitions</LI>
     * <LI>ChronoUnit.MONTHS for monthly repetitions</LI>
     * </UL>
     * @param terminationInclusive the date when this event ends
     */
    public FixedTerminationEvent(String title, LocalDateTime start, Duration duration, ChronoUnit frequency, LocalDate terminationInclusive) {
         super(title, start, duration, frequency);
         this.terminationInclusive=terminationInclusive;
         //On détermine le nombre d'occurence avec une méthode (car pas dans les paramètres) :
         this.numberOfOccurrences=determinerOccurrences();
    }

    /**
     * Constructs a fixed termination event ending after a number of iterations
     *
     * @param title the title of this event
     * @param start the start time of this event
     * @param duration the duration of this event
     * @param frequency one of :
     * <UL>
     * <LI>ChronoUnit.DAYS for daily repetitions</LI>
     * <LI>ChronoUnit.WEEKS for weekly repetitions</LI>
     * <LI>ChronoUnit.MONTHS for monthly repetitions</LI>
     * </UL>
     * @param numberOfOccurrences the number of occurrences of this repetitive event
     */
    public FixedTerminationEvent(String title, LocalDateTime start, Duration duration, ChronoUnit frequency, long numberOfOccurrences){
        super(title, start, duration, frequency);
        this.numberOfOccurrences=numberOfOccurrences;
        //On détermine la date de fin avec une méthode (car pas dans les paramètres) :
        this.terminationInclusive=determinerTerminationInclusive();
    }

    /**
     *
     * @return the termination date of this repetitive event
     */
    public LocalDate getTerminationDate() {
        return this.terminationInclusive;   
    }

    public long getNumberOfOccurrences() {
        return this.numberOfOccurrences;
    }

    @Override
    public String toString() {
        return "FixedTerminationEvent{" + "terminationInclusive=" + terminationInclusive + ", numberOfOccurrences=" + numberOfOccurrences + '}';
    }
    
    //Méthode pour déterminer le nombre d'occurences :
    public long determinerOccurrences(){
        //until permet de calculer le temps jusqu’à une autre date (date de fin) en fonction de l’unité spécifiée (fréquence)
        //Rq : on rajoute 1 pour prendre en compte l'occurence de la date de départ
        return this.getStart().toLocalDate().until(terminationInclusive, frequency) + 1;
    }
    
    //Méthode pour déterminer la date de fin :
    public LocalDate determinerTerminationInclusive() {
        if(frequency == ChronoUnit.DAYS){
            return this.getStart().plus(this.getNumberOfOccurrences() - 1, ChronoUnit.DAYS).toLocalDate();
        }
        if(frequency == ChronoUnit.WEEKS){
            return this.getStart().plus(this.getNumberOfOccurrences() - 1, ChronoUnit.WEEKS).toLocalDate();
        }
        if(frequency == ChronoUnit.MONTHS){
            return this.getStart().plus(this.getNumberOfOccurrences() - 1, ChronoUnit.MONTHS).toLocalDate();
        }
        //Sinon l'event se termine le jour même
        else return this.getStart().toLocalDate();
    }
}
