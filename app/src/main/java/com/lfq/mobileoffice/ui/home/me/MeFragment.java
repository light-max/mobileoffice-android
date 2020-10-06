package com.lfq.mobileoffice.ui.home.me;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lfq.mobileoffice.MainActivity;
import com.lfq.mobileoffice.R;
import com.lfq.mobileoffice.base.BaseFragment;
import com.lfq.mobileoffice.ui.attendance.AttendanceActivity;
import com.lfq.mobileoffice.ui.leave.AskForLeaveActivity;
import com.lfq.mobileoffice.ui.meinfo.MeInfoActivity;
import com.lfq.mobileoffice.ui.reimbursement.ReimbursementActivity;
import com.lfq.mobileoffice.ui.setting.SettingActivity;
import com.lfq.mobileoffice.ui.signin.SignInActivity;
import com.lfq.mobileoffice.ui.trip.BusinessTripActivity;

import java.util.Objects;

public class MeFragment extends BaseFragment {
    @Override
    public int getViewResource() {
        return R.layout.fragment_me;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        click(R.id.me_info, v -> startActivity(MeInfoActivity.class));
        click(R.id.ask_for_leave, v -> startActivity(AskForLeaveActivity.class));
        click(R.id.business_trip, v -> startActivity(BusinessTripActivity.class));
        click(R.id.reimbursement, v -> startActivity(ReimbursementActivity.class));
        click(R.id.go_to_work, v -> SignInActivity.goToWork(getContext()));
        click(R.id.off_work, v -> SignInActivity.offWork(getContext()));
        click(R.id.attendance, v -> startActivity(AttendanceActivity.class));
        click(R.id.setting, v -> {
            Intent intent = new Intent(requireActivity(), SettingActivity.class);
            startActivityForResult(intent, SettingActivity.REQUEST_CODE);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SettingActivity.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            requireActivity().finish();
            // 把自动登陆的选项去掉，启动MainActivity
            Objects.requireNonNull(getContext())
                    .getSharedPreferences("login", 0)
                    .edit()
                    .putBoolean("auto", false)
                    .apply();
            startActivity(MainActivity.class);
        }
    }
}
