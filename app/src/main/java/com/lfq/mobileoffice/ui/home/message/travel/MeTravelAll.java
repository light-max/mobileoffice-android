package com.lfq.mobileoffice.ui.home.message.travel;

import com.lfq.mobileoffice.data.result.TravelPager;
import com.lfq.mobileoffice.net.Net;
import com.lfq.mobileoffice.ui.home.message.MessageRecyclerFragment;
import com.lfq.mobileoffice.Api;

/**
 * 所有出差申请
 *
 * @author 李凤强
 */
public class MeTravelAll extends MessageRecyclerFragment<AdapterMeTravel> {
    @Override
    public void onRefresh() {
        Api.travelPage(null).success((Net.OnSuccess<TravelPager>) travelPager -> {
            setData(travelPager.getData());
            map("pager", travelPager.getPager());
        }).run();
    }

    @Override
    public void onLoadMore() {
        Api.travelPage(map("pager")).success((Net.OnSuccess<TravelPager>) travelPager -> {
            addData(travelPager.getData());
            map("pager", travelPager.getPager());
        }).run();
    }
}
