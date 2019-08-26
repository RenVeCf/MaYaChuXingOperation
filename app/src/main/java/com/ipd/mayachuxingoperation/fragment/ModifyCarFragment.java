package com.ipd.mayachuxingoperation.fragment;

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
import com.ipd.mayachuxingoperation.activity.ModifyCarDetailsActivity;
import com.ipd.mayachuxingoperation.adapter.ModifyCarAdapter;
import com.ipd.mayachuxingoperation.base.BaseFragment;
import com.ipd.mayachuxingoperation.bean.MalfunctionSumBean;
import com.ipd.mayachuxingoperation.bean.ModifyBean;
import com.ipd.mayachuxingoperation.bean.PauseBean;
import com.ipd.mayachuxingoperation.bean.UploadImgBean;
import com.ipd.mayachuxingoperation.common.view.SpacesItemDecoration;
import com.ipd.mayachuxingoperation.common.view.TopView;
import com.ipd.mayachuxingoperation.contract.MalfunctionSumContract;
import com.ipd.mayachuxingoperation.presenter.MalfunctionSumPresenter;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import io.reactivex.ObservableTransformer;

/**
 * Description ：修复车辆
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/7.
 */
public class ModifyCarFragment extends BaseFragment<MalfunctionSumContract.View, MalfunctionSumContract.Presenter> implements MalfunctionSumContract.View {
    @BindView(R.id.tv_modify_car)
    TopView tvModifyCar;
    @BindView(R.id.rv_modify_car)
    RecyclerView rvModifyCar;
    @BindView(R.id.srl_modify_car)
    SwipeRefreshLayout srlModifyCar;

    private List<MalfunctionSumBean.DataBean.ListBean> malfunctionSumBeanList = new ArrayList<>();
    private ModifyCarAdapter modifyCarAdapter;
    private int pageNum = 1;//页数
    private boolean isNextPage = false;//是否有下一页

    @Override
    public int getLayoutId() {
        return R.layout.fragment_modify_car;
    }

    @Override
    public MalfunctionSumContract.Presenter createPresenter() {
        return new MalfunctionSumPresenter(mContext);
    }

    @Override
    public MalfunctionSumContract.View createView() {
        return this;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void init(View view) {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(getActivity(), tvModifyCar);

        // 设置管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);//方向
        rvModifyCar.setLayoutManager(layoutManager);
        rvModifyCar.addItemDecoration(new SpacesItemDecoration(50));
        rvModifyCar.setHasFixedSize(true);// 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvModifyCar.setItemAnimator(new DefaultItemAnimator());//加载动画
        srlModifyCar.setColorSchemeResources(R.color.tx_bottom_navigation_select);//刷新圈颜色
    }

    @Override
    public void initListener() {
        //下拉刷新
        srlModifyCar.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                initData();
                srlModifyCar.setRefreshing(false);
            }
        });
    }

    @Override
    public void initData() {
        TreeMap<String, String> malfunctionSumMap = new TreeMap<>();
        malfunctionSumMap.put("status", "3");
        malfunctionSumMap.put("page", pageNum + "");
        malfunctionSumMap.put("limit", "10");
        getPresenter().getMalfunctionSum(malfunctionSumMap, false, false);
    }

    @Override
    public void resultMalfunctionSum(MalfunctionSumBean data) {
        if (data.getData().getList().size() > 0 || isNextPage) {
            if (pageNum == 1) {
                malfunctionSumBeanList.clear();
                malfunctionSumBeanList.addAll(data.getData().getList());
                modifyCarAdapter = new ModifyCarAdapter(malfunctionSumBeanList);
                rvModifyCar.setAdapter(modifyCarAdapter);
                modifyCarAdapter.bindToRecyclerView(rvModifyCar);
                modifyCarAdapter.setEnableLoadMore(true);
                modifyCarAdapter.openLoadAnimation();
                modifyCarAdapter.disableLoadMoreIfNotFullPage();

                modifyCarAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        startActivity(new Intent(getContext(), ModifyCarDetailsActivity.class).putExtra("id", malfunctionSumBeanList.get(position).getId() + ""));
                    }
                });

                //上拉加载
                modifyCarAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                    @Override
                    public void onLoadMoreRequested() {
                        rvModifyCar.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                initData();
                            }
                        }, 1000);
                    }
                }, rvModifyCar);

                if (malfunctionSumBeanList.size() >= 10) {
                    isNextPage = true;
                    pageNum += 1;
                } else {
                    modifyCarAdapter.loadMoreEnd();
                }
            } else {
                if (data.getData().getList().size() == 0)
                    modifyCarAdapter.loadMoreEnd(); //完成所有加载
                else if ((data.getData().getList().size() - pageNum * 10) >= 0) {
                    isNextPage = true;
                    pageNum += 1;
                    modifyCarAdapter.addData(data.getData().getList());
                    modifyCarAdapter.loadMoreComplete(); //完成本次
                } else {
                    modifyCarAdapter.addData(data.getData().getList());
                    modifyCarAdapter.loadMoreEnd(); //完成所有加载
                }
            }
        } else {
            malfunctionSumBeanList.clear();
            modifyCarAdapter = new ModifyCarAdapter(malfunctionSumBeanList);
            rvModifyCar.setAdapter(modifyCarAdapter);
            modifyCarAdapter.loadMoreEnd(); //完成所有加载
            modifyCarAdapter.setEmptyView(R.layout.null_data, rvModifyCar);
        }
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
        return this.bindUntilEvent(FragmentEvent.PAUSE);
    }
}
