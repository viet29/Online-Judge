package com.service.submittask_service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Execute {
    @JsonProperty("wall-time")
    private Double wall_time;
}
