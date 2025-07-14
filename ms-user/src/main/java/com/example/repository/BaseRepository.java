package com.example.repository;

import org.springframework.data.repository.ListCrudRepository;

import com.example.model.entity.BaseEntity;

// Parâmetro de Tipo: generics, template, ..
public interface BaseRepository<T extends BaseEntity, ID> extends ListCrudRepository<T, ID> {
    

    // findByIdAndDeletedIsTrue(ID id);

}
