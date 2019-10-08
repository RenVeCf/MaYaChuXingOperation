package com.ipd.mayachuxingoperation.activity;

import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxingoperation.R;
import com.ipd.mayachuxingoperation.adapter.FeedAdapter;
import com.ipd.mayachuxingoperation.base.BaseActivity;
import com.ipd.mayachuxingoperation.bean.FeedListBean;
import com.ipd.mayachuxingoperation.bean.IsFeedBean;
import com.ipd.mayachuxingoperation.common.view.SpacesItemDecoration;
import com.ipd.mayachuxingoperation.common.view.TopView;
import com.ipd.mayachuxingoperation.contract.FeedContract;
import com.ipd.mayachuxingoperation.presenter.FeedPresenter;
import com.ipd.mayachuxingoperation.utils.ApplicationUtil;
import com.ipd.mayachuxingoperation.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import io.reactivex.ObservableTransformer;

/**
 * Description ：馈电车辆
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/9/24.
 */
public class FeedCarActivity extends BaseActivity<FeedContract.View, FeedContract.Presenter> implements FeedContract.View {

    @BindView(R.id.tv_feed_car)
    TopView tvFeedCar;
    @BindView(R.id.rv_feed_car)
    RecyclerView rvFeedCar;
    @BindView(R.id.srl_feed_car)
    SwipeRefreshLayout srlFeedCar;

    private List<FeedListBean.DataBean.ListBean> feedList = new ArrayList<>();
    private FeedAdapter feedAdapter;
    private int pageNum = 1;//页数
    private boolean isNextPage = false;//是否有下一页
    private int removePosition;//要移除的position;

    @Override
    public int getLayoutId() {
        return R.layout.activity_feed_car;
    }

    @Override
    public FeedContract.Presenter createPresenter() {
        return new FeedPresenter(this);
    }

    @Override
    public FeedContract.View createView() {
        return this;
    }

    @Override
    public void init() {
        //将每个Activity加入到栈中
        ApplicationUtil.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvFeedCar);

        // 设置管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);//方向
        rvFeedCar.setLayoutManager(layoutManager);
        rvFeedCar.addItemDecoration(new SpacesItemDecoration(50));
        rvFeedCar.setHasFixedSize(true);// 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvFeedCar.setItemAnimator(new DefaultItemAnimator());//加载动画
        srlFeedCar.setColorSchemeResources(R.color.tx_bottom_navigation_select);//刷新圈颜色
    }

    @Override
    public void initData() {
        TreeMap<String, String> feedListMap = new TreeMap<>();
        feedListMap.put("page", pageNum + "");
        feedListMap.put("limit", "10");
        getPresenter().getFeedList(feedListMap, false, false);
    }

    @Override
    public void initListener() {
        //下拉刷新
        srlFeedCar.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                initData();
                srlFeedCar.setRefreshing(false);
            }
        });
    }

    @Override
    public void resultFeedList(FeedListBean data) {
        if (data.getCode() == 200) {
            if (data.getData().getList().size() > 0 || isNextPage) {
                if (pageNum == 1) {
                    feedList.clear();
                    feedList.addAll(data.getData().getList());
                    feedAdapter = new FeedAdapter(feedList, this);
                    rvFeedCar.setAdapter(feedAdapter);
                    feedAdapter.bindToRecyclerView(rvFeedCar);
                    feedAdapter.setEnableLoadMore(true);
                    feedAdapter.openLoadAnimation();
                    feedAdapter.disableLoadMoreIfNotFullPage();

                    feedAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                        @Override
                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                            switch (view.getId()) {
                                case R.id.bt_feed:
                                    removePosition = position;

                                    TreeMap<String, String> isFeedMap = new TreeMap<>();
                                    isFeedMap.put("imei", data.getData().getList().get(position).getImei());
                                    getPresenter().getIsFeed(isFeedMap, false, false);
                                    break;
                            }
                        }
                    });

                    //上拉加载
                    feedAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                        @Override
                        public void onLoadMoreRequested() {
                            rvFeedCar.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    initData();
                                }
                            }, 1000);
                        }
                    }, rvFeedCar);

                    if (feedList.size() >= 10) {
                        isNextPage = true;
                        pageNum += 1;
                    } else {
                        feedAdapter.loadMoreEnd();
                    }
                } else {
                    if (data.getData().getList().size() == 0)
                        feedAdapter.loadMoreEnd(); //完成所有加载
                    else if (data.getData().getList().size() >= 10) {
                        isNextPage = true;
                        pageNum += 1;
                        feedAdapter.addData(data.getData().getList());
                        feedAdapter.loadMoreComplete(); //完成本次
                    } else {
                        feedAdapter.addData(data.getData().getList());
                        feedAdapter.loadMoreEnd(); //完成所有加载
                    }
                }
            } else {
                feedList.clear();
                feedAdapter = new FeedAdapter(feedList, this);
                rvFeedCar.setAdapter(feedAdapter);
                feedAdapter.loadMoreEnd(); //完成所有加载
                feedAdapter.setEmptyView(R.layout.null_data, rvFeedCar);
            }
        } else
            ToastUtil.showShortToast(data.getMessage());
    }

    @Override
    public void resultIsFeed(IsFeedBean data) {
        ToastUtil.showShortToast(data.getMessage());
        if (data.getCode() == 200) {
            feedList.remove(removePosition);
            feedAdapter.notifyDataSetChanged();
            feedAdapter.setEmptyView(R.layout.null_data, rvFeedCar);
        }
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }
}
