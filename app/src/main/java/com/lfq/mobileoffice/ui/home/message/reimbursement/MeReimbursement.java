package com.lfq.mobileoffice.ui.home.message.reimbursement;

import com.lfq.mobileoffice.ui.home.message.PendingReviewAndAll;

/**
 * 我的报销申请记录
 *
 * @author 李凤强
 */
@PendingReviewAndAll.Pending(MeReimbursementPending.class)
@PendingReviewAndAll.All(MeReimbursementAll.class)
public class MeReimbursement extends PendingReviewAndAll {
}
