package com.example.abc.wittyfeed;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class MainFragment extends Fragment {

    View view;
    RecyclerView mRecyclerView;
    RecyclerView mRecyclerView2;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.LayoutManager mLayoutManager2;
    RecyclerView.Adapter mAdapter;
    VideoAdapter vAdapter;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_frag, container, false);


        ImageView main_img = view.findViewById(R.id.main_img);
        TextView main_content = view.findViewById(R.id.main_content);
        TextView vdo_content = view.findViewById(R.id.vdo_main_content);
        final StretchVideoView main_view = view.findViewById(R.id.mainVdo);
        final ImageView play_btn= view.findViewById(R.id.play_btn);
        final ImageView play_bg= view.findViewById(R.id.play);

        // Calling the RecyclerView
        mRecyclerView = (RecyclerView) view.findViewById(R.id.imageRV);
        mRecyclerView2 = (RecyclerView) view.findViewById(R.id.vdoRV);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView2.setHasFixedSize(true);

        // The number of Columns
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView2.setLayoutManager(mLayoutManager2);


        try {
            JSONArray obj = new JSONArray(loadJSONFromAsset());
            ArrayList<String> imageurlList = new ArrayList<String>();
            ArrayList<String> image_contentList = new ArrayList<String>();
            ArrayList<Video> vdo_urlList = new ArrayList<>();
            ArrayList<String> vdo_contentList = new ArrayList<String>();


            for (int i = 0; i < obj.length(); i++) {
                JSONObject jo_inside = obj.getJSONObject(i);
                Log.d("Details-->", obj.getJSONObject(i).toString());

                if (i > 0) {
                    imageurlList.add(obj.getJSONObject(i).getString("image_name"));
                    image_contentList.add(obj.getJSONObject(i).getString("image_content"));
                    Video video = new Video(obj.getJSONObject(i).getString("video"),
                            obj.getJSONObject(i).getString("video_content"));
                    vdo_urlList.add(video);
                }
            }

            mAdapter = new ImageAdapter(getActivity(), image_contentList, imageurlList);
            mRecyclerView.setAdapter(mAdapter);
            vAdapter = new VideoAdapter(getActivity(), vdo_urlList);
            mRecyclerView2.setAdapter(mAdapter);
            System.out.println("imageUri ::::: ");
            Picasso.with(getActivity()).load(obj.getJSONObject(0).getString("image_name")).into(main_img);
            main_content.setText(obj.getJSONObject(0).getString("image_content"));
            vdo_content.setText(obj.getJSONObject(0).getString("video_content"));
            String uriPath = obj.getJSONObject(0).getString("video"); //update server url


            Uri uri = Uri.parse(uriPath);
            main_view.setVideoURI(uri);
            main_view.seekTo(100);
            main_view.pause();
        } catch (JSONException e) {
            e.printStackTrace();
        }
play_btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        play_bg.setVisibility(View.GONE);
        play_btn.setVisibility(View.GONE);
        main_view.start();
    }
});

        main_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                play_bg.setVisibility(View.VISIBLE);
                play_btn.setVisibility(View.VISIBLE);
                main_view.pause();
                return true;
            }
        });
        main_view.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

                play_bg.setVisibility(View.VISIBLE);
                play_btn.setVisibility(View.VISIBLE);
                main_view.pause();
            }
        });

        return view;
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("details.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
