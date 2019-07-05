package maven.jdbc.cinema.service;


import lombok.RequiredArgsConstructor;
import maven.jdbc.cinema.connection.DbConnection;
import maven.jdbc.cinema.exceptions.ExceptionCode;
import maven.jdbc.cinema.exceptions.MyException;
import maven.jdbc.cinema.model.Ticket;
import maven.jdbc.cinema.repository.TicketRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
public class TicketDataService
{
    private final TicketRepo ticketRepo;

    public void addOneTicket(Ticket ticket)
    {
        if (ticket == null)
        {
            throw new MyException(ExceptionCode.SERVICE_TICKET, "TICKET OBJECT ARG IS NULL");
        }
            ticketRepo.add(Ticket.builder()
                    .customerId(ticket.getCustomerId())
                    .movieId(ticket.getMovieId())
                    .startDate(ticket.getStartDate())
                    .build());
    }

    public void updateOneTicket(Ticket ticket)
    {
        if (ticket == null)
        {
            throw new MyException(ExceptionCode.SERVICE_TICKET, "TICKET OBJECT ARG IS NULL");
        }
            ticketRepo.update(Ticket.builder()
                    .customerId(ticket.getCustomerId())
                    .movieId(ticket.getMovieId())
                    .startDate(ticket.getStartDate())
                    .build());
    }

    public void deleteOneTicket(Integer id)
    {
        if (id == null)
        {
            throw new MyException(ExceptionCode.SERVICE_TICKET, "TICKET ID ARG IS NULL");
        }
            ticketRepo.deleteOne(id);
    }

    public void deleteAllTickets()
    {
            ticketRepo.deleteAll();
    }

    public List<Ticket> allTicketsWithCustomerId(Integer id)
    {
        if (id == null)
        {
            throw new MyException(ExceptionCode.SERVICE_TICKET, "TICKET ID ARG IS NULL");
        }
        return ticketRepo.findAllTicketsByCustomerId(id);
    }






}













