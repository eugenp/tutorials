package com.baeldung.hexagonalarchitecture.liquiditytracker.main;

import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.hexagonalarchitecture.liquiditytracker.config.ConfigReader;
import com.baeldung.hexagonalarchitecture.liquiditytracker.config.ConfigValues;
import com.baeldung.hexagonalarchitecture.liquiditytracker.db.DbConnector;
import com.baeldung.hexagonalarchitecture.liquiditytracker.db.LiquidityLimitProviderImpl;
import com.baeldung.hexagonalarchitecture.liquiditytracker.db.LiquidityLimitSetterImpl;
import com.baeldung.hexagonalarchitecture.liquiditytracker.db.UtilizedLiquidityProviderImpl;
import com.baeldung.hexagonalarchitecture.liquiditytracker.db.UtilizedLiquiditySetterImpl;
import com.baeldung.hexagonalarchitecture.liquiditytracker.dispatch.InternalConsumer;
import com.baeldung.hexagonalarchitecture.liquiditytracker.dispatch.InternalProcessor;
import com.baeldung.hexagonalarchitecture.liquiditytracker.domain.impl.LiquidityTrackerImpl;
import com.baeldung.hexagonalarchitecture.liquiditytracker.lifecycle.ShutterImpl;
import com.baeldung.hexagonalarchitecture.liquiditytracker.messaging.BrokerConnector;
import com.baeldung.hexagonalarchitecture.liquiditytracker.messaging.MessageListener;
import com.baeldung.hexagonalarchitecture.liquiditytracker.messaging.MessageProcessorImpl;
import com.baeldung.hexagonalarchitecture.liquiditytracker.messaging.SenderImpl;
import com.baeldung.hexagonalarchitecture.liquiditytracker.protoapi.LiquidityTrackerIncomingMessage;

/**
 * @author Víctor Gil
 *
 * since March 2019
 */
public class LiquidityTrackerMain {
    private static final Logger log = LoggerFactory.getLogger(LiquidityTrackerMain.class);
    
    public static void main(String[] args) {
        new LiquidityTrackerMain().run();
    }
    
    private void run(){
        log.info("Starting application.");
        
        ConfigReader configReader = new ConfigReader();
        ConfigValues configValues;
        try {
            configValues = configReader.read();
        } catch (Exception ex) {
            log.error("FATAL: " + "Unable to read the config file, exiting.");
            return;
        } 
        
        LiquidityLimitProviderImpl liquidityLimitProvider = new LiquidityLimitProviderImpl();
        LiquidityLimitSetterImpl liquidityLimitSetter = new LiquidityLimitSetterImpl();
        UtilizedLiquidityProviderImpl utilizedLiquidityProvider = new UtilizedLiquidityProviderImpl();
        UtilizedLiquiditySetterImpl utilizedLiquiditySetter = new UtilizedLiquiditySetterImpl();
        
        DbConnector dbConnector = new DbConnector();
        dbConnector.setConfigValues(configValues.getDatabase());
        
        dbConnector.setLiquidityLimitProvider(liquidityLimitProvider);
        dbConnector.setLiquidityLimitSetter(liquidityLimitSetter);
        dbConnector.setUtilizedLiquidityProvider(utilizedLiquidityProvider);
        dbConnector.setUtilizedLiquiditySetter(utilizedLiquiditySetter);        
        
        try {
            dbConnector.start();
        } catch (SQLException ex) {
            log.error("FATAL: exiting", ex);
            return;
        }
        
        LiquidityTrackerImpl tracker = new LiquidityTrackerImpl();
        tracker.setLiquidityLimitProvider(liquidityLimitProvider);
        tracker.setLiquidityLimitSetter(liquidityLimitSetter);
        tracker.setLiquidityLimitProvider(liquidityLimitProvider);
        tracker.setUtilizedLiquiditySetter(utilizedLiquiditySetter);
        
        
        BrokerConnector brokerConnector = new BrokerConnector();
        brokerConnector.setConfigValues(configValues.getMessaging());
     
        SenderImpl sender = new SenderImpl();
        brokerConnector.setSender(sender);
 
        ShutterImpl shutter = new ShutterImpl();
        InternalProcessor internalProcessor = new InternalProcessor(sender, tracker, shutter);
        
        BlockingQueue<LiquidityTrackerIncomingMessage> queue = 
                new LinkedBlockingQueue<LiquidityTrackerIncomingMessage>();
        
        MessageProcessorImpl messageProcessor = new MessageProcessorImpl(queue);
        MessageListener messageListener = new MessageListener();
        messageListener.setProcessor(messageProcessor);
        
        brokerConnector.setMessageListener(messageListener);        
 
        InternalConsumer internalConsumer = new InternalConsumer(queue, internalProcessor);
        
        shutter.addStoppable(messageListener);
        shutter.addStoppable(dbConnector);
        shutter.addStoppable(internalConsumer);
        
        final ExecutorService executorService = Executors.newFixedThreadPool(1);     
        executorService.execute(internalConsumer);       
        executorService.shutdown();
        
        try {
            dbConnector.start();
            liquidityLimitProvider.start();
            liquidityLimitSetter.start();
            utilizedLiquidityProvider.start();
            utilizedLiquiditySetter.start();
        } catch (SQLException ex) {
            log.error("FATAL, exiting", ex);
            return;
        }
        
        try {
            brokerConnector.start();
            sender.start();
            messageListener.start();
        } catch (Exception ex) {
            log.error("FATAL, exiting", ex);
        }
    }
}
