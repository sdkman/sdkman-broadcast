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
package io.sdkman.config

import com.mongodb.Mongo
import com.mongodb.MongoClient
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoConfiguration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.core.mapping.MongoMappingContext

@Configuration
class MongoConfiguration extends AbstractMongoConfiguration {

    @Value("#{systemEnvironment['MONGO_HOST']}")
    String mongoHost = "localhost"

    @Value("#{systemEnvironment['MONGO_PORT']}")
    String mongoPort = "27017"

    @Value("#{systemEnvironment['MONGO_DB_NAME']}")
    String mongoDbName = "sdkman"

    @Value("#{systemEnvironment['MONGO_USERNAME']}")
    String mongoUsername

    @Value("#{systemEnvironment['MONGO_PASSWORD']}")
    String mongoPassword

    @Override
    protected String getDatabaseName() {
        mongoDbName
    }

    @Override
    Mongo mongo() throws Exception {
        def serverAddress = new ServerAddress(mongoHost, mongoPort.toInteger())
        if (mongoUsername && mongoPassword) {
            def credential = MongoCredential.createMongoCRCredential(mongoUsername, mongoDbName, mongoPassword.toCharArray())
            new MongoClient(serverAddress, [credential])
        } else {
            new MongoClient(serverAddress)
        }
    }

    @Bean
    MongoTemplate mongoTemplate() throws Exception {
        //remove _class
        def converter = new MappingMongoConverter(mongoDbFactory(), new MongoMappingContext())
        converter.setTypeMapper(new DefaultMongoTypeMapper(null))
        new MongoTemplate(mongoDbFactory(), converter)
    }
}
