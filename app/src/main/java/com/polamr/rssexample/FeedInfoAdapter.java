package com.polamr.rssexample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.polamr.rssexample.imageload.ImageLoader;
import com.polamr.rssexample.utils.RssItem;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Rajareddy on 28/11/15.
 */
public class FeedInfoAdapter extends RecyclerView.Adapter<FeedInfoAdapter.ViewHolder> {

    ImageLoader imageLoader;
    private ArrayList<RssItem> dataList;
    private static OnItemClickListener mOnItemClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtTitle;
        public ImageView imgFeed;

        public ViewHolder(View v) {
            super(v);
            txtTitle = (TextView) v.findViewById(R.id.feedInfoTitle);
            imgFeed = (ImageView) v.findViewById(R.id.feedInfoIcon);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mOnItemClickListener != null)
                mOnItemClickListener.onItemClick(v, getLayoutPosition());
        }
    }

    public FeedInfoAdapter(Context mContext, ArrayList<RssItem> list) {
        //imageLoader = new ImageLoader(mContext);
        this.dataList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_info_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RssItem item = dataList.get(position);
        Global.showLog("getImageUrl  : " + item.getImageUrl());
        holder.txtTitle.setText(item.getTitle());
        //imageLoader.DisplayImage(item.getImageUrl(), holder.imgFeed);

        GetImage get_iamge = new GetImage(item.getImageUrl(), holder.imgFeed);
        get_iamge.execute();
            //holder.imgFeed.setImageResource(R.mipmap.business_news);
            //holder.imgFeed.setImageBitmap(image);

    }

    private class GetImage extends AsyncTask<Void, Bitmap, Bitmap> {
        ImageView iv;
        String image;
        public GetImage(String url, ImageView mIv) {
            image = url;
            iv = mIv;
        }
        @Override
        protected Bitmap doInBackground(Void... params) {
            Bitmap bitmap = null;
            // TODO Auto-generated method stub

            Bitmap img = null;

            try {
                URL url = new URL(image);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            Global.showLog("bitmap : "+result);
            if(result != null)
                this.iv.setImageBitmap(result);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }
    public static void setOnItemClickListener(OnItemClickListener clickListener) {
        mOnItemClickListener = clickListener;
    }
}
