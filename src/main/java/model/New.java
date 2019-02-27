package model;

public class New {

    private String title;
    private String link;
    private String content;

    public New(String title, String link) {
        this.title = title;
        this.link = link;
        this.content = "";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() { return link; }

    public void setLink(String link) {this.link = link; }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
