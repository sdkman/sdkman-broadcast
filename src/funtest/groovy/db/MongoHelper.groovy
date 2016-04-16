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

import com.mongodb.BasicDBObject
import com.mongodb.MongoClient
import com.mongodb.WriteConcern
import com.mongodb.client.MongoDatabase
import org.bson.types.ObjectId

import java.util.concurrent.atomic.AtomicLong

class MongoHelper {

    static id = new AtomicLong()

    static prepareDB(){
        def mongo = new MongoClient()
        mongo.writeConcern = WriteConcern.UNACKNOWLEDGED
        mongo.getDatabase("sdkman")
    }

    static insertBroadcastInDb(MongoDatabase db, String broadcast, Date date = new Date(), uid = id.getAndIncrement().toString()){
        def collection  = db.getCollection("broadcast", BasicDBObject.class)
        def basicDbObject = new BasicDBObject()
        basicDbObject.append("_id", uid)
        basicDbObject.append("text", broadcast)
        basicDbObject.append("date", date)
        collection.insertOne(basicDbObject)
    }

    static readBroadcastById(MongoDatabase db, String uid) {
        def collection = db.getCollection("broadcast", BasicDBObject.class)
        def objectId = new ObjectId(uid)
        def basicDbObject = new BasicDBObject("_id", objectId)
        collection.find(basicDbObject).first()
    }

    static dropCollectionFromDb(MongoDatabase db, String collection){
        def dbCollection  = db.getCollection(collection)
        dbCollection.drop()
    }
}
