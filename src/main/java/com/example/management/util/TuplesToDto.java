package com.example.management.util;

import com.example.management.dto.TaskDto;
import jakarta.persistence.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TuplesToDto {
    public static List<TaskDto> tuplesToDto(List<Tuple> tuples) {
        if ((tuples == null) || tuples.isEmpty()) {
            return new ArrayList<>();
        }
        return tuples.stream().map(TuplesToDto::tupleToDto).distinct().collect(Collectors.toList());
    }

    public static TaskDto tupleToDto(Tuple tuple) {
        return TaskDto.builder()
                .id(tuple.get(0, Integer.class))
                .title(tuple.get(1, String.class))
                .priority(tuple.get(4, String.class))
                .build();
    }
}
