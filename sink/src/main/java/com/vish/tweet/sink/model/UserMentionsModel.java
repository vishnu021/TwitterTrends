package com.vish.tweet.sink.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserMentionsModel {
    private String screenName;
    private String name;
    private long id;
    private String idStr;
    private List<Long> indices;
}
