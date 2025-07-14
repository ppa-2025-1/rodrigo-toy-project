package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.NewTicket;
import com.example.demo.model.business.TicketBusiness;
import com.example.demo.model.entity.Ticket;
import com.example.demo.model.entity.Ticket.TicketStatus;
import com.example.demo.repository.TicketRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController extends AbstractController {

    private final TicketRepository ticketRepository;
    private final TicketBusiness ticketBusiness;

    public TicketController(TicketRepository ticketRepository, TicketBusiness ticketBusiness) {
        this.ticketRepository = ticketRepository;
        this.ticketBusiness = ticketBusiness;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void abrirChamado(@Valid @RequestBody NewTicket newTicket) {
        ticketBusiness.abrirChamado(newTicket);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Ticket>> listarChamados() {
        return ResponseEntity.ok(ticketRepository.findAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ticket> obterChamadoPorId(@PathVariable Integer id) {
        return ticketRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> alterarStatus(
        @PathVariable Integer id,
        @RequestParam TicketStatus novoStatus) {

        try {
            ticketBusiness.alterarStatus(id, novoStatus);
            return ResponseEntity.ok("Status alterado com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
