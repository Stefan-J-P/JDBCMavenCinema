package maven.jdbc.cinema;

import maven.jdbc.cinema.dataGenerators.dataManagers.MovieDataManager;
import maven.jdbc.cinema.service.PurchaseService;
import maven.jdbc.cinema.repository.CustomerRepo;
import maven.jdbc.cinema.repository.LoyaltyCardRepo;
import maven.jdbc.cinema.repository.MovieRepo;
import maven.jdbc.cinema.repository.TicketRepo;
import maven.jdbc.cinema.service.*;
import maven.jdbc.cinema.service.StatisticsService;
import maven.jdbc.cinema.validators.CustomerValidator;
import maven.jdbc.cinema.validators.MovieValidator;

public class App
{
    public static void main(String[] args)
    {
        var customerRepo = new CustomerRepo();
        var movieRepo = new MovieRepo();
        var loyaltyCardRepo = new LoyaltyCardRepo();
        var ticketRepo = new TicketRepo();

        var customerDataService = new CustomerDataService(customerRepo, loyaltyCardRepo);
        var movieDataService = new MovieDataService(movieRepo);
        var loyaltyCardDataService = new LoyaltyCardDataService(loyaltyCardRepo);
        var ticketDataService = new TicketDataService(ticketRepo);

        var scannerService = new ScannerService();
        var purchaseService = new PurchaseService(scannerService, movieRepo, loyaltyCardRepo, loyaltyCardDataService);
        var historyService = new HistoryService(customerRepo, ticketRepo, movieRepo, loyaltyCardRepo,customerDataService);
        var statisticsService = new StatisticsService(ticketRepo, movieRepo);

        var movieDataManager = new MovieDataManager();
        var movieValidator = new MovieValidator();
        var customerValidator = new CustomerValidator();

        new MenuService(
                customerRepo,
                movieRepo,
                loyaltyCardRepo,
                ticketRepo,
                scannerService,
                movieDataManager,
                purchaseService,
                historyService,
                statisticsService,
                customerDataService,
                movieDataService,
                loyaltyCardDataService,
                ticketDataService,
                movieValidator,
                customerValidator
        ).mainMenu();
    }
}
