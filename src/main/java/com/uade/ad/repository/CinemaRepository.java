package com.uade.ad.repository;

import com.uade.ad.model.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {
 List<Cinema> findAllByOwnerId(Long ownerId);
}
