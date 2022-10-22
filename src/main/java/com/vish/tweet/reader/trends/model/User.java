package com.vish.tweet.reader.trends.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private long id;
    private String id_str;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "screen_name")
    private String screen_name;
    private String location;
    private String url;
    private String description;
    private String translator_type;
    @JsonProperty(value = "protected")
    private String protected_value;
    @JsonProperty(value = "verified")
    private boolean verified;
    private long followers_count;
    private long friends_count;
    private long listed_count;
    private long favourites_count;
    private long statuses_count;
    private String created_at;
    private Object utc_offset;
    private Object time_zone;
    private boolean geo_enabled;
    private Object lang;
    private boolean contributors_enabled;
    private boolean is_translator;
    private String profile_background_color;
    private String profile_background_image_url;
    private String profile_background_image_url_https;
    private boolean profile_background_tile;
    private String profile_link_color;
    private String profile_sidebar_border_color;
    private String profile_sidebar_fill_color;
    private String profile_text_color;
    private boolean profile_use_background_image;
    private String profile_image_url;
    private String profile_image_url_https;
    private String profile_banner_url;
    private boolean default_profile;
    private boolean default_profile_image;
    private Object following;
    private Object follow_request_sent;
    private Object notifications;
    private List<Object> withheld_in_countries;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("name='").append(name).append('\'');
        sb.append(", scrn_name='").append(screen_name).append('\'');
        sb.append(location==null ? "" : ", location="+location);
        sb.append(verified ? ", verified" : "");
        sb.append(geo_enabled ? ", geo_enabled" : "");
        sb.append(lang==null ? "" : ", lang="+lang);
        sb.append('}');
        return sb.toString();
    }
}
