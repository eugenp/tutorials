module gson.exception {

    requires com.google.gson;
    requires org.slf4j;

    opens gson.exception to com.google.gson;

    exports gson.exception;
}
