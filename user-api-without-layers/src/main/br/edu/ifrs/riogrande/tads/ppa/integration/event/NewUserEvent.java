package br.edu.ifrs.riogrande.tads.ppa.integration.event;

import java.util.List;

public record NewUserEvent(
        String name,
        String handle,
        String email,
        String password,
        String company,
        Profile.AccountType type,
        List<String> roles
)  {

}
