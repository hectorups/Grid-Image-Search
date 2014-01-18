package com.codepath.example.gridimagesearch;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by hectormonserrate on 16/01/14.
 */
public class ImageResultArrayAdapter extends ArrayAdapter<ImageResult> {

    public ImageResultArrayAdapter(Context context, List<ImageResult> images) {
        super(context, R.layout.item_image_result, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageResult imageInfo = this.getItem(position);


        View itemView;
        if(convertView == null){
            LayoutInflater inflator = LayoutInflater.from(getContext());
            itemView = inflator.inflate(R.layout.item_image_result, parent,  false);
        } else {
            itemView = convertView;
        }

        ImageView ivImage = (ImageView) itemView.findViewById(R.id.ivThumbnail);
        TextView tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        tvTitle.setMaxLines(2);
        tvTitle.setEllipsize(TextUtils.TruncateAt.END);
        tvTitle.setText(imageInfo.getTitle());



        Picasso.with(getContext())
                        .load(Uri.parse(imageInfo.getThumbUrl()))
                        .noFade()
                        .resize(100, 100)
                        .centerCrop()
                        .into(ivImage);

        return itemView;
    }
}
