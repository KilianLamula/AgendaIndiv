package agenda;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FixedTerminationEventTest {

    // November 1st, 2020
    LocalDate nov_1_2020 = LocalDate.of(2020, 11, 1);

    // November 1st, 2020, 22:30
    LocalDateTime nov_1__2020_22_30 = LocalDateTime.of(2020, 11, 1, 22, 30);

    // January 5, 2021
    LocalDate jan_5_2021 = LocalDate.of(2021, 1, 5);

    // 120 minutes
    Duration min_120 = Duration.ofMinutes(120);

    // A Weekly Repetitive event ending at a given date
    FixedTerminationEvent fixedTermination = new FixedTerminationEvent("Fixed termination weekly", nov_1__2020_22_30, min_120, ChronoUnit.WEEKS, jan_5_2021);

    // A Weekly Repetitive event ending after a give number of occurrrences
    FixedTerminationEvent fixedRepetitions = new FixedTerminationEvent("Fixed termination weekly", nov_1__2020_22_30, min_120, ChronoUnit.WEEKS, 10);
    
    @Test
    public void canCalculateNumberOfOccurrencesFromTerminationDate() {
        assertEquals(10, fixedTermination.getNumberOfOccurrences(), "Cet événement doits se répéter 10 fois");
    }

    @Test
    public void canCalculateTerminationDateFromNumberOfOccurrences() {
        LocalDate termination = LocalDate.of(2021,1, 3);
        assertEquals(termination, fixedRepetitions.getTerminationDate(), "Cet événement doits se terminer le 3 janvier");
    }
    
    @Test
    public void eventIsInItsStartDay() {
        assertTrue(fixedTermination.isInDay(nov_1_2020), "Un événement a lieu dans son jour de début");
        assertTrue(fixedRepetitions.isInDay(nov_1_2020), "Un événement a lieu dans son jour de début");
    }

    @Test
    public void eventIsNotInDayBefore() {
        assertFalse(fixedTermination.isInDay(nov_1_2020.minus(1, ChronoUnit.DAYS)), "Un événement n'a pas lieu avant son jour de début");
        assertFalse(fixedRepetitions.isInDay(nov_1_2020.minus(1, ChronoUnit.DAYS)), "Un événement n'a pas lieu avant son jour de début");
    }

    @Test
    public void eventOccurs10WeeksAfter() {
        assertTrue(fixedTermination.isInDay(nov_1_2020.plus(6, ChronoUnit.WEEKS)), "Cet événement se produit toutes les semaines");
        assertTrue(fixedRepetitions.isInDay(nov_1_2020.plus(6, ChronoUnit.WEEKS)), "Cet événement se produit toutes les semaines");
    }
    
    //Rajout : pour tester les mois
    @Test
    public void eventOccurs1MonthAfter() {
        FixedTerminationEvent fixedRepetitionsMonths = new FixedTerminationEvent("Fixed termination weekly", nov_1__2020_22_30, min_120, ChronoUnit.MONTHS, 10);
        assertTrue(fixedRepetitionsMonths.isInDay(nov_1_2020.plus(1, ChronoUnit.MONTHS)), "Cet événement se produit tous les mois");
        assertFalse(fixedRepetitionsMonths.isInDay(nov_1_2020.plus(13, ChronoUnit.MONTHS)), "La date devrait être false");
    }

    @Test
    public void eventIsNotInExceptionDays() {
        fixedTermination.addException(nov_1_2020.plus(2, ChronoUnit.WEEKS)); // ne se produit pas à W+2
        fixedTermination.addException(nov_1_2020.plus(4, ChronoUnit.WEEKS)); // ne se produit pas à W+4
        assertTrue(fixedTermination.isInDay(nov_1_2020.plus(1, ChronoUnit.WEEKS)), "Cet événement se produit toutes les semaines");
        assertFalse(fixedTermination.isInDay(nov_1_2020.plus(2, ChronoUnit.WEEKS)), "Cet événement ne se produit pas à W+2");
        assertTrue(fixedTermination.isInDay(nov_1_2020.plus(3, ChronoUnit.WEEKS)), "Cet événement se produit toutes les semaines");
        assertFalse(fixedTermination.isInDay(nov_1_2020.plus(4, ChronoUnit.WEEKS)), "Cet événement ne se produit pas à W+4");
    }
    
    //Rajout : test toString()
    @Test
    public void toStringShowsEventTerminationAndOccurences() {
        assertTrue(fixedTermination.toString().contains("2021-01-05") && fixedRepetitions.toString().contains("10"), "toString() invalide");
    }
    
    //Rajout : tests(x3) méthode determinerTerminationInclusive()
    @Test
    public void testDeterminerTerminationInclusiveDays() {
        FixedTerminationEvent EventDayRepet = new FixedTerminationEvent("Day", nov_1__2020_22_30, min_120, ChronoUnit.DAYS, 3);
        assertEquals(LocalDate.of(2020, 11, 3), EventDayRepet.determinerTerminationInclusive(), "Date de fin mal déterminée");
    }
    
    @Test
    public void testDeterminerTerminationInclusiveWeeks() {
        FixedTerminationEvent EventWeekRepet = new FixedTerminationEvent("Week", nov_1__2020_22_30, min_120, ChronoUnit.WEEKS, 2);
        assertEquals(LocalDate.of(2020, 11, 8), EventWeekRepet.determinerTerminationInclusive(), "Date de fin mal déterminée");
    }
    
    @Test
    public void testDeterminerTerminationInclusiveMonths() {
        FixedTerminationEvent EventMonthRepet = new FixedTerminationEvent("Month", nov_1__2020_22_30, min_120, ChronoUnit.MONTHS, 3);
        assertEquals(LocalDate.of(2021, 1, 1), EventMonthRepet.determinerTerminationInclusive(), "Date de fin mal déterminée");
    }

}
