module com.example.bankeralgorithm {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.bankeralgorithm to javafx.fxml;
    exports com.example.bankeralgorithm;
}