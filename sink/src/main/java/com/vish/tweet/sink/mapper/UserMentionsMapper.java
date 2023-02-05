package com.vish.tweet.sink.mapper;

import com.vish.tweet.model.UserMentions;
import com.vish.tweet.sink.model.UserMentionsModel;

public class UserMentionsMapper {

    public static UserMentions map(UserMentionsModel model) {
        return UserMentions.newBuilder()
                .setScreenName(model.getScreenName())
                .setName(model.getName())
                .setId(model.getId())
                .setIdStr(model.getIdStr())
                .setIndices(model.getIndices())
                .build();
    }
}
