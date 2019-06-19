package com.piscesye.loadbitmapdemo.ui.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.piscesye.loadbitmapdemo.R;
import com.piscesye.loadbitmapdemo.control.LoadListOrGridHelper;
import com.piscesye.loadbitmapdemo.control.MemoryCacheHepler;
import com.piscesye.loadbitmapdemo.control.loadBitmapHelper;
import com.piscesye.loadbitmapdemo.tool.LoadBitmapAndTextFromStorageTask;
import com.piscesye.loadbitmapdemo.ui.activity.ShowBitmapViewPagerActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BitmapListViewFragment extends Fragment {

    private Context activityContext;
    private ListView showBitmapListView;

    public static BitmapListViewFragment newInstance() {

        Bundle args = new Bundle();

        BitmapListViewFragment fragment = new BitmapListViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_view_for_bitmap, container, false);
        showBitmapListView = root.findViewById(R.id.list_show_bitmap);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activityContext = getActivity();
        ListImageAdapter adapter = new ListImageAdapter(loadBitmapHelper.getImageSubDirListLength());
        showBitmapListView.setAdapter(adapter);
        showBitmapListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(ShowBitmapViewPagerActivity.newIntent(activityContext, position));
            }
        });
    }

    class ListImageAdapter extends BaseAdapter {

        private int viewSize;

        ListImageAdapter(int size) {
            viewSize = size;
        }

        @Override
        public int getCount() {
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
            View root = LayoutInflater.from(activityContext).inflate(R.layout.item_list_for_bitmap, parent, false);
            ImageView imageView = root.findViewById(R.id.image_list_bitmap);
            TextView imageName = root.findViewById(R.id.text_list_bitmap);
            TextView imageMessage = root.findViewById(R.id.text_list_bitmap_message);
            Bitmap image = MemoryCacheHepler.getBitmapFromMemoryCache(String.valueOf(position));
            if (image != null) {
                File thatFile =  loadBitmapHelper.getPiscesYEFile().listFiles()[position];
                imageName.setText(thatFile.getName());
                String message = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date(thatFile.lastModified()));
                long fileSize = thatFile.length() / 1024;
                if (fileSize > 1024) {
                    message += " " + String.valueOf(fileSize / 1024) + "MB";
                } else {
                    message += " " + String.valueOf(fileSize) + "KB";
                }
                imageMessage.setText(message);
                imageView.setImageBitmap(image);
            }else{
                LoadBitmapAndTextFromStorageTask task = new LoadBitmapAndTextFromStorageTask(imageView, imageName, imageMessage);
                task.execute(position);
            }

            return root;
        }
    }
}
