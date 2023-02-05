package com.vish.tweet.sink.mapper;

import com.vish.tweet.model.User;
import com.vish.tweet.sink.model.UserModel;

public class UserMapper {
    
    public static User map(UserModel model) {
        return User.newBuilder()
                .setId(model.getId())
                .setIdStr(model.getId_str())
                .setName(model.getName())
                .setScreenName(model.getScreen_name())
                .setLocation(model.getLocation())
                .setUrl(model.getUrl())
                .setDescription(model.getDescription())
                .setTranslatorType(model.getTranslator_type())
                .setProtected$(model.isProtected_value())
                .setVerified(model.isVerified())
                .setVerifiedType(model.getVerifiedType())
                .setFollowersCount(model.getFollowers_count())
                .setFriendsCount(model.getFriends_count())
                .setListedCount(model.getListed_count())
                .setFavouritesCount(model.getFavourites_count())
                .setStatusesCount(model.getStatuses_count())
                .setCreatedAt(model.getCreated_at())
                .setUtcOffset(model.getUtc_offset())
                .setTimeZone(model.getTime_zone())
                .setGeoEnabled(model.isGeo_enabled())
                .setLang(model.getLang())
                .setContributorsEnabled(model.isContributors_enabled())
                .setProfileBackgroundColor(model.getProfile_background_color())
                .setProfileBackgroundImageUrl(model.getProfile_background_image_url())
                .setProfileBackgroundImageUrlHttps(model.getProfile_background_image_url_https())
                .setProfileBackgroundTile(model.isProfile_background_tile())
                .setProfileLinkColor(model.getProfile_link_color())
                .setProfileSidebarBorderColor(model.getProfile_sidebar_border_color())
                .setProfileSidebarFillColor(model.getProfile_sidebar_fill_color())
                .setProfileTextColor(model.getProfile_text_color())
                .setProfileUseBackgroundImage(model.isProfile_use_background_image())
                .setProfileImageUrl(model.getProfile_image_url())
                .setProfileImageUrlHttps(model.getProfile_image_url_https())
                .setProfileBannerUrl(model.getProfile_banner_url())
                .setDefaultProfile(model.isDefault_profile())
                .setDefaultProfileImage(model.isDefault_profile_image())
                .setFollowing(model.getFollowing())
                .setFollowRequestSent(model.getFollow_request_sent())
                .setNotifications(model.getNotifications())
                .setWithheldInCountries(model.getWithheld_in_countries())
                .build();
    }
}
