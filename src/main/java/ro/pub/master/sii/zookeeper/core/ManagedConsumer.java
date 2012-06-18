package ro.pub.master.sii.zookeeper.core;

import com.google.common.base.Charsets;
import com.yammer.dropwizard.lifecycle.Managed;
import com.yammer.dropwizard.logging.Log;
import org.apache.commons.collections.Buffer;
import org.apache.commons.collections.BufferUtils;
import org.apache.commons.collections.buffer.CircularFifoBuffer;
import org.apache.zookeeper.KeeperException;

import java.util.Iterator;

import static com.google.common.base.Preconditions.checkState;

public class ManagedConsumer implements Managed {

    private static final Log LOG = Log.forClass(ManagedConsumer.class);

    public static class SequenceReader extends Thread {

        private final String queueName;
        private final ManagedZooKeeper zookeeper;
        private final Buffer latencies = BufferUtils.synchronizedBuffer(new CircularFifoBuffer(5));

        public SequenceReader(String queueName, ManagedZooKeeper zookeeper) {
            this.queueName = queueName;
            this.zookeeper = zookeeper;
        }

        @Override
        public void run() {
            LOG.info("Starting reader on queue /" + queueName);

            DistributedQueue queue = new DistributedQueue(zookeeper.get(), "/" + queueName);
            while (!interrupted()) {
                try {
                    long sent = Long.parseLong(new String(queue.take(), Charsets.UTF_8));
                    Long latency = (System.nanoTime() - sent) / 1000000;
                    LOG.info("Message latency: {}", latency);

                    latencies.add(latency);

                } catch (KeeperException e) {
                    continue;   /* ignore all zookeeper related exception and try again */
                } catch (InterruptedException e) {
                    break;
                }
            }
        }

        public long getLatencyAverage() {
            Iterator it = latencies.iterator();

            int count = 0;
            long sum = 0;

            while (it.hasNext()) {
                sum += ((Long) it.next()).longValue();
                count++;
            }

            return sum / count;
        }
    }

    private final ManagedZooKeeper zookeeper;
    private final SequenceReader sequenceReader;

    public ManagedConsumer(String queueName, ManagedZooKeeper zookeeper) {
        this.zookeeper = zookeeper;
        this.sequenceReader = new SequenceReader(queueName, zookeeper);
    }

    @Override
    public void start() throws Exception {
        checkState(zookeeper.isConnected());
        sequenceReader.start();
    }

    @Override
    public void stop() throws Exception {
        sequenceReader.interrupt();
    }

    public long getLatencyAverage() {
        return sequenceReader.getLatencyAverage();
    }
}
