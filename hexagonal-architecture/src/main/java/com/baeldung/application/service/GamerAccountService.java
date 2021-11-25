package com.baeldung.application.service;

import com.baeldung.application.domain.GamerAccount;
import com.baeldung.application.port.input.AddRankLevelUseCase;
import com.baeldung.application.port.input.ReduceRankLevelUseCase;
import com.baeldung.application.port.output.GetAccountPort;
import com.baeldung.application.port.output.SaveAccountPort;

public class GamerAccountService implements ReduceRankLevelUseCase, AddRankLevelUseCase {

    private GetAccountPort getAccountPort;
    private SaveAccountPort saveAccountPort;

    public GamerAccountService(GetAccountPort getAccountPort, SaveAccountPort saveAccountPort) {
        this.getAccountPort = getAccountPort;
        this.saveAccountPort = saveAccountPort;
    }

    @Override
    public void AddRankLevel(Long id, int level) {
        GamerAccount gamerAccount=getAccountPort.load(id);

        gamerAccount.addRankLevel(level);
        saveAccountPort.save(gamerAccount);

    }

    @Override
    public boolean reduceRankLevel(Long id, int level) {

        GamerAccount gamerAccount=getAccountPort.load(id);

        boolean hasReduce = gamerAccount.reduceRankLevel(level);
        if(hasReduce){
            saveAccountPort.save(gamerAccount);
        }
        return hasReduce;
    }
}
