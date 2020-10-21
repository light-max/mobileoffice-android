package com.lfq.mobileoffice.data.result;

/**
 * 公告
 *
 * @author 李凤强
 */
public class Notice {

    /**
     * id : 235
     * title : 标题94标题94标题94标题94标题94标题94标题94标题94标题94标题94标题94
     * content : 通知内容内容内容94
     * imageCount : 0
     * createTime : 1594373770138
     * top : true
     */

    private int id;
    private String title;
    private String content;
    private int imageCount;
    private long createTime;
    private boolean top;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getImageCount() {
        return imageCount;
    }

    public void setImageCount(int imageCount) {
        this.imageCount = imageCount;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }
}
