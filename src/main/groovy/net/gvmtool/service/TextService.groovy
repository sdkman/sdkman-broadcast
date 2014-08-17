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

    private buildMessage(broadcasts) {
        def output = ""
        broadcasts.each { broadcast ->
            output += "* ${getDateInstance(SHORT, Locale.UK).format(broadcast.date)}: $broadcast.text\n"
        }
        output
    }

    String composeStructuredMessage(String candidate, String version, String hashtag = candidate) {
        def prefixedHashtag = hashtag.startsWith("#") ? hashtag : "#$hashtag"
        "${candidate.toLowerCase().capitalize()} $version has been released on GVM. $prefixedHashtag"
    }
}
