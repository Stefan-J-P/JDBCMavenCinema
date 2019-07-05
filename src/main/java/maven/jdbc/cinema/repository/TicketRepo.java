package maven.jdbc.cinema.repository;


import maven.jdbc.cinema.connection.DbConnection;
import maven.jdbc.cinema.model.enums.GenreType;
import maven.jdbc.cinema.exceptions.ExceptionCode;
import maven.jdbc.cinema.exceptions.MyException;
import maven.jdbc.cinema.model.Customer;
import maven.jdbc.cinema.model.Movie;
import maven.jdbc.cinema.model.Ticket;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
//@RequiredArgsConstructor
public class TicketRepo implements CrudRepository<Ticket>
{

    private Connection connection = DbConnection.getInstance().getConnection();
    private CustomerRepo customerRepo = new CustomerRepo();

    @Override
    @SuppressWarnings("Duplicates")
    public void add(Ticket ticket)
    {
        if (ticket == null)
        {
            throw new MyException(ExceptionCode.REPOSITORY_TICKET, "TICKET OBJECT ARG IS NULL!");
        }
        final String sql = "insert into tickets (customerId, movieId, startDate) values (?, ?, ?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setInt(1, ticket.getCustomerId());
            preparedStatement.setInt(2, ticket.getMovieId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(ticket.getStartDate()));
            preparedStatement.execute();

        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_TICKET, "ADD: " + e.getMessage());
        }
    }

    @Override
    @SuppressWarnings("Duplicates")
    public void update(Ticket ticket)
    {
        if (ticket == null)
        {
            throw new MyException(ExceptionCode.REPOSITORY_TICKET, "TICKET OBJECT ARG IS NULL!");
        }
        final String sql = "update tickets set customerId = ?, movieId ? = startDate = ? where id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {

            preparedStatement.setInt(1, ticket.getCustomerId());
            preparedStatement.setInt(2, ticket.getMovieId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(ticket.getStartDate()));
            preparedStatement.execute();

        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_TICKET, "UPDATE: " + e.getMessage());
        }
    }

    @Override
    public void deleteOne(Integer id)
    {
        if (id == null)
        {
            throw new MyException(ExceptionCode.REPOSITORY_TICKET, "TICKET ID ARG IS NULL!");
        }
        final String sql = "delete from tickets where id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_TICKET, "DELETE ONE: " + e.getMessage());
        }
    }


    @Override
    public void deleteAll()
    {
        final String sql = "delete from tickets;";

        try (Statement statement = connection.createStatement())
        {
            statement.execute(sql);

        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_TICKET, "DELETE ALL: " + e.getMessage());
        }
    }


    @Override
    public Optional<Ticket> findOneById(Integer id)
    {
        if (id == null)
        {
            throw new MyException(ExceptionCode.REPOSITORY_TICKET, "TICKET ID ARG IS NULL!");
        }
        final String sql = "select id, customerId, movieId, startDate from tickets where id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                return Optional.of(Ticket.builder()
                        .id(resultSet.getInt(1))
                        .customerId(resultSet.getInt(2))
                        .movieId(resultSet.getInt(3))
                        .startDate(resultSet.getTimestamp(4).toLocalDateTime())
                        .build());
            }
            return Optional.empty();

        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_TICKET, "FIND ONE BY ID: " + e.getMessage());
        }
    }

    @Override
    @SuppressWarnings("Duplicates")
    public List<Ticket> findAll()
    {
        final String sql = "select id, customerId, movieId, startDate from tickets;";

        try (Statement statement = connection.createStatement())
        {
            ResultSet resultSet = statement.executeQuery(sql);

            List<Ticket> tickets = new ArrayList<>();

            while (resultSet.next())
            {
                tickets.add(Ticket.builder()
                        .id(resultSet.getInt(1))
                        .customerId(resultSet.getInt(2))
                        .movieId(resultSet.getInt(3))
                        .startDate(resultSet.getTimestamp(4).toLocalDateTime())
                        .build());
            }
            return tickets;

        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_TICKET, "FIND ALL: " + e.getMessage());
        }
    }

    @SuppressWarnings("Duplicates")
    public List<Ticket> findAllTicketsByCustomerId(Integer customerId)
    {
        if (customerId == null)
        {
            throw new MyException(ExceptionCode.REPOSITORY_TICKET, "CUSTOMER ID ARG IS NULL!");
        }
        final String sql = "select * from tickets where customerId = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Ticket> tickets = new ArrayList<>();
            while (resultSet.next())
            {
                tickets.add(Ticket.builder()
                        .id(resultSet.getInt(1))
                        .customerId(resultSet.getInt(2))
                        .movieId(resultSet.getInt(3))
                        .startDate(resultSet.getTimestamp(4).toLocalDateTime())
                        .build());
            }
            return tickets;

        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY, "TICKET:FIND ALL TICKETS BY CUSTOMER ID: " + e.getMessage());
        }
    }

    public Ticket getTicketFromOptional(Optional<Ticket> ticketOptional)
    {
        Ticket myTicket = null;
        if (!ticketOptional.isPresent())
        {
            System.out.println("TICKET OPTIONAL IS EMPTY!");
            return null;
        }
        myTicket = ticketOptional.get();
        return myTicket;
    }

    public Map<Integer, Integer> movieRankings()
    {
        final String sql =  "select movieId,\n" +
                            "count(customerId) as value_occurance\n" +
                            "from tickets\n" +
                            "group by movieId\n" +
                            "order by value_occurance DESC;";

        try (Statement statement = connection.createStatement())
        {
            ResultSet resultSet = statement.executeQuery(sql);
            Map<Integer, Integer> myMap = new LinkedHashMap<>();

            while (resultSet.next())
            {
                myMap.put(resultSet.getInt(1), resultSet.getInt(2));
            }
            return myMap;

        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_TICKET, "MOVIE RANKINGS: " + e.getMessage());
        }
    }

    public List<Movie> ticketsBetweenDates(LocalDateTime dt1, LocalDateTime dt2)
    {
        if (dt1 == null)
        {
            throw new MyException(ExceptionCode.REPOSITORY_TICKET, "DATE FROM ARG IS NULL!");
        }
        if (dt2 == null)
        {
            throw new MyException(ExceptionCode.REPOSITORY_TICKET, "DATE TO ARG IS NULL!");
        }

        final String sql =  "select m.id, m.title, m.genre, m.price, m.duration, m.releaseDate " +
                            "from tickets t join movies m on t.movieId = m.id where startDate > ? and startDate < ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(dt1));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(dt2));
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Movie> movies = new LinkedList<>();
            while (resultSet.next())
            {
                movies.add(Movie.builder()
                        .id(resultSet.getInt(1))
                        .title(resultSet.getString(2))
                        .genre(GenreType.valueOf(resultSet.getString(3)))
                        .price(resultSet.getBigDecimal(4))
                        .duration(resultSet.getInt(5))
                        .releaseDate(resultSet.getDate(6).toLocalDate())
                        .build());
            }
            return movies;
        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_TICKET, "TICKETS BETWEEN DATES: " + e.getMessage());
        }
    }


    public Map<String, Integer> customersWithMostTickets()
    {
        final String sql =  "select customerId, count(t.customerId) as value_occurance " +
                            "from tickets t join customers c on t.customerId = c.id where c.loyaltyCardId not null " +
                            "group by customerId order by value_occurance desc ;";

        try (Statement statement = connection.createStatement())
        {
            ResultSet resultSet = statement.executeQuery(sql);
            Map<Integer, Integer> tempMap = new LinkedHashMap<>();
            while (resultSet.next())
            {
                tempMap.put(resultSet.getInt(1), resultSet.getInt(2));
            }
            Map<String, Integer> finalMap = tempMap
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            e -> customerRepo.findOneById(e.getKey()).orElseThrow(() -> new MyException(ExceptionCode.REPOSITORY_CUSTOMER, "CUSTOMER WITH MOST TICKETS: ")).getEMail(),
                            Map.Entry::getValue,
                            (v1, v2) -> v1, LinkedHashMap::new));
            return finalMap;

        } catch (Exception e)
        {

            throw new MyException(ExceptionCode.REPOSITORY_TICKET, "CUSTOMERS WITH MOST TICKETS: " + e.getMessage());
        }
    }


    public Map<Customer, Integer> customersWithLoyaltyCardAndMostTickets()
    {
        final String sql =  "select customerId, count(t.customerId) as value_occurance " +
                            "from tickets t join customers c on t.customerId = c.id where c.loyaltyCardId not null " +
                            "group by customerId order by value_occurance desc ;";

        try (Statement statement = connection.createStatement())
        {
            ResultSet resultSet = statement.executeQuery(sql);
            Map<Integer, Integer> tempMap = new LinkedHashMap<>();
            while (resultSet.next())
            {
                tempMap.put(resultSet.getInt(1), resultSet.getInt(2));
            }
            Map<Customer, Integer> finalMap = tempMap
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            e -> customerRepo.findOneById(e.getKey()).orElseThrow(() -> new MyException(ExceptionCode.REPOSITORY_TICKET, "CUSTOMER WITH MOST TICKETS: ")),
                            Map.Entry::getValue,
                            (v1, v2) -> v1, LinkedHashMap::new));
            return finalMap;

        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_TICKET, "CUSTOMERS WITH LOYALTY CARDS AND MOST TICKETS: " + e.getMessage());
        }
    }


    public List<GenreType> getAllGenreTypesFromTickets()
    {
        final String sql =  "select m.genre from movies m " +
                            "join tickets t on t.movieId = m.id " +
                            "order by genre;";

        try (Statement statement = connection.createStatement())
        {
            List<GenreType> genres = new LinkedList<>();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next())
            {
                genres.add(GenreType.valueOf(resultSet.getString(1)));
            }
            return genres;

        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_TICKET, "GET ALL GENRE TYPES FROM TICKET: " + e.getMessage());
        }
    }

    public List<Ticket> findTicketsByCustomerId(Integer customerId)
    {
        if (customerId == null)
        {
            throw new MyException(ExceptionCode.REPOSITORY_TICKET, "USER ID ARG IS NULL!");
        }
        final String sql = "select t.id, t.customerId, t.movieId, t.startDate from tickets join customers c on tickets.customerId = c.id where c.id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Ticket> tickets = new ArrayList<>();
            while (resultSet.next())
            {
                tickets.add(Ticket.builder()
                        .id(resultSet.getInt(1))
                        .customerId(resultSet.getInt(2))
                        .movieId(resultSet.getInt(3))
                        .startDate(resultSet.getTimestamp(4).toLocalDateTime())
                        .build());
            }
            return tickets;

        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_TICKET, "FIND BY CUSTOMER ID: " + e.getMessage());
        }
    }


    @SuppressWarnings("Duplicates")
    public List<Ticket> ticketsByDuration(Integer cusId, Integer myDuration)
    {
        if (cusId == null)
        {
            throw new MyException(ExceptionCode.REPOSITORY_TICKET, "CUSTOMER ID ARGUMENT IS NULL");
        }
        if (myDuration == null)
        {
            throw new MyException(ExceptionCode.REPOSITORY_TICKET, "DURATION ARGUMENT IS NULL!");
        }

        final String sql =  "select t.id, t.customerId, t.movieId, t.startDate from tickets " +
                            "t join movies m on t.movieId = m.id where t.customerId = ? and m.duration >= ? " +
                            "order by duration desc;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setInt(1, cusId);
            preparedStatement.setInt(2, myDuration);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Ticket> tickets = new ArrayList<>();

            while (resultSet.next())
            {
                tickets.add(Ticket.builder()
                        .id(resultSet.getInt(1))
                        .customerId(resultSet.getInt(2))
                        .movieId(resultSet.getInt(3))
                        .startDate(resultSet.getTimestamp(4).toLocalDateTime())
                        .build()
                );
            }
            return tickets;

        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_TICKET, "TICKETS BY DURATION: " + e.getMessage());
        }
    }


    @SuppressWarnings("Duplicates")
    public List<Ticket> ticketsByMovieGenre(Integer customerId, GenreType... genres)
    {
        if (customerId == null)
        {
            throw new MyException(ExceptionCode.REPOSITORY_TICKET, "CUSTOMER ID ARG IS NULL!");
        }
        if (genres == null)
        {
            throw new MyException(ExceptionCode.REPOSITORY_TICKET, "GENRES ARRAY/OBJECT IS NULL");
        }
        String sqlGenreCondition = "( '" + Arrays.stream(genres).map(GenreType::toString).collect(Collectors.joining("', '")) + "' );";

        final String sql =  "select t.id, t.customerId, t.movieId, t.startDate " +
                            "from tickets t join movies m on t.movieId = m.id " +
                            "where t.customerId = ? and m.genre in " + sqlGenreCondition;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Ticket> tickets = new ArrayList<>();

            while (resultSet.next())
            {
                tickets.add(Ticket.builder()
                        .id(resultSet.getInt(1))
                        .customerId(resultSet.getInt(2))
                        .movieId(resultSet.getInt(3))
                        .startDate(resultSet.getTimestamp(4).toLocalDateTime())
                        .build()
                );
            }
            return tickets;

        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_TICKET, "FILTER BY MOVIE GENRE: " + e.getMessage());
        }
    }

    public Optional<Integer> getTheMostRecentTicketId()
    {
        final String sql = "select id from tickets order by id desc limit 1;";
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
            throw new MyException(ExceptionCode.REPOSITORY_TICKET, "GET LAST TICKET ID: " + e.getMessage());
        }
    }






}
































