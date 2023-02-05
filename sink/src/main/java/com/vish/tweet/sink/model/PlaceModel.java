package com.vish.tweet.sink.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceModel {
    private String id;
    private String url;
    private String placeType;
    private String name;
    private String fullName;
    private String countryCode;
    private String country;
    private BoundingBoxModel boundingBox;
    private Map<String, String> attributes;
}
