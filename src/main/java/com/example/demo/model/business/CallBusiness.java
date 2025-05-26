package com.example.demo.model.business;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.demo.dto.NewCall;
import com.example.demo.model.entity.Call;
import com.example.demo.model.entity.Call.CallStatus;
import com.example.demo.model.entity.User;
import com.example.demo.repository.CallRepository;
import com.example.demo.repository.UserRepository;

@Business
public class CallBusiness {

    private final CallRepository callRepository;
    private final UserRepository userRepository;

    public CallBusiness(CallRepository callRepository, UserRepository userRepository) {
        this.callRepository = callRepository;
        this.userRepository = userRepository;
    }

    public void abrirChamado(NewCall newCall) {
        User user = userRepository.findById(newCall.userId())
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Call call = new Call();
        call.setAction(newCall.action());
        call.setObject(newCall.object());
        call.setDetails(newCall.details());
        call.setStatus(CallStatus.NOVO);
        call.setUser(user);

        call.setCreatedAt(LocalDateTime.now());
        call.setUpdatedAt(LocalDateTime.now());

        callRepository.save(call);
    }

    public void alterarStatus(Integer callId, CallStatus novoStatus) {
        Call call = callRepository.findById(callId)
            .orElseThrow(() -> new IllegalArgumentException("Chamado não encontrado"));

        CallStatus statusAtual = call.getStatus();

        boolean transicaoValida =
            (statusAtual == CallStatus.NOVO && (novoStatus == CallStatus.ANDAMENTO || novoStatus == CallStatus.CANCELADO)) ||
            (statusAtual == CallStatus.ANDAMENTO && (novoStatus == CallStatus.RESOLVIDO || novoStatus == CallStatus.CANCELADO));

        if (!transicaoValida) {
            throw new IllegalArgumentException("Transição de status inválida");
        }

        call.setStatus(novoStatus);
        call.setUpdatedAt(LocalDateTime.now());

        callRepository.save(call);
    }

    public void abrirChamadoInicialParaNovoUsuario(User user) {
        Call call = new Call();
        call.setAction("CRIAR");
        call.setObject("E-MAIL");
        call.setDetails("Criar e-mail " + user.getHandle() + "@tads.rg.ifrs.edu.br");
        call.setStatus(CallStatus.NOVO);
        call.setUser(user);
        call.setCreatedAt(LocalDateTime.now());
        call.setUpdatedAt(LocalDateTime.now());

        callRepository.save(call);
    }
}
