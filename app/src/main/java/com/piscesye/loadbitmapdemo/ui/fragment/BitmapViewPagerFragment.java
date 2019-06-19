package com.piscesye.loadbitmapdemo.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.piscesye.loadbitmapdemo.R;
import com.piscesye.loadbitmapdemo.control.BigMemoryCacheHelper;
import com.piscesye.loadbitmapdemo.tool.LoadBitmapFromStorageTask;

public class BitmapViewPagerFragment extends Fragment {
    private ImageView showImage;
    public static final String TAG_FRAGMENT = "fragmentTag";

    public static BitmapViewPagerFragment newInstance(int position) {

        Bundle args = new Bundle();

        BitmapViewPagerFragment fragment = new BitmapViewPagerFragment();
        args.putInt(TAG_FRAGMENT, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_show_iamge, container, false);
        showImage = root.findViewById(R.id.image_view_pager_show);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int position = getArguments().getInt(TAG_FRAGMENT);
        Bitmap image = BigMemoryCacheHelper.getBitmapFromMemoryCache(String.valueOf(position));
        if (image != null) {
            showImage.setImageBitmap(image);
        }else{
            LoadBitmapFromStorageTask task = new LoadBitmapFromStorageTask(showImage, 1);
            task.execute(position);
        }
    }
}
