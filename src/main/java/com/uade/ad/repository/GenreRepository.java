package com.uade.ad.repository;

import com.uade.ad.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository  extends JpaRepository<Genre, Integer> {
}
