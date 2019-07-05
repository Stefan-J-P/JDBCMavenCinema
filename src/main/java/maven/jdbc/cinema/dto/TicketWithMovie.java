package maven.jdbc.cinema.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import maven.jdbc.cinema.model.enums.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketWithMovie
{
    private Integer id;
    private Integer movieId;
    private Integer customerId;
    private String title;
    private GenreType genre;
    private BigDecimal yourPrice;
    private BigDecimal regularPrice;
    private LocalDateTime startDate;

}
