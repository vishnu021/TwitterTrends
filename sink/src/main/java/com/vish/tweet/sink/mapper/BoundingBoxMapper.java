package com.vish.tweet.sink.mapper;

import com.vish.tweet.model.BoundingBox;
import com.vish.tweet.sink.model.BoundingBoxModel;

public class BoundingBoxMapper {

    public static BoundingBox map(BoundingBoxModel model) {
        return BoundingBox.newBuilder()
                .setType(model.getType())
                .setCoordinates(model.getCoordinates())
                .build();
    }
}
