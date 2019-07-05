package maven.jdbc.cinema.model;

import lombok.*;
import maven.jdbc.cinema.exceptions.ExceptionCode;
import maven.jdbc.cinema.exceptions.MyException;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Builder
@ToString
public class Ticket
{
    private Integer id;
    private Integer customerId;
    private Integer movieId;
    private LocalDateTime startDate;
    /*
    private BigDecimal yourPrice;
    private final BigDecimal reduced = new BigDecimal("0.5");

    public void setYourPrice(BigDecimal yourPrice)
    {
        if (yourPrice.compareTo(BigDecimal.ZERO) > 0)
        {
            this.yourPrice = yourPrice;
        }
        else
        {
            System.out.println("WRONG VALUE! Price must be greater than zero");
            this.yourPrice = yourPrice.abs();
        }

    }   */

    public void setId(Integer id)
    {
        this.id = id;
    }

    public void setCustomerId(Integer customerId)
    {
        this.customerId = customerId;
    }

    public void setMovieId(Integer movieId)
    {
        this.movieId = movieId;
    }

    public void setStartDate(LocalDateTime startDate)
    {
        if (startDate.isBefore(LocalDateTime.now()))
        {
            throw new MyException(ExceptionCode.REPOSITORY, "Date and Time cannot be from the past!");
        }
        this.startDate = startDate;
    }
}
