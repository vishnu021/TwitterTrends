package com.vish.tweet.sink.mapper;

import com.vish.tweet.model.Urls;
import com.vish.tweet.sink.model.UrlsModel;

public class UrlsMapper {

    public static Urls map(UrlsModel model) {
        return Urls.newBuilder()
                .setUrl(model.getUrl())
                .setExpandedUrl(model.getExpandedUrl())
                .setDisplayUrl(model.getDisplayUrl())
                .setIndices(model.getIndices())
                .build();
    }
}
