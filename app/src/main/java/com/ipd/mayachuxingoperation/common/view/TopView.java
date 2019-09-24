package com.ipd.mayachuxingoperation.common.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ipd.mayachuxingoperation.R;
import com.ipd.mayachuxingoperation.activity.FeedCarActivity;
import com.ipd.mayachuxingoperation.activity.PauseCarActivity;
import com.ipd.mayachuxingoperation.utils.ApplicationUtil;

import static com.ipd.mayachuxingoperation.utils.isClickUtil.isFastClick;

/**
 * Description : 公用标题栏
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2017/11/loading1
 */

public class TopView extends RelativeLayout implements View.OnClickListener {

    private TextView tvTopTitle;
    private LinearLayout llTopBack;
    private Button btTopPauseCar, btTopFeedCar;

    //各icon是否显示
    private Boolean isBack, isPauseCar, isFeedCar;
    private Context mContext;

    public TopView(Context context) {
        super(context);
        initValues(context);
    }

    public TopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initValues(context);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopView);
        tvTopTitle.setText(typedArray.getString(R.styleable.TopView_title));
        tvTopTitle.setTextColor(typedArray.getColor(R.styleable.TopView_title_color, getResources().getColor(R.color.black)));
        isBack = typedArray.getBoolean(R.styleable.TopView_is_back, false);
        isPauseCar = typedArray.getBoolean(R.styleable.TopView_is_pause_car, false);
        isFeedCar = typedArray.getBoolean(R.styleable.TopView_is_feed_car, false);
        typedArray.recycle();

        llTopBack.setVisibility(isBack ? View.VISIBLE : View.GONE);
        btTopPauseCar.setVisibility(isPauseCar ? View.VISIBLE : View.GONE);
        btTopFeedCar.setVisibility(isFeedCar ? View.VISIBLE : View.GONE);
    }

    public TopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initValues(context);
    }

    private void initValues(final Context context) {
        mContext = context;
        View.inflate(context, R.layout.top_view, this);
        tvTopTitle = (TextView) this.findViewById(R.id.tv_top_title);
        llTopBack = (LinearLayout) this.findViewById(R.id.ll_top_back);
        btTopPauseCar = (Button) this.findViewById(R.id.bt_top_pause_car);
        btTopFeedCar = (Button) this.findViewById(R.id.bt_top_feed_car);

        llTopBack.setOnClickListener(this);
        btTopPauseCar.setOnClickListener(this);
        btTopFeedCar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_top_back:
                if (mContext instanceof Activity && isFastClick()) {
                    ((Activity) mContext).finish();
                    if (((Activity) mContext).getCurrentFocus() != null) {
                        ((InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(((Activity) mContext).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
                break;
            case R.id.bt_top_pause_car:
                ApplicationUtil.getContext().startActivity(new Intent(ApplicationUtil.getContext(), PauseCarActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            case R.id.bt_top_feed_car:
                ApplicationUtil.getContext().startActivity(new Intent(ApplicationUtil.getContext(), FeedCarActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            default:
                break;
        }
    }
}
