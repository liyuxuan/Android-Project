package cn.com.core.data.model;

public class ShowItem {
    //  标题;
    private String title;
    //  内容;
    private String content;

    public ShowItem(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

}
