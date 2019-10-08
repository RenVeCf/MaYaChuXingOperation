package com.ipd.mayachuxingoperation.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxingoperation.R;
import com.ipd.mayachuxingoperation.activity.MalfunctionCarDetailsActivity;
import com.ipd.mayachuxingoperation.adapter.MalfunctionCarAdapter;
import com.ipd.mayachuxingoperation.base.BaseFragment;
import com.ipd.mayachuxingoperation.bean.MalfunctionSumBean;
import com.ipd.mayachuxingoperation.bean.ModifyBean;
import com.ipd.mayachuxingoperation.bean.PauseBean;
import com.ipd.mayachuxingoperation.bean.UploadImgBean;
import com.ipd.mayachuxingoperation.common.view.SpacesItemDecoration;
import com.ipd.mayachuxingoperation.common.view.TopView;
import com.ipd.mayachuxingoperation.contract.MalfunctionSumContract;
import com.ipd.mayachuxingoperation.presenter.MalfunctionSumPresenter;
import com.ipd.mayachuxingoperation.utils.ToastUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import io.reactivex.ObservableTransformer;
import okhttp3.RequestBody;

import static com.ipd.mayachuxingoperation.fragment.HomeFragment.getImageRequestBody;

/**
 * Description ：故障车辆
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/7.
 */
public class MalfunctionCarFragment extends BaseFragment<MalfunctionSumContract.View, MalfunctionSumContract.Presenter> implements MalfunctionSumContract.View {
    @BindView(R.id.tv_malfunction_car)
    TopView tvMalfunctionCar;
    @BindView(R.id.rv_malfunction_car)
    RecyclerView rvMalfunctionCar;
    @BindView(R.id.srl_malfunction_car)
    SwipeRefreshLayout srlMalfunctionCar;

    private List<MalfunctionSumBean.DataBean.ListBean> malfunctionSumBeanList = new ArrayList<>();
    private MalfunctionCarAdapter malfunctionCarAdapter;
    private int pageNum = 1;//页数
    private boolean isNextPage = false;//是否有下一页
    private String modifyId;//确认修复的id
    private int removePosition;//要移除的position;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_malfunction_car;
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
        ImmersionBar.setTitleBar(getActivity(), tvMalfunctionCar);

        // 设置管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);//方向
        rvMalfunctionCar.setLayoutManager(layoutManager);
        rvMalfunctionCar.addItemDecoration(new SpacesItemDecoration(50));
        rvMalfunctionCar.setHasFixedSize(true);// 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvMalfunctionCar.setItemAnimator(new DefaultItemAnimator());//加载动画
        srlMalfunctionCar.setColorSchemeResources(R.color.tx_bottom_navigation_select);//刷新圈颜色
    }

    @Override
    public void initListener() {
        //下拉刷新
        srlMalfunctionCar.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                initData();
                srlMalfunctionCar.setRefreshing(false);
            }
        });
    }

    @Override
    public void initData() {
        TreeMap<String, String> malfunctionSumMap = new TreeMap<>();
        malfunctionSumMap.put("status", "1");
        malfunctionSumMap.put("page", pageNum + "");
        malfunctionSumMap.put("limit", "10");
        getPresenter().getMalfunctionSum(malfunctionSumMap, false, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    TreeMap<String, RequestBody> map = new TreeMap<>();
                    map.put("file\";filename=\"" + ".jpeg", getImageRequestBody(PictureSelector.obtainMultipleResult(data).get(0).getCompressPath()));
                    getPresenter().getUploadImg(map, false, false);
                    break;
            }
        }
    }

    @Override
    public void resultMalfunctionSum(MalfunctionSumBean data) {
        if (data.getCode() == 200) {
            if (data.getData().getList().size() > 0 || isNextPage) {
                if (pageNum == 1) {
                    malfunctionSumBeanList.clear();
                    malfunctionSumBeanList.addAll(data.getData().getList());
                    malfunctionCarAdapter = new MalfunctionCarAdapter(malfunctionSumBeanList, getActivity());
                    rvMalfunctionCar.setAdapter(malfunctionCarAdapter);
                    malfunctionCarAdapter.bindToRecyclerView(rvMalfunctionCar);
                    malfunctionCarAdapter.setEnableLoadMore(true);
                    malfunctionCarAdapter.openLoadAnimation();
                    malfunctionCarAdapter.disableLoadMoreIfNotFullPage();

                    malfunctionCarAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            startActivity(new Intent(getContext(), MalfunctionCarDetailsActivity.class).putExtra("id", malfunctionSumBeanList.get(position).getId() + ""));
                        }
                    });

                    malfunctionCarAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                        @Override
                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                            switch (view.getId()) {
                                case R.id.bt_pause:
                                    removePosition = position;

                                    TreeMap<String, String> pauseMap = new TreeMap<>();
                                    pauseMap.put("id", malfunctionSumBeanList.get(position).getId() + "");
                                    getPresenter().getPause(pauseMap, false, false);
                                    break;
                                case R.id.bt_modify:
                                    removePosition = position;
                                    modifyId = malfunctionSumBeanList.get(position).getId() + "";

                                    PictureSelector.create(MalfunctionCarFragment.this)
                                            .openGallery(PictureMimeType.ofImage())
                                            .maxSelectNum(1)// 最大图片选择数量 int
                                            .isCamera(true)
                                            .compress(true)
                                            .minimumCompressSize(100)
                                            .forResult(PictureConfig.CHOOSE_REQUEST);
                                    break;
                            }
                        }
                    });

                    //上拉加载
                    malfunctionCarAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                        @Override
                        public void onLoadMoreRequested() {
                            rvMalfunctionCar.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    initData();
                                }
                            }, 1000);
                        }
                    }, rvMalfunctionCar);

                    if (malfunctionSumBeanList.size() >= 10) {
                        isNextPage = true;
                        pageNum += 1;
                    } else {
                        malfunctionCarAdapter.loadMoreEnd();
                    }
                } else {
                    if (data.getData().getList().size() == 0)
                        malfunctionCarAdapter.loadMoreEnd(); //完成所有加载
                    else if (data.getData().getList().size() >= 10) {
                        isNextPage = true;
                        pageNum += 1;
                        malfunctionCarAdapter.addData(data.getData().getList());
                        malfunctionCarAdapter.loadMoreComplete(); //完成本次
                    } else {
                        malfunctionCarAdapter.addData(data.getData().getList());
                        malfunctionCarAdapter.loadMoreEnd(); //完成所有加载
                    }
                }
            } else {
                malfunctionSumBeanList.clear();
                malfunctionCarAdapter = new MalfunctionCarAdapter(malfunctionSumBeanList, getActivity());
                rvMalfunctionCar.setAdapter(malfunctionCarAdapter);
                malfunctionCarAdapter.loadMoreEnd(); //完成所有加载
                malfunctionCarAdapter.setEmptyView(R.layout.null_data, rvMalfunctionCar);
            }
        } else
            ToastUtil.showLongToast(data.getMessage());
    }

    @Override
    public void resultPause(PauseBean data) {
        if (data.getCode() == 200) {
            malfunctionSumBeanList.remove(removePosition);
            malfunctionCarAdapter.notifyDataSetChanged();
            malfunctionCarAdapter.setEmptyView(R.layout.null_data, rvMalfunctionCar);
        } else
            ToastUtil.showLongToast(data.getMessage());
    }

    @Override
    public void resultModify(ModifyBean data) {
        if (data.getCode() == 200) {
            malfunctionSumBeanList.remove(removePosition);
            malfunctionCarAdapter.notifyDataSetChanged();
            malfunctionCarAdapter.setEmptyView(R.layout.null_data, rvMalfunctionCar);
        } else
            ToastUtil.showLongToast(data.getMessage());
    }

    @Override
    public void resultUploadImg(UploadImgBean data) {
        if (data.getCode() == 200) {
            TreeMap<String, String> modifyMap = new TreeMap<>();
            modifyMap.put("url", data.getData().getUrl());
            getPresenter().getModify(modifyId, modifyMap, false, false);
        } else
            ToastUtil.showLongToast(data.getMessage());
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindUntilEvent(FragmentEvent.PAUSE);
    }
}
