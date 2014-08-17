package net.gvmtool.service

import groovyx.gpars.GParsPool
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.social.twitter.api.impl.TwitterTemplate
import org.springframework.stereotype.Service

@Service
class TwitterService {

    @Autowired
    TwitterTemplate twitter

    void update(String status) {
        tweetAsync(status)
    }

    def tweet = { status -> twitter.timelineOperations().updateStatus(status) }

    def tweetAsync = { status -> GParsPool.withPool() { tweet.async()(status) } }

}
