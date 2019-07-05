package maven.jdbc.cinema;

import maven.jdbc.cinema.dto.TicketWithMovie;
import maven.jdbc.cinema.repository.CustomerRepo;
import maven.jdbc.cinema.repository.LoyaltyCardRepo;
import maven.jdbc.cinema.service.CustomerDataService;
import maven.jdbc.cinema.service.LoyaltyCardDataService;
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
import maven.jdbc.cinema.model.enums.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)

public class LoyaltyCardServiceTest
{

    @Mock
    private CustomerRepo customerRepo;
    @Mock
    private LoyaltyCardRepo loyaltyCardRepo;

    @InjectMocks
    private CustomerDataService customerDataService;
    @InjectMocks
    private LoyaltyCardDataService loyaltyCardDataService;

    @Test
    @DisplayName("Offer loyalty card with not null customer id")
    public void test2()
    {
        // GIVEN
        when(loyaltyCardRepo.countTicketsForCustomer(1)).thenReturn(3);
        when(loyaltyCardRepo.getTheMostRecentCardId()).thenReturn(Optional.of(10));

        when(loyaltyCardRepo.getCardIdFromOptional(ArgumentMatchers.isNotNull()))
                .thenAnswer(new Answer<Integer>()
                {
                    @Override
                    public Integer answer(InvocationOnMock invocationOnMock) throws Throwable
                    {
                        return invocationOnMock.getArgument(0);
                    }
                });
        TicketWithMovie t1 = TicketWithMovie.builder()
                .movieId(1)
                .customerId(18)
                .title("BATMAN")
                .genre(GenreType.HORROR)
                .yourPrice(new BigDecimal(17.47))
                .startDate(LocalDateTime.of(LocalDate.of(2019, 3,25), LocalTime.of(12, 00)))
                .build();

        // WHEN
        boolean result = customerDataService.isCreationDateBeforeStarDate(LocalDateTime.of(2018, 05, 1, 12, 00), t1.getStartDate());
        // THEN
        assertTrue(result, "TEST FAILED");
    }
}
