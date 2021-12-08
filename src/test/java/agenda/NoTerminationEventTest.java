package agenda;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NoTerminationEventTest {
    // November 1st, 2020
    LocalDate nov_1_2020 = LocalDate.of(2020, 11, 1);

    // November 1st, 2020, 22:30
    LocalDateTime nov_1__2020_22_30 = LocalDateTime.of(2020, 11, 1, 22, 30);

    // 120 minutes
    Duration min_120 = Duration.ofMinutes(120);

    // A daily repetitive event, never ending
    // November 1st, 2020, 22:30, 120 minutes
    RepetitiveEvent neverEnding = new RepetitiveEvent("Never Ending", nov_1__2020_22_30, min_120, ChronoUnit.DAYS);

    @Test
    public void eventIsInItsStartDay() {
        assertTrue(neverEnding.isInDay(nov_1_2020), "Un événement a lieu dans son jour de début");
    }

    @Test
    public void eventIsNotInDayBefore() {
        assertFalse(neverEnding.isInDay(nov_1_2020.minus(1, ChronoUnit.DAYS)),  "Un événement n'a pas lieu avant son jour de début");
    }

    @Test
    public void eventOccurs10DayAfter() {
        assertTrue(neverEnding.isInDay(nov_1_2020.plus(10, ChronoUnit.DAYS)),  "Cet événement se produit tous les jours");
    }
    
    @Test
    public void eventIsNotInExceptionDays() {
        neverEnding.addException(nov_1_2020.plus(2, ChronoUnit.DAYS)); // ne se produit pas à J+2
        neverEnding.addException(nov_1_2020.plus(4, ChronoUnit.DAYS)); // ne se produit pas à J+4
        assertTrue(neverEnding.isInDay(nov_1_2020.plus(1, ChronoUnit.DAYS)),  "Cet événement se produit tous les jours");
        assertFalse(neverEnding.isInDay(nov_1_2020.plus(2, ChronoUnit.DAYS)), "Cet événement ne se produit pas à J+2");
        assertTrue(neverEnding.isInDay(nov_1_2020.plus(3, ChronoUnit.DAYS)),  "Cet événement se produit tous les jours");
        assertFalse(neverEnding.isInDay(nov_1_2020.plus(4, ChronoUnit.DAYS)), "Cet événement ne se produit pas à J+4");
    }
    
}
