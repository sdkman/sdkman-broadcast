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
package net.gvmtool.service

import net.gvmtool.domain.Broadcast
import org.springframework.stereotype.Service

import static java.text.DateFormat.SHORT
import static java.text.DateFormat.getDateInstance

@Service
class TextService {

    static final HEADER = '==== BROADCAST ================================================================='
    static final FOOTER = '================================================================================'
    static final NO_BROADCASTS = "No broadcasts available at present.\n"

    String prepare(Broadcast broadcast) {
        prepare([broadcast])
    }

    String prepare(ArrayList<Broadcast> broadcasts) {
        def output = "$HEADER\n"
        if(broadcasts)
            output += buildMessage(broadcasts)
        else
            output += NO_BROADCASTS
        "${output}$FOOTER"
    }

    private static buildMessage(broadcasts) {
        def output = ""
        broadcasts.each { broadcast ->
            output += "* ${getDateInstance(SHORT, Locale.UK).format(broadcast.date)}: $broadcast.text\n"
        }
        output
    }

    String composeStructuredMessage(String candidate, String version, String hashtag = candidate) {
        def prefixedHashtag = hashtag.startsWith("#") ? hashtag : "#$hashtag"
        "${candidate.toLowerCase().capitalize()} $version released on SDKMAN! $prefixedHashtag"
    }
}
