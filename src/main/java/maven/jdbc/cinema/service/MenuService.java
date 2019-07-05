package maven.jdbc.cinema.service;

import lombok.RequiredArgsConstructor;
import maven.jdbc.cinema.connection.DbConnection;
import maven.jdbc.cinema.dataGenerators.dataManagers.MovieDataManager;

import maven.jdbc.cinema.model.enums.*;
import maven.jdbc.cinema.exceptions.ExceptionCode;
import maven.jdbc.cinema.exceptions.MyException;
import maven.jdbc.cinema.model.Customer;
import maven.jdbc.cinema.model.LoyaltyCard;
import maven.jdbc.cinema.model.Movie;
import maven.jdbc.cinema.model.Ticket;
import maven.jdbc.cinema.repository.CustomerRepo;
import maven.jdbc.cinema.repository.LoyaltyCardRepo;
import maven.jdbc.cinema.repository.MovieRepo;
import maven.jdbc.cinema.repository.TicketRepo;
import maven.jdbc.cinema.validators.CustomerValidator;
import maven.jdbc.cinema.validators.MovieValidator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
@RequiredArgsConstructor
public class MenuService
{
    private final CustomerRepo customerRepo;
    private final  MovieRepo movieRepo;
    private final LoyaltyCardRepo loyaltyCardRepo;
    private final TicketRepo ticketRepo;

    private final ScannerService scannerService;
    private final MovieDataManager movieDataManager;
    private final PurchaseService purchaseService;
    private final HistoryService historyService;
    private final StatisticsService statisticsService;

    private final CustomerDataService customerDataService;
    private final MovieDataService movieDataService;
    private final LoyaltyCardDataService loyaltyCardDataService;
    private final TicketDataService ticketDataService;

    private final MovieValidator movieValidator;
    private final CustomerValidator customerValidator;



    public void mainMenu()
    {
        while (true)
        {
            try
            {
                System.out.println("MENU");
                System.out.println("1. CUSTOMER");
                System.out.println("2. MOVIE");
                System.out.println("3. LOYALTY CARD");
                System.out.println("4. TICKET");
                System.out.println("5. PURCHASE SIMULATION");
                System.out.println("6. HISTORY");
                System.out.println("7. STATISTICS");
                System.out.println("8. CLOSE PROGRAM");
                int option = scannerService.getInt("CHOOSE AN OPTION");
                switch (option)
                {
                    case 1: // CUSTOMER CASE --------------------------------------------------------------------------------------------------------------
                        System.out.println("1. ADD ONE CUSTOMER");
                        System.out.println("2. UPDATE ONE CUSTOMER");
                        System.out.println("3. DELETE ONE CUSTOMER");
                        System.out.println("4. DELETE ALL CUSTOMERS");
                        System.out.println("5. FIND ONE CUSTOMER BY ID");
                        System.out.println("6. FIND ONE CUSTOMER BY NAME AND SURNAME");
                        System.out.println("6. FIND ONE CUSTOMER BY E-MAIL");
                        System.out.println("8. ADD EXISTING LOYALTY CARD MANUALLY");
                        System.out.println("9. GO BACK TO THE MAIN MENU");
                        int customerOption = scannerService.getInt("CHOOSE OPTION FOR THE CUSTOMER");
                        switch (customerOption)
                        {
                            case 1: // add one customer
                                customerOption1();
                                break;
                            case 2: // update one customer
                                customerOption2();
                                break;
                            case 3: // delete one customer
                                customerOption3();
                                break;
                            case 4: // delete all customers
                                customerOption4();
                                break;
                            case 5: // find one customer by ID
                                customerOption5();
                                break;
                            case 6: // find one customer by name and surname
                                customerOption6();
                                break;
                            case 7: // find one customer by e-mail
                                customerOption7();
                                break;
                            case 8: // add existing loyalty card
                                customerOption8();
                                break;
                            case 9: // go back to the main menu
                                break;
                        }
                        break;
                    case 2: // MOVIE CASE -----------------------------------------------------------------------------------------------------------------
                        System.out.println("1. ADD ONE MOVIE");
                        System.out.println("2. ADD ONE MOVIE FROM GENERATED FILE");
                        System.out.println("3. ADD MULTIPLE MOVIES FROM GENERATED FILE");
                        System.out.println("4. GENERATE ONE MOVIE IN JSON FORMAT");
                        System.out.println("5. GENERATE NEW JSON FILES WITH MOVIES");
                        System.out.println("6. UPDATE ONE MOVIE");
                        System.out.println("7. DELETE ONE MOVIE");
                        System.out.println("8. DELETE ALL MOVIES");
                        System.out.println("9. GO BACK TO THE MAIN MENU");
                        int movieOption = scannerService.getInt("CHOOSE OPTION FOR THE MOVIE");
                        switch (movieOption)
                        {
                            case 1: // add one movie manually
                                movieOption1();
                                break;
                            case 2: // add one movie from generated file
                                movieOption2();
                                break;
                            case 3: // add multiple movies from generated files automatically
                                movieOption3();
                                break;
                            case 4: // generate one movie in json format
                                movieOption4();
                                break;
                            case 5: // generate new json files with movies
                                movieOption5();
                                break;
                            case 6: // update one movie
                                movieOption6();
                                break;
                            case 7: // delete one movie
                                movieOption7();
                                break;
                            case 8: // delete all movies
                                movieOption8();
                                break;
                            case 9: // go back to main menu
                                break;
                        }
                        break;
                    case 3: // LOYALTY CARD CASE ----------------------------------------------------------------------------------------------------------
                        System.out.println("1. ADD ONE LOYALTY CARD");
                        System.out.println("2. UPDATE ONE LOYALTY CARD");
                        System.out.println("3. DELETE ONE LOYALTY CARD");
                        System.out.println("4. DELETE ALL LOYALTY CARDS");
                        System.out.println("5. EDIT MOVIE NUMBERS IN LOYALTY CARD");
                        System.out.println("6. EDIT DISCOUNT VALUE IN LOYALTY CARD");
                        System.out.println("7. GO BACK TO THE MAIN MENU");
                        int loyaltyCardOption = scannerService.getInt("CHOOSE OPTION FOR THE LOYALTY CARD");
                        switch (loyaltyCardOption)
                        {
                            case 1: // add loyalty card
                                loyaltyCardOption1();
                                break;
                            case 2: // update loyalty card
                                loyaltyCardOption2();
                                break;
                            case 3: // delete one loyalty card
                                loyaltyCardOption3();
                                break;
                            case 4: // delete all loyalty cards
                                loyaltyCardOption4();
                                break;
                            case 5: // edit movies number
                                loyaltyCardOption5();
                                break;
                            case 6: // edit discount value
                                loyaltyCardOption6();
                                break;
                            case 7: // go back to main menu
                                break;
                        }
                        break;
                    case 4: // TICKET CASE ----------------------------------------------------------------------------------------------------------------
                        System.out.println("1. ADD ONE TICKET");
                        System.out.println("2. UPDATE ONE TICKET");
                        System.out.println("3. DELETE ONE TICKET");
                        System.out.println("4. DELETE ALL TICKETS");
                        System.out.println("5. GO BACK TO THE MAIN MENU");
                        int ticketOption = scannerService.getInt("CHOOSE AN OPTION FOR THE TICKET: ");
                        switch (ticketOption)
                        {
                            case 1: // add one ticket
                                ticketOption1();
                                break;

                            case 2: // update one ticket
                                ticketOption2();
                                break;

                            case 3: // delete one ticket
                                ticketOption3();
                                break;

                            case 4: // delete all tickets
                                ticketOption4();
                                //Movie movieResult = ticketRepo.ticketDates(LocalDateTime.of(2019, 3, 1, 12, 0), LocalDateTime.of(2019, 3, 15, 12, 0));
                                //System.out.println("MOVIE: " + movieResult);
                                break;

                            case 5: // go back to the main menu
                                break;
                        }
                        break;

                    case 5: // SIMULATE SHOWING -----------------------------------------------------------------------------------------------------------
                        System.out.println("1. BUY A TICKET: ");
                        System.out.println("2. ASK FOR LOYALTY CARD: ");
                        System.out.println("3. MAKE PURCHASE HAVING LOYLATY CARD: ");
                        System.out.println("4. GO BACK TO MAIN MENU: ");
                        int simulateOption = scannerService.getInt("CHOOSE AN OPTION FOR THE PURCHASE SIMULATION: ");
                        switch (simulateOption)
                        {
                            case 1: // make purchaseService
                                makePurchase();
                                askForLoyaltyCard();
                                break;
                            case 2: // offer loyalty card
                                askForLoyaltyCard();
                                break;
                            case 3: // make purchaseService having a loyalty card
                                makePurchaseHavingLoyaltyCard();
                                break;
                            case 4: // go back to main menu
                                break;
                        }
                        break;

                    case 6: // HISTORY --------------------------------------------------------------------------------------------------------------------
                        System.out.println("1. SHOW ALL CUSTOMER'S TICKETS BY GENRE");
                        System.out.println("2. SHOW ALL CUSTOMER'S TICKETS BY DURATION OF THE MOVIE");
                        System.out.println("3. SHOW ALL CUSTOMER'S TICKETS BY DATE IN RANGE");
                        System.out.println("4. SHOW ALL CUSTOMER'S TICKETS BY ALL THREE ASPECTS");
                        System.out.println("5. SEND HISTORY TO CUSTOMER'S EMAIL");
                        System.out.println("6. GO BACK TO THE MAIN MENU");
                        int historyOption = scannerService.getInt("CHOOSE AN OPTION FOR HISTORY");
                        switch (historyOption)
                        {
                            case 1: // show all customer's tickets by genre
                                historyOption1();
                                break;
                            case 2: // show all customer's tickets by duration of the movie
                                historyOption2();
                                break;
                            case 3: // show all customer's tickets by date in range
                                historyOption3();
                                break;
                            case 4: // show all customer's tickets by all aspects
                                historyOption4();
                                break;
                            case 5: // send historyService to customer's email
                                historyOption5();
                                break;
                            case 6: // go back to the main menu
                                break;
                        }
                        break;
                    case 7: // STATISTICS ----------------------------------------------------------------------------------------------------
                        System.out.println("1. HOW MANY CUSTOMERS BOUGHT TICKET FOR SELECTED MOVIE GENRE: ");
                        System.out.println("2. MOST POPULAR MOVIES WITH NUMBER OF CUSTOMERS WHO SAW EVERY MOVIE: ");
                        System.out.println("3. RAITINGS BY GENRE: ");
                        System.out.println("4. THE MOST POPULAR MOVIE IN SELECTED PERIOD OF TIME: ");
                        System.out.println("5. CUSTOMERS WITH MOST TICKETS AMONG THOSE WITH LOYALTY CARD: ");
                        System.out.println("6. GO BACK TO THE MAIN MENU: ");
                        int statOption = scannerService.getInt("CHOOSE AN OPTION FOR STATISTICS");
                        switch (statOption)
                        {
                            case 1: // how many customers bought ticket for selected movie genre
                                statisticsOption1();
                                break;
                            case 2: // most popular movies with number of customers who saw the every movie
                                statisticsOption2();
                                break;
                            case 3: // raitings by genre
                                statisticsOption3();
                                break;
                            case 4: // the most popular movie in selected period of time
                                statisticsOption4();
                                break;
                            case 5: // among customers with loyalty card who has bought the most tickets
                                statisticsOption5();
                                break;
                            case 6: // return to the main menu
                                break;
                        }
                        break;

                    case 8: // CLOSE PROGRAM --------------------------------------------------------------------------------------------------------------
                        System.out.println("CLOSING PROGRAM");
                        scannerService.close();
                        return;
                }

            } catch (MyException e)
            {
                System.out.println("*************************** EXCEPTION ********************************************");
                System.err.println(e.getExceptionInfo().getCode());
                System.err.println(e.getExceptionInfo().getMessage());
                System.err.println(e.getExceptionInfo().getDateTime());
                System.out.println("**********************************************************************************");
            }
        }
    }


    // 1.) CUSTOMER'S CASE -------------------------------------------------------------------------------------------------------------------
    @SuppressWarnings("Duplicates")
    public void customerOption1()   // add one customer
    {
        Customer customer = new Customer();
        customer.setName(scannerService.getString("Enter customer's name"));
        customer.setSurname(scannerService.getString("Enter customer's surname"));
        customer.setEMail(scannerService.getString("Enter customer's e-Mail"));
        customer.setAge(scannerService.getInt("Enter customer's age"));

        Map<String, String> errors = customerValidator.validate(customer);
        if (!customerValidator.hasErrors())
        {
            customerDataService.addCustomer(customer);
        }
        else
        {
            errors.forEach((k, v) -> System.out.println(k + " " + v));
        }
    }

    @SuppressWarnings("Duplicates")
    public void customerOption2()   // update one customer
    {
        Integer customerIdUpdate = scannerService.getInt("Enter ID of the customer that you want to update:");
        Customer customer = new Customer();
        Optional<Customer> result = customerRepo.findOneById(customerIdUpdate);

        if (result.isPresent())
        {
            customer = result.get();
        }

        customer.setName(scannerService.getString("Enter customer's name"));
        customer.setSurname(scannerService.getString("Enter customer's surname"));
        customer.setEMail(scannerService.getString("Enter customer's e-Mail"));
        customer.setAge(scannerService.getInt("Enter customer's age"));

        Map<String, String> errors = customerValidator.validate(customer);
        if (!customerValidator.hasErrors())
        {
            customerDataService.updateCustomer(customer);
        }
        else
        {
            errors.forEach((k, v) -> System.out.println(k + " " + v));
        }
    }

    public void customerOption3()   // delete one customer
    {
        Integer deleteCustomerId = scannerService.getInt("Enter ID of the customer that you want to delete:");
        customerDataService.deleteOneCustomer(deleteCustomerId);
    }

    public void customerOption4()   // delete all customers
    {
        customerDataService.deleteAllCustomersFromDataBase();
    }

    public void customerOption5()   // find one customer by ID
    {
        Integer customerId = scannerService.getInt("Enter ID of the customer:");
        Optional<Customer> result = customerRepo.findOneById(customerId);
        Customer oneCustomer = new Customer();
        if (result.isPresent())
        {
            oneCustomer = result.get();
        }
        System.out.println(oneCustomer);
    }

    public void customerOption6()   // find one customer by name and surname
    {
        String customerName = scannerService.getString("Enter the name:");
        String customerSurname = scannerService.getString("Enter the surname");
        Optional<Customer> customerOptional = customerRepo.findOneCustomerByName(customerName, customerSurname);
        if (!customerOptional.isPresent())
        {
            System.out.println("Customer optional is empty!");
            return;
        }
        Customer chosenOne = customerOptional.get();
    }

    public void customerOption7()   // find one customer by e-mail
    {
        String email = scannerService.getString("Enter email:");
        Optional<Customer> customerOptional1 = customerRepo.findOneCustomerByEMail(email);
        if (!customerOptional1.isPresent())
        {
            System.out.println("Customer Optional is empty!");
            return;
        }
        Customer chosenCustomer = customerOptional1.get();
        System.out.println(chosenCustomer);
    }

    public void customerOption8() // add existing loyalty card
    {
        Integer cardId = scannerService.getInt("Enter Card ID: ");
        Integer cusId = scannerService.getInt("Enter Customer ID: ");

        Customer customer = customerRepo.getCustomerFromOptional(customerRepo.findOneById(cusId));
        customerRepo.addLoyaltyCardToTheCustomer(customer, cardId);
    }

    // 2.) MOVIE'S CASE --------------------------------------------------------------------------------------------------------------------------
    public void movieOption1()  // add one movie manually
    {
        Movie movie = new Movie();
        movie.setTitle(scannerService.getString("Enter title of the movie"));
        movie.setGenre(scannerService.getGenreType("Enter genre of the movie"));
        movie.setPrice(scannerService.getBigDecimal("Enter yourPrice of the movie"));
        movie.setDuration(scannerService.getInt("Enter duration of the movie"));
        movie.setReleaseDate(scannerService.getDate("Enter release date of the movie"));

        Map<String, String> errors = movieValidator.validate(movie);
        if (!movieValidator.hasErrors())
        {
            movieDataService.addOneMovie(movie);
        }
        else
        {
            errors.forEach((k, v) -> System.out.println(k + " " + v));
        }
    }

    public void movieOption2()  // add one movie from generated file
    {
        String jsonFileName = scannerService.getString("Enter the chosen json file name:");
        Movie movie = movieDataManager.parseMovieFromJsonFile(jsonFileName);
        movieDataService.addOneMovie(movie);
    }

    public void movieOption3()  // add multiple movies from generated files automatically
    {
        int howManyGeneratedMovies = scannerService.getInt("Enter number: how many movies do you want to generate?");
        List<String> jsonFileNameList = MovieDataManager.generateJsonFilesToList(howManyGeneratedMovies);
        movieDataManager.parseMoviesFromJsonFileList(jsonFileNameList);
    }

    public void movieOption4()  // generate one movie in json format
    {
        String movieFilename = scannerService.getString("Enter the name of the generated file");
        movieDataManager.generateOneMovieJsonFile(movieFilename);
    }

    public void movieOption5()  // generate new json files with movies
    {
        Integer howManyMovies = scannerService.getInt("Enter number: how many movies do you want to generate?");
        movieDataManager.generateMoreJsonFiles(howManyMovies);
    }

    public void movieOption6()  // update one movie
    {
        Integer movieIdUpdate = scannerService.getInt("Enter ID of the movie that you want to update:");
        Movie movie = new Movie();
        Optional<Movie> result = movieRepo.findOneById(movieIdUpdate);

        if (result.isPresent())
        {
            movie = result.get();
        }

        movie.setTitle(scannerService.getString("Enter new title of the movie:"));
        movie.setGenre(scannerService.getGenreType("Enter genre of the movie"));
        movie.setPrice(scannerService.getBigDecimal("Enter new yourPrice for the movie:"));
        movie.setDuration(scannerService.getInt("Enter new duration of the movie:"));
        movie.setReleaseDate(scannerService.getDate("Enter new release date of the movie:"));

        Map<String, String> errors = movieValidator.validate(movie);
        if (!movieValidator.hasErrors())
        {
            movieDataService.updateOneMovie(movie);
        }
        else
        {
            errors.forEach((k, v) -> System.out.println(k + " " + v));
        }
    }

    public void movieOption7()  // delete one movie
    {
        Integer movieIdDeleteOne = scannerService.getInt("Enter ID of the movie that you want to delete:");
        movieDataService.deleteOneMovie(movieIdDeleteOne);
    }

    public void movieOption8()  // delete all movies
    {
        movieDataService.deleteAllMoviesFromDataBase();
    }


    // 3.) LOYALTY CARD'S CASE -----------------------------------------------------------------------------------------------------------------
    public void loyaltyCardOption1()    // add loyalty card
    {
        LoyaltyCard loyaltyCard = new LoyaltyCard();
        loyaltyCard.setExpirationDate(LocalDate.now().plusDays(90));
        loyaltyCard.setDiscount(scannerService.generateDiscount());
        loyaltyCard.setMoviesNumber(10);
        loyaltyCard.setCreationDate(LocalDateTime.now());
        loyaltyCardDataService.addLoyaltyCard(loyaltyCard);
    }

    public void loyaltyCardOption2()    // update loyalty card
    {
        Integer loyaltyCardIdUpdate = scannerService.getInt("Enter ID of the card that you want to update:");
        LoyaltyCard loyaltyCard = new LoyaltyCard();
        Optional<LoyaltyCard> result = loyaltyCardRepo.findOneById(loyaltyCardIdUpdate);

        if (result.isPresent())
        {
            loyaltyCard = result.get();
        }

        loyaltyCard.setExpirationDate(scannerService.getDate("Enter new date"));
        loyaltyCard.setDiscount(scannerService.getBigDecimal("Enter new value - Big Decimal:"));
        loyaltyCard.setMoviesNumber(scannerService.getInt("Enter new value - Integer"));
        loyaltyCardDataService.updateLoyaltyCard(loyaltyCard);
    }

    public void loyaltyCardOption3()  // delete one loyalty card
    {
        Integer cardId = scannerService.getInt("Enter ID of the card that you want to delete:");
        loyaltyCardDataService.deleteOneLoyaltyCard(cardId);
    }

    public void loyaltyCardOption4()    // delete all loyalty cards
    {
        loyaltyCardDataService.deleteAllCardsFromDataBase();
    }

    public void loyaltyCardOption5()
    {
        Integer cardId = scannerService.getInt("Enter card ID: ");
        Integer movieNumber = scannerService.getInt("Enter new movies number");
        LoyaltyCard myCard = loyaltyCardRepo.getLoyaltyCardFromOptional(loyaltyCardRepo.findOneById(cardId));
        loyaltyCardRepo.editMovieNumber(myCard, movieNumber);
    }

    public void loyaltyCardOption6()
    {
        Integer cardId = scannerService.getInt("Enter card ID: ");
        BigDecimal discount = scannerService.getBigDecimal("Enter new discount value: ");
        LoyaltyCard myCard = loyaltyCardRepo.getLoyaltyCardFromOptional(loyaltyCardRepo.findOneById(cardId));
        loyaltyCardRepo.editDiscount(myCard, discount);
    }

    // 4.) TICKET'S CASE ------------------------------------------------------------------------------------------------------------------------
    @SuppressWarnings("Duplicates")
    public void ticketOption1() // add one ticket
    {
        Ticket ticket = new Ticket();
        ticket.setCustomerId(scannerService.getInt("Enter ID of the customer"));
        ticket.setMovieId(scannerService.getInt("Enter ID of the movie"));
        ticket.setStartDate(LocalDateTime.of(scannerService.getDate("Enter the date in format yyyy-MM-dd"), scannerService.getTime("Enter the time in format HH:MM")));
        ticketDataService.addOneTicket(ticket);
    }

    @SuppressWarnings("Duplicates")
    public void ticketOption2() // update one ticket
    {
        Integer ticketUpdateId = scannerService.getInt("Enter the ID of the ticket:");
        Ticket ticket = new Ticket();
        Optional<Ticket> result = ticketRepo.findOneById(ticketUpdateId);

        if (!result.isPresent())
        {
            System.out.println("Optional ticket is Empty");
            return;
        }

        ticket = result.get();
        ticket.setCustomerId(scannerService.getInt("Enter ID of the customer"));
        ticket.setMovieId(scannerService.getInt("Enter ID of the movie"));
        ticket.setStartDate(LocalDateTime.of(scannerService.getDate("Enter the date in format yyyy-MM-dd"), scannerService.getTime("Enter the time in format HH:MM")));
        ticketDataService.updateOneTicket(ticket);
    }

    public void ticketOption3() // delete one ticket
    {
        Integer ticketDeleteId = scannerService.getInt("Enter the ID of the ticket that you want to delete:");
        ticketDataService.deleteOneTicket(ticketDeleteId);
    }

    public void ticketOption4() // delete all tickets
    {
        ticketDataService.deleteAllTickets();
    }


    // 5.) SIMULATE PURCHASE CASE ---------------------------------------------------------------------------------------------------------
    @SuppressWarnings("Duplicates")
    public Customer addNewCustomer()
    {
        Customer customer = new Customer();
        customer.setName(scannerService.getString("Enter customer's name"));
        customer.setSurname(scannerService.getString("Enter customer's surname"));
        customer.setEMail(scannerService.getString("Enter customer's e-Mail"));
        customer.setAge(scannerService.getInt("Enter customer's age"));

        Map<String, String> errors = customerValidator.validate(customer);
        if (!customerValidator.hasErrors())
        {
            customerDataService.addCustomer(customer);
        }
        else
        {
            errors.forEach((k, v) -> System.out.println(k + " " + v));
        }

        return customer;
    }

    private Optional<Customer> getCustomer()
    {
        String isNewCustomer = scannerService.getString("Is it a new Customer? (Answer with yes or no)");

        Optional<Customer> customerOptional = Optional.empty();

        if (isNewCustomer.equalsIgnoreCase("YES"))
        {
            customerOptional = Optional.of(addNewCustomer());
        }
        else if (isNewCustomer.equalsIgnoreCase("NO"))
        {
            customerOptional = customerRepo.findOneCustomerByEMail(scannerService.getString("Enter the customer's email:"));
        }
        else
        {
            System.out.println("You have entered the wrong value! TRY AGAIN! ;-) ");
        }
        return customerOptional;
    }

    @SuppressWarnings("Duplicates")
    public void makePurchase()
    {
        try
        {
            DbConnection.getInstance().getConnection().setAutoCommit(false);        // disable autocommit
            // get or add customer
            Optional<Customer> customerOptional = getCustomer();
            if (!customerOptional.isPresent())
            {
                System.out.println("Customer Optional is empty!");
                return;
            }
            Customer chosenCustomer = customerOptional.get();

            // show all available movies
            purchaseService.showAllMovies();
            System.out.println("\n");

            // get movie from database by entering the title
            String title = scannerService.getString("Enter the title of the movie in block letters: ");
            Movie myMovie = movieRepo.getMovieFromOptional(movieRepo.findOneByTitle(title));
            System.out.println("\n");

            // show all timepoints
            List<LocalTime> timepoints = purchaseService.generateTimepointsForMovies(); // generate timepoints
            timepoints.forEach(System.out::println);    // // show available timepoints
            System.out.println("\n");

            // make purchaseService
            purchaseOneTicket(chosenCustomer, myMovie);
            DbConnection.getInstance().getConnection().commit();    // commit changes manually

        } catch (Exception e)
        {
            e.printStackTrace();
            try
            {
                DbConnection.getInstance().getConnection().rollback();  // if something went wrong rollback (cancel) changes
            } finally
            {
                throw new MyException(ExceptionCode.SERVICE_MENU, "MAKE PURCHASE EXCEPTION: " + e.getMessage());
            }
        } finally
        {
            try
            {
                DbConnection.getInstance().getConnection().setAutoCommit(true);     // enable autocommit again
            } catch (Exception ee)
            {
                throw new MyException(ExceptionCode.SERVICE_MENU, "MAKE PURCHASE EXCEPTION: " + ee.getMessage());
            }
        }
    }

    @SuppressWarnings("Duplicates")
    private void purchaseOneTicket(Customer chosenCustomer, Movie myMovie)
    {
            Integer movieId = myMovie.getId();
            LocalDate myDate = scannerService.getDate("Enter date in format yyyy-MM-dd");   // get LocalDate from user
            LocalTime myTime = scannerService.getTime("Enter time in format HH:mm");        // get LocalTime from user

            Ticket myTicket = Ticket.builder()
                    .customerId(chosenCustomer.getId())
                    .movieId(movieId)
                    .startDate(LocalDateTime.of(myDate, myTime))
                    .build();
            ticketRepo.add(myTicket);
    }

    @SuppressWarnings("Duplicates")
    private void makePurchaseHavingLoyaltyCard()
    {
        String email = scannerService.getString("Enter customer's email: ");
        Customer chosenCustomer = customerRepo.getCustomerFromOptional(customerRepo.findOneCustomerByEMail(email));
        // show all available movies
        purchaseService.showAllMovies();
        System.out.println("\n");

        // get movie
        //Movie myMovie = getMovie().orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "MOVIE IS NOT CORRECT"));
        String title = scannerService.getString("Enter the title of the movie in block letters: ");
        Movie myMovie = movieRepo.getMovieFromOptional(movieRepo.findOneByTitle(title));
        System.out.println("\n");

        // make purchaseService
        purchaseOneTicketHavingLoyaltyCard(chosenCustomer, myMovie);
    }

    @SuppressWarnings("Duplicates")
    private void purchaseOneTicketHavingLoyaltyCard(Customer customer, Movie movie)
    {
        if (customer == null)
        {
            throw new MyException(ExceptionCode.SERVICE_MENU, "PURCHASE ONE TICKET HAVING LOYALTY CARD EXCEPTION: CUSTOMER IS NULL!");
        }

        if (movie == null)
        {
            throw new MyException(ExceptionCode.SERVICE_MENU, "PURCHASE ONE TICKET HAVING LOYALTY CARD EXCEPTION: MOVIE IS NULL!");
        }

        try
        {
            DbConnection.getInstance().getConnection().setAutoCommit(false);

            LocalDate myDate = scannerService.getDate("Enter date in format yyyy-MM-dd");       // get date from user
            List<LocalTime> timepoints = purchaseService.generateTimepointsForMovies(); // generate timepoints
            timepoints.forEach(System.out::println);            // show available timepoints
            LocalTime myTime = scannerService.getTime("Enter time in format HH:mm");       // get time from user

            // find the loyalty card by using card id from the customer
            Integer movieId = movie.getId();                    // get movie id from movie passed as argument
            LoyaltyCard myCard = loyaltyCardRepo.getLoyaltyCardFromOptional(loyaltyCardRepo.findOneById(customer.getLoyaltyCardId()));
            BigDecimal priceWithDiscount = myCard.getDiscount().multiply(movie.getPrice());       // temp variable for multiplying the yourPrice by discount value

            Ticket myTicket = Ticket.builder()                  // creating a new ticket which has movieId from new added
                    .customerId(customer.getId())
                    .movieId(movieId)
                    .startDate(LocalDateTime.of(myDate, myTime))
                    .build();
            ticketRepo.add(myTicket);                           // adding new ticket to the database

            LoyaltyCard editedCard = LoyaltyCard.builder()      // create loyalty card for editing purpose
                    .id(myCard.getId())
                    .expirationDate(myCard.getExpirationDate())
                    .discount(myCard.getDiscount())
                    .moviesNumber(myCard.getMoviesNumber() - 1)
                    .creationDate(myCard.getCreationDate())
                    .build();

            loyaltyCardRepo.update(editedCard);                 // update edited loyalty card
            DbConnection.getInstance().getConnection().commit();

            // show ticket, yourPrice and loyalty card balance
            System.out.println("--------------------------------- YOUR TICKET ---------------------------------");
            System.out.println("NORMAL PRICE: " + movie.getPrice() + "\n" + "YOUR PRICE AFTER 30% DISCOUNT: " + priceWithDiscount + "\n");
            System.out.println("CUSTOMER NAME: " + customer.getName() + " " + customer.getSurname() + "\n" + "CUSTOMER'S EMAIL: " + customer.getEMail() + "\n");
            System.out.println("MOVIE: " + movie.getTitle() + "\n" + "START DATE: " + " " + myTicket.getStartDate() + "\n");
            System.out.println("YOUR LOYALTY CARD BALANCE: \n" + "CARD EXPIRES: " + editedCard.getExpirationDate() + "\n" + "NUMBERS OF MOVIES TO WATCH: " + editedCard.getMoviesNumber());

        } catch (Exception e)
        {
            e.printStackTrace();
            try
            {
                DbConnection.getInstance().getConnection().rollback();
            } finally
            {
                throw new MyException(ExceptionCode.SERVICE, "PURCHASE ONE TICKET HAVING LOYALTY CARD EXCEPTION: " + e.getMessage());
            }
        } finally
        {
            try
            {
                DbConnection.getInstance().getConnection().setAutoCommit(true);
            } catch (Exception ee)
            {
                throw new MyException(ExceptionCode.SERVICE, "PURCHASE ONE TICKET HAVING LOYALTY CARD EXCEPTION: " + ee.getMessage());
            }
        }
    }



    public void askForLoyaltyCard()
    {
        System.out.println("---------------------- Asking for loyalty card ------------------------");
        Integer customerId = scannerService.getInt("Enter customer ID: ");
        //String email = scannerService.getString("Enter email:");
        Customer customer = customerRepo.getCustomerFromOptional(customerRepo.findOneById(customerId));
        Integer loyaltyCardId = purchaseService.offerLoyaltyCard(customer.getId());
        customerRepo.addLoyaltyCardToTheCustomer(customer, loyaltyCardId);
    }

    // 6.) HISTORY ----------------------------------------------------------------------------------------------------------------
    public void historyOption1()    // show all customer's tickets by movie genre
    {
        Integer customerId = scannerService.getInt("Enter Customer ID:");

        List<GenreType> myGenresList = new LinkedList<>();
        GenreType myGenre;/* = scannerService.getGenreType("Enter genre type");*/
        //myGenresList.add(myGenre);
        Integer howManyGenres = scannerService.getInt("Enter how many Genre Types you want to verify: ");
        GenreType[] genres = myGenresList.toArray(new GenreType[myGenresList.size()]);

        Integer genreNumber = scannerService.getInt("Enter number of genres: ");
        for (int i = 0; i < howManyGenres; ++i)
        {
            myGenre = scannerService.getGenreType("Enter genre type: ");
        }

        List<Ticket> tickets = ticketRepo.ticketsByMovieGenre(customerId, genres);
        tickets.forEach(System.out::println);
        tickets.forEach(ticket -> ticket.getStartDate().toString());
    }

    @SuppressWarnings("Duplicates")
    public void historyOption2() // show all customer's tickets by duration of the movie
    {
        Integer customerId = scannerService.getInt("Enter Customer ID:");
        Integer duration = scannerService.getInt("Enter duration of the movie: ");
        List<Ticket> tickets = ticketRepo.ticketsByDuration(customerId, duration);
        tickets.forEach(System.out::println);
    }

    @SuppressWarnings("Duplicates")
    public void historyOption3() // show all customer's tickets by date in range
    {
        Integer customerId = scannerService.getInt("Enter the id of the customer: ");
        /*
        LocalDate myDate1 = scannerService.getDate("Enter date A in format yyyy-MM-dd: ");
        LocalTime myTime1 = scannerService.getTime("Enter time A in format HH:MM: ");
        LocalDateTime myDateTime1 = LocalDateTime.of(myDate1, myTime1);

        LocalDate myDate2 = scannerService.getDate("Enter date B in format yyyy-MM-dd: ");
        LocalTime myTime2 = scannerService.getTime("Enter time B in format HH:MM: ");
        LocalDateTime myDateTime2 = LocalDateTime.of(myDate2, myTime2);
        */
        LocalDateTime from = LocalDateTime.of(2019, 1, 1, 12, 0);
        LocalDateTime to = LocalDateTime.of(2019, 5, 15, 12, 0);
        //List<Ticket> tick = ticketRepo.findAllTicketsByCustomerId(12);
        //tick.forEach(System.out::println);
        List<Ticket> tickets = historyService.ticketsByDateInRange(customerId, from, to);
        tickets.forEach(System.out::println);
    }

    @SuppressWarnings("Duplicates")
    public void historyOption4() // show all customer's tickets by all aspects
    {
        Integer customerId = scannerService.getInt("Enter the id of the customer: ");
        Integer duration = scannerService.getInt("Enter the duration of the movie: ");

        LocalDate myDateFrom = scannerService.getDate("Enter date A in format yyyy-MM-dd: ");
        LocalTime myTimeFrom = scannerService.getTime("Enter time A in format HH:MM: ");
        LocalDateTime myDateTimeFrom = LocalDateTime.of(myDateFrom, myTimeFrom);

        LocalDate myDateTo = scannerService.getDate("Enter date B in format yyyy-MM-dd: ");
        LocalTime myTimeTo = scannerService.getTime("Enter time B in format HH:MM: ");
        LocalDateTime myDateTimeTo = LocalDateTime.of(myDateTo, myTimeTo);
        /*
        LocalDateTime from = LocalDateTime.of(2019, 1, 1, 12, 0);
        LocalDateTime to = LocalDateTime.of(2019, 5, 15, 12, 0);
        */
        GenreType genres = scannerService.getGenreType("Enter genre types");

        List<Ticket> tickets = historyService.ticketsFilteredByAll(customerId, duration, myDateTimeFrom, myDateTimeTo, genres);
        tickets.forEach(System.out::println);
    }

    public void historyOption5() // send historyService to customer's email
    {
        final EmailService emailService = new EmailService();
        String email = scannerService.getString("Enter customer's email: ");
        Integer customerId = customerRepo.getCustomerFromOptional(customerRepo.findOneCustomerByEMail(email)).getId();
        //Integer id = scannerService.getInt("Enter ID: ");
        System.out.println("------------------------------");
        System.out.println("CUSTOMER ID:   " + customerId);
        System.out.println("------------------------------");

        String result = historyService.ticketsByCustomerIdAsHtml(customerId);
        //System.out.println(result);
        //emailService.sendMessageAsHtml("stefan.panczyszyn@gmail.com", "HTML MESSAGE", "<h1>HELLO WORLD!!!!!</h1>");
        emailService.sendMessageAsHtml("stefan.panczyszyn@gmail.com", "HTML MESSAGE", result);
    }


    // 7.) STATISTICS ----------------------------------------------------------------------------------------
    public void statisticsOption1()
    {
        //Map<GenreType, Integer> result = statisticsService.howManyCustomersWithGenreType();
        //result.forEach((k, v) -> System.out.println(k + " " + v));
        Map<GenreType, Long> resultMap = statisticsService.mostPopularGenres();
        resultMap.forEach((k, v) -> System.out.println(k + " " + v));
    }

    public void statisticsOption2()
    {
        Map<String, Integer> resultMap = statisticsService.moviePopularity();
        resultMap.forEach((k, v) -> System.out.println(k + " " + v));
    }

    public void statisticsOption3()
    {
        Map<GenreType, BigDecimal> resultMap = statisticsService.raitingsByGenreType();
        resultMap.forEach((k, v) -> System.out.println("KEY: " + k + "\t" + "VALUE: " + v + "%"));
    }

    @SuppressWarnings("Duplicates")
    public void statisticsOption4()
    {
        LocalDate myDateFrom = scannerService.getDate("Enter date A in format yyyy-MM-dd: ");
        LocalTime myTimeFrom = scannerService.getTime("Enter time A in format HH:MM: ");
        LocalDateTime myDateTimeFrom = LocalDateTime.of(myDateFrom, myTimeFrom);

        LocalDate myDateTo = scannerService.getDate("Enter date B in format yyyy-MM-dd: ");
        LocalTime myTimeTo = scannerService.getTime("Enter time B in format HH:MM: ");
        LocalDateTime myDateTimeTo = LocalDateTime.of(myDateTo, myTimeTo);
        /*
        LocalDateTime from = LocalDateTime.now();
        LocalDateTime to = from.plusDays(5);    */

        Map<GenreType, Movie> resultMap = statisticsService.theMostPopularMoviesInTimespan(myDateTimeFrom, myDateTimeTo);
        resultMap.forEach((k, v) -> System.out.println("KEY: " + k + "\n" + "VALUE: " + v + "\n"));
    }

    public void statisticsOption5()
    {
        System.out.println("----------------------------------------------------------------------------------------------------");
        Map<Customer, Integer> resultMap2 = ticketRepo.customersWithLoyaltyCardAndMostTickets();
        resultMap2.forEach((k, v) -> System.out.println(k + "\n" + k.getName() + " " + k.getSurname() + " TICKETS:" + v + "\n"));

        Customer resultMAX = statisticsService.maxTicketCustomer();
        Customer resultMIN = statisticsService.minTicketCustomer();
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("MAX: " + "\n" + resultMAX);
        System.out.println("MIN: " + "\n" + resultMIN);


    }



}



















