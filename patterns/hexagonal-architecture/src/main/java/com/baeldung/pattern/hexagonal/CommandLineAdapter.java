package com.baeldung.pattern.hexagonal;

class CommandLineAdapter {
    private static final String MSG_TRX_SUCCESSFUL = "Existing trade book exists, volume added";
    private static final String MSG_TRX_QUEUED = "New trade book created";
    private static final String MSG_ERROR = "Transaction error";
    private static final int ORDER_MODIFIED = 0;
    private static final int ORDER_NEW = 1;

    private StockBroker stockBroker;

    String execute(String command, String stockCode, String price, String volume) {
        double priceVal = Double.parseDouble(price);
        int volumeVal = Integer.parseInt(volume);

        String resultCode = stockBroker.addOrder(command, stockCode, priceVal, volumeVal);
        return resultCodeToMessage(resultCode);
    }

    void setStockBroker(StockBroker stockBroker) {
        this.stockBroker = stockBroker;
    }

    private String resultCodeToMessage(String resultCode) {
        String[] codes = resultCode.split(",");
        int status = Integer.parseInt(codes[0]);
        switch (status) {
            case ORDER_MODIFIED:
                return MSG_TRX_SUCCESSFUL;
            case ORDER_NEW:
                return MSG_TRX_QUEUED;
            default:
                return MSG_ERROR;
        }
    }
}
