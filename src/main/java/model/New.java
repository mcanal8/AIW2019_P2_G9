package model;

import java.util.ArrayList;
import java.util.List;

public class New {

    private String title;
    private String link;
    private String content;
    private String summary;
    private String author;
    private String date;
    private List<String> categories;

    public New(){}

    public New(String title, String link, String content, String summary, String author, String date) {
        this.title = title;
        this.link = link;
        this.content = content;
        this.summary = summary;
        this.author = author;
        this.date = date;
        this.categories = new ArrayList<String>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) { this.content = content; }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAuthor() { return author; }

    public void setAuthor(String author) { this.author = author; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public List<String> getCategories() { return categories; }

    public void setCategories(List<String> categories) { this.categories = categories; }
}
