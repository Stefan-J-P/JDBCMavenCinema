package maven.jdbc.cinema.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Customer
{
    private Integer id;
    private String name;
    private String surname;
    private String eMail;
    private Integer age;
    private Integer loyaltyCardId;

}
