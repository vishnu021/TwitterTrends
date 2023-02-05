package com.vish.tweet.sink.mapper;

import com.vish.tweet.model.IndexMap;
import com.vish.tweet.sink.model.IndexMapModel;

public class IndexMapMapper {

    public static IndexMap map(IndexMapModel model) {
        return IndexMap.newBuilder()
                .setText(model.getText())
                .setIndices(model.getIndices())
                .build();
    }
}
