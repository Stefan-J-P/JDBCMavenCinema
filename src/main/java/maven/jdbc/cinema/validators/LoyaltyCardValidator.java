package maven.jdbc.cinema.validators;

import maven.jdbc.cinema.model.LoyaltyCard;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LoyaltyCardValidator implements Validator<LoyaltyCard>
{
    private Map<String, String> errors = new HashMap<>();

    @Override
    public Map<String, String> validate(LoyaltyCard loyaltyCard)
    {
        errors.clear();
        if (loyaltyCard == null)
        {
            errors.put("LOYALTY CARD", "NULL");
            return errors;
        }

        if (isDateTimeValid(loyaltyCard))
        {
            errors.put("DATE" + loyaltyCard.getExpirationDate(), "IS NOT VALID");
        }




        return null;
    }

    @Override
    public boolean hasErrors()
    {
        return errors.isEmpty();
    }

    /*
    private boolean isDateValid(LoyaltyCard loyaltyCard)
    {
        return loyaltyCard.getExpirationDate() != null &&
                loyaltyCard.getExpirationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }   */



    private boolean isDateTimeValid(LoyaltyCard loyaltyCard)
    {
        return  loyaltyCard.getCreationDate() != null &&
                loyaltyCard.getCreationDate().compareTo(LocalDateTime.now()) <= 0;
    }



}
