package net.gvmtool

import org.springframework.data.mongodb.core.mapping.Document

@Document
class Broadcast {
    String text
    Date date

    String toString(){
        text
    }
}
