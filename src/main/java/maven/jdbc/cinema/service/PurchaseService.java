package maven.jdbc.cinema.service;

import lombok.RequiredArgsConstructor;
import maven.jdbc.cinema.connection.DbConnection;
import maven.jdbc.cinema.exceptions.ExceptionCode;
import maven.jdbc.cinema.exceptions.MyException;
import maven.jdbc.cinema.model.Customer;
import maven.jdbc.cinema.model.LoyaltyCard;
import maven.jdbc.cinema.model.Movie;
import maven.jdbc.cinema.repository.CustomerRepo;
import maven.jdbc.cinema.repository.LoyaltyCardRepo;
import maven.jdbc.cinema.repository.MovieRepo;
import maven.jdbc.cinema.repository.TicketRepo;
import maven.jdbc.cinema.service.LoyaltyCardDataService;
import maven.jdbc.cinema.service.ScannerService;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
public class PurchaseService
{
    private final ScannerService scannerService;
    private final MovieRepo movieRepo;
    private final LoyaltyCardRepo loyaltyCardRepo;
    private final LoyaltyCardDataService loyaltyCardDataService;


    public Customer getCustomerDataFromTheUser()
    {
        Customer customer = new Customer();
        customer.setName(scannerService.getString("Enter name of the customer: "));
        customer.setSurname(scannerService.getString("Enter surname of the customer: "));
        customer.setEMail(scannerService.getString("Enter E-Mail of the customer: "));
        return customer;
    }

    public List<Movie> showAllAvailableMovies()
    {
        List<Movie> moviesList = movieRepo.findAll();
        return moviesList;
    }

    public void showAllMovies()
    {
        List<Movie> myMovies = showAllAvailableMovies();
        myMovies.forEach(System.out::println);
    }

    public List<LocalTime> generateTimepointsForMovies()
    {
        // wygeneruj strumieniem w ostatecznosci forem
        List<LocalTime> timepoints = new ArrayList<>(Arrays.asList(
                LocalTime.of(9, 0),
                LocalTime.of(9, 30),
                LocalTime.of(10, 0),
                LocalTime.of(10, 30),
                LocalTime.of(11, 0),
                LocalTime.of(11, 30),
                LocalTime.of(12, 0),
                LocalTime.of(12, 30),
                LocalTime.of(13, 0),
                LocalTime.of(13, 30),
                LocalTime.of(14, 0),
                LocalTime.of(14, 30),
                LocalTime.of(15, 0),
                LocalTime.of(15, 30),
                LocalTime.of(16, 0),
                LocalTime.of(16, 30),
                LocalTime.of(17, 0),
                LocalTime.of(17, 30),
                LocalTime.of(18, 0),
                LocalTime.of(18, 30),
                LocalTime.of(19, 0),
                LocalTime.of(19, 30),
                LocalTime.of(20, 0),
                LocalTime.of(20, 30),
                LocalTime.of(21, 0),
                LocalTime.of(21, 30),
                LocalTime.of(22, 0),
                LocalTime.of(22, 30)
                ));

        return timepoints;
    }


    public Integer offerLoyaltyCard(Integer customerId)
    {
        try
        {
            if (customerId == null)
            {
                throw new MyException(ExceptionCode.SERVICE_PURCHASE, "OFFER LOYALTY CARD: CUSTOMER ID ARG IS NULL");
            }
            DbConnection.getInstance().getConnection().setAutoCommit(false);

            // count ticket for customer, can he have loyalty card?
            Integer ticketsNumber = loyaltyCardRepo.countTicketsForCustomer(customerId);
            if (ticketsNumber < 3)
            {
                System.out.println("YOU DO NOT HAVE ENOUGH MOVIES TO GET LOYALTY CARD :-(");
                return null;
            }

            // ask customer does he wants to get loyalty card
            ScannerService scannerService = new ScannerService();
            String answer = scannerService.getString("Do you want to get a loyalty card?");
            LoyaltyCard loyaltyCard = null;

            // if YES: create instance of loyalty card and add it to the database
            if (answer.equalsIgnoreCase("YES"))
            {
                loyaltyCard = LoyaltyCard.builder()
                        .expirationDate(LocalDate.now().plusDays(30))
                        .discount(new BigDecimal(0.7))
                        .moviesNumber(10)
                        .creationDate(LocalDateTime.now())
                        .build();

                loyaltyCardDataService.addLoyaltyCard(loyaltyCard);    // add new loyalty card to the database
                System.out.println("NEW CARD: " + loyaltyCard);

                // get recent card ID and return its value
                Integer recentId = loyaltyCardRepo.getCardIdFromOptional(loyaltyCardRepo.getTheMostRecentCardId());
//                System.out.println("================================");
//                System.out.println("Chosen Loyalty Card ID: " + recentId);
//                System.out.println("================================");

                DbConnection.getInstance().getConnection().commit();
                return recentId;

            }
            else if (answer.equalsIgnoreCase("NO"))
            {
                System.out.println("Thank you! Let us know if you will change your mind!");
                return 0;
            }
            else
            {
                System.out.println("You have entered the wrong value! TRY AGAIN! ;-) ");
                return 0;
            }

        } catch (Exception e)
        {
            try
            {
                DbConnection.getInstance().getConnection().rollback();
            } finally
            {
                throw new MyException(ExceptionCode.SERVICE_PURCHASE, "OFFER LOYALTY CARD: ROLLBACK EXCEPTION" + e.getMessage());
            }
        } finally
        {
            try
            {
                DbConnection.getInstance().getConnection().setAutoCommit(true);

            } catch (Exception e)
            {
                throw new MyException(ExceptionCode.SERVICE_PURCHASE, "OFFER LOYALTY CARD: AUTOCOMMIT OFF EXCEPTION");
            }
        }
    }



}

























