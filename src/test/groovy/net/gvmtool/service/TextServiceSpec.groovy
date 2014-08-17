package net.gvmtool.service

import net.gvmtool.domain.Broadcast
import spock.lang.Specification

import static net.gvmtool.service.TextService.*

class TextServiceSpec extends Specification {

    TextService service

    Broadcast broadcast1 = new Broadcast(id: 1, text: "text1", date: new Date())
    Broadcast broadcast2 = new Broadcast(id: 2, text: "text2", date: new Date())

    void setup() {
        service = new TextService()
    }

    void "should add header line when preparing broadcast text"() {
        given:
        def broadcasts = [broadcast1, broadcast2]

        when:
        def text = service.prepare(broadcasts)
        def firstLine = text.readLines().first()

        then:
        firstLine == '==== BROADCAST ================================================================='
    }

    void "should add footer line when preparing broadcast text"() {
        given:
        def broadcasts = [broadcast1, broadcast2]

        when:
        def text = service.prepare(broadcasts)
        def lastLine = text.readLines().last()

        then:
        lastLine == '================================================================================'
    }

    void "should place each string on a new line"() {
        given:
        def broadcasts = [broadcast1, broadcast2]

        when:
        def text = service.prepare(broadcasts)
        def readLines = text.readLines()

        then:
        readLines.size() == 4
        readLines[0] == HEADER
        readLines[1].contains(broadcast1.text)
        readLines[2].contains(broadcast2.text)
        readLines[3] == FOOTER
    }

    void "should allow conversion of single broadcasts"() {
        when:
        def text = service.prepare(broadcast1)
        def readLines = text.readLines()

        then:
        readLines.size() == 3
        readLines[0] == HEADER
        readLines[1].contains(broadcast1.text)
        readLines[2] == FOOTER
    }

    void "should render a friendly message if no broadcasts are passed in"() {
        when:
        def text = service.prepare([])
        def readLines = text.readLines()

        then:
        readLines[0] == HEADER
        readLines[1] == "No broadcasts available at present."
        readLines[2] == FOOTER
    }

    void "should compose a structured release message line"() {
        given:
        def candidate = "groovy"
        def version = "2.3.0"
        def hashtag = "groovylang"

        when:
        String message = service.composeStructuredMessage(candidate, version, hashtag)

        then:
        message == "Groovy 2.3.0 has been released on GVM. #groovylang"
    }

    void "should compose a structured release message ignoring a # in the hashtag"() {
        given:
        def hashtag = "#groovylang"

        when:
        String message = service.composeStructuredMessage("", "", hashtag)

        then:
        message.contains " #groovylang"
    }

    void "should compose a structured release message including a # in the hashtag"() {
        given:
        def hashtag = "groovylang"

        when:
        String message = service.composeStructuredMessage("", "", hashtag)

        then:
        message.contains " #groovylang"
    }
}
