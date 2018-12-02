package vinkkeri.ui.gui.components;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import vinkkeri.objects.Tip;


public class SearchBar extends ToolBar {
	
	private ListView listView;
	private TextField searchField;
	private Button clear;
	
	public SearchBar(ListView lv) {
		this.listView = lv;
		this.searchField = makeSearchField();
		this.clear = makeClearButton();
		this.getItems().add(this.searchField);
		this.getItems().add(this.clear);
	}

	public TextField makeSearchField() {
		TextField text = new TextField();
		text.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!oldValue.equals(newValue)) {
				listView.refreshTipList();
				listView.populateTipList(listView.tipsList.getItems().filtered(tip -> {
					Tip t = (Tip) tip;	
					//check if searchterm is present in tags
					boolean inTags = t.getTags().stream().filter(tag -> tag.contains(newValue)).findFirst().isPresent();
					return (t.getAuthor() + t.getTitle()).contains(newValue) || inTags;
				}));
			}
		});

		return text;
	}

	private Button makeClearButton() {
		Button b = new Button("clear");
		b.setOnAction(event -> {
			searchField.clear();
		});
		return b;
	}


}
