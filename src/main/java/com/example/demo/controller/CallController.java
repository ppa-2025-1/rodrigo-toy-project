package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.NewCall;
import com.example.demo.model.business.CallBusiness;
import com.example.demo.model.entity.Call;
import com.example.demo.model.entity.Call.CallStatus;
import com.example.demo.repository.CallRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/calls")
public class CallController extends AbstractController {

    private final CallRepository callRepository;
    private final CallBusiness callBusiness;

    public CallController(CallRepository callRepository, CallBusiness callBusiness) {
        this.callRepository = callRepository;
        this.callBusiness = callBusiness;
    }

    // POST - Abrir um chamado (status = NOVO)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void abrirChamado(@Valid @RequestBody NewCall newCall) {
        callBusiness.abrirChamado(newCall);
    }

    // GET - Consultar todos os chamados
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Call>> listarChamados() {
        return ResponseEntity.ok(callRepository.findAll());
    }

    // GET - Consultar um chamado pelo ID
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Call> obterChamadoPorId(@PathVariable Integer id) {
        return callRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PATCH - Alterar a situação de um chamado
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> alterarStatus(
        @PathVariable Integer id,
        @RequestParam CallStatus novoStatus) {

        try {
            callBusiness.alterarStatus(id, novoStatus);
            return ResponseEntity.ok("Status alterado com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
