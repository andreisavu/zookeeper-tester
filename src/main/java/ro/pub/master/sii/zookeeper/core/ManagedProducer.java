package ro.pub.master.sii.zookeeper.core;

import com.google.common.base.Charsets;
import com.yammer.dropwizard.lifecycle.Managed;
import com.yammer.dropwizard.logging.Log;
import org.apache.zookeeper.KeeperException;

import static com.google.common.base.Preconditions.checkState;

public class ManagedProducer implements Managed {

    private static final Log LOG = Log.forClass(ManagedConsumer.class);

    public static class SequenceWriter extends Thread {

        private final String queueName;
        private final ManagedZooKeeper zookeeper;

        public SequenceWriter(String queueName, ManagedZooKeeper zookeeper) {
            this.queueName = queueName;
            this.zookeeper = zookeeper;
        }

        @Override
        public void run() {
            LOG.info("Starting writer on queue /" + queueName);

            DistributedQueue queue = new DistributedQueue(zookeeper.get(), "/" + queueName);
            while (!interrupted()) {
                try {
                    queue.offer(("" + System.nanoTime()).getBytes(Charsets.UTF_8));
                    Thread.sleep(250);

                } catch (KeeperException e) {
                    continue;   /* ignore all zookeeper related exception and try again */
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    private final ManagedZooKeeper zookeeper;
    private final SequenceWriter sequenceWriter;

    public ManagedProducer(String queueName, ManagedZooKeeper zookeeper) {
        this.zookeeper = zookeeper;
        this.sequenceWriter = new SequenceWriter(queueName, zookeeper);
    }

    @Override
    public void start() throws Exception {
        checkState(zookeeper.isConnected());
        sequenceWriter.start();
    }

    @Override
    public void stop() throws Exception {
        sequenceWriter.interrupt();
    }
}
