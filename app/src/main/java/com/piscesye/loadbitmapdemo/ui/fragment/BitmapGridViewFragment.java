package com.piscesye.loadbitmapdemo.ui.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.piscesye.loadbitmapdemo.R;
import com.piscesye.loadbitmapdemo.control.MemoryCacheHepler;
import com.piscesye.loadbitmapdemo.control.loadBitmapHelper;
import com.piscesye.loadbitmapdemo.tool.LoadBitmapFromStorageTask;
import com.piscesye.loadbitmapdemo.ui.activity.ShowBitmapViewPagerActivity;

public class BitmapGridViewFragment extends Fragment {
    public static final String TAG = "Grid字段测试：";

    public static final String TAG_GRID_FRAGMENT = "com.piscesye.loadbitmapdemo.ui.fragment.gridFragment";

    private GridView showBitmapGridView;

    private Context activityContext;

    public static BitmapGridViewFragment newInstance() {

        Bundle args = new Bundle();

        BitmapGridViewFragment fragment = new BitmapGridViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_grid_view_for_bitmap, container, false);
        showBitmapGridView = root.findViewById(R.id.grid_show_bitmap);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activityContext = getActivity();
        //TODO 实现图片数据的加载和布局

        GridImageAdapter adapter = new GridImageAdapter(loadBitmapHelper.getImageSubDirListLength());
        showBitmapGridView.setAdapter(adapter);
        showBitmapGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.d(TAG, "测试点击的Positon：" + position);
                startActivity(ShowBitmapViewPagerActivity.newIntent(activityContext, position));
            }
        });
    }

    class GridImageAdapter extends BaseAdapter {

        private int viewSize;

        GridImageAdapter(int size) {
            viewSize = size;
        }

        @Override
        public int getCount() {
            //Log.d(TAG, "Grid获取到的图片数量是" + viewSize);
            return viewSize;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(activityContext);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(250, 500));
            } else {
                imageView = (ImageView) convertView;
            }
            Bitmap image = MemoryCacheHepler.getBitmapFromMemoryCache(String.valueOf(position));
            if (image != null) {
                imageView.setImageBitmap(image);
            } else {
                LoadBitmapFromStorageTask task = new LoadBitmapFromStorageTask(imageView);
                task.execute(position);
            }
            return imageView;
        }
    }
}
