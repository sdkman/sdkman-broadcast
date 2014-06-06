package db

import com.mongodb.BasicDBObjectBuilder
import com.mongodb.DB
import com.mongodb.MongoClient
import com.mongodb.WriteConcern

import java.util.concurrent.atomic.AtomicLong

import static com.mongodb.BasicDBObjectBuilder.start

class MongoHelper {

    static id = new AtomicLong()

    static prepareDB(){
        def mongo = new MongoClient()
        mongo.writeConcern = WriteConcern.NORMAL
        mongo.getDB("gvm")
    }

    static insertBroadcastInDb(DB db, String broadcast, Date date = new Date(), uid = id.getAndIncrement()){
        def collection  = db.getCollection("broadcast")
        BasicDBObjectBuilder builder = start()
            .add("_id", uid.toString())
            .add("text", broadcast)
            .add("date", date)
        collection.insert(builder.get())
    }

    static dropCollectionFromDb(DB db, String collection){
        def dbCollection  = db.getCollection(collection)
        dbCollection.drop()
    }
}