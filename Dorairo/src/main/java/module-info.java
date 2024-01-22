module Dorairo {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.base;
	requires javafx.graphics;
	requires com.google.gson;
	requires okhttp3;
	opens controller to com.google.gson; 

	opens application to javafx.graphics, javafx.fxml;
	opens models to javafx.fxml;
}
