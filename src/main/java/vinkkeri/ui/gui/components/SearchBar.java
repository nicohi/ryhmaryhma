package vinkkeri.ui.gui.components;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import vinkkeri.objects.Tip;

import java.util.Arrays;

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
				refreshTips();
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
			refreshTips();
        });

        return c;
    }
	
	/**
	 * Gets tips from db and filters with search().
	 */
	private void refreshTips() {
		listView.refreshTipList();
		listView.populateTipList(listView.tipsList.getItems().filtered(tip -> {
			return search((Tip) tip, searchField.getText(), readStatus);
		}));
	}
	
	/**
	 * Searches for list of searchterms in tip.
	 * @param t
	 * @param s searchterms separated by commas
	 * @param read The read status being searched for. "false" if you want unread tips.
	 * @return true if tip matches search
	 */
    private boolean search(Tip t, String s, String read) {
        String[] searchTerms = s.toLowerCase().split(",");

        //true if any searchterm is present in any tag
        boolean inTags = Arrays.stream(searchTerms).anyMatch(term ->
						 //check all tags
						 t.getTags().stream().anyMatch(tag -> tag.toLowerCase().contains(term)));

        //true if any searchterm is present in the author field 
        boolean inAuthor = Arrays.stream(searchTerms).anyMatch(term -> t.getAuthor().toLowerCase().contains(term));

        //true if any searchterm is present in the title field 
        boolean inTitle = Arrays.stream(searchTerms).anyMatch(term -> t.getTitle().toLowerCase().contains(term));

		boolean readCheck = t.isRead().toLowerCase().contains(read);

        return (inAuthor || inTitle || inTags) && readCheck;
    }
}
