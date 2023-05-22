module com.example.firstjavafxproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.desktop;

    opens org.example.firstjavafxproject to javafx.fxml;
    exports org.example.firstjavafxproject;
}