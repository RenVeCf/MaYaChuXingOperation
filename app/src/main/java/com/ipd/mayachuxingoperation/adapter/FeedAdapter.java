package com.ipd.mayachuxingoperation.adapter;

import android.content.Context;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ipd.mayachuxingoperation.R;
import com.ipd.mayachuxingoperation.bean.FeedListBean;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.util.List;

import static com.ipd.mayachuxingoperation.utils.AppUtils.goToGaodeMap;

public class FeedAdapter extends BaseQuickAdapter<FeedListBean.DataBean.ListBean, BaseViewHolder> {

    private Context context;

    public FeedAdapter(@Nullable List<FeedListBean.DataBean.ListBean> data, Context context) {
        super(R.layout.adapter_feed_car, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, FeedListBean.DataBean.ListBean item) {
        SuperTextView tvMalfunctionCarItemTitle = helper.getView(R.id.tv_malfunction_car_item_title);
        tvMalfunctionCarItemTitle.setLeftString("车辆编号: " + item.getImei())
                .setRightTvClickListener(new SuperTextView.OnRightTvClickListener() {
                    @Override
                    public void onClickListener() {
                        //跳导航
                        goToGaodeMap(context, item.getLat() + "", item.getLng() + "");
                    }
                });
        helper.setText(R.id.tv_lable, "车辆馈电")
//                .setText(R.id.tv_time, item.getTime())
                .addOnClickListener(R.id.bt_feed);
    }
}