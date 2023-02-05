package com.vish.tweet.sink.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BoundingBoxModel {
    private String type;
    private List<List<List<Long>>> coordinates;
}
