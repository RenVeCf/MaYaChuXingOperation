package com.ipd.mayachuxingoperation.adapter;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ipd.mayachuxingoperation.R;
import com.ipd.mayachuxingoperation.bean.HomeInfoBean;
import com.ipd.mayachuxingoperation.utils.ApplicationUtil;

import java.util.List;

/**
 * Description ：
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2019/7/5.
 */
public class HomeGridAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {

    private AppCompatTextView tvHomeGridItemTitle;
    private HomeInfoBean.DataBean homeInfoBean;

    public HomeGridAdapter(@Nullable List<Integer> data, HomeInfoBean.DataBean homeInfoBean) {
        super(R.layout.adapter_home_grid, data);
        this.homeInfoBean = homeInfoBean;
    }

    @Override
    protected void convert(BaseViewHolder helper, Integer item) {
        tvHomeGridItemTitle = helper.getView(R.id.tv_home_grid_item_title);
        switch (item) {
            case 0:
                tvHomeGridItemTitle.setCompoundDrawablesWithIntrinsicBounds(ApplicationUtil.getContext().getResources().getDrawable(R.drawable.ic_blue_point), null, null, null);
                helper.setText(R.id.tv_home_grid_item_title, "城市总车辆")
                        .setText(R.id.tv_home_grid_item_content, homeInfoBean.getCount() + "辆");
                break;
            case 1:
                tvHomeGridItemTitle.setCompoundDrawablesWithIntrinsicBounds(ApplicationUtil.getContext().getResources().getDrawable(R.drawable.ic_orange_point), null, null, null);
                helper.setText(R.id.tv_home_grid_item_title, "在运行车辆")
                        .setText(R.id.tv_home_grid_item_content, homeInfoBean.getNormal() + "辆");
                break;
            case 2:
                tvHomeGridItemTitle.setCompoundDrawablesWithIntrinsicBounds(ApplicationUtil.getContext().getResources().getDrawable(R.drawable.ic_pink_point), null, null, null);
                helper.setText(R.id.tv_home_grid_item_title, "总故障车辆")
                        .setText(R.id.tv_home_grid_item_content, homeInfoBean.getCountProblem() + "辆");
                break;
            case 3:
                tvHomeGridItemTitle.setCompoundDrawablesWithIntrinsicBounds(ApplicationUtil.getContext().getResources().getDrawable(R.drawable.ic_green_point), null, null, null);
                helper.setText(R.id.tv_home_grid_item_title, "暂停使用车辆")
                        .setText(R.id.tv_home_grid_item_content, homeInfoBean.getCountStop() + "辆");
                break;
            case 4:
                tvHomeGridItemTitle.setCompoundDrawablesWithIntrinsicBounds(ApplicationUtil.getContext().getResources().getDrawable(R.drawable.ic_blue_point), null, null, null);
                helper.setText(R.id.tv_home_grid_item_title, "本月故障车辆")
                        .setText(R.id.tv_home_grid_item_content, homeInfoBean.getMonthCount() + "辆");
                break;
            case 5:
                tvHomeGridItemTitle.setCompoundDrawablesWithIntrinsicBounds(ApplicationUtil.getContext().getResources().getDrawable(R.drawable.ic_orange_point), null, null, null);
                helper.setText(R.id.tv_home_grid_item_title, "本月修复车辆")
                        .setText(R.id.tv_home_grid_item_content, homeInfoBean.getMonthRepair() + "辆");
                break;
            case 6:
                tvHomeGridItemTitle.setCompoundDrawablesWithIntrinsicBounds(ApplicationUtil.getContext().getResources().getDrawable(R.drawable.ic_pink_point), null, null, null);
                helper.setText(R.id.tv_home_grid_item_title, "当前故障车辆")
                        .setText(R.id.tv_home_grid_item_content, homeInfoBean.getCountBade() + "辆");
                break;
            case 7:
                tvHomeGridItemTitle.setCompoundDrawablesWithIntrinsicBounds(ApplicationUtil.getContext().getResources().getDrawable(R.drawable.ic_green_point), null, null, null);
                helper.setText(R.id.tv_home_grid_item_title, "已修复车辆")
                        .setText(R.id.tv_home_grid_item_content, homeInfoBean.getCountNormal() + "辆");
                break;
        }
    }
}
