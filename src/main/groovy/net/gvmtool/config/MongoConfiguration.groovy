package net.gvmtool.config

import com.mongodb.Mongo
import com.mongodb.MongoClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoConfiguration

@Configuration
class MongoConfiguration extends AbstractMongoConfiguration {

    @Value("#{systemEnvironment['MONGO_HOST']}")
    String mongoHost = "localhost"

    @Value("#{systemEnvironment['MONGO_PORT']}")
    String mongoPort = "27017"

    @Value("#{systemEnvironment['MONGO_DB_NAME']}")
    String mongoDbName = "gvm"

    @Override
    protected String getDatabaseName() {
        mongoDbName
    }

    @Override
    Mongo mongo() throws Exception {
        new MongoClient(mongoHost, mongoPort.toInteger())
    }
}
