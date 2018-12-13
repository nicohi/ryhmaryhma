package vinkkeri.ui.gui.components;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import vinkkeri.ui.gui.controllers.TipListUtils;


/**
 *
 */
public class SearchBar extends ToolBar {

    private ListView listView;
    private TextField searchField;
    private CheckBox hideRead;
    private String readStatus;

    /**
     * @param lv
     */
    public SearchBar(ListView lv) {
        this.readStatus = "";
        this.listView = lv;
        this.searchField = makeSearchField();
        Button clear = makeClearButton();
        this.hideRead = makeHideRead();
        this.getItems().add(this.searchField);
        this.getItems().add(this.hideRead);
        this.getItems().add(clear);
    }

    private TextField makeSearchField() {
        TextField text = new TextField();
        text.prefWidthProperty().bind(listView.tipsList.widthProperty().multiply(0.849));
        text.setId("searchField");
        text.textProperty().addListener((observable, oldValue, newValue) -> {
            //if the text in the searchfield changes
            if (!oldValue.equals(newValue)) {
				TipListUtils.refreshTips(listView, searchField.getText(), readStatus);
            }
        });
        return text;
    }

    private Button makeClearButton() {
        Button b = new Button("clear");
        b.prefWidthProperty().bind(listView.tipsList.widthProperty().multiply(0.048));
        b.setId("searchClear");
        b.setOnAction(event -> {
            searchField.clear();
            hideRead.setSelected(false);
        });
        return b;
    }

    private CheckBox makeHideRead() {
        CheckBox c = new CheckBox("hide read");
        c.setId("hideRead");
        c.prefWidthProperty().bind(listView.tipsList.widthProperty().multiply(0.098));
        c.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (!c.isSelected()) {
                readStatus = "";
            } else if (new_val) {
                readStatus = "false";
            }
			TipListUtils.refreshTips(listView, searchField.getText(), readStatus);
        });

        return c;
    }

	public String getReadStatus() {
		return readStatus;
	}
	
	public String getSearchTerms() {
		return searchField.getText();
	}
}
