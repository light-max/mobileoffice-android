package com.lfq.mobileoffice.ui.home.me;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.BaseFragment;
import com.lfq.mobileoffice.ui.leave.AskForLeaveActivity;
import com.lfq.mobileoffice.ui.reimbursement.ReimbursementActivity;
import com.lfq.mobileoffice.ui.trip.BusinessTripActivity;

public class MeFragment extends BaseFragment {
    @Override
    public int getViewResource() {
        return R.layout.fragment_me;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        click(R.id.ask_for_leave, v -> startActivity(AskForLeaveActivity.class));
        click(R.id.business_trip, v -> startActivity(BusinessTripActivity.class));
        click(R.id.reimbursement, v -> startActivity(ReimbursementActivity.class));
    }
}
