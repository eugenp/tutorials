package com.baeldung.ethereumj.listeners;

import org.ethereum.core.Block;
import org.ethereum.core.TransactionReceipt;
import org.ethereum.facade.Ethereum;
import org.ethereum.listener.EthereumListenerAdapter;
import org.ethereum.util.BIUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.List;

public class EthListener extends EthereumListenerAdapter {

    private Logger l = LoggerFactory.getLogger(EthListener.class);
    private Ethereum ethereum;
    private boolean syncDone = false;
    private static final int thou = 1000;

    private void out(String t) {
        l.info(t);
    }

    private String calcNetHashRate(Block block) {
        String response = "Net hash rate not available";
        if (block.getNumber() > thou) {
            long timeDelta = 0;
            for (int i = 0; i < thou; ++i) {
                Block parent = ethereum
                  .getBlockchain()
                  .getBlockByHash(block.getParentHash());
                timeDelta += Math.abs(block.getTimestamp() - parent.getTimestamp());
            }
            response = String.valueOf(block
              .getDifficultyBI()
              .divide(BIUtil.toBI(timeDelta / thou))
              .divide(new BigInteger("1000000000"))
              .doubleValue()) + " GH/s";
        }
        return response;
    }

    public EthListener(Ethereum ethereum) {
        this.ethereum = ethereum;
    }

    @Override
    public void onBlock(Block block, List<TransactionReceipt> receipts) {
        if (syncDone) {
            out("Net hash rate: " + calcNetHashRate(block));
            out("Block difficulty: " + block.getDifficultyBI().toString());
            out("Block transactions: " + block.getTransactionsList().toString());
            out("Best block (last block): " + ethereum
              .getBlockchain()
              .getBestBlock().toString());
            out("Total difficulty: " + ethereum
              .getBlockchain()
              .getTotalDifficulty().toString());
        }
    }

    @Override
    public void onSyncDone(SyncState state) {
        out("onSyncDone " + state);
        if (!syncDone) {
            out(" ** SYNC DONE ** ");
            syncDone = true;
        }
    }
}