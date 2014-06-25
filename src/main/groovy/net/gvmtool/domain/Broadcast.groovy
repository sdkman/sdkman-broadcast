package net.gvmtool.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Broadcast {
    @Id BigInteger id
    String text
    Date date

    String toString(){
        text
    }

    BroadcastId toBroadcastId(){
        new BroadcastId(value: id)
    }
}
