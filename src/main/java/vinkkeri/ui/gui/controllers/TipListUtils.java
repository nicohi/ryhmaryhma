package vinkkeri.ui.gui.controllers;

import java.util.Arrays;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.TextField;
import vinkkeri.objects.Tip;
import vinkkeri.ui.gui.components.ListView;

public class TipListUtils {

	/**
	 * Gets tips from db and filters with search().
	 */
	public static void refreshTips(ListView lv, String searchTerms, String readStatus) {
		populateTipList(lv, lv.display.getController().getTips());
		populateTipList(lv, lv.tipsList.getItems().filtered(tip -> {
			return search((Tip) tip, searchTerms, readStatus);
		}));
	}

    public static void populateTipList(ListView lv, List<Tip> tips) {
        lv.tipsList.setItems(FXCollections.observableArrayList(tips));
    }
	
	/**
	 * Searches for list of searchterms in tip.
	 * @param t
	 * @param s searchterms separated by commas
	 * @param read The read status being searched for. "false" if you want unread tips.
	 * @return true if tip matches search
	 */
    public static boolean search(Tip t, String s, String read) {
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
