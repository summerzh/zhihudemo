package com.it.zhihudemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
            Picasso.with(mContext).load(mResults.get(position).getUrl())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(ViewHolder.mImageView);
            ViewHolder.mTextView.setText(mResults.get(position).getWho());
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

        private ImageView mImageView;
        private TextView  mTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            mImageView = (ImageView) itemView.findViewById(R.id.iv_girl);
            mTextView = (TextView) itemView.findViewById(R.id.tv_des);

        }
    }
}