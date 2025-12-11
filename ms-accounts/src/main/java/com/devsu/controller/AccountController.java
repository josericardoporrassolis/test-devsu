package com.devsu.controller;

import com.devsu.dto.AccountDTO;
import com.devsu.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PreAuthorize("hasAuthority('read:accounts')")
    @GetMapping("/{accountNumber}")
    public ResponseEntity<List<AccountDTO>> getAllAccounts(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.getAllAccounts(accountNumber));
    }

    @PreAuthorize("hasAuthority('write:accounts')")
    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO) {
        return new ResponseEntity<>(accountService.createAccount(accountDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('update:accounts')")
    @PutMapping("/{accountNumber}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable String accountNumber, @RequestBody AccountDTO accountDTO) {
        try {
            return ResponseEntity.ok(accountService.updateAccount(accountNumber, accountDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}