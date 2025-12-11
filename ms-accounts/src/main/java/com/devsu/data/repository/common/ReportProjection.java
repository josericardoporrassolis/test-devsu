package com.devsu.data.repository.common;

import java.time.LocalDateTime;

public interface ReportProjection {
    LocalDateTime getDate();
    String getClientName();
    String getAccountNumber();
    String getType();
    Double getInitialBalance();
    boolean getStatus();
    Double getMovementAmount();
    Double getAvailableBalance();
}
