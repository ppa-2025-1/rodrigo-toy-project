package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.model.entity.Ticket;
import com.example.model.entity.Ticket.TicketStatus;

@Repository
public interface TicketRepository extends BaseRepository<Ticket, Integer> {

    Optional<Ticket> findById(Integer id);

    List<Ticket> findByStatus(TicketStatus status);

    List<Ticket> findByUserId(Integer userId);

    List<Ticket> findByActionContainingIgnoreCase(String action);

    List<Ticket> findByObjectContainingIgnoreCase(String object); 
}
