package net.gvmtool

import org.springframework.data.mongodb.repository.MongoRepository

interface BroadcastRepository extends MongoRepository<Broadcast, BigInteger> {}