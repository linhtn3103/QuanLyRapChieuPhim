package controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import usercontrol.control.HomeButton;

public class HomeController implements Initializable {
	@FXML private GridPane pane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// warring
		if (MainController.menu.length > 10)
			pane.addColumn(4, new Node[pane.getRowConstraints().size()]);

		int r = pane.getRowConstraints().size();
		int ignore = 0;
		for (int i = 0; i < MainController.menu.length; i++) {
			if (MainController.menu[i] == "Home") {
				ignore += 1;
				continue;
			}
			int R = (i - ignore) % r;
			int C = (i - ignore) / r;
			pane.add(new HomeButton(MainController.menu[i]), R, C);
		}
	}
}