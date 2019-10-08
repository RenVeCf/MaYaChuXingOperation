package com.ipd.mayachuxingoperation.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxingoperation.R;
import com.ipd.mayachuxingoperation.activity.MalfunctionCarDetailsActivity;
import com.ipd.mayachuxingoperation.activity.PersonalDocumentActivity;
import com.ipd.mayachuxingoperation.adapter.HomeAdapter;
import com.ipd.mayachuxingoperation.adapter.HomeGridAdapter;
import com.ipd.mayachuxingoperation.base.BaseFragment;
import com.ipd.mayachuxingoperation.bean.DayMalfunctionBean;
import com.ipd.mayachuxingoperation.bean.HomeInfoBean;
import com.ipd.mayachuxingoperation.bean.ModifyBean;
import com.ipd.mayachuxingoperation.bean.PauseBean;
import com.ipd.mayachuxingoperation.bean.UploadImgBean;
import com.ipd.mayachuxingoperation.common.view.SpacesItemDecoration;
import com.ipd.mayachuxingoperation.common.view.TopView;
import com.ipd.mayachuxingoperation.contract.HomeContract;
import com.ipd.mayachuxingoperation.presenter.HomePresenter;
import com.ipd.mayachuxingoperation.utils.SPUtil;
import com.ipd.mayachuxingoperation.utils.ToastUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.ipd.mayachuxingoperation.common.config.IConstants.AVATAR;
import static com.ipd.mayachuxingoperation.common.config.IConstants.CITY;
import static com.ipd.mayachuxingoperation.common.config.IConstants.NAME;
import static com.ipd.mayachuxingoperation.common.config.IConstants.REQUEST_CODE_90;
import static com.ipd.mayachuxingoperation.common.config.UrlConfig.BASE_LOCAL_URL;
import static com.ipd.mayachuxingoperation.utils.AppUtils.isInstalled;

/**
 * Description ：首页
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/7.
 */
public class HomeFragment extends BaseFragment<HomeContract.View, HomeContract.Presenter> implements HomeContract.View {
    @BindView(R.id.tv_home)
    TopView tvHome;
    @BindView(R.id.iv_user_head)
    RadiusImageView ivUserHead;
    @BindView(R.id.tv_user_name)
    AppCompatTextView tvUserName;
    @BindView(R.id.tv_city)
    AppCompatTextView tvCity;
    @BindView(R.id.rv_grid_home)
    RecyclerView rvGridHome;
    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    @BindView(R.id.srl_home)
    SwipeRefreshLayout srlHome;

    private List<Integer> itr = new ArrayList<>();//菜单
    private HomeGridAdapter homeGridAdapter;
    private List<DayMalfunctionBean.DataBean.ListBean> dayMalfunctionBeanList = new ArrayList<>();
    private HomeAdapter homeAdapter;
    private int pageNum = 1;//页数
    private boolean isNextPage = false;//是否有下一页
    private String modifyId;//确认修复的id
    private int removePosition;//要移除的position;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public HomeContract.Presenter createPresenter() {
        return new HomePresenter(mContext);
    }

    @Override
    public HomeContract.View createView() {
        return this;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void init(View view) {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(getActivity(), tvHome);

        //菜单
        GridLayoutManager NotUseList = new GridLayoutManager(getContext(), 4);
        rvGridHome.setLayoutManager(NotUseList);
        rvGridHome.addItemDecoration(new GridSpacingItemDecoration(4, 30, false));
        rvGridHome.setNestedScrollingEnabled(false);
        rvGridHome.setHasFixedSize(true); //item如果一样的大小，可以设置为true让RecyclerView避免重新计算大小
        rvGridHome.setItemAnimator(new DefaultItemAnimator()); //默认动画

        // 设置管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);//方向
        rvHome.setLayoutManager(layoutManager);
        rvHome.addItemDecoration(new SpacesItemDecoration(50));
        rvHome.setHasFixedSize(true);// 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvHome.setItemAnimator(new DefaultItemAnimator());//加载动画
        srlHome.setColorSchemeResources(R.color.tx_bottom_navigation_select);//刷新圈颜色

        Glide.with(getActivity()).load(BASE_LOCAL_URL + SPUtil.get(getActivity(), AVATAR, "")).apply(new RequestOptions().placeholder(R.drawable.ic_default_head)).into(ivUserHead);
        tvUserName.setText(SPUtil.get(getActivity(), NAME, "") + "");
        tvCity.setText(SPUtil.get(getActivity(), CITY, "") + "");

        getPresenter().getHomeInfo(false, false);
    }

    @Override
    public void initListener() {
        //下拉刷新
        srlHome.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                initData();
                srlHome.setRefreshing(false);
            }
        });
    }

    @Override
    public void initData() {
        TreeMap<String, String> dayMalfunctionMap = new TreeMap<>();
        dayMalfunctionMap.put("page", pageNum + "");
        dayMalfunctionMap.put("limit", "10");
        getPresenter().getDayMalfunction(dayMalfunctionMap, false, false);
    }

    public static RequestBody getImageRequestBody(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        return RequestBody.create(MediaType.parse(options.outMimeType), new File(filePath));
    }

    /**
     * 跳转高德地图
     */
    private void goToGaodeMap(String lat, String lon) {
        if (!isInstalled(getActivity(), "com.autonavi.minimap")) {
            ToastUtil.showShortToast("请先安装高德地图客户端");
            return;
        }
        StringBuffer stringBuffer = new StringBuffer("amapuri://openFeature?featureName=").append("OnRideNavi");
        stringBuffer.append("&rideType=").append("bike")
                .append("&sourceApplication=").append("amap")
                .append("&lat=").append(lat)
                .append("&lon=").append(lon)
                .append("&dev=").append(0);
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuffer.toString()));
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setPackage("com.autonavi.minimap");
        startActivity(intent);
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
                case REQUEST_CODE_90:
                    Glide.with(this)
                            .load(data.getStringExtra("modify_head"))
                            .into(new SimpleTarget<Drawable>() {
                                @Override
                                public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                                    ivUserHead.setImageDrawable(resource);
                                }
                            });
                    tvUserName.setText(data.getStringExtra("modify_name"));
                    break;
            }
        }
    }

    @OnClick(R.id.cl_home)
    public void onViewClicked() {
        startActivityForResult(new Intent(getContext(), PersonalDocumentActivity.class), REQUEST_CODE_90);
    }

    @Override
    public void resultHomeInfo(HomeInfoBean data) {
        if (data.getCode() == 200) {
            for (int i = 0; i < 8; i++) {
                itr.add(i);
            }
            rvGridHome.setAdapter(homeGridAdapter = new HomeGridAdapter(itr, data.getData()));
        } else
            ToastUtil.showLongToast(data.getMessage());
    }

    @Override
    public void resultDayMalfunction(DayMalfunctionBean data) {
        if (data.getCode() == 200) {
            if (data.getData().getList().size() > 0 || isNextPage) {
                if (pageNum == 1) {
                    dayMalfunctionBeanList.clear();
                    dayMalfunctionBeanList.addAll(data.getData().getList());
                    homeAdapter = new HomeAdapter(dayMalfunctionBeanList, getActivity());
                    rvHome.setAdapter(homeAdapter);
                    homeAdapter.bindToRecyclerView(rvHome);
                    homeAdapter.setEnableLoadMore(true);
                    homeAdapter.openLoadAnimation();
                    homeAdapter.disableLoadMoreIfNotFullPage();

                    homeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            startActivity(new Intent(getContext(), MalfunctionCarDetailsActivity.class).putExtra("id", dayMalfunctionBeanList.get(position).getId() + ""));
                        }
                    });

                    homeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                        @Override
                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                            switch (view.getId()) {
                                case R.id.tv_malfunction_car_item_title:
                                    SuperTextView tvNavigation = (SuperTextView) view;
                                    tvNavigation.setRightTvClickListener(new SuperTextView.OnRightTvClickListener() {
                                        @Override
                                        public void onClickListener() {
                                            goToGaodeMap(dayMalfunctionBeanList.get(position).getLat() + "", dayMalfunctionBeanList.get(position).getLng() + "");
                                        }
                                    });
                                    break;
                                case R.id.bt_pause:
                                    removePosition = position;
                                    TreeMap<String, String> pauseMap = new TreeMap<>();
                                    pauseMap.put("id", dayMalfunctionBeanList.get(position).getId() + "");
                                    getPresenter().getPause(pauseMap, false, false);
                                    break;
                                case R.id.bt_modify:
                                    removePosition = position;
                                    modifyId = dayMalfunctionBeanList.get(position).getId() + "";

                                    PictureSelector.create(HomeFragment.this)
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
                    homeAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                        @Override
                        public void onLoadMoreRequested() {
                            rvHome.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    initData();
                                }
                            }, 1000);
                        }
                    }, rvHome);

                    if (dayMalfunctionBeanList.size() >= 10) {
                        isNextPage = true;
                        pageNum += 1;
                    } else {
                        homeAdapter.loadMoreEnd();
                    }
                } else {
                    if (data.getData().getList().size() == 0)
                        homeAdapter.loadMoreEnd(); //完成所有加载
                    else if (data.getData().getList().size() >= 10) {
                        isNextPage = true;
                        pageNum += 1;
                        homeAdapter.addData(data.getData().getList());
                        homeAdapter.loadMoreComplete(); //完成本次
                    } else {
                        homeAdapter.addData(data.getData().getList());
                        homeAdapter.loadMoreEnd(); //完成所有加载
                    }
                }
            } else {
                dayMalfunctionBeanList.clear();
                homeAdapter = new HomeAdapter(dayMalfunctionBeanList, getActivity());
                rvHome.setAdapter(homeAdapter);
                homeAdapter.loadMoreEnd(); //完成所有加载
                homeAdapter.setEmptyView(R.layout.null_data, rvHome);
            }
        } else
            ToastUtil.showLongToast(data.getMessage());
    }

    @Override
    public void resultPause(PauseBean data) {
        if (data.getCode() == 200) {
            dayMalfunctionBeanList.remove(removePosition);
            homeAdapter.notifyDataSetChanged();
            homeAdapter.setEmptyView(R.layout.null_data, rvHome);
        } else
            ToastUtil.showLongToast(data.getMessage());
    }

    @Override
    public void resultModify(ModifyBean data) {
        if (data.getCode() == 200) {
            dayMalfunctionBeanList.remove(removePosition);
            homeAdapter.notifyDataSetChanged();
            homeAdapter.setEmptyView(R.layout.null_data, rvHome);
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
