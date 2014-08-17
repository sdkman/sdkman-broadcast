package net.gvmtool.service

import org.springframework.social.twitter.api.TimelineOperations
import org.springframework.social.twitter.api.impl.TwitterTemplate
import spock.lang.Specification

class TwitterServiceSpec extends Specification {

    TwitterService service
    TwitterTemplate template = Mock()

    void setup(){
        service = new TwitterService(twitter: template)
    }

    void "should tweet status"() {
        given:
        def status = "a status update"
        def timelineOperations = Mock(TimelineOperations)

        when:
        service.update(status)

        then:
        1 * template.timelineOperations() >> timelineOperations
        1 * timelineOperations.updateStatus(status)
    }

}
