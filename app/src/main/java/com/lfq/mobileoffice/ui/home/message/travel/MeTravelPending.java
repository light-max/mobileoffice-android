package com.lfq.mobileoffice.ui.home.message.travel;

import com.lfq.mobileoffice.data.result.TravelPager;
import com.lfq.mobileoffice.net.Net;
import com.lfq.mobileoffice.ui.home.message.MessageRecyclerFragment;
import com.lfq.mobileoffice.Api;

/**
 * 待审核的出差申请
 */
public class MeTravelPending extends MessageRecyclerFragment<AdapterMeTravel> {
    @Override
    public void onRefresh() {
        Api.travelPending().success((Net.OnSuccess<TravelPager>) travelPager -> {
            setData(travelPager.getData());
        }).run();
    }
}
