package com.vish.tweet.sink.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TimeZone;

@Data
@Slf4j
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tweet {

    @JsonProperty(value = "created_at")
    private String at;
    private long id;
    private String id_str;
    private String text;
    private String source;
    private boolean truncated;
    private Object in_reply_to_status_id;
    private Object in_reply_to_status_id_str;
    private Object in_reply_to_user_id_str;
    private Object in_reply_to_screen_name;
    private User user;
    private Object geo;
    private Object coordinates;
    private Object place;
    private Object contributors;
    private Tweet retweeted_status;
    private boolean is_quote_status;
    private long quote_count;
    private long reply_count;
    private long retweet_count;
    private long favorite_count;
    private Map<Object, Object> entities;
    private boolean favorited;
    private boolean retweeted;
    private String filter_level;
    private String lang;
    private long timestamp_ms;


    public String getAt() {
        SimpleDateFormat inputFormatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
        SimpleDateFormat outputFormatter = new SimpleDateFormat("dd-MMM-yy HH:mm:ss");
        outputFormatter.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        try {
            return outputFormatter.format(inputFormatter.parse(at));
        } catch (ParseException e) {
            log.error("Exception while parsing tweet time", e);
            return at;
        }
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("\n");
        appendTweetMetadata(sb, "");
        return sb.toString();
    }

    private void appendTweetMetadata(StringBuffer sb, String tab) {
        sb.append("at='").append(getAt()).append('\'');
        sb.append(", user=").append(user);
        sb.append(place==null ? "" : ", place="+place);

        sb.append(", link=").append(getTweeetLink());

        if(in_reply_to_status_id!=null) {
            sb.append("\n").append(tab).append("reply to =").append(getTweeetLink());
        }
        if(retweeted_status!=null) {
            sb.append("\n").append(tab).append(retweeted_status.toString("    "));
        }
        sb.append("\n").append(tab).append("text='").append(text.replaceAll("\\n", "\\\\n")).append('\'');
    }

    private String getTweeetLink() {
        return "https://twitter.com/i/web/status/" + id;
    }

    public String toString(String tab) {
        final StringBuffer sb = new StringBuffer();
        sb.append(tab).append("ReTweet of{");

        appendTweetMetadata(sb, tab);
        sb.append("}");
        return sb.toString();
    }
}
