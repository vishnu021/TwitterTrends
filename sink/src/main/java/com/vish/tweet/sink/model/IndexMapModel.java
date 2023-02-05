package com.vish.tweet.sink.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IndexMapModel {
    private String text;
    private ArrayList<Long> indices;
}
