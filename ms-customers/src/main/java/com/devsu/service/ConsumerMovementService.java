package com.devsu.service;

import com.devsu.common.MovementEvent;
import com.devsu.data.model.Customer;
import com.devsu.data.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsumerMovementService {

    private static final Logger LOGGER = LogManager.getLogger(ConsumerMovementService.class);

    private final CustomerRepository customerRepository;

    @KafkaListener(topics = "mov-topic-alert", groupId = "ms-customers-group")
    public void processEventMov(String msj) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            MovementEvent movementEvent = mapper.readValue(msj, MovementEvent.class);

            //TODO send email
            /*notificationService.sendEmail(
                    customer.getEmail(),
                    "Movimiento en su cuenta",
                    "Estimado cliente, en su cuenta " + movementEvent.getAccountNumber() +
                            " tuvo un movimiento por un monto de " + movementEvent.getValue() +
                            " de tipo " + movementEvent.getAccountType()
            );*/
            //Customer customer = customerRepository.findByAccountNumber(id);
            // send email or SMS
            LOGGER.info("Estimado cliente, en su cuenta " + movementEvent.getAccountNumber() +
                    " tuvo un movimiento por un monto de " + movementEvent.getValue() + " de tipo " + movementEvent.getAccountType());

        } catch (Exception e) {
            LOGGER.error(e);
        }
    }
}
