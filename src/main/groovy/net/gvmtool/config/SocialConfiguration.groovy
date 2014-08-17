package net.gvmtool.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.social.twitter.api.impl.TwitterTemplate

@Configuration
class SocialConfiguration {

    @Value("#{systemEnvironment['TWITTER_CONSUMER_KEY']}")
    String twitterConsumerKey = "twitter_consumer_key"

    @Value("#{systemEnvironment['TWITTER_CONSUMER_SECRET']}")
    String twitterConsumerSecret = "twitter_consumer_secret"

    @Value("#{systemEnvironment['TWITTER_ACCESS_TOKEN']}")
    String twitterAccessToken = "twitter_access_token"

    @Value("#{systemEnvironment['TWITTER_ACCESS_TOKEN_SECRET']}")
    String twitterAccessTokenSecret = "twitter_access_token_secret"

    @Bean
    TwitterTemplate twitterTemplate() {
        new TwitterTemplate(
            twitterConsumerKey,
            twitterConsumerSecret,
            twitterAccessToken,
            twitterAccessTokenSecret
        )
    }
}
