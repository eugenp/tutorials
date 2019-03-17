package port;

import domain.Board;

public interface IBoardDisplayer {
    public static final IBoardDisplayer NO_DISPLAYER = new IBoardDisplayer() {
        @Override
        public void display(Board board) {
            return;
        }
    };

    public void display(Board board);
}
