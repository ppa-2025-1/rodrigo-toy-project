package com.example.demo.model.business;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.demo.dto.NewTicket;
import com.example.demo.model.entity.Ticket;
import com.example.demo.model.entity.Ticket.TicketStatus;
import com.example.demo.model.entity.User;
import com.example.demo.repository.TicketRepository;
import com.example.demo.repository.UserRepository;

@Business
public class TicketBusiness {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TicketBusiness(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    public void abrirChamado(NewTicket newTicket) {
        User user = userRepository.findById(newTicket.userId())
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Ticket ticket = new Ticket();
        ticket.setAction(newTicket.action());
        ticket.setObject(newTicket.object());
        ticket.setDetails(newTicket.details());
        ticket.setStatus(TicketStatus.NOVO);
        ticket.setUser(user);

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

    public void abrirChamadoInicialParaNovoUsuario(User user) {
        Ticket ticket = new Ticket();
        ticket.setAction("CRIAR");
        ticket.setObject("E-MAIL");
        ticket.setDetails("Criar e-mail " + user.getHandle() + "@tads.rg.ifrs.edu.br");
        ticket.setStatus(TicketStatus.NOVO);
        ticket.setUser(user);
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());

        ticketRepository.save(ticket);
    }
}
