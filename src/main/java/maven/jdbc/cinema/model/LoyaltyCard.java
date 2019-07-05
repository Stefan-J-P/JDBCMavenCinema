package maven.jdbc.cinema.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LoyaltyCard
{
    private Integer id;
    private LocalDate expirationDate;
    private BigDecimal discount;
    private Integer moviesNumber;
    private LocalDateTime creationDate;
}
