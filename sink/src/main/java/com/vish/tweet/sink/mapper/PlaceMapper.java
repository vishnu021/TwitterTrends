package com.vish.tweet.sink.mapper;

import com.vish.tweet.model.Place;
import com.vish.tweet.sink.model.PlaceModel;

public class PlaceMapper {

    public static Place map(PlaceModel model) {
        Place.Builder placeBuilder = Place.newBuilder()
                .setId(model.getId())
                .setUrl(model.getUrl())
                .setPlaceType(model.getPlaceType())
                .setName(model.getName())
                .setFullName(model.getFullName())
                .setCountryCode(model.getCountryCode())
                .setCountry(model.getCountry())
                .setAttributes(model.getAttributes());

        if(model.getBoundingBox()!=null) {
            placeBuilder.setBoundingBox(BoundingBoxMapper.map(model.getBoundingBox()));
        }

        return placeBuilder.build();
    }
}
