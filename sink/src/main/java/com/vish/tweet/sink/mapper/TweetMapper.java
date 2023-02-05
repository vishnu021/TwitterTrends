package com.vish.tweet.sink.mapper;

import com.vish.tweet.model.Tweet;
import com.vish.tweet.sink.model.TweetModel;

public class TweetMapper {
    public static Tweet map(TweetModel model) {
        Tweet.Builder twitterBuilder = Tweet.newBuilder().setCreatedAt(model.getAt())
                .setId(model.getId())
                .setIdStr(model.getId_str())
                .setText(model.getText())
                .setSource(model.getSource())
                .setTruncated(model.isTruncated())
                .setInReplyToStatusId(model.getIn_reply_to_status_id())
                .setInReplyToStatusIdStr(model.getIn_reply_to_status_id_str())
                .setInReplyToUserId(model.getIn_reply_to_user_id())
                .setInReplyToUserIdStr(model.getIn_reply_to_user_id_str())
                .setInReplyToScreenName(model.getIn_reply_to_screen_name())
                // user should not be null
                .setUser(UserMapper.map(model.getUser()))
                .setGeo(model.getGeo())
                .setCoordinates(model.getCoordinates())
                .setContributors(model.getContributors())
                .setIsQuoteStatus(model.is_quote_status())
                .setQuoteCount(model.getQuote_count())
                .setReplyCount(model.getReply_count())
                .setRetweetCount(model.getRetweet_count())
                .setFavoriteCount(model.getFavorite_count())
                .setFavorited(model.isFavorited())
                .setRetweeted(model.isRetweeted())
                .setFilterLevel(model.getFilter_level())
                .setLang(model.getLang())
                .setTimestampMs(model.getTimestamp_ms());


        if(model.getRetweeted_status()!=null) {
            twitterBuilder.setRetweetedStatus(TweetMapper.map(model.getRetweeted_status()));
        }

        if(model.getPlace()!=null) {
            twitterBuilder.setPlace(PlaceMapper.map(model.getPlace()));
        }

        if(model.getEntities()!=null) {
            twitterBuilder.setEntities(EntitiesMapper.map(model.getEntities()));
        }

        return twitterBuilder.build();
    }
}
