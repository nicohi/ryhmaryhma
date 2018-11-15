package vinkkeri.objects;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jpssilve
 */
public class Tip {

    private int id;
    private String date;
    private String type;
    private String title;
    private String author;
    private String summary;
    private String isbn;
    private String url;
    private boolean read;

    private List<String> tags;
    private List<String> relC;
    private List<String> reqC;

    public Tip(int id, String date, String type, String title, String author, String summary, String isbn, String url, boolean read) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.title = title;
        this.author = author;
        this.summary = summary;
        this.isbn = isbn;
        this.url = url;
        this.read = read;
        this.tags = new ArrayList<>();
        this.relC = new ArrayList<>();
        this.reqC = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getSummary() {
        return summary;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isRead() {
        return read;
    }

    public String getUrl() {
        return url;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<String> getRelC() {
        return relC;
    }

    public List<String> getReqC() {
        return reqC;
    }
}
