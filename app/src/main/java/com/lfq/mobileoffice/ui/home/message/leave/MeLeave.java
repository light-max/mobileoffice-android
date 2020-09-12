package com.lfq.mobileoffice.ui.home.message.leave;

import com.lfq.mobileoffice.ui.home.message.PendingReviewAndAll;

/**
 * 我的请假申请记录
 */
@PendingReviewAndAll.Pending(MeLeavePending.class)
@PendingReviewAndAll.All(MeLeaveAll.class)
public class MeLeave extends PendingReviewAndAll {
}
