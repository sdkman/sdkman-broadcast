package net.gvmtool

import com.mongodb.Mongo
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoConfiguration

@Configuration
class MongoConfiguration extends AbstractMongoConfiguration {
    @Override
    protected String getDatabaseName() {
        "gvm"
    }

    @Override
    Mongo mongo() throws Exception {
        return new Mongo()
    }
}
