package com.lfq.mobileoffice.ui.home.message.roomapply;

import com.lfq.mobileoffice.logger.LoggerName;
import com.lfq.mobileoffice.ui.home.message.PendingReviewAndAll;

/**
 * "我的"会议室预约记录
 *
 * @author 李凤强
 */
@LoggerName("会议室预约记录")
@PendingReviewAndAll.Pending(MeRoomApplyPending.class)
@PendingReviewAndAll.All(MeRoomApplyAll.class)
public class MeRoomApply extends PendingReviewAndAll {
}
