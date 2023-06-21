package com.uade.ad.repository;

import com.uade.ad.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository  extends JpaRepository<Review, Integer> {
}
