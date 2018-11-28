package vinkkeri.objects;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author jpssilve
 */
public class Tip {

    private SimpleStringProperty id;
    private SimpleStringProperty date;
    private SimpleStringProperty type;
    private SimpleStringProperty title;
    private SimpleStringProperty author;
    private SimpleStringProperty summary;
    private SimpleStringProperty isbn;
    private SimpleStringProperty url;
    private SimpleStringProperty read;

    private List<String> tags;
    private List<String> relatedCourses;
    private List<String> requiredCourses;

    /**
     *
     * @param id The id of the database entry for Tip
     * @param date Tip creation date
     * @param type What type of tip, i.e. book, course, link etc
     * @param title The name of the tip type
     * @param author Who authored the tip content
     * @param summary A summary of the tip content
     * @param isbn If the type has an isbn-code
     * @param url The url of the content if it is in the web
     * @param read Has the tip content been read or not
     */
    public Tip(int id, String date, String type, String title, String author, String summary, String isbn, String url, boolean read) {
        this.id = new SimpleStringProperty(id+"");
        this.date = new SimpleStringProperty(date);
        this.type = new SimpleStringProperty(type);
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.summary = new SimpleStringProperty(summary);
        this.isbn = new SimpleStringProperty(isbn);
        this.url = new SimpleStringProperty(url);
        this.read = new SimpleStringProperty(read+"");
        this.tags = new ArrayList<>();
        this.relatedCourses = new ArrayList<>();
        this.requiredCourses = new ArrayList<>();
    }

    /**
     * Constructor for Tips not from the database
     * @param type
     * @param title
     * @param author
     * @param summary
     * @param isbn
     * @param url
     * @param read
     */
    public Tip(String type, String title, String author, String summary, String isbn, String url, boolean read) {
        this(-1, "null", type, title, author, summary, isbn, url, read);
    }

    public int getId() {
        return Integer.parseInt(id.get());
    }

    public String getDate() {
        return date.get();
    }

    public String getType() {
        return type.get();
    }

    public String getTitle() {
        return title.get();
    }

    public String getAuthor() {
        return author.get();
    }

    public String getSummary() {
        return summary.get();
    }

    @Override
    public String toString() {
        
        String tempISBN = "";
        if(!this.isbn.equals("")) {
            tempISBN = "ISBN: " + this.isbn.get() + "\n";
        }
        
        String tempURL = "";
        if(!this.url.equals("")) {
            tempURL = "Link: " + this.url.get() + "\n";
        }
        
        String tagStr = "";
        String rlcStr = "";
        String rqcStr = "";
        
        for(String tag : this.tags) {
            tagStr += tag + " ";
        }
        
        if(!tagStr.equals("")) {
            tagStr = "Tags: " + tagStr + "\n";
        }
        
        
        for(String course : this.relatedCourses) {
            rlcStr += course + " ";
        }
        
        if(!rlcStr.equals("")) {
            rlcStr = "Related Courses: " + rlcStr + "\n";
        }
        
        
        for(String course : this.requiredCourses) {
            rqcStr += course + " ";
        }
        
        if(!rqcStr.equals("")) {
            rqcStr = "Required Courses: " + rqcStr + "\n";
        }
        
        return "Title: " + title.get() + "\n"
                + "Added: " + date.get() + "\n"
                + "Author: " + author.get() + "\n"
                + "Summary: " + summary.get() + "\n"
                + tempISBN
                + tempURL
                + "Read: " + read.get() + "\n"
                + tagStr
                + rqcStr
                + rlcStr;
    }

    public String getIsbn() {
        return isbn.get();
    }

    public boolean isRead() {
        return Boolean.parseBoolean(read.get());
    }
    
    public void setRead(boolean read) {
        this.read = new SimpleStringProperty(read+"");
    }

    public String getUrl() {
        return url.get();
    }

    /**
     * Tags related to the tip's content
     *
     * @return A list of tags
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * Allows the setting of tags, which are related to the tip.
     *
     * @param tags A list of related tags
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    /**
     * All the courses, which are related to the tip's subject matter.
     *
     * @return The related courses
     */
    public List<String> getRelatedCourses() {
        return this.relatedCourses;
    }

    /**
     * Allows the setting of computer science courses which are related to the
     * tip.
     *
     * @param relC A list of related courses
     */
    public void setRelatedCourses(List<String> relatedCourses) {
        this.relatedCourses = relatedCourses;
    }

    /**
     * If the Tip type is a course, then it might have a list of courses that
     * need to be completed before one can enroll on the course specified in
     * this tip.
     *
     * @return A list of required courses
     */
    public List<String> getRequiredCourses() {
        return this.requiredCourses;
    }

    /**
     * Allows the setting of required previous courses, which is useful if the
     * Tip type is a course.
     *
     * @param reqC A list of required courses
     */
    public void setRequiredCourses(List<String> requiredCourses) {
        this.requiredCourses = requiredCourses;
    }
}
