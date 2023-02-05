package com.vish.tweet.sink.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EntitiesModel {

    private List<IndexMapModel> hashtags;
    private List<UrlsModel> urls;
    private List<UserMentionsModel> userMentions;
    private List<IndexMapModel> symbols;
}
