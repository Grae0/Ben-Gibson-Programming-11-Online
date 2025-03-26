module sample.friendsbook {
    requires javafx.controls;
    requires javafx.fxml;


    opens sample.friendsbook to javafx.fxml;
    exports sample.friendsbook;
}