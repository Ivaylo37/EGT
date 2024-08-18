package com.example.egt.repos;

import com.example.egt.models.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Currency findTopByCodeOrderByTimeStampDesc(String code);

    List<Currency> findByCodeAndTimeStampBetweenOrderByTimeStampDesc(
            String code, LocalDateTime startDate, LocalDateTime endDate);
}
