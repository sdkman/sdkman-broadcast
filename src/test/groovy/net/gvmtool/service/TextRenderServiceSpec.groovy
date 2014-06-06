package net.gvmtool.service

import spock.lang.Specification

class TextRenderServiceSpec extends Specification {

    TextRenderService service

    void setup() {
        service = new TextRenderService()
    }

    void "should add header line when preparing broadcast text"() {
        given:
        def lines = ['1', '2']

        when:
        def text = service.prepare(lines)
        def firstLine = text.readLines().first()

        then:
        firstLine == '==== BROADCAST ================================================================='
    }

    void "should add footer line when preparing broadcast text"() {
        given:
        def lines = ['1', '2']

        when:
        def text = service.prepare(lines)
        def lastLine = text.readLines().last()

        then:
        lastLine == '================================================================================'
    }

    void "should place each string on a new line"() {
        given:
        def lines = ['1', '2']

        when:
        def text = service.prepare(lines)

        then:
        text.readLines().size() == 4
    }

}
