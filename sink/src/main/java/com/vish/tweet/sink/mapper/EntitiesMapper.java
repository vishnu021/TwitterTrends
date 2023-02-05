package com.vish.tweet.sink.mapper;

import com.vish.tweet.model.Entities;
import com.vish.tweet.sink.model.EntitiesModel;

import java.util.stream.Collectors;

public class EntitiesMapper {

    public static Entities map(EntitiesModel model) {
        Entities.Builder entitiesBuilder = Entities.newBuilder();

        if(model.getHashtags()!=null) {
            entitiesBuilder.setHashtags(model.getHashtags().stream().map(IndexMapMapper::map).collect(Collectors.toList()));
        }

        if(model.getUrls()!=null) {
            entitiesBuilder.setUrls(model.getUrls().stream().map(UrlsMapper::map).collect(Collectors.toList()));
        }

        if(model.getUserMentions()!=null) {
            entitiesBuilder.setUserMentions(model.getUserMentions().stream().map(UserMentionsMapper::map).collect(Collectors.toList()));
        }

        if(model.getSymbols()!=null) {
            entitiesBuilder.setSymbols(model.getSymbols().stream().map(IndexMapMapper::map).collect(Collectors.toList()));
        }

        return entitiesBuilder.build();
    }
}
