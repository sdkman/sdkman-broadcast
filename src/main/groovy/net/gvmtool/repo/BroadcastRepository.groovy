package net.gvmtool.repo

import net.gvmtool.domain.Broadcast
import org.springframework.data.mongodb.repository.MongoRepository

interface BroadcastRepository extends MongoRepository<Broadcast, BigInteger> {}