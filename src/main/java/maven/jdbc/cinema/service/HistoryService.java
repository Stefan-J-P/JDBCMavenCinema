package maven.jdbc.cinema.service;

import lombok.RequiredArgsConstructor;
import maven.jdbc.cinema.dto.TicketWithMovie;
import maven.jdbc.cinema.model.Customer;
import maven.jdbc.cinema.model.LoyaltyCard;
import maven.jdbc.cinema.model.enums.GenreType;
import maven.jdbc.cinema.exceptions.ExceptionCode;
import maven.jdbc.cinema.exceptions.MyException;
import maven.jdbc.cinema.model.Movie;
import maven.jdbc.cinema.model.Ticket;
import maven.jdbc.cinema.repository.CustomerRepo;
import maven.jdbc.cinema.repository.LoyaltyCardRepo;
import maven.jdbc.cinema.repository.MovieRepo;
import maven.jdbc.cinema.repository.TicketRepo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static j2html.TagCreator.*;
@RequiredArgsConstructor
public class HistoryService
{
    private final CustomerRepo customerRepo;
    private final TicketRepo ticketRepo;
    private final MovieRepo movieRepo;
    private final LoyaltyCardRepo loyaltyCardRepo;
    private final CustomerDataService customerDataService;

    public List<Ticket> ticketsByDateInRange(Integer customerId, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo)
    {
        if (customerId == null)
        {
            throw new MyException(ExceptionCode.SERVICE_HISTORY, "CUSTOMER ID ARG IS NULL!");
        }
        if (dateTimeFrom == null)
        {
            throw new MyException(ExceptionCode.SERVICE_HISTORY, "DATE TIME FROM ARG IS NULL!");
        }
        if (dateTimeTo == null)
        {
            throw new MyException(ExceptionCode.SERVICE_HISTORY, "DATE TIME TO ARG IS NULL!");
        }

        return ticketRepo.findAllTicketsByCustomerId(customerId)
                .stream()
                .filter(t -> t.getStartDate().compareTo(dateTimeFrom) >= 0 && t.getStartDate().compareTo(dateTimeTo) <= 0)
                .collect(Collectors.toList());
    }

    public List<Ticket> ticketsFilteredByAll(Integer customerId, Integer duration, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo, GenreType... genreType)
    {
        List<Ticket> filteredTickets = ticketRepo.ticketsByMovieGenre(customerId, genreType);
        filteredTickets.retainAll(ticketsByDateInRange(customerId, dateTimeFrom, dateTimeTo));
        filteredTickets.retainAll(ticketRepo.ticketsByDuration(customerId, duration));
        return filteredTickets;
    }

    // takes tickets of user
    public List<Ticket> ticketsByCustomerId(Integer customerId)
    {
        if (customerId == null)
        {
            throw new MyException(ExceptionCode.SERVICE_HISTORY, "CUSTOMER ID ARG IS NULL!");
        }
        return ticketRepo.findAllTicketsByCustomerId(customerId);
    }

    public String ticketsByCustomerIdAsHtml(Integer customerId)
    {
        if (customerId == null)
        {
            throw new MyException(ExceptionCode.SERVICE_HISTORY, "CUSTOMER ID ARG IS NULL!");
        }

        boolean hasCard = customerDataService.doesCustomerHaveLoyaltyCard(customerId);
        System.out.println("DOES CUSTOMER HAVE A LOYALTY CARD? :   " + hasCard);
        Customer customer = customerRepo.getCustomerFromOptional(customerRepo.findOneById(customerId));
        System.out.println("CUSTOMER: " + customer);
        LoyaltyCard loyaltyCard = loyaltyCardRepo.getLoyaltyCardFromOptional(loyaltyCardRepo.findOneById(customer.getLoyaltyCardId()));
        if (loyaltyCard != null)
        {
            System.out.println(loyaltyCard.getCreationDate());
        }
        List<TicketWithMovie> ticketWithMoviesList;

        if (hasCard)
        {
            ticketWithMoviesList = ticketsByCustomerId(customerId)
                    .stream()
                    .map(ticket -> {
                        Movie myMovie = movieRepo
                                .findOneById(ticket.getMovieId())
                                .orElseThrow(() -> new MyException(ExceptionCode.SERVICE_HISTORY, "NO MOVIE WITH ID: " + ticket.getMovieId()));
                        return TicketWithMovie.builder()
                                .customerId(customerId)
                                .movieId(myMovie.getId())
                                .title(myMovie.getTitle())
                                .genre(myMovie.getGenre())
                                //.yourPrice(myMovie.getYourPrice().multiply(Objects.requireNonNull(loyaltyCard).getDiscount()))
                                .yourPrice(customerDataService
                                        .isCreationDateBeforeStarDate(Objects.requireNonNull(loyaltyCard).getCreationDate(), ticket.getStartDate())
                                        ? myMovie.getPrice().multiply(Objects.requireNonNull(loyaltyCard).getDiscount())
                                        : myMovie.getPrice()
                                )
                                .regularPrice(myMovie.getPrice())
                                .startDate(ticket.getStartDate())
                                .build(); })
                    .collect(Collectors.toList());
        }
        else
        {
            ticketWithMoviesList = ticketsByCustomerId(customerId)
                    .stream()
                    .map(ticket -> {
                        Movie myMovie = movieRepo
                                .findOneById(ticket.getMovieId())
                                .orElseThrow(() -> new MyException(ExceptionCode.SERVICE_HISTORY, "NO MOVIE WITH ID: " + ticket.getMovieId()));
                        return TicketWithMovie.builder()
                                .customerId(customerId)
                                .movieId(myMovie.getId())
                                .title(myMovie.getTitle())
                                .genre(myMovie.getGenre())
                                .yourPrice(myMovie.getPrice())
                                .regularPrice(myMovie.getPrice())
                                .startDate(ticket.getStartDate())
                                .build(); })
                    .collect(Collectors.toList());
        }

        return html(
                head(
                        title("Customer tickets history")
                ),
                table(
                        thead(
                                th("MOVIE TITLE"),
                                th("MOVIE GENRE"),
                                th("MOVIE YOUR PRICE"),
                                th("MOVIE REGULAR PRICE"),
                                th("START DATE")
                        ),
                        tbody(
                                each(ticketWithMoviesList, ticketWithMovie -> tr(
                                        td(ticketWithMovie.getTitle()),
                                        td(ticketWithMovie.getGenre().toString()),
                                        td(ticketWithMovie.getYourPrice().toString()),
                                        td(ticketWithMovie.getRegularPrice().toString()),
                                        td(ticketWithMovie.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                                ))
                        )
                )
        ).renderFormatted();
    }
}
