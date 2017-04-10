package mainpackage.playstertest.model;

/**
 * Created by deeppandya on 2017-04-09.
 */

public class Story {
    private String title;
    private String pubDate;
    private String author;
    private String imgUrl;
    private String link;

    public Story() {
    }

    public Story(String title, String pubDate, String author) {
        this.title = title;
        this.pubDate = pubDate;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
