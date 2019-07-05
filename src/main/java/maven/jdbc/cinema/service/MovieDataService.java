package maven.jdbc.cinema.service;

import lombok.RequiredArgsConstructor;
import maven.jdbc.cinema.connection.DbConnection;
import maven.jdbc.cinema.exceptions.ExceptionCode;
import maven.jdbc.cinema.exceptions.MyException;
import maven.jdbc.cinema.model.Movie;
import maven.jdbc.cinema.repository.MovieRepo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
@RequiredArgsConstructor
public class MovieDataService
{
    private final MovieRepo movieRepo;
    /*
    public MovieDataService(MovieRepo movieRepo)
    {
        this.movieRepo = movieRepo;
    }   */

    public void addOneMovie(Movie movie)
    {
        if (movie == null)
        {
            throw new MyException(ExceptionCode.SERVICE_MOVIE, "MOVIE OBJECT ARG IS NULL");
        }
        movieRepo.add(Movie.builder()
                .title(movie.getTitle())
                .genre(movie.getGenre())
                .price(movie.getPrice())
                .duration(movie.getDuration())
                .releaseDate(movie.getReleaseDate())
                .build()
        );
    }

    public void updateOneMovie(Movie movie)
    {
            if (movie == null)
            {
                throw new MyException(ExceptionCode.SERVICE_MOVIE, "MOVIE OBJECT ARG IS NULL");
            }

            movieRepo.update(Movie.builder()
                    .title(movie.getTitle())
                    .genre(movie.getGenre())
                    .price(movie.getPrice())
                    .duration(movie.getDuration())
                    .releaseDate(movie.getReleaseDate())
                    .id(movie.getId())
                    .build());
    }

    @SuppressWarnings("Duplicates")
    public void deleteOneMovie(Integer movieId)
    {
            if (movieId == null)
            {
                throw new MyException(ExceptionCode.SERVICE_MOVIE, "MOVIE ID ARG IS NULL");
            }
            movieRepo.deleteOne(movieId);
    }

    public void deleteAllMoviesFromDataBase()
    {
            movieRepo.deleteAll();
    }




}
