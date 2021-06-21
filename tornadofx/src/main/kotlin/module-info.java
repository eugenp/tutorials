module TFXSAMPLE {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;
    requires tornadofx;
    requires kotlin.stdlib;
    exports com.example.demo.app to javafx.graphics, tornadofx;
    exports com.example.demo.view to tornadofx;
}