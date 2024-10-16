package com.task.library_management_system.controller;

import com.task.library_management_system.controller.model.Response;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Map;

public class BaseController {

    public static ModelMapper modelMapper = getMapper();

    public static ModelMapper getMapper() {
        var mapper = new ModelMapper();
        mapper.getConfiguration()
                .setPropertyCondition(Conditions.isNotNull())
                .setMatchingStrategy(MatchingStrategies.STRICT);

        return mapper;
    }

    public Response buildResponse(String message, HttpStatus status, Map<?, ?> data) {
        return Response.builder()
                .timeStamp(Instant.now())
                .statusCode(status.value())
                .status(status)
                .message(message)
                .data(data)
                .build();
    }
}
