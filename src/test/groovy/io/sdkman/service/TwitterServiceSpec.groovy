/*
 * Copyright 2014 Marco Vermeulen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.sdkman.service

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
