package maven.jdbc.cinema.repository;



import maven.jdbc.cinema.connection.DbConnection;
import maven.jdbc.cinema.exceptions.ExceptionCode;
import maven.jdbc.cinema.exceptions.MyException;
import maven.jdbc.cinema.model.LoyaltyCard;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LoyaltyCardRepo implements CrudRepository<LoyaltyCard>
{
    private Connection connection = DbConnection.getInstance().getConnection();
    

    @Override
    @SuppressWarnings("Duplicates")
    public void add(LoyaltyCard loyaltyCard)
    {
        if (loyaltyCard == null)
        {
            throw new MyException(ExceptionCode.REPOSITORY_LOYALTY_CARD, "LOYALTY CARD IS NULL");
        }
        final String sql = "insert into loyaltyCards (expirationDate, discount, moviesNumber, creationDate) values (?, ?, ?, ?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setDate(1, Date.valueOf(loyaltyCard.getExpirationDate()));
            preparedStatement.setBigDecimal(2, loyaltyCard.getDiscount());
            preparedStatement.setInt(3, loyaltyCard.getMoviesNumber());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(loyaltyCard.getCreationDate()));
            preparedStatement.execute();
            
        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_LOYALTY_CARD, "ADD: " + e.getMessage());
        }
    }
    

    @Override
    public void update(LoyaltyCard loyaltyCard)
    {
        if (loyaltyCard == null)
        {
            throw new MyException(ExceptionCode.REPOSITORY_LOYALTY_CARD, "LOYALTY CARD IS NULL");
        }
        final String sql = "update loyaltyCards set expirationDate = ?, discount = ?, moviesNumber = ?, creationDate = ? where id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setDate(1, Date.valueOf(loyaltyCard.getExpirationDate()));
            preparedStatement.setBigDecimal(2, loyaltyCard.getDiscount());
            preparedStatement.setInt(3, loyaltyCard.getMoviesNumber());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(loyaltyCard.getCreationDate()));
            preparedStatement.setInt(5, loyaltyCard.getId());
            preparedStatement.execute();
            
        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_LOYALTY_CARD, "UPDATE: " + e.getMessage());
        }
    }

    public void editMovieNumber(LoyaltyCard loyaltyCard, Integer movieNumber)
    {
        if (loyaltyCard == null)
        {
            throw new MyException(ExceptionCode.REPOSITORY_LOYALTY_CARD, "LOYALTY CARD IS NULL!");
        }
        if (movieNumber == null)
        {
            throw new MyException(ExceptionCode.REPOSITORY_LOYALTY_CARD, "MOVIE NUMBER IS NULL!");
        }
        final String sql = "update loyaltyCards set moviesNumber = ? where id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setInt(1, movieNumber);
            preparedStatement.setInt(2, loyaltyCard.getId());
            preparedStatement.execute();
        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_LOYALTY_CARD, "EDIT MOVIE NUMBER: " + e.getMessage());
        }
    }

    public void editDiscount(LoyaltyCard loyaltyCard, BigDecimal discount)
    {
        if (loyaltyCard == null)
        {
            throw new MyException(ExceptionCode.REPOSITORY_LOYALTY_CARD, "LOYALTY CARD IS NULL!");
        }
        if (discount == null)
        {
            throw new MyException(ExceptionCode.REPOSITORY_LOYALTY_CARD, "DISCOUNT IS NULL!");
        }

        final String sql = "update loyaltyCards set discount = ? where id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setBigDecimal(1, discount);
            preparedStatement.setInt(2, loyaltyCard.getId());
            preparedStatement.execute();
        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_LOYALTY_CARD, "LOYALTY CARD: EDIT MOVIE NUMBER: " + e.getMessage());
        }
    }



    @Override
    public void deleteOne(Integer id)
    {
        if (id == null)
        {
            throw new MyException(ExceptionCode.REPOSITORY_LOYALTY_CARD, "LOYALTY CARD ID IS NULL");
        }
        final String sql = "delete from loyaltyCards where id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            
        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_LOYALTY_CARD, "DELETE ONE: " + e.getMessage());
        }
    }
    

    @Override
    public void deleteAll()
    {
        final String sql = "delete from loyaltyCards;";

        try (Statement statement = connection.createStatement())
        {
            statement.execute(sql);
        
        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_LOYALTY_CARD, "LOYALTY_CARD:DELETE_ALL: " + e.getMessage());
        }
    }
    

    @Override
    public Optional<LoyaltyCard> findOneById(Integer id)
    {
        if (id == null)
        {
            throw new MyException(ExceptionCode.REPOSITORY_LOYALTY_CARD, "LOYALTY CARD ID IS NULL");
        }
        final String sql = "select id, expirationDate, discount, moviesNumber, creationDate from loyaltyCards where id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next())
            {
                return Optional.of(LoyaltyCard.builder()
                        .id(resultSet.getInt(1))
                        .expirationDate(resultSet.getDate(2).toLocalDate())
                        .discount(resultSet.getBigDecimal(3))
                        .moviesNumber(resultSet.getInt(4))
                        .creationDate(resultSet.getTimestamp(5).toLocalDateTime())
                        .build());
            }
            return Optional.empty();
        
        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_LOYALTY_CARD, "FIND ONE BY ID: " + e.getMessage());
        }
    }
    

    @Override
    public List<LoyaltyCard> findAll()
    {
        final String sql = "select id, expirationDate, discount, moviesNumber, creationDate from loyaltyCards;";

        try (Statement statement = connection.createStatement())
        {
            ResultSet resultSet = statement.executeQuery(sql);
            List<LoyaltyCard> loyaltyCards = new ArrayList<>();
            
            while (resultSet.next())
            {
                loyaltyCards.add(LoyaltyCard.builder()
                        .id(resultSet.getInt(1))
                        .expirationDate(resultSet.getDate(2).toLocalDate())
                        .discount(resultSet.getBigDecimal(3))
                        .moviesNumber(resultSet.getInt(4))
                        .creationDate(resultSet.getTimestamp(5).toLocalDateTime())
                        .build());
            }
            return loyaltyCards;
        
        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_LOYALTY_CARD, "FIND ALL: " + e.getMessage());
        }
    }


    public Integer countTicketsForCustomer(Integer customerId)
    {
        if (customerId == null)
        {
            throw new MyException(ExceptionCode.REPOSITORY_LOYALTY_CARD, "CUSTOMER ID IS NULL!");
        }
        final String sql = "select count(id) from tickets where customerId = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                return resultSet.getInt(1);
            }
            return null;
        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_LOYALTY_CARD, "COUNT TICKETS FOR CUSTOMER: " + e.getMessage());
        }
    }


    public LoyaltyCard getLoyaltyCardFromOptional(Optional<LoyaltyCard> loyaltyCardOptional)
    {
        if (loyaltyCardOptional.isEmpty())
        {
            System.out.println("LOYALTY CARD OPTIONAL IS EMPTY!");
            return null;
        }
        return loyaltyCardOptional.get();
    }

    public Integer getCardIdFromOptional(Optional<Integer> cardIdOptional)
    {
        if (!cardIdOptional.isPresent())
        {
            System.out.println("CARD ID OPTIONAL IS EMPTY!");
            return null;
        }
        return cardIdOptional.get();
    }


    public Optional<Integer> getTheMostRecentCardId()
    {
        final String sql = "select id from loyaltyCards order by id desc limit 1;";
        try (Statement statement = connection.createStatement())
        {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next())
            {
                return Optional.of(resultSet.getInt(1));
            }
            return Optional.empty();

        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_LOYALTY_CARD, "GET LAST LOYALTY CARD ID: " + e.getMessage());
        }
    }

}



























