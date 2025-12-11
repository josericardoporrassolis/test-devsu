package com.devsu.service;

import com.devsu.connector.CustomerClient;
import com.devsu.data.model.Account;
import com.devsu.data.model.Movement;
import com.devsu.data.repository.AccountRepository;
import com.devsu.data.repository.MovementRepository;
import com.devsu.data.repository.common.ReportProjection;
import com.devsu.dto.MovementDTO;
import com.devsu.dto.ReportDTO;
import com.devsu.exception.DataNotFoundException;
import com.devsu.exception.InsufficientBalanceException;
import com.devsu.mapper.AccountMapper;
import com.devsu.mapper.MovementMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovementService {

    private final MovementRepository movementRepository;
    private final AccountRepository accountRepository;
    private final MovementMapper movementMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final CustomerClient customerClient;

    @Transactional
    public MovementDTO createMovement(MovementDTO movementDTO) {
        Account account = accountRepository.findById(movementDTO.getAccountNumber())
                .orElseThrow(() -> new DataNotFoundException("Account not found"));

        Double currentBalance = movementRepository.findTopByAccount_AccountNumberOrderByMovementIdDesc(account.getAccountNumber())
                .map(Movement::getBalance)
                .orElse(account.getInitialBalance());

        Double newBalance = currentBalance + movementDTO.getValue();

        if (newBalance < 0) {
            throw new InsufficientBalanceException("Saldo no disponible");
        }

        Movement movement = movementMapper.toEntity(movementDTO);
        movement.setDate(LocalDateTime.now());
        movement.setAccount(account);
        movement.setBalance(newBalance);

        Movement savedMovement = movementRepository.save(movement);
        eventMov(savedMovement);
        return movementMapper.toDto(savedMovement);
    }

    public List<ReportDTO> generateReport(LocalDate startDate, LocalDate endDate, String customerName, String accountNumber) {
        return generateReportCustomerName(startDate, endDate, customerClient.getCustomerByName(customerName), accountNumber);
    }

    private List<ReportDTO> generateReportCustomerName(LocalDate startDate, LocalDate endDate, Integer personId, String accountNumber) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(23, 59, 59);

        List<ReportProjection> list = movementRepository.getReportMovements(start, end, accountNumber, personId);
        return movementMapper.toDtoList(list);
    }

    @Transactional
    public MovementDTO updateMovement(Long movementId, MovementDTO movementDTO) {
        Movement existingMovement = movementRepository.findById(movementId)
                .orElseThrow(() -> new DataNotFoundException("Movement not found with id: " + movementId));

        movementMapper.updateMovementEntityFromDto(movementDTO, existingMovement);
        Movement savedMovement = movementRepository.save(existingMovement);
        return movementMapper.toDto(savedMovement);
    }

    @Async
    public void eventMov(Movement movement) {
        String event = String.format("{\"accountNumber\":\"%s\",\"accountType\":\"%s\",\"value\":%s}", movement.getAccount().getAccountNumber(), movement.getAccount().getAccountType(), movement.getValue());
        kafkaTemplate.send("mov-topic-alert", event);
    }
}
