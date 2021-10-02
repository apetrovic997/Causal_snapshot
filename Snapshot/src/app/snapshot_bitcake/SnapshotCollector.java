package app.snapshot_bitcake;

import app.Cancellable;

/**
 * Describes a snapshot collector. Made not-so-flexibly for readability.
 * 
 * @author bmilojkovic
 *
 */
public interface SnapshotCollector extends Runnable, Cancellable {

	BitcakeManager getBitcakeManager();

	void addNaiveSnapshotInfo(String snapshotSubject, int amount);
	void addABSnapshotInfo(int id, ABSnapshotResult abSnapshotResult);
	void addAVDoneMessages(int id, Boolean received);

	void startCollecting();

}