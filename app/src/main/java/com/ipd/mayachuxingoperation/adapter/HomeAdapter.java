package com.ipd.mayachuxingoperation.adapter;

import android.content.Context;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ipd.mayachuxingoperation.R;
import com.ipd.mayachuxingoperation.bean.DayMalfunctionBean;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.util.List;

import static com.ipd.mayachuxingoperation.utils.AppUtils.goToGaodeMap;

public class HomeAdapter extends BaseQuickAdapter<DayMalfunctionBean.DataBean.ListBean, BaseViewHolder> {

    private Context context;

    public HomeAdapter(@Nullable List<DayMalfunctionBean.DataBean.ListBean> data, Context context) {
        super(R.layout.adapter_malfunction_car, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, DayMalfunctionBean.DataBean.ListBean item) {
        SuperTextView tvMalfunctionCarItemTitle = helper.getView(R.id.tv_malfunction_car_item_title);
        tvMalfunctionCarItemTitle.setLeftString("车辆编号: " + item.getItem_no())
                .setRightTvClickListener(new SuperTextView.OnRightTvClickListener() {
                    @Override
                    public void onClickListener() {
                        //跳导航
                        goToGaodeMap(context, item.getLat() + "", item.getLng() + "");
                    }
                });
        helper.setText(R.id.tv_lable, item.getType())
                .setText(R.id.tv_time, item.getTime())
                .addOnClickListener(R.id.bt_pause)
                .addOnClickListener(R.id.bt_modify);
    }
}
