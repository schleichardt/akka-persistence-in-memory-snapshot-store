import com.typesafe.config.{ConfigFactory, Config}
import info.schleichardt.akka.persistence.snapshotstore.SnapshotStoreSpec

class ReactiveMongoSnapshotStoreSpec extends SnapshotStoreSpec {
  override def testConfig: Config = ConfigFactory.parseString(
    s"""
      | akka.persistence.snapshot-store.plugin = "in-memory-snapshot-store"
      | akka.persistence.journal.plugin = "akka.persistence.journal.inmem"
    """.stripMargin)
}