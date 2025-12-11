package com.devsu.mapper;

import com.devsu.data.model.Account;
import com.devsu.data.model.Movement;
import com.devsu.data.repository.common.ReportProjection;
import com.devsu.dto.AccountDTO;
import com.devsu.dto.MovementDTO;
import com.devsu.dto.ReportDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MovementMapper {

    @Mapping(source = "accountNumber", target = "account.accountNumber")
    Movement toEntity(MovementDTO movementDTO);

    @Mapping(source = "account.accountNumber", target = "accountNumber")
    MovementDTO toDto(Movement movement);

    void updateMovementEntityFromDto(MovementDTO dto, @MappingTarget Movement entity);

    ReportDTO toDto(ReportProjection projection);

    List<ReportDTO> toDtoList(List<ReportProjection> projections);
}
