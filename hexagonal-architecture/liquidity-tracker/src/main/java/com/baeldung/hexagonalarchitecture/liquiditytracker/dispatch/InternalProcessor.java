package com.baeldung.hexagonalarchitecture.liquiditytracker.dispatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.hexagonalarchitecture.liquiditytracker.domain.LiquidityTracker;
import com.baeldung.hexagonalarchitecture.liquiditytracker.domain.NotEnoughAvailableLiquidityException;
import com.baeldung.hexagonalarchitecture.liquiditytracker.lifecycle.Shutter;
import com.baeldung.hexagonalarchitecture.liquiditytracker.messaging.Sender;
import com.baeldung.hexagonalarchitecture.liquiditytracker.protoapi.LiquidityTrackerIncomingMessage;

/**
 * @author Víctor Gil
 *
 * since March 2019
 */
public class InternalProcessor {
    private static final Logger log = LoggerFactory.getLogger(InternalProcessor.class);
    
    private final LiquidityTracker liquidityTracker;
    
    private final Sender sender;
    
    private final Shutter shutter;
    
    public InternalProcessor(Sender sender, LiquidityTracker liquidityTracker, Shutter shutter) {
        this.sender = sender;
        this.liquidityTracker = liquidityTracker;
        this.shutter = shutter;
    }
    
    public void process(LiquidityTrackerIncomingMessage message){
        log.trace("New incoming message to be processed, type: " + message.getType().name());
        
        switch (message.getType()) {
        
            case GET_LIQUIDITY_LIMIT: 
                long liquidityLimit = liquidityTracker.getLiquidityLimit();
                sender.sendLiquidityLimit(liquidityLimit);
            break;

            case SET_LIQUIDITY_LIMIT:            
                try {
                    liquidityTracker.setLiquidityLimit(message.getValue());
                } catch (NotEnoughAvailableLiquidityException ex) {
                    sender.sendSetLiquidityLimitError(ex.getMessage());
                }
            break;

            case GET_AVAILABLE_LIQUIDITY:
                long availableLiquidity = liquidityTracker.getAvailableLiquidity();
                sender.sendAvailableLiquidity(availableLiquidity);
            break;
            
            case GET_UTILIZED_LIQUIDITY: 
                long utilizedLiquidity = liquidityTracker.getUtilizedLiquidity();
                sender.sendUtilizedLiquidity(utilizedLiquidity);
            break;

            case INCREASE_UTILIZED_LIQUIDITY: 
                try {
                    liquidityTracker.increaseUtilizedLiquidity(message.getValue());
                } catch (NotEnoughAvailableLiquidityException ex) {
                    sender.increaseUtilizedLiquidityError(ex.getMessage());
                }
            break;

            case DECREASE_UTILIZED_LIQUIDITY: 
                liquidityTracker.decreaseUtilizedLiquidity(message.getValue());               
            break;
            
            case SHUTDOWN: 
                shutter.shutdown();               
            break;
            
            default:
                //this will never happen
            break;
        }
    }
}
