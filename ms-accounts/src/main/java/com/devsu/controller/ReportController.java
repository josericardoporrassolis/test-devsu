package com.devsu.controller;

import com.devsu.dto.ReportDTO;
import com.devsu.service.MovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReportController {

    private final MovementService movementService;

    @PreAuthorize("hasAuthority('read:report')")
    @GetMapping
    public ResponseEntity<List<ReportDTO>> getReport(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(name = "cliente", required = false) String clientName, @RequestParam(name = "cuenta", required = false) String accountNumber) {

        return ResponseEntity.ok(movementService.generateReport(startDate, endDate, clientName, accountNumber));
    }
}
