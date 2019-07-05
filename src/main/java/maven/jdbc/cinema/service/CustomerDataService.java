package maven.jdbc.cinema.service;

import lombok.RequiredArgsConstructor;
import maven.jdbc.cinema.exceptions.ExceptionCode;
import maven.jdbc.cinema.exceptions.MyException;
import maven.jdbc.cinema.model.Customer;
import maven.jdbc.cinema.repository.CustomerRepo;
import maven.jdbc.cinema.repository.LoyaltyCardRepo;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class CustomerDataService
{
    private final CustomerRepo customerRepo;
    private final LoyaltyCardRepo loyaltyCardRepo;

    public void addCustomer(Customer customer)
    {
        if (customer == null)
        {
            throw new MyException(ExceptionCode.REPOSITORY_CUSTOMER, "CUSTOMER OBJECT ARG IS NULL");
        }
        customerRepo.add(Customer.builder()
                .name(customer.getName())
                .surname(customer.getSurname())
                .eMail(customer.getEMail())
                .age(customer.getAge())
                .build());
    }

    public void updateCustomer(Customer customer)
    {
        if (customer == null)
        {
            throw new MyException(ExceptionCode.REPOSITORY_CUSTOMER, "CUSTOMER OBJECT ARG IS NULL");
        }
        customerRepo.update(Customer.builder()
                .name(customer.getName())
                .surname(customer.getSurname())
                .eMail(customer.getEMail())
                .age(customer.getAge())
                .id(customer.getId())
                .build());
    }

    public void deleteOneCustomer(Integer customerId)
    {
        customerRepo.deleteOne(customerId);
    }

    public void deleteAllCustomersFromDataBase()
    {
        customerRepo.deleteAll();
    }


    public boolean doesCustomerHaveLoyaltyCard(Integer customerId)
    {
        if (customerId == null)
        {
            throw new MyException(ExceptionCode.SERVICE_CUSTOMER, "CUSTOMER ID ARG IS NULL");
        }
        Customer customer = customerRepo.getCustomerFromOptional(customerRepo.findOneById(customerId));
        return customer.getLoyaltyCardId() != null;
    }

    public boolean doesCustomerHaveLoyaltyCard2(Customer customer)
    {
        if (customer == null)
        {
            throw new MyException(ExceptionCode.SERVICE_CUSTOMER, "CUSTOMER ID ARG IS NULL");
        }

        return customer.getLoyaltyCardId() != null;
    }

    public boolean isCreationDateBeforeStarDate(LocalDateTime creationDate, LocalDateTime startDate)
    {
        if (creationDate == null)
        {
            throw new MyException(ExceptionCode.SERVICE_CUSTOMER, "CREATION DATE ARG IS NULL");
        }
        if (startDate == null)
        {
            throw new MyException(ExceptionCode.SERVICE_CUSTOMER, "START DATE ARG IS NULL");
        }
        return creationDate.isBefore(startDate);
    }
}