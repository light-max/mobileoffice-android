package com.lfq.mobileoffice.data.result;

import java.util.List;

/**
 * 公告分页
 */
public class NoticePager {

    /**
     * pager : {"pageCount":14,"currentPage":1,"url":"/notice/list","size":15,"tailAppend":""}
     * data : [{"id":235,"title":"标题94标题94标题94标题94标题94标题94标题94标题94标题94标题94标题94","content":"通知内容内容内容94","imageCount":0,"createTime":1594373770138,"top":true},{"id":42,"title":"标题1标题1标题1标题1标题1标题1标题1标题1标题1标题1标题1标题1标题1标题1","content":"通知内容内容内容1通知内容内容内容1通知内容内容内容1通知内容内容内容1通知内容内容内容1通知内容内容内容1通知内容内容内容1通知内容内容内容1通知内容内容内容1通知内容内容内容1通知内容内容内容1通知内容内容内容1通知内容内容内容1通知内容内容内容1通知内容内容内容1","imageCount":0,"createTime":1594370827109,"top":false},{"id":43,"title":"标题2标题2标题2标题2标题2标题2标题2标题2标题2标题2标题2标题2标题2标题2标题2","content":"通知内容内容内容2通知内容内容内容2通知内容内容内容2通知内容内容内容2通知内容内容内容2通知内容内容内容2通知内容内容内容2","imageCount":0,"createTime":1594370827119,"top":false},{"id":44,"title":"标题3标题3标题3标题3标题3标题3标题3标题3标题3标题3","content":"通知内容内容内容3通知内容内容内容3通知内容内容内容3通知内容内容内容3通知内容内容内容3通知内容内容内容3通知内容内容内容3","imageCount":0,"createTime":1594370827128,"top":false},{"id":45,"title":"标题4标题4标题4标题4标题4标题4标题4标题4标题4标题4","content":"通知内容内容内容4通知内容内容内容4通知内容内容内容4","imageCount":0,"createTime":1594370827137,"top":false},{"id":46,"title":"标题5标题5标题5标题5标题5","content":"通知内容内容内容5通知内容内容内容5通知内容内容内容5通知内容内容内容5通知内容内容内容5","imageCount":0,"createTime":1594370827145,"top":false},{"id":47,"title":"置顶111","content":"通知内容的撒法楼上的楼上大量粮食","imageCount":0,"createTime":1594370327172,"top":false},{"id":48,"title":"标题7标题7标题7标题7标题7标题7标题7标题7标题7标题7标题7标题7标题7标题7标题7标题7","content":"通知内容内容内容7通知内容内容内容7通知内容内容内容7通知内容内容内容7通知内容内容内容7通知内容内容内容7","imageCount":0,"createTime":1594370827172,"top":false},{"id":49,"title":"标题8标题8标题8标题8标题8","content":"通知内容内容内容8通知内容内容内容8通知内容内容内容8通知内容内容内容8通知内容内容内容8通知内容内容内容8通知内容内容内容8通知内容内容内容8通知内容内容内容8通知内容内容内容8通知内容内容内容8通知内容内容内容8通知内容内容内容8通知内容内容内容8通知内容内容内容8","imageCount":0,"createTime":1594370827180,"top":false},{"id":50,"title":"标题9标题9标题9标题9标题9标题9标题9标题9标题9标题9标题9标题9标题9标题9标题9标题9标题9","content":"通知内容内容内容9","imageCount":0,"createTime":1594370827189,"top":false},{"id":51,"title":"标题10标题10标题10标题10","content":"通知内容内容内容10通知内容内容内容10通知内容内容内容10通知内容内容内容10通知内容内容内容10","imageCount":0,"createTime":1594370827196,"top":false},{"id":52,"title":"标题11标题11标题11标题11标题11标题11标题11标题11标题11标题11标题11标题11标题11标题11标题11标题11标题11","content":"通知内容内容内容11通知内容内容内容11通知内容内容内容11通知内容内容内容11通知内容内容内容11通知内容内容内容11通知内容内容内容11通知内容内容内容11通知内容内容内容11通知内容内容内容11通知内容内容内容11通知内容内容内容11通知内容内容内容11","imageCount":0,"createTime":1594370827202,"top":false},{"id":53,"title":"标题12标题12标题12标题12标题12标题12","content":"通知内容内容内容12通知内容内容内容12通知内容内容内容12通知内容内容内容12通知内容内容内容12通知内容内容内容12","imageCount":0,"createTime":1594370827210,"top":false},{"id":54,"title":"标题13标题13标题13标题13标题13标题13标题13标题13标题13标题13标题13标题13标题13标题13标题13标题13","content":"通知内容内容内容13通知内容内容内容13通知内容内容内容13通知内容内容内容13","imageCount":0,"createTime":1594370827217,"top":false},{"id":55,"title":"标题14标题14标题14标题14标题14标题14标题14","content":"通知内容内容内容14通知内容内容内容14通知内容内容内容14通知内容内容内容14通知内容内容内容14","imageCount":0,"createTime":1594370827226,"top":false}]
     */

    private Pager pager;
    private List<Notice> data;

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public List<Notice> getData() {
        return data;
    }

    public void setData(List<Notice> data) {
        this.data = data;
    }
}
