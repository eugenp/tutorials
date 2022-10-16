package com.baeldung.sbe;

import java.nio.ByteBuffer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MarketDataStreamServer {

    private static final Logger log = LoggerFactory.getLogger(MarketDataStreamServer.class);

    ByteBuffer buffer = ByteBuffer.allocate(128);

    final AtomicLong writePos = new AtomicLong();

    ScheduledExecutorService writerThread = Executors.newScheduledThreadPool(1);
    ScheduledExecutorService readerThreadPool = Executors.newScheduledThreadPool(2);

    private class Client {

        final String name;
        final ByteBuffer readOnlyBuffer;

        final AtomicLong readPos = new AtomicLong();

        Client(String name, ByteBuffer source) {
            this.name = name;
            this.readOnlyBuffer = source.asReadOnlyBuffer();
        }

        void readTradeData() {
            while (readPos.get() < writePos.get()) {
                try {
                    final int pos = this.readOnlyBuffer.position();
                    final MarketData data = MarketDataUtil.readAndDecode(this.readOnlyBuffer);
                    readPos.addAndGet(this.readOnlyBuffer.position() - pos);
                    log.info("<readTradeData> client: {}, read/write gap: {}, data: {}", name, writePos.get() - readPos.get(), data);
                } catch (IndexOutOfBoundsException e) {
                    this.readOnlyBuffer.clear(); // ring buffer
                } catch (Exception e) {
                    log.error("<readTradeData> cannot read from buffer {}", readOnlyBuffer);
                }
            }
            if (this.readOnlyBuffer.remaining() == 0) {
                this.readOnlyBuffer.clear(); // ring buffer
            }
        }

        void read() {
            readerThreadPool.scheduleAtFixedRate(this::readTradeData, 1, 1, TimeUnit.SECONDS);
        }
    }

    private Client newClient(String name) {
        return new Client(name, buffer);
    }

    private void writeTradeData(MarketData data) {
        try {
            final int writtenBytes = MarketDataUtil.encodeAndWrite(buffer, data);
            writePos.addAndGet(writtenBytes);
            log.info("<writeTradeData> buffer size remaining: %{}, data: {}", 100 * buffer.remaining() / buffer.capacity(), data);
        } catch (IndexOutOfBoundsException e) {
            buffer.clear(); // ring buffer
            writeTradeData(data);
        } catch (Exception e) {
            log.error("<writeTradeData> cannot write into buffer {}", buffer);
        }
    }

    private void run(MarketDataSource source) {
        writerThread.scheduleAtFixedRate(() -> {
            if (source.hasNext()) {
                writeTradeData(source.next());
            }
        }, 1, 2, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        MarketDataStreamServer server = new MarketDataStreamServer();
        Client client1 = server.newClient("client1");
        client1.read();
        Client client2 = server.newClient("client2");
        client2.read();
        server.run(new MarketDataSource());
    }

}
