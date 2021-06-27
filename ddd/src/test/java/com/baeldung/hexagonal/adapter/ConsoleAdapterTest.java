package java.com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.adapter.ConsoleAdapter;
import com.baeldung.hexagonal.adapter.ConsoleAdapter.State;
import com.baeldung.hexagonal.core.port.WeightTrackerAllService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.PrintStream;

import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ConsoleAdapterTest {
    @Mock
    PrintStream mockOut;

    @Mock
    ConsoleAdapter.LineReader mockReader;

    @Mock
    WeightTrackerAllService mockService;

    ConsoleAdapter adapter;


    @Before
    public void setUp() {
        adapter = ConsoleAdapter.of(mockReader, mockOut, mockService);
    }

    @Test
    public void whenStateIsWelcome_thenItShouldWelcomeTheUserAndLeadToMenu() {
        State state = State.WELCOME.collect(mockReader, mockOut, new ConsoleAdapter.DataCollector(mockService));

        verify(mockOut, atLeastOnce()).println(contains("Welcome"));
        Assert.assertSame(State.MENU, state);
    }

    @Test
    public void whenStateIsMenu_thenItShouldGiveTheUserOptions() {
        when(mockReader.nextLine()).thenReturn("EXIT"); // Just to exit the loop
        State state = State.MENU.collect(mockReader, mockOut, new ConsoleAdapter.DataCollector(mockService));
        Assert.assertSame(State.END_SESSION, state);

        verify(mockOut, atLeastOnce()).println(contains("select"));
        verify(mockOut, atLeastOnce()).println(eq("1 - Enter Weight"));
        verify(mockOut, atLeastOnce()).println(eq("2 - Show History"));
        verify(mockOut, atLeastOnce()).println(eq("3 - Delete History Item"));
    }

    @Test
    public void whenStateIsMenuAndUserTypes1_thenItShouldLeadToCollectWeight() {
        when(mockReader.nextLine()).thenReturn("1");
        State state = State.MENU.collect(mockReader, mockOut, new ConsoleAdapter.DataCollector(mockService));

        Assert.assertSame(State.COLLECTING_WEIGHT, state);
    }

    @Test
    public void whenStateIsMenuAndUserTypes2_thenItShouldLeadToListingHistory() {
        when(mockReader.nextLine()).thenReturn("2");
        State state = State.MENU.collect(mockReader, mockOut, new ConsoleAdapter.DataCollector(mockService));

        Assert.assertSame(State.LISTING_HISTORY, state);
    }

    @Test
    public void whenStateIsMenuAndUserTypes3_thenItShouldLeadToDeleteHistoryItem() {
        when(mockReader.nextLine()).thenReturn("3");
        State state = State.MENU.collect(mockReader, mockOut, new ConsoleAdapter.DataCollector(mockService));

        Assert.assertSame(State.DELETE_HISTORY_ITEM, state);
    }

    @Test
    public void whenStateIsMenuAndUserTypesAnythingOtherThan1or2or3_thenItShouldPrintErrorAndLeadBackToMenu() {
        when(mockReader.nextLine()).thenReturn("a");
        State state = State.MENU.collect(mockReader, mockOut, new ConsoleAdapter.DataCollector(mockService));

        Assert.assertSame(State.MENU, state);
        verify(mockOut, atLeastOnce()).println(contains("invalid"));
    }

    @Test
    public void whenStateIsCollectingWeightAndUserTypesValidWeight_thenItShouldLeadToCollectDate() {
        when(mockReader.nextLine()).thenReturn("75");
        State state = State.COLLECTING_WEIGHT.collect(mockReader, mockOut, new ConsoleAdapter.DataCollector(mockService));

        Assert.assertSame(State.COLLECTING_DATE, state);
    }

    @Test
    public void whenStateIsCollectingWeightAndUserTypesInvalidWeight_thenItShouldLeadBackToCollectingWeightAndPrintError() {
        when(mockReader.nextLine()).thenReturn("-75");
        State state = State.COLLECTING_WEIGHT.collect(mockReader, mockOut, new ConsoleAdapter.DataCollector(mockService));

        Assert.assertSame(State.COLLECTING_WEIGHT, state);
        verify(mockOut, atLeastOnce()).println(contains("invalid"));
    }

    @Test
    public void whenStateIsCollectingDateAndUserTypesValidDate_thenItShouldLeadToMenuAndPrintSuccess() {
        when(mockReader.nextLine()).thenReturn("2021-06-21");
        State state = State.COLLECTING_DATE.collect(mockReader, mockOut, new ConsoleAdapter.DataCollector(mockService));

        Assert.assertSame(State.MENU, state);
        verify(mockOut, atLeastOnce()).println(contains("Weight Added"));
    }

    @Test
    public void whenStateIsCollectingDateAndUserTypesInvalidDate_thenItShouldLeadBackToCollectingDateAndPrintError() {
        when(mockReader.nextLine()).thenReturn("2021-00-21");
        State state = State.COLLECTING_DATE.collect(mockReader, mockOut, new ConsoleAdapter.DataCollector(mockService));

        Assert.assertSame(State.COLLECTING_DATE, state);
        verify(mockOut, atLeastOnce()).println(contains("invalid"));
    }

    @Test
    public void whenStateIsDeletingHistoryAndUserTypesValidId_thenItShouldLeadToMenuAndPrintSuccess() {
        when(mockReader.nextLine()).thenReturn("1");
        when(mockService.remove(eq(1L))).thenReturn(true);
        State state = State.DELETE_HISTORY_ITEM.collect(mockReader, mockOut, new ConsoleAdapter.DataCollector(mockService));

        Assert.assertSame(State.MENU, state);
        verify(mockOut, atLeastOnce()).println(contains("Type the id"));
        verify(mockOut, atLeastOnce()).println(contains("Successfully"));
    }

    @Test
    public void whenStateIsDeletingHistoryAndUserTypesInvalidId_thenItShouldLeadBackToDeleteAndPrintError() {
        when(mockReader.nextLine()).thenReturn("-1");
        State state = State.DELETE_HISTORY_ITEM.collect(mockReader, mockOut, new ConsoleAdapter.DataCollector(mockService));

        Assert.assertSame(State.DELETE_HISTORY_ITEM, state);
        verify(mockOut, atLeastOnce()).println(contains("invalid"));
    }

    @Test
    public void whenStateIsDeletingHistoryAndUserInexistentId_thenItShouldLeadBackToDeleteAndPrintError() {
        when(mockReader.nextLine()).thenReturn("15");
        when(mockService.remove(eq(15L))).thenReturn(false);
        State state = State.DELETE_HISTORY_ITEM.collect(mockReader, mockOut, new ConsoleAdapter.DataCollector(mockService));

        Assert.assertSame(State.DELETE_HISTORY_ITEM, state);
        verify(mockOut, atLeastOnce()).println(contains("Unable to remove item."));
    }

    @Test
    public void givenLineIsExitOrQuit_whenIsEndInvoked_thenItShouldReturnTrue() {
        Assert.assertTrue(ConsoleAdapter.isEnd("EXIT"));
        Assert.assertTrue(ConsoleAdapter.isEnd("eXiT"));
        Assert.assertTrue(ConsoleAdapter.isEnd("QUIT"));
        Assert.assertTrue(ConsoleAdapter.isEnd("QUit"));
        Assert.assertTrue(ConsoleAdapter.isEnd(" quit "));
    }

    @Test
    public void givenLineIsNotExitOrQuit_whenIsEndInvoked_thenItShouldReturnFalse() {
        Assert.assertFalse(ConsoleAdapter.isEnd(""));
        Assert.assertFalse(ConsoleAdapter.isEnd("somethingElse"));
    }

    @Test
    public void givenOnlyNumbers_whenIsFloat_thenItShouldReturnTrue() {
        Assert.assertTrue(ConsoleAdapter.isPositiveFloat("5843895934"));
    }

    @Test
    public void givenNumbersWithOnePointInBetween_whenIsFloat_thenItShouldReturnTrue() {
        Assert.assertTrue(ConsoleAdapter.isPositiveFloat("58438.95934"));
        Assert.assertTrue(ConsoleAdapter.isPositiveFloat("58.43895934"));
    }

    @Test
    public void givenNumbersWithMoreThanOnePoint_whenIsFloat_thenItShouldReturnFalse() {
        Assert.assertFalse(ConsoleAdapter.isPositiveFloat("5843895934.."));
        Assert.assertFalse(ConsoleAdapter.isPositiveFloat("..5843895934"));
        Assert.assertFalse(ConsoleAdapter.isPositiveFloat("58438.95.934"));
    }

    @Test
    public void givenAnyNonNumbersExceptPoints_whenIsFloat_thenItShouldReturnFalse() {
        Assert.assertFalse(ConsoleAdapter.isPositiveFloat("58438959.34a"));
    }

    @Test
    public void givenOnlyNumbers_whenIsLong_thenItShouldReturnTrue() {
        Assert.assertTrue(ConsoleAdapter.isPositiveLong("58438"));
        Assert.assertTrue(ConsoleAdapter.isPositiveLong("0"));
    }

    @Test
    public void givenAnyNonNumbers_whenIsLong_thenItShouldReturnFalse() {
        Assert.assertFalse(ConsoleAdapter.isPositiveLong("-58438"));
        Assert.assertFalse(ConsoleAdapter.isPositiveLong("a"));
    }

    @Test
    public void giveValidFullDateTime_whenIsDate_thenItShouldReturnTrue() {
        Assert.assertTrue(ConsoleAdapter.isDate("2021-06-26 10:39:00"));
    }

    @Test
    public void giveValidFullDate_whenIsDate_thenItShouldReturnTrue() {
        Assert.assertTrue(ConsoleAdapter.isDate("2021-06-26"));
    }

    @Test
    public void giveInvalidYear_whenIsDate_thenItShouldReturnFalse() {
        Assert.assertFalse(ConsoleAdapter.isDate("0021-06-26"));
    }

    @Test
    public void giveInvalidMonth_whenIsDate_thenItShouldReturnFalse() {
        Assert.assertFalse(ConsoleAdapter.isDate("2021-00-26"));
        Assert.assertFalse(ConsoleAdapter.isDate("2021-13-26"));
    }

    @Test
    public void giveInvalidDay_whenIsDate_thenItShouldReturnFalse() {
        Assert.assertFalse(ConsoleAdapter.isDate("2021-12-00"));
        Assert.assertFalse(ConsoleAdapter.isDate("2021-12-32"));
    }

    @Test
    public void giveInvalidHour_whenIsDate_thenItShouldReturnFalse() {
        Assert.assertFalse(ConsoleAdapter.isDate("2021-12-31 24:00:00"));
    }

    @Test
    public void giveInvalidMinute_whenIsDate_thenItShouldReturnFalse() {
        Assert.assertFalse(ConsoleAdapter.isDate("2021-12-31 23:60:00"));
    }

    @Test
    public void giveInvalidSecond_whenIsDate_thenItShouldReturnFalse() {
        Assert.assertFalse(ConsoleAdapter.isDate("2021-12-31 23:00:60"));
    }
}