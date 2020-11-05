package com.lfq.mobileoffice.ui.notice;

import android.annotation.SuppressLint;

import com.lfq.mobileoffice.Api;
import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.BaseDetailsActivity;
import com.lfq.mobileoffice.data.result.Notice;
import com.lfq.mobileoffice.logger.LoggerName;
import com.lfq.mobileoffice.util.Utils;

/**
 * 公告详情activity
 *
 * @author 李凤强
 */
@LoggerName("公告详情")
@BaseDetailsActivity.ViewResource(R.layout.activity_notice_details)
@BaseDetailsActivity.RelativePath("notice")
public class NoticeDetailsActivity extends BaseDetailsActivity<Notice> {
    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onLoad(Notice notice) {
        String time = Utils.dateFormat("YYYY年M月d HH:mm", notice.getCreateTime());
        text(R.id.title).setText(notice.getTitle());
        text(R.id.time).setText(time);
        text(R.id.content).setText(notice.getContent());
        Api.isNoticeRead(notice.getId(), aBoolean -> {
            if (aBoolean) {
                text(R.id.i_know, "已读");
                text(R.id.i_know).setTextColor(getColor(android.R.color.darker_gray));
            }
        });
        click(R.id.i_know, v -> Api.noticeRead(notice.getId(), aBoolean -> {
            text(R.id.i_know, "已读");
            text(R.id.i_know).setTextColor(getColor(android.R.color.darker_gray));
        }));
    }


}
