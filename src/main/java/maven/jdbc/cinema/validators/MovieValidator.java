package maven.jdbc.cinema.validators;

import maven.jdbc.cinema.model.Movie;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class MovieValidator implements Validator<Movie>
{
    private Map<String, String> errors = new HashMap<>();

    @Override
    public Map<String, String> validate(Movie movie)
    {
        errors.clear();

        if (movie == null)
        {
            errors.put("MOVIE", "NULL");
            return errors;
        }

        if (!isTitleValid(movie))
        {
            errors.put("TITLE: " + movie.getTitle(), "IS NOT VALID" );
        }

        if (!isGenreValid(movie))
        {
            errors.put("GENRE: " + movie.getGenre(), "GENRE OBJECT IS NULL");
        }

        if (!isPriceValid(movie))
        {
            errors.put("PRICE: " + movie.getPrice(), "IS NOT VALID");
        }

        return errors;
    }

    @Override
    public boolean hasErrors()
    {
        return !errors.isEmpty();
    }

    private boolean isTitleValid(Movie movie)
    {
        return movie.getTitle() != null && movie.getTitle().matches("^[A-Z ]+$");
    }

    private boolean isGenreValid(Movie movie)
    {
        return movie.getGenre() != null;
    }

    private boolean isPriceValid(Movie movie)
    {
        return movie.getPrice() != null && movie.getPrice().compareTo(BigDecimal.ZERO) > 0;
    }
}
