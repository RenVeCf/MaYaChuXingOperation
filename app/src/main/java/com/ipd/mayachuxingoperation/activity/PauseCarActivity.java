package com.ipd.mayachuxingoperation.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxingoperation.R;
import com.ipd.mayachuxingoperation.adapter.PauseCarAdapter;
import com.ipd.mayachuxingoperation.base.BaseActivity;
import com.ipd.mayachuxingoperation.bean.MalfunctionSumBean;
import com.ipd.mayachuxingoperation.bean.ModifyBean;
import com.ipd.mayachuxingoperation.bean.PauseBean;
import com.ipd.mayachuxingoperation.bean.UploadImgBean;
import com.ipd.mayachuxingoperation.common.view.SpacesItemDecoration;
import com.ipd.mayachuxingoperation.common.view.TopView;
import com.ipd.mayachuxingoperation.contract.MalfunctionSumContract;
import com.ipd.mayachuxingoperation.presenter.MalfunctionSumPresenter;
import com.ipd.mayachuxingoperation.utils.ApplicationUtil;
import com.ipd.mayachuxingoperation.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import io.reactivex.ObservableTransformer;

/**
 * Description ：暂停车辆
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/8.
 */
public class PauseCarActivity extends BaseActivity<MalfunctionSumContract.View, MalfunctionSumContract.Presenter> implements MalfunctionSumContract.View {

    @BindView(R.id.tv_pause_car)
    TopView tvPauseCar;
    @BindView(R.id.rv_pause_car)
    RecyclerView rvPauseCar;
    @BindView(R.id.srl_pause_car)
    SwipeRefreshLayout srlPauseCar;

    private List<MalfunctionSumBean.DataBean.ListBean> malfunctionSumBeanList = new ArrayList<>();
    private PauseCarAdapter pauseCarAdapter;
    private int pageNum = 1;//页数

    @Override
    public int getLayoutId() {
        return R.layout.activity_pause_car;
    }

    @Override
    public MalfunctionSumContract.Presenter createPresenter() {
        return new MalfunctionSumPresenter(this);
    }

    @Override
    public MalfunctionSumContract.View createView() {
        return this;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void init() {
        //将每个Activity加入到栈中
        ApplicationUtil.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvPauseCar);

        // 设置管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);//方向
        rvPauseCar.setLayoutManager(layoutManager);
        rvPauseCar.addItemDecoration(new SpacesItemDecoration(50));
        rvPauseCar.setHasFixedSize(true);// 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvPauseCar.setItemAnimator(new DefaultItemAnimator());//加载动画
        srlPauseCar.setColorSchemeResources(R.color.tx_bottom_navigation_select);//刷新圈颜色
    }

    @Override
    public void initData() {
        TreeMap<String, String> malfunctionSumMap = new TreeMap<>();
        malfunctionSumMap.put("status", "2");
        malfunctionSumMap.put("page", pageNum + "");
        malfunctionSumMap.put("limit", "10");
        getPresenter().getMalfunctionSum(malfunctionSumMap, false, false);
    }

    @Override
    public void initListener() {
        //下拉刷新
        srlPauseCar.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                initData();
                srlPauseCar.setRefreshing(false);
            }
        });
    }

    @Override
    public void resultMalfunctionSum(MalfunctionSumBean data) {
        if (data.getCode() == 200) {
            if (data.getData().getList().size() > 0) {
                if (pageNum == 1) {
                    malfunctionSumBeanList.clear();
                    malfunctionSumBeanList.addAll(data.getData().getList());
                    pauseCarAdapter = new PauseCarAdapter(malfunctionSumBeanList);
                    rvPauseCar.setAdapter(pauseCarAdapter);
                    pauseCarAdapter.bindToRecyclerView(rvPauseCar);
                    pauseCarAdapter.setEnableLoadMore(true);
                    pauseCarAdapter.openLoadAnimation();
                    pauseCarAdapter.disableLoadMoreIfNotFullPage();

                    pauseCarAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            startActivity(new Intent(PauseCarActivity.this, PauseCarDetailsActivity.class).putExtra("id", malfunctionSumBeanList.get(position).getId() + ""));
                        }
                    });

                    //上拉加载
                    pauseCarAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                        @Override
                        public void onLoadMoreRequested() {
                            rvPauseCar.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    initData();
                                }
                            }, 1000);
                        }
                    }, rvPauseCar);

                    if (malfunctionSumBeanList.size() >= 10) {
                        pageNum += 1;
                    } else {
                        pauseCarAdapter.loadMoreEnd();
                    }
                } else {
                    if ((data.getData().getList().size() - pageNum * 10) >= 0) {
                        pageNum += 1;
                        pauseCarAdapter.addData(data.getData().getList());
                        pauseCarAdapter.loadMoreComplete(); //完成本次
                    } else {
                        pauseCarAdapter.addData(data.getData().getList());
                        pauseCarAdapter.loadMoreEnd(); //完成所有加载
                    }
                }
            } else {
                malfunctionSumBeanList.clear();
                pauseCarAdapter = new PauseCarAdapter(malfunctionSumBeanList);
                rvPauseCar.setAdapter(pauseCarAdapter);
                pauseCarAdapter.loadMoreEnd(); //完成所有加载
                pauseCarAdapter.setEmptyView(R.layout.null_data, rvPauseCar);
            }
        } else
            ToastUtil.showLongToast(data.getMessage());
    }

    @Override
    public void resultPause(PauseBean data) {

    }

    @Override
    public void resultModify(ModifyBean data) {

    }

    @Override
    public void resultUploadImg(UploadImgBean data) {

    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }
}
