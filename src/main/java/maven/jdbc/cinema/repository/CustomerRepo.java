package maven.jdbc.cinema.repository;



import maven.jdbc.cinema.connection.DbConnection;
import maven.jdbc.cinema.exceptions.ExceptionCode;
import maven.jdbc.cinema.exceptions.MyException;
import maven.jdbc.cinema.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class CustomerRepo implements CrudRepository<Customer>
{
    private Connection connection = DbConnection.getInstance().getConnection();

    @Override
    @SuppressWarnings("Duplicates")
    public void add(Customer customer)
    {
        if (customer == null)
        {
            throw new MyException(ExceptionCode.REPOSITORY_CUSTOMER, "CUSTOMER OBJECT ARG IS NULL");
        }
        final String sql = "insert into customers (name, surname, eMail, age) values (?, ?, ?, ?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getSurname());
            preparedStatement.setString(3, customer.getEMail());
            preparedStatement.setInt(4, customer.getAge());
            preparedStatement.execute();
            
        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_CUSTOMER, "ADD: " + e.getMessage());
        }
    }


    @Override
    @SuppressWarnings("Duplicates")
    public void update(Customer customer)
    {
        if (customer == null)
        {
            throw new MyException(ExceptionCode.REPOSITORY_CUSTOMER, "CUSTOMER OBJECT ARG IS NULL");
        }
        final String sql = "update customers set name = ?, surname = ?, eMail = ?, age = ? where id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getSurname());
            preparedStatement.setString(3, customer.getEMail());
            preparedStatement.setInt(4, customer.getAge());
            preparedStatement.setInt(5, customer.getId());
            preparedStatement.execute();
        
        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_CUSTOMER, "UPDATE: " + e.getMessage());
        }
    }

    public void addLoyaltyCardToTheCustomer(Customer customer, Integer cardId)
    {
        if (customer == null)
        {
            throw new MyException(ExceptionCode.REPOSITORY_CUSTOMER, "CUSTOMER OBJECT ARG IS NULL");
        }
        if (cardId == null)
        {
            throw new MyException(ExceptionCode.REPOSITORY_CUSTOMER, "CUSTOMER ID ARG IS NULL");
        }
        final String sql = "update customers set loyaltyCardId = ? where id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setInt(1, cardId);
            preparedStatement.setInt(2, customer.getId());
            preparedStatement.execute();

        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_CUSTOMER, "ADD LOYALTY CARD: " + e.getMessage());
        }
    }
    // ======================================================================================================


    @Override
    public void deleteOne(Integer id)
    {
        if (id == null /*|| id == 0*/)
        {
            throw new MyException(ExceptionCode.REPOSITORY_CUSTOMER, "CUSTOMER ID ARG IS NULL!");
        }
        if (id == 0)
        {
            throw new MyException(ExceptionCode.REPOSITORY_CUSTOMER, "CUSTOMER ID ARG IS ZERO!");
        }


        final String sql = "delete from customers where id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        
        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_CUSTOMER, "DELETE: " + e.getMessage());
        }
    }
    

    @Override
    public void deleteAll()
    {
        final String sql = "delete from customers;";

        try (Statement statement = connection.createStatement())
        {
            statement.execute(sql);
            
        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_CUSTOMER, "DELETE ALL: " + e.getMessage());
        }
    }
    


    @Override
    public Optional<Customer> findOneById(Integer id)
    {
        if (id == null)
        {
            throw new MyException(ExceptionCode.REPOSITORY_CUSTOMER, "CUSTOMER ID ARG IS NULL!");
        }
        final String sql = "select id, name, surname, eMail, age, loyaltyCardId from customers where id = ?;";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setInt(1, id);
            return getCustomer(preparedStatement);

        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_CUSTOMER, "FIND ONE BY ID: " + e.getMessage());
        }
    }


    public Optional<Customer> findOneCustomerByName(String name, String surname)
    {
        if (name == null || surname == null)
        {
            throw new MyException(ExceptionCode.REPOSITORY_CUSTOMER, "NAME OR SURNAME ARG IS NULL");
        }
        String sql = "select id, name, surname, eMail, age from customers where name = ? and surname = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            return getCustomer(preparedStatement);

        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_CUSTOMER, "FIND ONE CUSTOMER BY NAME: " + e.getMessage());
        }
    }


    public Optional<Customer> findOneCustomerByEMail(String email)
    {
        if (email == null)
        {
            throw new MyException(ExceptionCode.REPOSITORY_CUSTOMER, "EMAIL ARG IS NULL!");
        }
        String sql = "select id, name, surname, eMail, age, loyaltyCardId from customers where eMail = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setString(1, email);
            return getCustomer(preparedStatement);

        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_CUSTOMER, "FIND ONE BY EMAIL: " + e.getMessage());
        }

    }


    @Override
    public List<Customer> findAll()
    {
        final String sql = "select id, name, surname, eMail, age, loyaltyCardId from customers;";

        try (Statement statement = connection.createStatement())
        {
            ResultSet resultSet = statement.executeQuery(sql);
            List<Customer> customers = new ArrayList<>();
            
            while (resultSet.next())
            {
                customers.add(Customer.builder()
                        .id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .surname(resultSet.getString(3))
                        .eMail(resultSet.getString(4))
                        .age(resultSet.getInt(5))
                        .loyaltyCardId(resultSet.getInt(6))
                        .build());
            }
            return customers;
            
        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_CUSTOMER, "FIND ALL: " + e.getMessage());
        }
    }


    private Optional<Customer> getCustomer(PreparedStatement preparedStatement) throws SQLException
    {
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next())
        {
            return Optional.of(Customer.builder()
                    .id(resultSet.getInt(1))
                    .name(resultSet.getString(2))
                    .surname(resultSet.getString(3))
                    .eMail(resultSet.getString(4))
                    .age(resultSet.getInt(5))
                    .loyaltyCardId(resultSet.getInt(6))
                    .build());
        }
        return Optional.empty();
    }

    public Customer getCustomerFromOptional(Optional<Customer> customerOptional)
    {
        Customer myCusotmer = null;
        if (!customerOptional.isPresent())
        {
            System.out.println("CUSTOMER OPTIONAL IS EMPTY!");
            return null;
        }
        myCusotmer = customerOptional.get();
        return myCusotmer;
    }

    public Optional<Integer> getTheMostRecentCustomerId()
    {
        final String sql = "select id from customers order by id desc limit 1;";
        try (Statement statement = connection.createStatement())
        {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next())
            {
                return Optional.of(resultSet.getInt(1));
            }
            return Optional.empty();

        } catch (Exception e)
        {
            throw new MyException(ExceptionCode.REPOSITORY_CUSTOMER, "GET LAST CUSTOMER ID: " + e.getMessage());
        }
    }


}
