package info.schleichardt.akka.persistence.snapshotstore

import akka.persistence.snapshot.SnapshotStore
import akka.persistence.{SnapshotMetadata, SelectedSnapshot, SnapshotSelectionCriteria}
import scala.concurrent.Future
import concurrent.ExecutionContext.Implicits.global

class InMemorySnapshotStore extends SnapshotStore {
  var snapshots: Vector[SelectedSnapshot] = Vector.empty

  override def delete(processorId: String, criteria: SnapshotSelectionCriteria): Unit = ???

  override def delete(metadata: SnapshotMetadata): Unit = ???

  override def saved(metadata: SnapshotMetadata): Unit = {}

  override def saveAsync(metadata: SnapshotMetadata, snapshot: Any): Future[Unit] = Future {
    snapshots = snapshots :+ SelectedSnapshot(metadata, snapshot)
  }

  override def loadAsync(processorId: String, criteria: SnapshotSelectionCriteria): Future[Option[SelectedSnapshot]] = Future {
    snapshots.filter(x => x.metadata.processorId == processorId &&
      x.metadata.sequenceNr <= criteria.maxSequenceNr &&
      x.metadata.timestamp <= criteria.maxTimestamp).
      sortBy(_.metadata.timestamp).lastOption
  }
}
