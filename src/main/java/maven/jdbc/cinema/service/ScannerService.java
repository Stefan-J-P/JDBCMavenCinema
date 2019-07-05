package maven.jdbc.cinema.service;

import lombok.RequiredArgsConstructor;
import maven.jdbc.cinema.model.enums.GenreType;
import maven.jdbc.cinema.exceptions.ExceptionCode;
import maven.jdbc.cinema.exceptions.MyException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

//@RequiredArgsConstructor
public class ScannerService
{
    private Scanner sc = new Scanner(System.in);
    BigDecimal DISCOUNT = new BigDecimal(0.15);

    public String getString(String message)
    {
        try
        {
            System.out.println(message);
            return sc.nextLine();
        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.SERVICE_SCANNER, "GET STRING EXCEPTION: " + e.getMessage());
        }
    }

    public Integer getInt(String message)
    {
        System.out.println(message);
        String text = sc.nextLine();
        if (!text.matches("\\d+"))
        {
            throw new MyException(ExceptionCode.SERVICE_SCANNER, "GET INT VALUE EXCEPTION: " + text);
        }
        return Integer.parseInt(text);
    }

    public BigDecimal getBigDecimal(String message)
    {
        try
        {
            System.out.println(message);
            return new BigDecimal(sc.nextLine());
        
        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.SERVICE_SCANNER, "GET BIG DECIMAL EXCEPTION: " + e.getMessage());
        }
    
    }

    public LocalDate getDate(String message)
    {
        try
        {
            System.out.println(message);
            return LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            
        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.SERVICE_SCANNER, "GET DATE EXCEPTION: " + e.getMessage());
        }
    }

    public LocalTime getTime(String message)
    {
        try
        {
            System.out.println(message);
            return LocalTime.parse(sc.nextLine(), DateTimeFormatter.ofPattern("HH:mm"));

        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.SERVICE_SCANNER, "GET TIME EXCEPTION: " + e.getMessage());
        }
    }

    public LocalDate generateExpirationDate()
    {
        try
        {
            return LocalDate.now().plusDays(30);

        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.SERVICE_SCANNER, "GENERATE DATE EXCEPTION: " + e.getMessage());
        }
    }

    public BigDecimal generateDiscount()
    {
        try
        {
            BigDecimal min = new BigDecimal(0.15);
            BigDecimal max = new BigDecimal(0.5);
            BigDecimal rnd = min.add(new BigDecimal(Math.random()).multiply(max.subtract(min)));

            return rnd.setScale(2, BigDecimal.ROUND_HALF_UP);
        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.SERVICE_SCANNER, "GENERATE DISCOUNT EXCEPTION: " + e.getMessage());
        }
    }

    public GenreType getGenreType(String message)
    {
        System.out.println("Enter genre:");
        String text = sc.nextLine();

        final String regex = "(" + Arrays.stream(GenreType.values())
                .map(GenreType::toString)
                .collect(Collectors.joining("|")) + ")";

        if (!text.matches(regex))
        {
            throw new MyException(ExceptionCode.SERVICE_SCANNER, "GENRE TYPE VALUE IS NOT CORRECT: " + text);
        }
        return GenreType.valueOf(text);
    }

    public void close ()
    {
        if (sc != null)
        {
            sc.close();
        }
    }
    
    
}


















