package maven.jdbc.cinema;

import maven.jdbc.cinema.dto.TicketWithMovie;
import maven.jdbc.cinema.model.Movie;
import maven.jdbc.cinema.model.enums.GenreType;
import maven.jdbc.cinema.exceptions.ExceptionCode;
import maven.jdbc.cinema.exceptions.MyException;
import maven.jdbc.cinema.model.Customer;
import maven.jdbc.cinema.repository.CustomerRepo;
import maven.jdbc.cinema.repository.LoyaltyCardRepo;
import maven.jdbc.cinema.service.CustomerDataService;
import maven.jdbc.cinema.service.LoyaltyCardDataService;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.stubbing.Answer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CustomerServiceTest
{
    @Mock
    private CustomerRepo customerRepo;
    @Mock
    private CustomerDataService customerDataServiceMock;

    @InjectMocks
    private CustomerDataService customerDataService;

    @Test
    @DisplayName("Add customer with null argument")
    public void test1()
    {
        // GIVEN
        doThrow(new MyException(ExceptionCode.REPOSITORY_CUSTOMER, "CUSTOMER OBJECT ARG IS NULL"))
                .when(customerRepo).add(ArgumentMatchers.isNull());

        // WHEN + THEN
        MyException throwable = assertThrows(MyException.class, () -> customerDataService.addCustomer(null));
        assertTrue(throwable != null, "EXCEPTION IS NOT MyException.class");

        MyException exception = throwable;
        assertEquals(exception.getExceptionInfo().getMessage(), "CUSTOMER OBJECT ARG IS NULL");
        assertEquals(exception.getExceptionInfo().getCode(), ExceptionCode.REPOSITORY_CUSTOMER);
    }

    @Test
    @DisplayName("Add customer with is ANY Customer class argument")
    public void test1a()
    {
        doNothing().when(customerRepo).add(ArgumentMatchers.any(Customer.class));
    }


    // =====================================================================================================================================
    @Test
    @DisplayName("Update one customer with null argument")
    public void test2()
    {
        doThrow(new MyException(ExceptionCode.REPOSITORY_CUSTOMER, "CUSTOMER OBJECT ARG IS NULL"))
                .when(customerRepo).update(ArgumentMatchers.isNull());

        Throwable throwable = assertThrows(MyException.class, () -> customerDataService.updateCustomer(null));
        assertTrue( throwable != null && throwable instanceof MyException, "EXCEPTION IS NOT OF TYP MyException");

        MyException exception = (MyException)throwable;
        assertEquals(exception.getExceptionInfo().getMessage(), "CUSTOMER OBJECT ARG IS NULL");
        assertEquals(exception.getExceptionInfo().getCode(), ExceptionCode.REPOSITORY_CUSTOMER);
    }

    @Test
    @DisplayName("Update one customer with Customer class argument")
    public void test2a()
    {
        doNothing().when(customerRepo).update(ArgumentMatchers.any(Customer.class));
    }

    // =====================================================================================================================================
    @Test
    @DisplayName("Delete one customer with null argument")
    public void test3()
    {
        doThrow(new MyException(ExceptionCode.REPOSITORY_CUSTOMER, "CUSTOMER ID ARG IS NULL"))
                .when(customerRepo).deleteOne(ArgumentMatchers.isNull());
        MyException throwable = assertThrows(MyException.class, () -> customerDataService.deleteOneCustomer(null));
        assertTrue(throwable != null, "EXCEPTION IS NOT MyException.class");

        assertEquals(throwable.getExceptionInfo().getMessage(), "CUSTOMER ID ARG IS NULL");
        assertEquals(throwable.getExceptionInfo().getCode(), ExceptionCode.REPOSITORY_CUSTOMER);
    }

    @Test
    @DisplayName("Delete one customer with Integer ID argument")
    public void test3a()
    {
        doNothing().when(customerRepo).deleteOne(ArgumentMatchers.anyInt());
    }

    // =====================================================================================================================================
    @Test
    @DisplayName("Does customer have loyalty card: null argument")
    public void test6()
    {
        doThrow(new MyException(ExceptionCode.SERVICE_CUSTOMER, "CUSTOMER ID ARG IS NULL"))
                .when(customerDataServiceMock).doesCustomerHaveLoyaltyCard(ArgumentMatchers.isNull());

        MyException throwable = assertThrows(MyException.class, () -> customerDataServiceMock.doesCustomerHaveLoyaltyCard(null));
        assertNotNull(throwable, "EXCEPTION IS NOT MyException.class");

        MyException exception = throwable;
        assertEquals(exception.getExceptionInfo().getMessage(), "CUSTOMER ID ARG IS NULL");
        assertEquals(exception.getExceptionInfo().getCode(), ExceptionCode.SERVICE_CUSTOMER);
    }

    @Test
    @DisplayName("Does customer have loyalty card: False")
    public void test6a()
    {
        Customer c1 = Customer.builder()
                .id(1)
                .name("John")
                .surname("Smith")
                .age(55)
                .eMail("john.smith@gmail.com")
                .loyaltyCardId(null)
                .build();

        when(customerRepo.findOneById(1)).thenReturn(Optional.of(c1));
        when(customerRepo.getCustomerFromOptional(ArgumentMatchers.isNotNull()))
                .thenAnswer(new Answer<Customer>()
                {
                    @Override
                    public Customer answer(InvocationOnMock invocationOnMock) throws Throwable
                    {
                        return (Customer)invocationOnMock.getArgument(0, Optional.class).orElse(null);
                    }
                });

        boolean result = customerDataService.doesCustomerHaveLoyaltyCard(c1.getId());
        assertFalse(result, "TEST OK");
    }

    @Test
    @DisplayName("Does customer have loyalty card: True")
    public void test6b()
    {
        Customer c1 = Customer.builder()
                .id(1)
                .name("John")
                .surname("Smith")
                .age(55)
                .eMail("john.smith@gmail.com")
                .loyaltyCardId(1)
                .build();

        when(customerRepo.findOneById(1)).thenReturn(Optional.of(c1));
        when(customerRepo.getCustomerFromOptional(ArgumentMatchers.isNotNull()))
                .thenAnswer(new Answer<Customer>()
                {
                    @Override
                    public Customer answer(InvocationOnMock invocationOnMock) throws Throwable
                    {
                        return (Customer)invocationOnMock.getArgument(0, Optional.class).orElse(1);
                    }
                });

        boolean result = customerDataService.doesCustomerHaveLoyaltyCard(c1.getId());
        assertTrue(result, "TEST OK");
    }

    // =====================================================================================================================================
    @Test
    @DisplayName("Is creationDate before startDate other arguments")
    public void test7()
    {
        doThrow(new MyException(ExceptionCode.SERVICE_CUSTOMER, "CREATION DATE ARG IS NULL"))
                .when(customerDataServiceMock).isCreationDateBeforeStarDate(ArgumentMatchers.isNull(), ArgumentMatchers.isNull());

        Throwable throwable = assertThrows(MyException.class,
                () -> customerDataServiceMock.isCreationDateBeforeStarDate(null, null));
        assertTrue(throwable instanceof MyException, "EXCEPTION IS NOT MyException.class");

        MyException exception = (MyException) throwable;
        assertEquals(exception.getExceptionInfo().getMessage(), "CREATION DATE ARG IS NULL");
        assertEquals(exception.getExceptionInfo().getCode(), ExceptionCode.SERVICE_CUSTOMER);
    }


    @Test
    @DisplayName("Is creationDate before startDate: proper arguments")
    public void test7a()
    {
        LocalDateTime creation = LocalDateTime.of
                (2019, 02, 11, 12, 00);
        LocalDateTime start = LocalDateTime.of(LocalDate.of
                (2019, 3,25), LocalTime.of(12, 00));
        assertTrue(customerDataService.isCreationDateBeforeStarDate(creation, start));
    }

    @Test
    @DisplayName("Is creationDate before startDate: wrong arguments")
    public void test7b()
    {
        LocalDateTime creation = LocalDateTime.of
                (2019, 04, 27, 12, 00);
        LocalDateTime start = LocalDateTime.of(LocalDate.of
                (2019, 3,25), LocalTime.of(12, 00));
        assertFalse(customerDataService.isCreationDateBeforeStarDate(creation, start));
    }


}
