package dbDive.airbnbClone.config.utils;

public interface JwtProperties {
    long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60L;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";

    String secretKey = "WhatisyourETAWhatisyourETAWhatisyourETAWhatisyourETA";
}
