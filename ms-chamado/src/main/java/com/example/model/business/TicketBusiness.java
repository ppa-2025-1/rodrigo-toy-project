package com.example.model.business;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.dto.NewTicket;
import com.example.model.entity.Ticket;
import com.example.model.entity.Ticket.TicketStatus;
import com.example.repository.TicketRepository;

@Business
public class TicketBusiness {

    private final TicketRepository ticketRepository;

    public TicketBusiness(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public void abrirChamado(NewTicket newTicket) {
        if (newTicket.userId() == null) {
            throw new IllegalArgumentException("ID do usuário não pode ser nulo");
        }

        Ticket ticket = new Ticket();
        ticket.setAction(newTicket.action());
        ticket.setObject(newTicket.object());
        ticket.setDetails(newTicket.details());
        ticket.setStatus(TicketStatus.NOVO);
        ticket.setUserId(newTicket.userId());

        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());

        ticketRepository.save(ticket);
    }

    public void alterarStatus(Integer ticketId, TicketStatus novoStatus) {
        Ticket ticket = ticketRepository.findById(ticketId)
            .orElseThrow(() -> new IllegalArgumentException("Chamado não encontrado"));

        TicketStatus statusAtual = ticket.getStatus();

        boolean transicaoValida =
            (statusAtual == TicketStatus.NOVO && (novoStatus == TicketStatus.ANDAMENTO || novoStatus == TicketStatus.CANCELADO)) ||
            (statusAtual == TicketStatus.ANDAMENTO && (novoStatus == TicketStatus.RESOLVIDO || novoStatus == TicketStatus.CANCELADO));

        if (!transicaoValida) {
            throw new IllegalArgumentException("Transição de status inválida");
        }

        ticket.setStatus(novoStatus);
        ticket.setUpdatedAt(LocalDateTime.now());

        ticketRepository.save(ticket);
    }
}
