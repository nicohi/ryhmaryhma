package vinkkeri.ui.gui.components;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;

public class TipButtonBar extends ToolBar {
	
	
	public TipButtonBar(List<LabelTextInputControl> textItems, ArrayList<RadioButton> rButtons, TextField txtDate, DatePicker datePicker, ArrayList<RadioButton> rButtons2) {
		super();
        // Luodaan valikon napit
        Button btnDelete = new Button("Delete");
        btnDelete.setOnAction((e) -> {
            System.out.println("painettu delete");
        });

        Button btnFindTip = new Button("Find Tip");
        btnFindTip.setOnAction((p) -> {
            System.out.println("painettu Find Tip");
        });

        Button btnSave = new Button("Save");
        btnSave.setOnAction((e) -> {
            System.out.println("painettu tallennusta");
        });

        Button btnNewTip = new Button("New Tip");
        btnNewTip.setOnAction((n) -> {
            System.out.println("painettu uusi vinkki");
        });

        Button btnClear = new Button("Clear");
        btnClear.setOnAction((ActionEvent c) -> {
            for (LabelTextInputControl lt : textItems) {
                lt.clearField();
            }
            txtDate.clear();
            datePicker.setValue(null);

            for (RadioButton rb : rButtons) {
                rb.setSelected(false);
            }

            for (RadioButton rb : rButtons2) {
                rb.setSelected(false);
            }

            System.out.println("painettu clear");
        });

        this.getChildren().addAll(btnClear, btnFindTip, btnNewTip, btnSave, btnDelete);
	}

}
