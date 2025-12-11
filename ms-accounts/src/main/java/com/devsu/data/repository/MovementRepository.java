package com.devsu.data.repository;

import com.devsu.data.model.Movement;
import com.devsu.data.repository.common.ReportProjection;
import com.devsu.dto.ReportDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {

    Optional<Movement> findTopByAccount_AccountNumberOrderByMovementIdDesc(String accountNumber);

    @Query(name ="movement.reportMovements", nativeQuery = true)
    List<ReportProjection> getReportMovements(@Param("startDate") LocalDateTime startDate,
                                                    @Param("endDate") LocalDateTime endDate,
                                                    @Param("accountNumber") String accountNumber,
                                                    @Param("personId") Integer personId);

}
