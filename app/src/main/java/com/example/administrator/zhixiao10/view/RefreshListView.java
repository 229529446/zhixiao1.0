package com.example.administrator.zhixiao10.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.zhixiao10.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 下拉刷新ListView
 */
public class RefreshListView extends ListView implements AbsListView.OnScrollListener{

    private static final int STATE_PULL_REFRESH = 0;//下拉刷新
    private static final int STATE_REKEASE_REFRESH = 1;//松开刷新
    private static final int STATE_REFRESHING = 2;//正在刷新


    private int mCurrentState = STATE_PULL_REFRESH;//当前状态

    private int startY = -1; //滑动起点的y坐标
    private View mHearderView;
    private int mHearderViewHegiht;
    private TextView tvTitle;
    private TextView tvTime;
    private ImageView ivArrow;
    private ProgressBar pbProgress;
    private RotateAnimation animationUP;
    private RotateAnimation animationDown;
    private int mFooterViewHeight;
    private View mFooterView;

    public RefreshListView(Context context) {
        super(context);
        initHeaderView();
        initFooterView();
    }



    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();
        initFooterView();
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView();
        initFooterView();
    }


    /**
     * 初始化头布局
     */
    private void initHeaderView() {

        mHearderView = View.inflate(getContext(), R.layout.refresh_listview_header,null);
        this.addHeaderView(mHearderView);//添加头布局

        tvTitle = (TextView) mHearderView.findViewById(R.id.tv_title);
        tvTime = (TextView) mHearderView.findViewById(R.id.tv_time);
        ivArrow = (ImageView) mHearderView.findViewById(R.id.iv_arr);
        pbProgress = (ProgressBar) mHearderView.findViewById(R.id.pb_progress);


        mHearderView.measure(0,0);
        mHearderViewHegiht = mHearderView.getMeasuredHeight();
        //隐藏头布局
        mHearderView.setPadding(0,-mHearderViewHegiht,0,0);

        //动画
        initArrowAnim();

        //更新时间
        tvTime.setText("最后刷新时间:"+getCurrenttime());

    }

    /**
     * 初始化尾布局
     *
     */
    public void initFooterView(){
        mFooterView = View.inflate(getContext(), R.layout.refresh_listview_footer,null);

        this.addFooterView(mFooterView);

        mFooterView.measure(0,0);
        mFooterViewHeight = mFooterView.getMeasuredHeight();


        mFooterView.setPadding(0,-mFooterViewHeight,0,0);//隐藏

        this.setOnScrollListener(this);

    }


    /**
     * 处理用户触摸事件
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:

                startY = (int) ev.getRawY();//获取滑动起点的y坐标
                break;
            case MotionEvent.ACTION_MOVE:
                if ( startY == -1){ //有时候不响应ACTION_DOWN事件，初始化设为-1确保starty有效
                    startY = (int) ev.getRawY();
                }

                if(mCurrentState == STATE_REFRESHING){//正在刷新是不做处理

                    break;
                }
                int endY = (int) ev.getRawY();  //拿到移动最后的y坐标
                int dy = endY-startY;//移动偏移量；

                if (dy>0 && getFirstVisiblePosition() ==0){//下拉手势而且是第一个
                    int padding = dy-mHearderViewHegiht;
                    mHearderView.setPadding(0,padding,0,0);//动态设置头布局的偏移量

                    if (padding > 0 && mCurrentState !=STATE_REKEASE_REFRESH){//将状态改为松开刷新
                        mCurrentState = STATE_REKEASE_REFRESH;
                        refreshState();
                    }else if (padding < 0 &&mCurrentState != STATE_PULL_REFRESH){//改为下拉刷新
                        mCurrentState = STATE_PULL_REFRESH;
                        refreshState();
                    }
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                startY = -1;
                if (mCurrentState == STATE_REKEASE_REFRESH){
                    mCurrentState = STATE_REFRESHING;//正在刷新
                    mHearderView.setPadding(0,0,0,0);//回位显示
                    refreshState();
                    return true;

                }else if (mCurrentState == STATE_PULL_REFRESH){
                    mHearderView.setPadding(0,-mHearderViewHegiht,0,0);//隐藏
                }

                break;

        }
        return super.onTouchEvent(ev);
    }

    private void refreshState() {

        switch (mCurrentState){
            case STATE_PULL_REFRESH:
                tvTitle.setText("下拉刷新");
                ivArrow.setVisibility(View.VISIBLE);
                pbProgress.setVisibility(View.INVISIBLE);
                ivArrow.startAnimation(animationDown);
                break;
            case STATE_REFRESHING:
                tvTitle.setText("正在刷新...");
                ivArrow.clearAnimation();//要清除动画才可以隐藏
                ivArrow.setVisibility(View.INVISIBLE);
                pbProgress.setVisibility(View.VISIBLE);
                if (mListener != null){
                    mListener.onRefresh();
                }
                break;
            case STATE_REKEASE_REFRESH:
                tvTitle.setText("松开刷新");
                ivArrow.setVisibility(View.VISIBLE);
                pbProgress.setVisibility(View.INVISIBLE);
                ivArrow.startAnimation(animationUP);
                break;


        }

    }


    private void initArrowAnim(){
        //箭头向上动画
        animationUP = new RotateAnimation(0,-180, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animationUP.setDuration(200);
        animationUP.setFillAfter(true);


        //箭头向下动画
        animationDown = new RotateAnimation(-180,0, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animationDown.setDuration(200);
        animationDown.setFillAfter(true);


    }


    OnRefreshListener mListener;
    public void setOnRefreshListener(OnRefreshListener Listener){

        mListener = Listener;
    }



    public interface  OnRefreshListener{
        public void onRefresh();//刷新
        public void onLoadMore();//加载更多
    }


    /**
     * 收起下拉刷新
     */
    public void onRefreshComplete(Boolean success){
        if (isLoadingMore){//正在加载更多
            mFooterView.setPadding(0,-mFooterViewHeight,0,0);
            isLoadingMore = false;

        }else {
            Log.i("jj", "onRefreshComplete: 收起下拉刷新！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");
            mCurrentState = STATE_PULL_REFRESH;
            tvTitle.setText("下拉刷新");
            ivArrow.setVisibility(View.VISIBLE);
            pbProgress.setVisibility(View.INVISIBLE);

            mHearderView.setPadding(0, -mHearderViewHegiht, 0, 0);


            if (success) {
                tvTime.setText("最后刷新时间" + getCurrenttime());
            }
        }

    }

    /**
     * 获取当前时间
     */

    public  String  getCurrenttime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return format.format(new Date());

    }


    private boolean isLoadingMore;

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_FLING){
            if (getLastVisiblePosition() == getCount()-1 && !isLoadingMore){
                Log.i("jj", "onScrollStateChanged: 到底了");
                mFooterView.setPadding(0,0,0,0);
                setSelection(getCount()-1);
                isLoadingMore = true;

                if(mListener != null){
                    mListener.onLoadMore();
                }
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }



}
