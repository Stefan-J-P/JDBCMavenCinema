package maven.jdbc.cinema.service;

import lombok.RequiredArgsConstructor;
import maven.jdbc.cinema.connection.DbConnection;
import maven.jdbc.cinema.exceptions.ExceptionCode;
import maven.jdbc.cinema.exceptions.MyException;
import maven.jdbc.cinema.model.LoyaltyCard;
import maven.jdbc.cinema.repository.LoyaltyCardRepo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
@RequiredArgsConstructor
public class LoyaltyCardDataService
{
    private final LoyaltyCardRepo loyaltyCardRepo;

    public void addLoyaltyCard(LoyaltyCard loyaltyCard)
    {
        if (loyaltyCard == null)
        {
            throw new MyException(ExceptionCode.SERVICE_LOYALTY_CARD, "ADD LOYALTY CARD EXCEPTION: ");
        }
            loyaltyCardRepo.add(LoyaltyCard.builder()
                    .expirationDate(loyaltyCard.getExpirationDate())
                    .discount(loyaltyCard.getDiscount())
                    .moviesNumber(loyaltyCard.getMoviesNumber())
                    .creationDate(loyaltyCard.getCreationDate())
                    .build());
    }


    public void updateLoyaltyCard(LoyaltyCard loyaltyCard)
    {
        if (loyaltyCard == null)
        {
            throw new MyException(ExceptionCode.SERVICE_LOYALTY_CARD, "UPDATE LOYALTY CARD EXCEPTION: ");
        }

            loyaltyCardRepo.update(LoyaltyCard.builder()
                    .expirationDate(loyaltyCard.getExpirationDate())
                    .discount(loyaltyCard.getDiscount())
                    .moviesNumber(loyaltyCard.getMoviesNumber())
                    .creationDate(loyaltyCard.getCreationDate())
                    .id(loyaltyCard.getId())
                    .build());
    }

    public void deleteOneLoyaltyCard(Integer id)
    {
        if (id == null)
        {
            throw new MyException(ExceptionCode.SERVICE_LOYALTY_CARD, "DELETE ONE LOYALTY CARD: ");
        }
            loyaltyCardRepo.deleteOne(id);
    }


    public void deleteAllCardsFromDataBase()
    {
            loyaltyCardRepo.deleteAll();
    }


    @SuppressWarnings("Duplicates")
    public Integer offerLoyaltyCard(Integer customerId)
    {
        try
        {
            if (customerId == null)
            {
                throw new MyException(ExceptionCode.SERVICE_LOYALTY_CARD, "OFFER LOYALTY CARD: CUSTOMER ID IS NULL");
            }
            DbConnection.getInstance().getConnection().setAutoCommit(false);

            // count ticket for customer, can he have loyalty card?
            Integer ticketsNumber = loyaltyCardRepo.countTicketsForCustomer(customerId);
            if (ticketsNumber < 3)
            {
                System.out.println("NOT ENOUGH MOVIES TO OFFER LOYALTY CARD :-(");
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

                addLoyaltyCard(loyaltyCard);    // add new loyalty card to the database
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
                throw new MyException(ExceptionCode.SERVICE, "DELETE ONE MOVIE EXCEPTION: " + e.getMessage());
            }
        } finally
        {
            try
            {
                DbConnection.getInstance().getConnection().setAutoCommit(true);

            } catch (Exception e)
            {
                throw new MyException(ExceptionCode.SERVICE, "DELETE ONE MOVIE EXCEPTION: AUTOCOMMIT OFF EXCEPTION");
            }
        }
        }
    }



























