package com.it.zhihudemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.it.zhihudemo.listener.OnMeiziClickedListener;

import java.util.List;

/**
 * created by gyt on 2016/8/25
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {


    private final List<GirlInfo.ResultsEntity> mResults;
    private final        Context                      mContext;
    private static       GirlInfo.ResultsEntity       mMeizi;
    private static       OnMeiziClickedListener       mOnMeiziClickedListener;

    public MyRecyclerAdapter(Context context, List<GirlInfo.ResultsEntity> results) {
        this.mResults = results;
        this.mContext = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = View.inflate(mContext, R.layout.item_girl, null);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder ViewHolder, int position) {
        mMeizi = mResults.get(position);
        ViewHolder.mTextView.setText(mMeizi.getUrl());
        Glide.with(mContext)
                .load(mMeizi.getUrl())
                .placeholder(R.color.white)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(ViewHolder.mImageView);
    }

    @Override
    public int getItemCount() {
        if (mResults == null) {
            return 0;
        }
        return mResults.size();
    }

    public void setOnMeiziClickedListener(OnMeiziClickedListener onMeiziClickedListener) {
        this.mOnMeiziClickedListener = onMeiziClickedListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RatioImageView mImageView;
        private TextView       mTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            mImageView = (RatioImageView) itemView.findViewById(R.id.iv_girl);
            mTextView = (TextView) itemView.findViewById(R.id.tv_url);
            mImageView.setRatio(1.0);
            mImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            GirlInfo.ResultsEntity data = mResults.get(getPosition());
            mOnMeiziClickedListener.onMeiziClicked(v, mImageView, data);
        }
    }
}