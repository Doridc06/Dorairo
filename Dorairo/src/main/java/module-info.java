module Dorairo {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.base;
	requires javafx.graphics;
	requires com.google.gson;
	requires okhttp3;
	requires java.sql;
	requires hibernate.core;
	requires hibernate.jpa;

	opens models to com.google.gson, javafx.fxml;
	opens controller to javafx.fxml;

	opens application to javafx.graphics, javafx.fxml;
}
