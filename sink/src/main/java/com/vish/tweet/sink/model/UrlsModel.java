package com.vish.tweet.sink.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UrlsModel {
    private String url;
    private String expandedUrl;
    private String displayUrl;
    private List<Long> indices;
}
