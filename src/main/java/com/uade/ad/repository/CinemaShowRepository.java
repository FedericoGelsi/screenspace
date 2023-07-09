package com.uade.ad.repository;

import com.uade.ad.model.CinemaShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemaShowRepository extends JpaRepository<CinemaShow, Long> {
    Long deleteCinemaShowById(final Long id);

    CinemaShow findCinemaShowById(final Long id);
}
