package com.ipd.mayachuxingoperation.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ipd.mayachuxingoperation.R;
import com.ipd.mayachuxingoperation.bean.MalfunctionSumBean;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.util.List;

public class ModifyCarAdapter extends BaseQuickAdapter<MalfunctionSumBean.DataBean.ListBean, BaseViewHolder> {

    public ModifyCarAdapter(@Nullable List<MalfunctionSumBean.DataBean.ListBean> data) {
        super(R.layout.adapter_modify_car, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MalfunctionSumBean.DataBean.ListBean item) {
        SuperTextView tvModifyCarItemTitle = helper.getView(R.id.tv_modify_car_item_title);
        tvModifyCarItemTitle.setLeftString("车辆编号: " + item.getItem_no());
        helper.setText(R.id.tv_lable, item.getType())
                .setText(R.id.tv_time, item.getTime());
    }
}
