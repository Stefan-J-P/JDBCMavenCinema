package maven.jdbc.cinema.repository;

import maven.jdbc.cinema.connection.DbConnection;
import maven.jdbc.cinema.model.enums.GenreType;
import maven.jdbc.cinema.exceptions.ExceptionCode;
import maven.jdbc.cinema.exceptions.MyException;
import maven.jdbc.cinema.model.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieRepo implements CrudRepository<Movie>
{
    private Connection connection = DbConnection.getInstance().getConnection();

    @Override
    @SuppressWarnings("Duplicates")
    public void add(Movie movie)
    {
        if(movie == null)
        {
            throw new MyException(ExceptionCode.REPOSITORY_MOVIE, "MOVIE OBJECT ARG IS NULL!");
        }
        final String sql = "insert into movies (title, genre, price, duration, releaseDate) values (?, ?, ?, ?, ?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setString(1, movie.getTitle());
            preparedStatement.setString(2, movie.getGenre().toString());
            preparedStatement.setBigDecimal(3, movie.getPrice());
            preparedStatement.setInt(4, movie.getDuration());
            preparedStatement.setDate(5, Date.valueOf(movie.getReleaseDate()));
            preparedStatement.execute();
            
        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_MOVIE, "ADD: " + e.getMessage());
        }
    }

    @Override
    @SuppressWarnings("Duplicates")
    public void update(Movie movie)
    {
        if (movie == null)
        {
            throw new MyException(ExceptionCode.REPOSITORY_MOVIE, "MOVIE OBJECT ARG IS NULL!");
        }
        final String sql = "update movies set title = ?, genre= ?, price = ?, duration = ?, releaseDate = ? where id = ?;";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setString(1, movie.getTitle());
            preparedStatement.setString(2, movie.getGenre().toString());
            preparedStatement.setBigDecimal(3, movie.getPrice());
            preparedStatement.setInt(4, movie.getDuration());
            preparedStatement.setDate(5, Date.valueOf(movie.getReleaseDate()));
            preparedStatement.setInt(6, movie.getId());
            preparedStatement.execute();
            
            
        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_MOVIE, "UPDATE: " + e.getMessage());
        }
    }

    @Override
    public void deleteOne(Integer id)
    {
        if(id == null)
        {
            throw new MyException(ExceptionCode.REPOSITORY_MOVIE, "MOVIE ID ARG IS NULL");
        }
        final String sql = "delete from movies where id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_MOVIE, "DELETE: " + e.getMessage());
        }
    
    }

    @Override
    public void deleteAll()
    {
        final String sql = "delete from movies;";
        try (Statement statement = connection.createStatement())
        {
            statement.execute(sql);
        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_MOVIE, "DELETE ALL: " + e.getMessage());
        }
    }

    @Override
    @SuppressWarnings("Duplicates")
    public Optional<Movie> findOneById(Integer id)
    {
        if (id == null)
        {
            throw new MyException(ExceptionCode.REPOSITORY_MOVIE, "MOVIE ID ARG IS NULL!");
        }
        final String sql = "select id, title, genre, price, duration, releaseDate from movies where id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next())
            {
                return Optional.of(Movie.builder()
                        .id(resultSet.getInt(1))
                        .title(resultSet.getString(2))
                        .genre(GenreType.valueOf(resultSet.getString(3)))
                        .price(resultSet.getBigDecimal(4))
                        .duration(resultSet.getInt(5))
                        .releaseDate(resultSet.getDate(6).toLocalDate())
                        .build());
            }
            return Optional.empty();

        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_MOVIE, "FIND ONE BY ID: " + e.getMessage());
        }
    }

    public String findOneMovieTitleById(Integer id)
    {
        if (id == null)
        {
            throw new MyException(ExceptionCode.REPOSITORY_MOVIE, "MOVIE ID ARG IS NULL!");
        }
        final String sql = "select id, title, genre, price, duration, releaseDate from movies where id = ?;";
        String str = "MOVIE TITLE NOT FOUND!";
        Movie movie = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
                movie = Movie.builder()
                        .id(resultSet.getInt(1))
                        .title(resultSet.getString(2))
                        .genre(GenreType.valueOf(resultSet.getString(3)))
                        .price(resultSet.getBigDecimal(4))
                        .duration(resultSet.getInt(5))
                        .releaseDate(resultSet.getDate(6).toLocalDate())
                        .build();

                return  movie.getTitle();
            }
            return str;
        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_MOVIE, "FIND ONE MOVIE TITLE BY ID: " + e.getMessage());
        }
    }

    public Movie findOneByIdNoOptional(Integer id)
    {
        if (id == null)
        {
            throw new MyException(ExceptionCode.REPOSITORY_MOVIE, "MOVIE ID ARG IS NULL!");
        }
        Optional<Movie> movieOptional = findOneById(id);
        Movie myMovie = null;
        if (!movieOptional.isPresent())
        {
            System.out.println("MOVIE OPTIONAL IS EMPTY!");
            return null;
        }
        myMovie = movieOptional.get();
        return myMovie;
    }

    @SuppressWarnings("Duplicates")
    public Optional<Movie> findOneByTitle(String myTitle)
    {
        if (myTitle == null)
        {
            throw new MyException(ExceptionCode.REPOSITORY_MOVIE, "TITLE OBJECT ARG IS NULL!");
        }
        String sql = "select id, title, genre, price, duration, releaseDate from movies where title = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setString(1, myTitle);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                return Optional.of(Movie.builder()
                        .id(resultSet.getInt(1))
                        .title(resultSet.getString(2))
                        .genre(GenreType.valueOf(resultSet.getString(3)))
                        .price(resultSet.getBigDecimal(4))
                        .duration(resultSet.getInt(5))
                        .releaseDate(resultSet.getDate(6).toLocalDate())
                        .build()
                );
            }
            return Optional.empty();

        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_MOVIE, "FIND ONE BY TITLE: " + e.getMessage());
        }
    }

    
    // FIND ALL Movies --------------------------------------------------------------------------------
    @Override
    @SuppressWarnings("Duplicates")
    public List<Movie> findAll()
    {
        final String sql = "select id, title, genre, price, duration, releaseDate from movies;";

        try (Statement statement = connection.createStatement())
        {
            ResultSet resultSet = statement.executeQuery(sql);
            List<Movie> movies = new ArrayList<>();
            
            while(resultSet.next())
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
            throw new MyException(ExceptionCode.REPOSITORY_MOVIE, "FIND ALL: " + e.getMessage());
        }
    }

    public Movie getMovieFromOptional(Optional<Movie> movieOptional)
    {
        Movie myMovie = null;
        if (!movieOptional.isPresent())
        {
            System.out.println("MOVIE OPTIONAL IS EMPTY!");
            return null;
        }
        myMovie = movieOptional.get();
        return myMovie;
    }

    public Integer getMovieIdFromOptional(Optional<Integer> movieIdOptional)
    {
        if (!movieIdOptional.isPresent())
        {
            System.out.println("MOVIE ID OPTIONAL IS EMPTY!");
            return null;
        }
        return movieIdOptional.get();
    }

    public Optional<Integer> getTheMostRecentMovieId()
    {
        final String sql = "select id from movies order by id desc limit 1;";
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
            throw new MyException(ExceptionCode.REPOSITORY_MOVIE, "GET LAST MOVIE ID: " + e.getMessage());
        }
    }


}
