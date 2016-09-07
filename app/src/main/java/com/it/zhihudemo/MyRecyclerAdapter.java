package com.it.zhihudemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * created by gyt on 2016/8/25
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {


    private final List<GirlInfo.ResultsEntity> mResults;
    private final Context mContext;

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
    public void onBindViewHolder(ViewHolder ViewHolder, int position) {

        if (mResults != null) {
            Glide.with(mContext)
                    .load(mResults.get(position).getUrl())
                    .placeholder(R.color.white)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.mipmap.ic_launcher)
                    .into(ViewHolder.mImageView);
        }
    }

    @Override
    public int getItemCount() {
        if (mResults == null) {
            return 0;
        }
        return mResults.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private RatioImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);

            mImageView = (RatioImageView) itemView.findViewById(R.id.iv_girl);
            mImageView.setOriginalSize(50, 50);
        }
    }
}