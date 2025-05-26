package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.Call;
import com.example.demo.model.entity.Call.CallStatus;

@Repository
public interface CallRepository extends BaseRepository<Call, Integer> {

    Optional<Call> findById(Integer id);

    List<Call> findByStatus(CallStatus status);

    List<Call> findByUserId(Integer userId);

    List<Call> findByActionContainingIgnoreCase(String action);

    List<Call> findByObjectContainingIgnoreCase(String object); 
}
