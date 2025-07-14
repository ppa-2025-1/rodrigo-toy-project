package com.example.repository;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.repository.ListCrudRepository;

import com.example.model.entity.Role;

public interface RoleRepository extends BaseRepository<Role, Integer> {

    Role findByName(String name);

    Set<Role> findByNameIn(Collection<String> names);

    boolean existsByName(String name);
    
}
