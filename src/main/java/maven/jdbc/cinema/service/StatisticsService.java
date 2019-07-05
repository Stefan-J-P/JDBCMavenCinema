package maven.jdbc.cinema.service;

import lombok.RequiredArgsConstructor;
import maven.jdbc.cinema.model.enums.GenreType;
import maven.jdbc.cinema.exceptions.ExceptionCode;
import maven.jdbc.cinema.exceptions.MyException;
import maven.jdbc.cinema.model.Customer;
import maven.jdbc.cinema.model.Movie;
import maven.jdbc.cinema.repository.MovieRepo;
import maven.jdbc.cinema.repository.TicketRepo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class StatisticsService
{
    private final TicketRepo ticketRepo;
    private final MovieRepo movieRepo;

    // 1. Pogrupwoanie danych do mapy gdzie kluczem jest gatunek filmu a wartoscia ile osob zakupilo bilet na taki gatunek
    // - dane posortowane od najpopularniejszego gatunku w dol

    @SuppressWarnings("Duplicates")
    public Map<GenreType, Long> mostPopularGenres()
    {
        return ticketRepo.getAllGenreTypesFromTickets()
                .stream()
                .collect(Collectors.groupingBy(x -> x, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));
    }




    // 2. Ranking filmow, czyli posortowane malejaco zestawienie filmow wedlug liczby ogladalnosci
    @SuppressWarnings("Duplicates")
    public Map<String, Integer> moviePopularity()
    {
        Map<String, Integer> resMap = ticketRepo
                    .movieRankings()
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(  // get movie title by using movie id
                            /*e -> movieRepo.findOneById(e.getKey()).orElseThrow(() -> new MyException(ExceptionCode.STATISTICS, "Exception ")).getTitle(),*/
                            e -> movieRepo.findOneMovieTitleById(e.getKey()),
                            Map.Entry::getValue,
                            (v1, v2) -> v1, LinkedHashMap::new));
            return resMap;
    }

    // 3. Przedstawic zestawienie w ktorym pokazesz jak procentowo rozklada sie ogladalnosc
    // ze wzgledu na gatunki filmu
    @SuppressWarnings("Duplicates")
    public Map<GenreType, BigDecimal> raitingsByGenreType()
    {
        List<GenreType> genres = ticketRepo.getAllGenreTypesFromTickets();
        Set<GenreType> myGenres = new HashSet<>(genres);
        myGenres.forEach(System.out::println);
        Integer ticketsNumber = ticketRepo.findAll().size();
        BigDecimal size = BigDecimal.valueOf(ticketsNumber);

        //System.out.println("============= LONG ===================");

        Map<GenreType, Long> resMap = genres
                .stream()
                .collect(Collectors.groupingBy(x -> x, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        LinkedHashMap::new));

        //System.out.println("============= DOUBLE ===================");
        /*
        Map<GenreType, Double> finalMap = resMap
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> (Double.valueOf(e.getValue()) / ticketsNumber) * 100,
                        //e -> ((double)(int)(Double.valueOf(e.getValue()) / ticketsNumber*100)),
                        //e -> Double.parseDouble(new DecimalFormat("0.00").format(Double.valueOf(e.getValue()) / ticketsNumber)),
                        (v1, v2) -> v1, LinkedHashMap::new));
        //finalMap.forEach((k, v) -> System.out.println(k + " " + v));
        */
        //System.out.println("============= BIG DECIMAL ===================");
        Map<GenreType, BigDecimal> ultimateMap = resMap
                .entrySet().stream()
                //.sorted((e1, e2) -> BigDecimal.)
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> BigDecimal.valueOf(e.getValue()).divide(new BigDecimal(ticketsNumber), 2, RoundingMode.DOWN).multiply(new BigDecimal(100)),
                        (v1, v2) -> v1, LinkedHashMap::new));

        //ultimateMap.forEach((k, v) -> System.out.println(k + " " + v));
        return ultimateMap;
    }

    // 4. User podaje przedzial czasowy od do i dostaje informacje ktory film w tym czasie
    // dla kazdego gatunku jest najpopularniejszy
    public Map<GenreType, Movie> theMostPopularMoviesInTimespan(LocalDateTime from, LocalDateTime to)
    {
        if (from == null)
        {
            throw new MyException(ExceptionCode.SERVICE_STATISTICS, "DATE FROM ARG IS NULL");
        }
        if (to == null)
        {
            throw new MyException(ExceptionCode.SERVICE_STATISTICS, "DATE TO ARG IS NULL");
        }

        Map<GenreType, List<Movie>> groupedByGenre =  ticketRepo
                .ticketsBetweenDates(from, to)
                .stream()
                .collect(Collectors.groupingBy(Movie::getGenre));

        Map<GenreType, Map<Movie, Long>> groupedMovies = groupedByGenre
            .entrySet()
            .stream()
            .collect(Collectors.toMap(
                    e -> e.getKey(),
                    e -> e.getValue().stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())))
            );

        Map<GenreType, Movie> mostPopularMovies = groupedMovies
            .entrySet()
            .stream()
            .collect(Collectors.toMap(
                    e -> e.getKey(),
                    e -> e.getValue()
                            .entrySet()
                            .stream()
                            .sorted((ee1, ee2) -> Long.compare(ee2.getValue(), ee1.getValue()))
                            .findFirst().orElse(null).getKey()

            ));
        return mostPopularMovies;
    }

    // 5. Z tych co maja karte lojalnosciowa wybrac klientów ktorzy kupili najwiecej i najmniej biletow
    public Customer maxTicketCustomer()
    {
        return ticketRepo.customersWithLoyaltyCardAndMostTickets().entrySet()
                .stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
                .findFirst()
                .orElse(null)
                .getKey();
    }

    public Customer minTicketCustomer()
    {
        return ticketRepo.customersWithLoyaltyCardAndMostTickets().entrySet()
                .stream()
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .findFirst()
                .orElse(null)
                .getKey();
    }

}

















