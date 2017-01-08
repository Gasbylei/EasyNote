package cn.ftl213.easynote.Bean;

import cn.bmob.v3.BmobObject;


public class Notes extends BmobObject {

    private String title;
    private String content;
    private String writer;

//    public Notes(String title, String content) {
//        this.title = title;
//        this.content = content;
//    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

}
