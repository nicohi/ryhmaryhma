package vinkkeri.ui.gui.components;

import java.util.Arrays;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import vinkkeri.objects.Tip;

/**
 *
 */
public class SearchBar extends ToolBar {
	
	private ListView listView;
	private TextField searchField;
	private Button clear;
	
	/**
	 *
	 * @param lv
	 */
	public SearchBar(ListView lv) {
		this.listView = lv;
		this.searchField = makeSearchField();
		this.clear = makeClearButton();
		this.getItems().add(this.searchField);
		this.getItems().add(this.clear);
	}

	private TextField makeSearchField() {
		TextField text = new TextField();
		text.setId("searchField");
		text.textProperty().addListener((observable, oldValue, newValue) -> {
			//if the text in the searchfield changes
			if (!oldValue.equals(newValue)) {
				listView.refreshTipList();
				listView.populateTipList(listView.tipsList.getItems().filtered(tip -> {
					return search((Tip) tip, newValue);
				}));
			}
		});
		return text;
	}

	private boolean search(Tip t, String s) {
		String[] searchTerms = s.toLowerCase().split(",");
		//check if any searchterm is present in any tag
		boolean inTags = t.getTags().stream()
				.anyMatch(tipTag -> Arrays.stream(searchTerms).anyMatch(tag -> tipTag.contains(tag.toLowerCase())));
		boolean inAuthor = Arrays.stream(searchTerms).anyMatch(term -> t.getAuthor().toLowerCase().contains(term));
		boolean inTitle = Arrays.stream(searchTerms).anyMatch(term -> t.getTitle().toLowerCase().contains(term));
		return inAuthor || inTitle || inTags;
	}
	
	private Button makeClearButton() {
		Button b = new Button("clear");
		b.setId("searchClear");
		b.setOnAction(event -> {
			searchField.clear();
		});
		return b;
	}


}
