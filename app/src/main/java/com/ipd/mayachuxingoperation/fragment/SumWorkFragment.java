package com.ipd.mayachuxingoperation.fragment;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatTextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxingoperation.R;
import com.ipd.mayachuxingoperation.base.BaseFragment;
import com.ipd.mayachuxingoperation.bean.SumWorkBean;
import com.ipd.mayachuxingoperation.common.view.TopView;
import com.ipd.mayachuxingoperation.contract.SumWorkContract;
import com.ipd.mayachuxingoperation.presenter.SumWorkPresenter;
import com.ipd.mayachuxingoperation.utils.DateUtils;
import com.ipd.mayachuxingoperation.utils.ToastUtil;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.Calendar;
import java.util.Date;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;

/**
 * Description ：工作统计
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/7.
 */
public class SumWorkFragment extends BaseFragment<SumWorkContract.View, SumWorkContract.Presenter> implements SumWorkContract.View {
    @BindView(R.id.tv_sum_work)
    TopView tvSumWork;
    @BindView(R.id.tv_start_time)
    AppCompatTextView tvStartTime;
    @BindView(R.id.tv_end_time)
    AppCompatTextView tvEndTime;
    @BindView(R.id.tv_malfunction_car_num)
    AppCompatTextView tvMalfunctionCarNum;
    @BindView(R.id.tv_modify_car_num)
    AppCompatTextView tvModifyCarNum;
    @BindView(R.id.tv_day_malfunction_car_num)
    AppCompatTextView tvDayMalfunctionCarNum;
    @BindView(R.id.tv_day_modify_car_num)
    AppCompatTextView tvDayModifyCarNum;

    private TimePickerView pvTime;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_sum_work;
    }

    @Override
    public SumWorkContract.Presenter createPresenter() {
        return new SumWorkPresenter(mContext);
    }

    @Override
    public SumWorkContract.View createView() {
        return this;
    }

    @Override
    public void init(View view) {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(getActivity(), tvSumWork);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        tvMalfunctionCarNum.setText("-");
        tvModifyCarNum.setText("-");
    }

    private void request() {
        TreeMap<String, String> sumWorkMap = new TreeMap<>();
        sumWorkMap.put("btime", tvStartTime.getText().toString().trim());
        sumWorkMap.put("etime", tvEndTime.getText().toString().trim());
        getPresenter().getSumWork(sumWorkMap, false, false);
    }

    //时间选择器
    private void selectTime(final int type) {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        //startDate.set(2013,1,1);
        Calendar endDate = Calendar.getInstance();
        //endDate.set(2020,1,1);

        //正确设置方式 原因：注意事项有说明
        startDate.set(2019, 7, 1);
        endDate.set(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance().get(Calendar.DATE));

        pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                switch (type) {
                    case 1:
                        tvStartTime.setText(DateUtils.timedate1(date.getTime() + ""));
                        tvStartTime.setTextColor(Color.BLACK);

                        if (!"选择起始日期".equals(tvStartTime.getText().toString().trim()) && !"选择结束日期".equals(tvEndTime.getText().toString().trim()))
                            request();
                        break;
                    case 2:
                        tvEndTime.setText(DateUtils.timedate1(date.getTime() + ""));
                        tvEndTime.setTextColor(Color.BLACK);

                        if (!"选择起始日期".equals(tvStartTime.getText().toString().trim()) && !"选择结束日期".equals(tvEndTime.getText().toString().trim()))
                            request();
                        break;
                }

            }
        })
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final Button tvSubmit = (Button) v.findViewById(R.id.bt_pickview_confirm);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.returnData();
                                pvTime.dismiss();
                            }
                        });
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
//                .setCancelText("请选择起始时间")//取消按钮文字
//                .setSubmitText("")//确认按钮文字
                //                .setContentSize(18)//滚轮文字大小
//                .setTitleSize(16)//标题文字大小
//                .setTitleText("请选择起始时间")
                .setDecorView((ViewGroup) getActivity().getWindow().getDecorView().findViewById(android.R.id.content))
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
//                .setTitleColor(Color.BLACK)//标题文字颜色
//                .setSubmitColor(Color.BLACK)//确定按钮文字颜色
//                .setCancelColor(Color.BLACK)//取消按钮文字颜色
//                .setTitleBgColor(0xFFFFFFFF)//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setDividerColor(getResources().getColor(R.color.transparent))
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setLabel("", "", "", "时", "分", "秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .build();
        pvTime.show();
    }

    @OnClick({R.id.tv_start_time, R.id.tv_end_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_start_time:
                selectTime(1);
                break;
            case R.id.tv_end_time:
                selectTime(2);
                break;
        }
    }

    @Override
    public void resultSumWork(SumWorkBean data) {
        if (data.getCode() == 200) {
            tvMalfunctionCarNum.setText(data.getData().getProblem() + "");
            tvModifyCarNum.setText(data.getData().getRepair() + "");
            tvDayMalfunctionCarNum.setText("当日故障车辆: " + data.getData().getDayProblem() + "");
            tvDayModifyCarNum.setText("当日修复车辆: " + data.getData().getDayRepair() + "");
        } else
            ToastUtil.showLongToast(data.getMessage());
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindUntilEvent(FragmentEvent.PAUSE);
    }
}
