package vinkkeri.ui.gui.components;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import vinkkeri.objects.Tip;

import java.util.Arrays;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;

/**
 *
 */
public class SearchBar extends ToolBar {

    private ListView listView;
    private TextField searchField;
    private Button clear;
	private CheckBox hideRead;

    /**
     *
     * @param lv
     */
    public SearchBar(ListView lv) {
        this.listView = lv;
        this.searchField = makeSearchField();
        this.clear = makeClearButton();
        this.hideRead = makeHideRead();
        this.getItems().add(this.searchField);
        this.getItems().add(this.hideRead);
        this.getItems().add(this.clear);
    }

    private TextField makeSearchField() {
        TextField text = new TextField();
        text.prefWidthProperty().bind(listView.tipsList.widthProperty().multiply(0.85));
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
        b.prefWidthProperty().bind(listView.tipsList.widthProperty().multiply(0.05));
        b.setId("searchClear");
        b.setOnAction(event -> {
            searchField.clear();
        });
        return b;
    }

	private CheckBox makeHideRead() {
		CheckBox c = new CheckBox("hide read");
        c.setId("hideRead");

		c.selectedProperty().addListener(new ChangeListener<Boolean>() {
        public void changed(ObservableValue<? extends Boolean> ov,
							Boolean old_val, Boolean new_val) {
				listView.refreshTipList();
                if (new_val) {
					listView.populateTipList(listView.tipsList.getItems().filtered(tip -> ((Tip) tip).isRead().equals("false")));
				}
        }
		});
		
		return c;
	}

}
