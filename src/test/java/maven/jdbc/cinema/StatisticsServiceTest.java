package maven.jdbc.cinema;


import maven.jdbc.cinema.model.Movie;
import maven.jdbc.cinema.model.Ticket;
import maven.jdbc.cinema.model.enums.GenreType;
import maven.jdbc.cinema.repository.CustomerRepo;
import maven.jdbc.cinema.repository.LoyaltyCardRepo;
import maven.jdbc.cinema.repository.MovieRepo;
import maven.jdbc.cinema.repository.TicketRepo;
import maven.jdbc.cinema.service.CustomerDataService;
import maven.jdbc.cinema.service.HistoryService;
import maven.jdbc.cinema.service.StatisticsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class StatisticsServiceTest
{
    @Mock
    private CustomerRepo customerRepo;
    @Mock
    private TicketRepo ticketRepo;
    @Mock
    private MovieRepo movieRepo;
    @Mock
    private LoyaltyCardRepo loyaltyCardRepo;
    @Mock
    private CustomerDataService customerDataService0;


/*    @InjectMocks
    private CustomerDataService customerDataService;
    @InjectMocks
    private HistoryService historyService;*/
    @InjectMocks
    private StatisticsService statisticsService;

    @Test
    @DisplayName("abc")
    public void test1()
    {
        // GIVEN
        when(ticketRepo.getAllGenreTypesFromTickets())
                .thenReturn(List.of(
                    GenreType.ADVENTURE,
                    GenreType.ADVENTURE,
                    GenreType.ADVENTURE,
                    GenreType.CRIME,
                    GenreType.CRIME,
                    GenreType.ACTION,
                    GenreType.COMEDY
                ));

        // WHEN
        Map<GenreType, Long> groupedGenres = statisticsService.mostPopularGenres();

        // THEN
        Map<GenreType, Long> expectedGroupedGenres = new LinkedHashMap<>();
        expectedGroupedGenres.put(GenreType.ADVENTURE, 3L);
        expectedGroupedGenres.put(GenreType.CRIME, 2L);
        expectedGroupedGenres.put(GenreType.ACTION, 1L);
        expectedGroupedGenres.put(GenreType.COMEDY, 1L);
        Assertions.assertEquals(expectedGroupedGenres, groupedGenres, "TEST OK");
    }

    @Test
    @DisplayName("xyz")
    public void test2()
    {
        // GIVEN
        Map<Integer, Integer> map = new LinkedHashMap<>();
        map.put(3, 3);
        map.put(2, 2);
        map.put(1, 1);

        when(ticketRepo.movieRankings()).thenReturn(map);
        when(movieRepo.findOneMovieTitleById(1)).thenReturn("A");
        when(movieRepo.findOneMovieTitleById(2)).thenReturn("B");
        when(movieRepo.findOneMovieTitleById(3)).thenReturn("C");

        // WHEN
        Map<String, Integer> result = statisticsService.moviePopularity();
        String firstMovieTitle = result.entrySet().stream().findFirst().orElseThrow().getKey();

        // THEN
        Assertions.assertEquals(result.size(), 3, "TEST 1 OK");
        Assertions.assertEquals(firstMovieTitle, "C", "TEST 2 OK");

    }

    @Test
    @DisplayName("Raitings by genre type")
    public void test5()
    {
        // GIVEN
        when(ticketRepo.getAllGenreTypesFromTickets())
                .thenReturn(List.of(
                        GenreType.ADVENTURE,
                        GenreType.ADVENTURE,
                        GenreType.CRIME,
                        GenreType.CRIME,
                        GenreType.ACTION
                ));

        when(ticketRepo.findAll()).thenReturn(List.of(
                Ticket.builder().customerId(1).movieId(1).startDate(LocalDateTime.now().minusDays(10)).build(),
                Ticket.builder().customerId(2).movieId(2).startDate(LocalDateTime.now().minusDays(15)).build(),
                Ticket.builder().customerId(3).movieId(3).startDate(LocalDateTime.now().minusDays(20)).build(),
                Ticket.builder().customerId(4).movieId(4).startDate(LocalDateTime.now().minusDays(25)).build(),
                Ticket.builder().customerId(5).movieId(5).startDate(LocalDateTime.now().minusDays(30)).build()
        ));

        // WHEN
        Map<GenreType, BigDecimal> recieved = statisticsService.raitingsByGenreType();


        // THEN
        Map<GenreType, BigDecimal> expected = new LinkedHashMap<>();
        expected.put(GenreType.ADVENTURE, new BigDecimal("40.00"));
        expected.put(GenreType.CRIME, new BigDecimal("40.00"));
        expected.put(GenreType.ACTION, new BigDecimal("20.00"));

        assertEquals(recieved, expected, "TEST OK");

    }


    @Test
    @DisplayName("The most popular movies in time span: ")
    public void test4()
    {
        LocalDateTime from = LocalDateTime.now().minusDays(100);
        LocalDateTime to = LocalDateTime.now().plusDays(3);
        Map<GenreType, Movie> map = new LinkedHashMap<>();

        when(ticketRepo.ticketsBetweenDates(from, to)).thenReturn(List.of(
                Movie.builder().id(1).title("ABC").genre(GenreType.HORROR).price(new BigDecimal(30)).duration(90).releaseDate(LocalDate.now().minusDays(50)).build(),
                Movie.builder().id(1).title("ABC").genre(GenreType.HORROR).price(new BigDecimal(30)).duration(90).releaseDate(LocalDate.now().minusDays(50)).build(),
                Movie.builder().id(1).title("ABC").genre(GenreType.HORROR).price(new BigDecimal(30)).duration(90).releaseDate(LocalDate.now().minusDays(50)).build(),
                Movie.builder().id(2).title("DEF").genre(GenreType.ADVENTURE).price(new BigDecimal(35)).duration(80).releaseDate(LocalDate.now().minusDays(10)).build(),
                Movie.builder().id(3).title("GHI").genre(GenreType.CRIME).price(new BigDecimal(40)).duration(100).releaseDate(LocalDate.now().minusDays(70)).build(),
                Movie.builder().id(3).title("GHI").genre(GenreType.CRIME).price(new BigDecimal(40)).duration(100).releaseDate(LocalDate.now().minusDays(70)).build(),
                Movie.builder().id(3).title("GHI").genre(GenreType.CRIME).price(new BigDecimal(40)).duration(100).releaseDate(LocalDate.now().minusDays(70)).build(),
                Movie.builder().id(4).title("IJK").genre(GenreType.COMEDY).price(new BigDecimal(25)).duration(120).releaseDate(LocalDate.now().minusDays(25)).build(),
                Movie.builder().id(5).title("LMN").genre(GenreType.MYSTERY).price(new BigDecimal(31)).duration(75).releaseDate(LocalDate.now().minusDays(30)).build()
        ));

        map.put(GenreType.HORROR, Movie.builder().id(1).title("ABC").genre(GenreType.HORROR).price(new BigDecimal(30)).duration(90).releaseDate(LocalDate.now().minusDays(50)).build());
        map.put(GenreType.MYSTERY, Movie.builder().id(5).title("LMN").genre(GenreType.MYSTERY).price(new BigDecimal(31)).duration(75).releaseDate(LocalDate.now().minusDays(30)).build());
        map.put(GenreType.COMEDY, Movie.builder().id(4).title("IJK").genre(GenreType.COMEDY).price(new BigDecimal(25)).duration(120).releaseDate(LocalDate.now().minusDays(25)).build());
        map.put(GenreType.CRIME, Movie.builder().id(3).title("GHI").genre(GenreType.CRIME).price(new BigDecimal(40)).duration(100).releaseDate(LocalDate.now().minusDays(70)).build());
        map.put(GenreType.ADVENTURE, Movie.builder().id(2).title("DEF").genre(GenreType.ADVENTURE).price(new BigDecimal(35)).duration(80).releaseDate(LocalDate.now().minusDays(10)).build());

        Map<GenreType, Movie> resultMap = statisticsService.theMostPopularMoviesInTimespan(from, to);
        assertEquals(resultMap, map, "TEST OK");
    }



















}












