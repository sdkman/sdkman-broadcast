package net.gvmtool.service

import org.springframework.stereotype.Service

@Service
class TextRenderService {

    final HEADER = '==== BROADCAST ================================================================='
    final FOOTER = '================================================================================'

    String prepare(ArrayList<String> strings) {
        def text = strings.join("\n")
        "${HEADER}\n${text}\n${FOOTER}"
    }
}
