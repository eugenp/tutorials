import java.util.Date;


public class NotifierFactory {

    public <T> Notifier<T> getNotifier(Class<T> clazz) {
        if (clazz == String.class) {
            return Record.STRING.make();
        }
        if (clazz == Date.class) {
            return Record.DATE.make();
        }
        return null;
    }

}


// import java.util.HashMap;
// import java.util.Map;
// import java.util.Date;

// public class HandlerFactory {
//   private Map<Class<T>, Handler<T>> registry = new HashMap<>();

//   private <T> void registerHandler(Class<T> dataType, Handler<T> handlerType) {
//     registry.put(dataType, handlerType);
//   }

//   public <T> HandlerFactory() {
//     registerHandler(String.class, StringHandler.class);
//     registerHandler(Date.class, DateHandler.class);
//   }

//   public <T> Handler<T> getHandler(Class<T> c) {
//     return registry.get(c).newInstance();
//   }
// }
