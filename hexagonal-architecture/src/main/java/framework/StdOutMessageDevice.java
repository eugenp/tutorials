package framework;

import domain.Board;
import port.IMessageOutDevice;

public abstract class StdOutMessageDevice implements IMessageOutDevice {
    @Override
    public void out(String msg) {
        System.out.println(msg);
    }


    @Override
    public abstract void outWinningSymbol(Board.Symbol symbol);
}
