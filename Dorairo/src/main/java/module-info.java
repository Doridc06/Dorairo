module Dorairo {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.base;
	requires javafx.graphics;
	requires com.google.gson;
	requires okhttp3;
	requires java.sql;
	requires java.persistence;
	requires org.hibernate.orm.core;

	opens models to com.google.gson, javafx.fxml, org.hibernate.orm.core;
	opens controller to javafx.fxml;

	opens application to javafx.graphics, javafx.fxml;
}
