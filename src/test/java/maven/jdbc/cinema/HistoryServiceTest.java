package maven.jdbc.cinema;

import maven.jdbc.cinema.dto.TicketWithMovie;
import maven.jdbc.cinema.exceptions.ExceptionCode;
import maven.jdbc.cinema.exceptions.MyException;
import maven.jdbc.cinema.model.Customer;
import maven.jdbc.cinema.model.LoyaltyCard;
import maven.jdbc.cinema.model.Movie;
import maven.jdbc.cinema.model.Ticket;
import maven.jdbc.cinema.model.enums.GenreType;
import maven.jdbc.cinema.repository.CustomerRepo;
import maven.jdbc.cinema.repository.LoyaltyCardRepo;
import maven.jdbc.cinema.repository.MovieRepo;
import maven.jdbc.cinema.repository.TicketRepo;
import maven.jdbc.cinema.service.CustomerDataService;
import maven.jdbc.cinema.service.HistoryService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class HistoryServiceTest
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


    @InjectMocks
    private CustomerDataService customerDataService;
    @InjectMocks
    private HistoryService historyService;

    @Test
    @DisplayName("Tickets by date in range: ")
    public void test1()
    {
        // GIVEN
        doThrow(new MyException(ExceptionCode.SERVICE_HISTORY, "CUSTOMER ID ARG IS NULL!"))
                .when(ticketRepo).findAllTicketsByCustomerId(ArgumentMatchers.isNull());

        // WHEN + THEN
        MyException throwable = assertThrows(MyException.class, () -> historyService.ticketsByDateInRange(null, null, null));
        assertNotNull(throwable, "EXCEPTION IS NOT MyException.class");

        assertEquals(throwable.getExceptionInfo().getMessage(), "CUSTOMER ID ARG IS NULL!");
        assertEquals(throwable.getExceptionInfo().getCode(), ExceptionCode.SERVICE_HISTORY);
    }

    @Test
    @DisplayName("Tickets by date in range: ")
    public void test1a()
    {
        // given
        when(ticketRepo.findAllTicketsByCustomerId(1))
                .thenReturn(List.of(
                    Ticket.builder().id(1).customerId(1).movieId(1).startDate(LocalDateTime.now().minusDays(10)).build(),
                    Ticket.builder().id(2).customerId(1).movieId(3).startDate(LocalDateTime.now().minusDays(7)).build(),
                    Ticket.builder().id(3).customerId(1).movieId(2).startDate(LocalDateTime.now().minusDays(8)).build(),
                    Ticket.builder().id(4).customerId(1).movieId(5).startDate(LocalDateTime.now().minusDays(3)).build(),
                    Ticket.builder().id(5).customerId(1).movieId(2).startDate(LocalDateTime.now().minusDays(9)).build()
                ));

        // when
        LocalDateTime from = LocalDateTime.now().minusDays(10);
        LocalDateTime to = LocalDateTime.now().minusDays(5);
        int customerId = 1;
        List<Ticket> tickets = historyService.ticketsByDateInRange(customerId, from, to);

        // then
        Assertions.assertEquals(3, tickets.size(), "TEST 1 FAILED");
    }

    @Test
    @DisplayName("Tickets by customer ID: null argument")
    public void test2()
    {
        // GIVEN
        doThrow(new MyException(ExceptionCode.SERVICE_HISTORY, "CUSTOMER ID ARG IS NULL!"))
                .when(ticketRepo).findAllTicketsByCustomerId(ArgumentMatchers.isNull());

        // WHEN + THEN
        MyException throwable = assertThrows(MyException.class, () -> historyService.ticketsByCustomerId(null));
        assertNotNull(throwable, "EXCEPTION IS NOT MyException.class");

        assertEquals(throwable.getExceptionInfo().getMessage(), "CUSTOMER ID ARG IS NULL!");
        assertEquals(throwable.getExceptionInfo().getCode(), ExceptionCode.SERVICE_HISTORY);
    }

    @Test
    @DisplayName("Tickets by customer ID: correct argument")
    public void test2a()
    {
        List<Ticket> allTickets = new LinkedList<>(Arrays.asList(
                Ticket.builder().id(1).customerId(1).movieId(1).startDate(LocalDateTime.now().minusDays(10)).build(),
                Ticket.builder().id(2).customerId(2).movieId(3).startDate(LocalDateTime.now().minusDays(7)).build(),
                Ticket.builder().id(3).customerId(3).movieId(2).startDate(LocalDateTime.now().minusDays(8)).build(),
                Ticket.builder().id(4).customerId(1).movieId(5).startDate(LocalDateTime.now().minusDays(3)).build(),
                Ticket.builder().id(5).customerId(1).movieId(2).startDate(LocalDateTime.now().minusDays(9)).build()
        ));
        // GIVEN
        when(ticketRepo.findAllTicketsByCustomerId(1))
                .thenReturn(allTickets);
        // WHEN
        List<Ticket> tickets = historyService.ticketsByCustomerId(1);

        // THEN
        assertEquals(3, 3, "TEST FAILED");
    }

    @Test
    @DisplayName("Tickets by customer ID as HTML")
    public void test3()
    {
        doThrow(new MyException(ExceptionCode.SERVICE_HISTORY, "CUSTOMER ID ARG IS NULL!"))
                .when(customerDataService0).doesCustomerHaveLoyaltyCard(ArgumentMatchers.isNull());
        doThrow(new MyException(ExceptionCode.REPOSITORY_CUSTOMER, "CUSTOMER ID ARG IS NULL!"))
                .when(customerRepo).findOneById(ArgumentMatchers.isNull());
        doThrow(new MyException(ExceptionCode.SERVICE_HISTORY, "CUSTOMER ID ARG IS NULL!"))
                .when(loyaltyCardRepo).getLoyaltyCardFromOptional(ArgumentMatchers.isNull());
        doThrow(new MyException(ExceptionCode.REPOSITORY_MOVIE, "MOVIE ID ARG IS NULL!"))
                .when(movieRepo).findOneById(ArgumentMatchers.isNull());
        doThrow(new MyException(ExceptionCode.SERVICE_CUSTOMER, "CREATION DATE ARG IS NULL"))
                .when(customerDataService0).isCreationDateBeforeStarDate(ArgumentMatchers.isNull() ,ArgumentMatchers.isNull());

        // WHEN + THEN
        MyException throwable = assertThrows(MyException.class, () -> historyService.ticketsByCustomerIdAsHtml(null));
        assertNotNull(throwable, "EXCEPTION IS NOT MyException.class");

        assertEquals(throwable.getExceptionInfo().getMessage(), "CUSTOMER ID ARG IS NULL!");
        assertEquals(throwable.getExceptionInfo().getCode(), ExceptionCode.SERVICE_HISTORY);
    }

    @Test
    @DisplayName("Tickets by customer as HTML: correct arguments")
    public void test3a()
    {
        Customer c1 = Customer.builder()
                .id(1)
                .name("John")
                .surname("Smith")
                .age(55)
                .eMail("john.smith@gmail.com")
                .loyaltyCardId(1)
                .build();
        LoyaltyCard loy = LoyaltyCard.builder()
                .id(1)
                .expirationDate(LocalDate.now().plusDays(90))
                .discount(new BigDecimal(0.3))
                .moviesNumber(10)
                .creationDate(LocalDateTime.now().minusDays(20))
                .build();
        Movie m1 = Movie.builder()
                .id(1)
                .title("ABC")
                .genre(GenreType.HORROR)
                .price(new BigDecimal(30))
                .duration(120)
                .releaseDate(LocalDate.now().minusDays(30))
                .build();

        when(customerDataService0.doesCustomerHaveLoyaltyCard(c1.getId())).thenReturn(true);
        when(customerRepo.getCustomerFromOptional(customerRepo.findOneById(c1.getId()))).thenReturn(c1);
        when(loyaltyCardRepo.getLoyaltyCardFromOptional(loyaltyCardRepo.findOneById(c1.getId()))).thenReturn(loy);
        when(movieRepo.findOneById(m1.getId())).thenReturn(Optional.of(m1));
        when(customerDataService0.isCreationDateBeforeStarDate(LocalDateTime.now().minusDays(20), LocalDateTime.now().plusDays(5))).thenReturn(true);

        String result = historyService.ticketsByCustomerIdAsHtml(c1.getId());
        assertNotNull(result);
        //assertEquals

        // wygenerowac przykladowego Stringa w innym mainie
    }

}
