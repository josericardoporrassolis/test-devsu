package com.devsu.controller;

import com.devsu.dto.MovementDTO;
import com.devsu.service.MovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
public class MovementController {

    private final MovementService movementService;

    @PreAuthorize("hasAuthority('write:movements')")
    @PostMapping
    public ResponseEntity<MovementDTO> createMovement(@RequestBody MovementDTO movementDTO) {
        return new ResponseEntity<>(movementService.createMovement(movementDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('update:movements')")
    @PutMapping("/{movementId}")
    public ResponseEntity<MovementDTO> updateMovement(@PathVariable Long movementId, @RequestBody MovementDTO movementDTO) {
        return ResponseEntity.ok(movementService.updateMovement(movementId, movementDTO));
    }
}
