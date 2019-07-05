package maven.jdbc.cinema.parsers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import maven.jdbc.cinema.exceptions.ExceptionCode;
import maven.jdbc.cinema.exceptions.MyException;


import java.io.FileReader;
import java.io.FileWriter;

public class JsonParser<T>
{
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Class<T> type;
    
    // ARG CONSTRUCTOR -----------------------------------------------------
    public JsonParser(Class<T> type)
    {
        this.type = type;
    }
    
    // WRITE JSON FILE -----------------------------------------------------
    public String toJson(T t, String jSonFilename)
    {
        try (FileWriter fileWriter = new FileWriter(jSonFilename))
        {
            if (t == null)
            {
                throw new NullPointerException("OBJECT CLASS T IS NULL");
            }
            
            if (jSonFilename == null)
            {
                throw new NullPointerException("JSON FILENAME IS NULL");
            }
            
            String json = gson.toJson(t);
            fileWriter.write(json);
            return json;
        
        
        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.JSON_PARSER, "TO JSON : " + e.getMessage());
        }
    }
    
    // READ FROM JSON FILE ------------------------------------------------
    public T fromJson(String jSonFilename)
    {
        try (FileReader fileReader = new FileReader(jSonFilename))
        {
            if (jSonFilename == null)
            {
                throw new NullPointerException("JSON FILENAME IS NULL");
            }
            return gson.fromJson(fileReader, type);
            
        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.JSON_PARSER, "FROM JSON : " + e.getMessage());
        }
    }
    
    
    
}
