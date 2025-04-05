module sample.finalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;


    opens sample.finalproject to javafx.fxml;
    exports sample.finalproject;
}