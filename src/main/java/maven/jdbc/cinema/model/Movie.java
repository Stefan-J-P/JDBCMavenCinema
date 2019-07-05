package maven.jdbc.cinema.model;

import lombok.*;
import maven.jdbc.cinema.model.enums.GenreType;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Movie
{
   private Integer id;
   private String title;
   private GenreType genre;
   private BigDecimal price;
   private Integer duration;
   private LocalDate releaseDate;
}