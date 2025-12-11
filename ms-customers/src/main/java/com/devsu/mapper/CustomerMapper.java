package com.devsu.mapper;

import com.devsu.data.model.Customer;
import com.devsu.dto.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CustomerMapper {

    CustomerDTO toDto(Customer customer);
    Customer toEntity(CustomerDTO customerDTO);
    void updateEntityFromDto(CustomerDTO dto, @MappingTarget Customer entity);
}
