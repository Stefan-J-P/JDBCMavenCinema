package maven.jdbc.cinema.dataGenerators.dataManagers;

import maven.jdbc.cinema.model.enums.GenreType;
import maven.jdbc.cinema.model.enums.MovieTitle;
import maven.jdbc.cinema.exceptions.ExceptionCode;
import maven.jdbc.cinema.exceptions.MyException;
import maven.jdbc.cinema.model.Movie;
import maven.jdbc.cinema.parsers.JsonParser;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class MovieDataManager
{
    private final static Integer MIN_PRICE = 5;
    private final static Integer MAX_PRICE = 50;
    private final static Integer MIN_DURATION = 60;
    private final static Integer MAX_DURATION = 240;

    private final static Random rnd = new Random();
    /*
    public List<String> titles = new ArrayList<>(Arrays.asList(
            "BATMAN",
            "SUPERMAN",
            "IRON MAN",
            "AVENGERS",
            "WONDER WOMAN",
            "HULK",
            "CAPITAN AMERICA",
            "THOR",
            "SANDMAN",
            "ANT MAN"

    ));     */

    // /** + Enter
    // TOOLS + GENERATE JAVA DOC
    /**
     *
     * @param enumType
     * @param <T>
     * @return
     */
    public static <T> T getEnumType(Class<T> enumType)
    {
        try
        {
            // table of Enums, from which data will be generated
            T[]values = enumType.getEnumConstants();
            // returning generated value
            return values[rnd.nextInt(values.length)];
            
        } catch (Exception e)
        {
            e.printStackTrace();
            throw new MyException(ExceptionCode.DATA_MANAGER, "GET ENUM TYPE EXCEPTION: " + e.getMessage());
        }
    }
    

    public static String getMovieTitle()
    {
        try
        {
            List<String> titles = new ArrayList<>(Arrays.asList(
                    "BATMAN",
                    "SUPERMAN",
                    "IRON MAN",
                    "AVENGERS",
                    "WONDER WOMAN",
                    "HULK",
                    "CAPITAN AMERICA",
                    "THOR",
                    "SANDMAN",
                    "ANT MAN",
                    "ROCK",
                    "DIE HARD",
                    "DR STRANGE",
                    "SPACE JAM",
                    "JAMES BOND",
                    "SHERLOCK HOLMES"
            ));

            EnumSet<MovieTitle> movieTitle = EnumSet.of(getEnumType(MovieTitle.class));
            //String movieRandomValue = movieTitle.stream().map(Enum::toString).collect(Collectors.joining());
            String movieRandomValue = titles.get(rnd.nextInt(titles.size()));
            //System.out.println("MOVIE: " + movieRandomValue);
            return movieRandomValue;
            
        } catch (Exception e)
        {
            e.printStackTrace();
            throw new MyException(ExceptionCode.DATA_MANAGER, "GET MOVIE TITLES EXCEPTION: " + e.getMessage());
        }
    }
    


    public static GenreType getGenre()
    {
        try
        {
            /*EnumSet<GenreType> genreTypes = EnumSet.of(getEnumType(GenreType.class));
            //String genreRandomValue = genreTypes.stream().map(Enum::toString).collect(Collectors.joining());
            GenreType
            //System.out.println("GENRE: " + genreRandomValue);
            return genreRandomValue;*/

            //Random rnd = new Random();
            GenreType[] genreTypes = GenreType.values();
            return genreTypes[rnd.nextInt(genreTypes.length)];
            
        } catch (Exception e)
        {
            e.printStackTrace();
            throw new MyException(ExceptionCode.DATA_MANAGER, "GET GENRE TYPE EXCEPTION: " + e.getMessage());
        }
    }
    

    private static BigDecimal generatePrice()
    {
        try
        {
            Integer number = rnd.nextInt(MAX_PRICE - MIN_PRICE + 1) + MIN_PRICE;
            Integer decimals = rnd.nextInt(100);
            return new BigDecimal(number + "." + decimals);
        
        } catch (Exception e)
        {
            e.printStackTrace();
            throw new MyException(ExceptionCode.DATA_MANAGER, "GENERATE PRICE EXCEPTION: " + e.getMessage());
        }
    }
    

    private static Integer generateDuration()
    {
        try
        {
            return rnd.nextInt(MAX_DURATION - MIN_DURATION + 1) + MIN_DURATION;
        
        } catch (Exception e)
        {
            e.printStackTrace();
            throw new MyException(ExceptionCode.DATA_MANAGER, "GENERATE DURATION EXCEPTION: " + e.getMessage());
        }
    }
    

    private static LocalDate generateLocalDate()
    {
        try
        {
            Long minDay = LocalDate.of(2019, 1, 1).toEpochDay();
            Long maxDay = LocalDate.of(2019, 12, 31).toEpochDay();
            Long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
            LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
            return randomDate;
        
        } catch (Exception e)
        {
            e.printStackTrace();
            throw new MyException(ExceptionCode.DATA_MANAGER, "GENERATE LOCAL DATE EXCEPTION: " + e.getMessage());
        }
    }
    

    public void generateOneMovieJsonFile(String filename)
    {
        try
        {
            Movie movie = Movie.builder()
                    .title(getMovieTitle())
                    //.theGenre(Enum.valueOf(Movie::getTheGenre))   // ENUM
                    .genre(getGenre())
                    .price(generatePrice())
                    .duration(generateDuration())
                    .releaseDate(generateLocalDate())
                    .build();
            
            //System.out.println(movie);
            JsonParser<Movie> movieToJson = new JsonParser<>(Movie.class);
            final String jsonFilename = filename;
            String jSon = movieToJson.toJson(movie, jsonFilename);
            
        } catch (Exception e)
        {
            e.printStackTrace();
            throw new MyException(ExceptionCode.DATA_MANAGER, "GENERATE THE MOVIE EXCEPTION: " + e.getMessage());
        }
    }

    @SuppressWarnings("Duplicates")
    public void generateMoreJsonFiles(Integer number)
    {
        try
        {
    
            for (int i = 0; i < number; ++i)
            {


                Movie movie = Movie.builder()
                        .title(getMovieTitle())
                        //.theGenre(Enum.valueOf(g))    // ENUM
                        .genre(getGenre())
                        .price(generatePrice())
                        .duration(generateDuration())
                        .releaseDate(generateLocalDate())
                        .build();

                JsonParser<Movie> movieToJson = new JsonParser<>(Movie.class);
                final String jsonFilename = "Movie" + i + ".json";
                String jSon = movieToJson.toJson(movie, jsonFilename);
            }
        
        } catch (Exception e)
        {
                e.printStackTrace();
            throw new MyException(ExceptionCode.DATA_MANAGER, "GENERATE THE MOVIE EXCEPTION: " + e.getMessage());
        }
    }

    @SuppressWarnings("Duplicates")
    public static List<String> generateJsonFilesToList(int number)
    {
        try
        {
            List<String> jsonFiles = new ArrayList<>();
            for (int i = 0; i < number; ++i)
            {
                Movie movie = Movie.builder().
                        title(getMovieTitle())
                        .genre(getGenre())
                        .price(generatePrice())
                        .duration(generateDuration())
                        .releaseDate(generateLocalDate())
                        .build();

                JsonParser<Movie> movieToJson = new JsonParser<>(Movie.class);
                final String jsonFilename = "Movie" + i + ".json";
                String jSon = movieToJson.toJson(movie, jsonFilename);
                jsonFiles.add(jsonFilename);
            }
            return jsonFiles;

        } catch (Exception e)
        {
            e.printStackTrace();
            throw new MyException(ExceptionCode.DATA_MANAGER, "GENERATE THE MOVIE EXCEPTION: " + e.getMessage());
        }
    }


    public Movie parseMovieFromJsonFile(String filename)
    {
        JsonParser<Movie> movieJsonParser = new JsonParser<>(Movie.class);
        Movie movieFromJson = movieJsonParser.fromJson(filename);
        //System.out.println(movieFromJson);
        return movieFromJson;
    }



    public void parseMoviesFromJsonFileList(List<String> jsonFiles)
    {
        /*try
        {
            if (jsonFiles.isEmpty())
            {
                throw new NullPointerException("JSON FILES LIST IS EMPTY");
            }

            // MovieDataService movieDataService = new MovieDataService(movieRepo);
            JsonParser<Movie> movieJsonParser = new JsonParser<>(Movie.class);
            for (int i = 0; i < jsonFiles.size(); ++i)
            {
                Movie movieFromJson = movieJsonParser.fromJson(jsonFiles.get(i));
                movieDataService.addOneMovie(movieFromJson);
            }

        } catch (Exception e)
        {
            e.printStackTrace();
            throw new MyException(ExceptionCode.DATA_MANAGER, "PARSE MOVIES FROM JSON FILE LIST EXCEPTION: " + e.getMessage());
        }*/



    }
    
    
}
