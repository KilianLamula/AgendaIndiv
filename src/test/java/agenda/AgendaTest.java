package agenda;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class AgendaTest {
    Agenda agenda;
    
    // November 1st, 2020
    LocalDate nov_1_2020 = LocalDate.of(2020, 11, 1);

    // January 5, 2021
    LocalDate jan_5_2021 = LocalDate.of(2021, 1, 5);

    // November 1st, 2020, 22:30
    LocalDateTime nov_1__2020_22_30 = LocalDateTime.of(2020, 11, 1, 22, 30);

    // 120 minutes
    Duration min_120 = Duration.ofMinutes(120);

    // A simple event
    // November 1st, 2020, 22:30, 120 minutes
    Event simple = new Event("Simple event", nov_1__2020_22_30, min_120);

    // A Weekly Repetitive event ending at a given date
    RepetitiveEvent fixedTermination = new FixedTerminationEvent("Fixed termination weekly", nov_1__2020_22_30, min_120, ChronoUnit.WEEKS, jan_5_2021);

    // A Weekly Repetitive event ending after a give number of occurrrences
    RepetitiveEvent fixedRepetitions = new FixedTerminationEvent("Fixed termination weekly", nov_1__2020_22_30, min_120, ChronoUnit.WEEKS, 10);
    
    // A daily repetitive event, never ending
    // November 1st, 2020, 22:30, 120 minutes
    RepetitiveEvent neverEnding = new RepetitiveEvent("Never Ending", nov_1__2020_22_30, min_120, ChronoUnit.DAYS);

    @BeforeEach
    public void setUp() {
        agenda = new Agenda();
        agenda.addEvent(simple);
        agenda.addEvent(fixedTermination);
        agenda.addEvent(fixedRepetitions);
        agenda.addEvent(neverEnding);
    }
    
    @Test
    public void testMultipleEventsInDay() {
        assertEquals(4, agenda.eventsInDay(nov_1_2020).size(), "Il y a 4 événements ce jour là");
        assertTrue(agenda.eventsInDay(nov_1_2020).contains(neverEnding));
    }
    
    
    //Rajout individuel des tests :
    @Test 
    public void testFindByTitleTrue(){
        assertTrue(agenda.findByTitle("Simple event").contains(simple));
    }
    
    @Test
    public void testFindByTitleFalse(){
        Event simple1 = new Event("Simple event1", nov_1__2020_22_30, min_120);
        assertFalse(agenda.findByTitle("Simple event").contains(simple1));
    }
    
    @Test 
    public void testIsFreeForFalse(){
        //Cas même date de début ou de fin :
        assertFalse(agenda.isFreeFor(simple));
        //Cas date prévue pendant un event (commence pendant simple) :
        Event e1=new Event("after", LocalDateTime.of(2020, 11, 1, 23, 30), Duration.ofMinutes(120));
        assertFalse(agenda.isFreeFor(e1));
        //Cas date de fin pendant un event (déborde sur simple) :
        Event e2=new Event("before", LocalDateTime.of(2020, 11, 1, 21, 30), Duration.ofMinutes(120));
        assertFalse(agenda.isFreeFor(e2));
        //Cas date de début avant de début avant et date de fin après :
        Event e3=new Event("on", LocalDateTime.of(2020, 11, 1, 21, 30), Duration.ofMinutes(500));
        assertFalse(agenda.isFreeFor(e3));
    }
    
    @Test 
    public void testIsFreeForTrue(){
        Event simple1 = new Event("Simple event1", LocalDateTime.of(2021, 4, 12, 23, 05), min_120);
        assertTrue(agenda.isFreeFor(simple1));
    }

}
