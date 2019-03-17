package port;

public interface ILoggingDevice {
    public static final ILoggingDevice NO_DEVICE = new ILoggingDevice() {
        @Override
        public void log(String msg) {
            return;
        }


        @Override
        public void log(String msg, Exception e) {
            return;
        }
    };

    public void log(String msg);
    public void log(String msg, Exception e);
}
