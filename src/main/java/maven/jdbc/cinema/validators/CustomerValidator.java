package maven.jdbc.cinema.validators;

import maven.jdbc.cinema.model.Customer;

import java.util.HashMap;
import java.util.Map;

public class CustomerValidator implements Validator<Customer>
{
    private Map<String, String> errors = new HashMap<>();

    @Override
    public Map<String, String> validate(Customer customer)
    {
        errors.clear();

        if(customer == null)
        {
            errors.put("CUSTOMER", "NULL");
        }

        if (!isNameValid(customer))
        {
            errors.put("NAME: " + customer.getName(), "IS NOT VALID");
        }

        if (!isSurnameValid(customer))
        {
            errors.put("SURNAME: " + customer.getSurname(), "IS NOT VALID");
        }

        if (!isAgeValid(customer))
        {
            errors.put("AGE: " + customer.getAge(), "IS NOT VALID");
        }

        if (!isEmailValid(customer))
        {
            errors.put("E-MAIL: " + customer.getEMail(), "IS NOT VALID");
        }
        /*
        if (!isLoyaltyCardIdValid(customer))
        {
            errors.put("loyaltyCardId", "loyaltyCardId is not valid: " + customer.getLoyaltyCardId());
        }   */

        return errors;
    }

    @Override
    public boolean hasErrors()
    {
        return !errors.isEmpty();
    }

    private boolean isNameValid(Customer customer)
    {
        return customer.getName() != null && customer.getName().matches("^[A-Z][a-z]+$");
    }

    private boolean isSurnameValid(Customer customer)
    {
        return customer.getSurname() != null && customer.getSurname().matches("^[A-Z][a-z]+$");
    }

    private boolean isAgeValid(Customer customer)
    {
        return customer.getAge() != null && customer.getAge() > 0;
    }

    private boolean isEmailValid(Customer customer)
    {
        return customer.getEMail() != null && customer.getEMail().matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    /*
    private boolean isLoyaltyCardIdValid(Customer customer)
    {
        return customer.getLoyaltyCardId() != null && customer.getLoyaltyCardId() > 0;
    }   */


}
