module iiitd.assignment4 {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.web;

	requires org.controlsfx.controls;
	requires com.dlsc.formsfx;
	requires net.synedra.validatorfx;
	requires org.kordamp.ikonli.javafx;
	requires org.kordamp.bootstrapfx.core;
//	requires eu.hansolo.tilesfx;
	requires com.almasb.fxgl.all;

	opens iiitd.assignment4 to javafx.fxml;
	exports iiitd.assignment4;
}