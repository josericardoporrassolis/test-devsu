package com.devsu.service;

import com.devsu.data.model.Account;
import com.devsu.data.repository.AccountRepository;
import com.devsu.dto.AccountDTO;
import com.devsu.exception.DataNotFoundException;
import com.devsu.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public List<AccountDTO> getAllAccounts(String account) {
        return accountRepository.findById(account).stream()
                .map(accountMapper::toDto)
                .collect(Collectors.toList());
    }

    public AccountDTO createAccount(AccountDTO accountDTO) {
        Account account = accountMapper.toEntity(accountDTO);
        return accountMapper.toDto(accountRepository.save(account));
    }

    public AccountDTO updateAccount(String accountNumber, AccountDTO accountDTO) {
        Account existingAccount = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new DataNotFoundException("Account not found with number: " + accountNumber));

        accountMapper.updateEntityFromDto(accountDTO, existingAccount);
        Account updatedAccount = accountRepository.save(existingAccount);
        return accountMapper.toDto(updatedAccount);
    }
}