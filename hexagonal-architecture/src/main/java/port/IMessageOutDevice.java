package port;

import domain.Board;

public interface IMessageOutDevice {
    public static final IMessageOutDevice NO_DEVICE = new IMessageOutDevice() {
        @Override
        public void out(String msg) {
            return;
        }


        @Override
        public void outWinningSymbol(Board.Symbol symbol) {

        }
    };

    public void out(String msg);
    public void outWinningSymbol(Board.Symbol symbol);
}
