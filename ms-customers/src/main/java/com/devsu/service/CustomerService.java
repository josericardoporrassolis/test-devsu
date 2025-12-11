package com.devsu.service;

import com.devsu.data.model.Customer;
import com.devsu.data.repository.CustomerRepository;
import com.devsu.dto.CustomerDTO;
import com.devsu.exception.CustomerException;
import com.devsu.exception.DataNotFoundException;
import com.devsu.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::toDto)
                .collect(Collectors.toList());
    }

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Optional<Customer> existing = customerRepository.findByIdentification(customerDTO.getIdentification());
        if (existing.isPresent())
            throw new CustomerException(existing.get().getId());

        Customer customer = customerMapper.toEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.toDto(savedCustomer);
    }

    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Customer not found"));
        return customerMapper.toDto(customer);
    }

    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Customer not found with id: " + id));

        customerMapper.updateEntityFromDto(customerDTO, existingCustomer);
        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return customerMapper.toDto(updatedCustomer);
    }

    public boolean deleteCustomer(Long id) {
        if (!customerRepository.existsById(id))
            return Boolean.FALSE;
        customerRepository.deleteById(id);
        return Boolean.TRUE;
    }

    public CustomerDTO getCustomerByName(String name) {
        Customer customer = customerRepository.findByName(name)
                .orElseThrow(() -> new DataNotFoundException("Cliente no encontrado"));
        return customerMapper.toDto(customer);
    }

}
