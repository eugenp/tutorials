package com.baeldung.spring;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicLong;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.baeldung.model.Quote;
import com.baeldung.rxjava.service.QuotesService;

import groovy.util.logging.Slf4j;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import ratpack.func.Action;
import ratpack.handling.Chain;
import ratpack.http.ResponseChunks;
import ratpack.http.Status;
import ratpack.server.ServerConfig;
import ratpack.spring.config.EnableRatpack;
import ratpack.sse.ServerSentEvents;
import ratpack.stream.Streams;
import ratpack.stream.TransformablePublisher;
import ratpack.websocket.WebSockets;
import rx.subscriptions.Subscriptions;

/**
 * @author psevestre
 */
@SpringBootApplication
@EnableRatpack
public class EmbedRatpackStreamsApp {
    
    private static final Logger log = LoggerFactory.getLogger(EmbedRatpackStreamsApp.class);

    @Autowired 
    private QuotesService quotesService;
    
    private AtomicLong idSeq =  new AtomicLong(0);
    
    
    @Bean
    public ScheduledExecutorService executorService() {
        return Executors.newScheduledThreadPool(1);
    }
    
    @Bean
    public QuotesService quotesService(ScheduledExecutorService executor) {
        return new QuotesService(executor);
    }

    @Bean
    public Action<Chain> quotes() {
        ServerSentEvents sse = ServerSentEvents.serverSentEvents(quotesService.newTicker(), (evt) -> {
            evt
              .id(Long.toString(idSeq.incrementAndGet()))
              .event("quote")
              .data( q -> q.toString());
        });
        
        return chain -> chain.get("quotes", ctx -> ctx.render(sse));
    }

    @Bean
    public Action<Chain> quotesWS() {
        Publisher<String> pub = Streams.transformable(quotesService.newTicker())
          .map(Quote::toString);
        return chain -> chain.get("quotes-ws", ctx -> WebSockets.websocketBroadcast(ctx, pub));
    }
    
    @Bean
    public Action<Chain> uploadFile() {
        
        return chain -> chain.post("upload", ctx -> {
            TransformablePublisher<? extends ByteBuf> pub = ctx.getRequest().getBodyStream();
            pub.subscribe(new Subscriber<ByteBuf>() {
                private Subscription sub;
                @Override
                public void onSubscribe(Subscription sub) {
                    this.sub = sub;
                    sub.request(1);
                }
    
                @Override
                public void onNext(ByteBuf t) {
                    try {
                        int len = t.readableBytes();
                        log.info("Got {} bytes", len);
    
                        // Do something useful with data
                        
                        // Request next chunk
                        sub.request(1);
                    }
                    finally {
                        // DO NOT FORGET to RELEASE !
                        t.release();
                    }
                }
    
                @Override
                public void onError(Throwable t) {
                    ctx.getResponse().status(500);
                }
    
                @Override
                public void onComplete() {
                    ctx.getResponse().status(202);
                }
            }); 
        });
    }
    
    @Bean
    public Action<Chain> download() {
        return chain -> chain.get("download", ctx -> {
            ctx.getResponse().sendStream(new RandomBytesPublisher(1024,512));
        });
    }

    @Bean
    public Action<Chain> downloadChunks() {
        return chain -> chain.get("downloadChunks", ctx -> {
            ctx.render(ResponseChunks.bufferChunks("application/octetstream",
              new RandomBytesPublisher(1024,512)));
        });
    }
    
    @Bean
    public ServerConfig ratpackServerConfig() {
        return ServerConfig
          .builder()
          .findBaseDir("public")
          .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(EmbedRatpackStreamsApp.class, args);
    }
    
    
    public static class RandomBytesPublisher implements Publisher<ByteBuf> {
        
        private int bufCount;
        private int bufSize;
        private Random rnd = new Random();
        

        RandomBytesPublisher(int bufCount, int bufSize) {
            this.bufCount = bufCount;
            this.bufSize = bufSize;
        }

        @Override
        public void subscribe(Subscriber<? super ByteBuf> s) {
            s.onSubscribe(new Subscription() {
                
                private boolean cancelled = false;
                private boolean recurse;
                private long requested = 0;
                
                @Override
                public void request(long n) {
                    if ( bufCount == 0 ) {
                        s.onComplete();
                        return;
                    }
                    
                    requested += n;
                    if ( recurse ) {
                       return; 
                    }
                    
                    recurse = true;
                    try {
                        while ( requested-- > 0 && !cancelled && bufCount-- > 0 ) {
                            byte[] data = new byte[bufSize];
                            rnd.nextBytes(data);
                            ByteBuf buf = Unpooled.wrappedBuffer(data);
                            s.onNext(buf);
                        }
                    }
                    finally {
                        recurse = false;
                    }
                }
                
                @Override
                public void cancel() {
                    cancelled = true;
                }
            });
            
        }
    }

}
