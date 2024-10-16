package com.task.library_management_system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class BaseDto {

    @JsonProperty(access = Access.READ_ONLY)
    private Long id;

    @JsonProperty(access = Access.READ_ONLY)
    private UUID uuid;

    @JsonProperty(access = Access.READ_ONLY)
    private Instant creationDate;

}
