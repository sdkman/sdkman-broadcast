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
package db

import com.mongodb.BasicDBObjectBuilder
import com.mongodb.DB
import com.mongodb.MongoClient
import com.mongodb.WriteConcern
import org.bson.types.ObjectId

import java.util.concurrent.atomic.AtomicLong

import static com.mongodb.BasicDBObjectBuilder.start

class MongoHelper {

    static id = new AtomicLong()

    static prepareDB(){
        def mongo = new MongoClient()
        mongo.writeConcern = WriteConcern.NORMAL
        mongo.getDB("sdkman")
    }

    static insertBroadcastInDb(DB db, String broadcast, Date date = new Date(), uid = id.getAndIncrement().toString()){
        def collection  = db.getCollection("broadcast")
        BasicDBObjectBuilder builder = start()
            .add("_id", uid)
            .add("text", broadcast)
            .add("date", date)
        collection.insert(builder.get())
    }

    static readBroadcastById(DB db, String uid) {
        def collection = db.getCollection("broadcast")
        def objectId = new ObjectId(uid)
        BasicDBObjectBuilder builder = start().add("_id", objectId)
        collection.findOne(builder.get())
    }

    static dropCollectionFromDb(DB db, String collection){
        def dbCollection  = db.getCollection(collection)
        dbCollection.drop()
    }
}
