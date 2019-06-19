package com.piscesye.loadbitmapdemo.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.piscesye.loadbitmapdemo.R;
import com.piscesye.loadbitmapdemo.control.LoadListOrGridHelper;
import com.piscesye.loadbitmapdemo.ui.fragment.BitmapGridViewFragment;
import com.piscesye.loadbitmapdemo.ui.fragment.BitmapListViewFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ShowBitmapListActivity extends AppCompatActivity {
    public static final String TAG = "提交测试：";

    public static final int REQUEST_CODE = 1;
    public static final String TAG_LIST_OR_GRID = "tagListOrGird";
    public static final int TAG_LIST = 4;
    public static final int TAG_GRID = 5;

    private TextView showSwtichText;
    private Switch switchListOrGrid;

    private Fragment gridFragmnet;
    private Fragment listFragment;

    private boolean tagIsGrid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bitmap_list);
        setTitle("显示图片列表");

        //------------------ perssions request -------------------------
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this
                    , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE
                            , Manifest.permission.WRITE_EXTERNAL_STORAGE}
                    , REQUEST_CODE);
        }

        //---------------- conversion switch -----------------------------
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        tagIsGrid = LoadListOrGridHelper.loadListOrGrid(TAG_LIST_OR_GRID) == TAG_GRID ? true : false;
        showSwtichText = findViewById(R.id.text_show_switch_conversion);
        showSwtichText.setText(loadTextRes());
        switchListOrGrid = findViewById(R.id.switch_conversion_list_and_grid);
        switchListOrGrid.setChecked(tagIsGrid);
        switchListOrGrid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //isChecked => true->ListView;false->GridView
                if (isChecked) {
                    //Conversion to GridView
                    //从无到有,4-5
                    tagIsGrid = true;
                    LoadListOrGridHelper.setListOrGrid(TAG_LIST_OR_GRID, TAG_GRID);
                    showSwtichText.setText(loadTextRes());
                    //Log.d(TAG, String.valueOf(TAG_GRID));
                    //TODO 切换到GridView显示图片
                    if (gridFragmnet == null)
                        gridFragmnet = BitmapGridViewFragment.newInstance();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.layout_fragment_list_and_grid, gridFragmnet)
                            .addToBackStack(null).commit();
                } else {
                    //Conversion to ListView
                    //从有到无,5-4
                    tagIsGrid = false;
                    LoadListOrGridHelper.setListOrGrid(TAG_LIST_OR_GRID, TAG_LIST);
                    showSwtichText.setText(loadTextRes());
                    //Log.d(TAG, String.valueOf(TAG_LIST));
                    //TODO 切换到ListView显示图片
                    if (listFragment == null)
                        listFragment = BitmapListViewFragment.newInstance();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.layout_fragment_list_and_grid, listFragment)
                            .addToBackStack(null).commit();
                }
            }
        });


        //--------------- init load fragment ---------------------
        if (tagIsGrid){
            if (gridFragmnet == null)
                gridFragmnet = BitmapGridViewFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.layout_fragment_list_and_grid, gridFragmnet)
                    .addToBackStack(null).commit();
        }else{
            if (listFragment == null)
                listFragment = BitmapListViewFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.layout_fragment_list_and_grid, listFragment)
                    .addToBackStack(null).commit();
        }

        //------------ test ------------------
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home_return:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    String loadTextRes() {
        if (!tagIsGrid) {
            return getResources().getString(R.string.text_conversion_grid_view);
        } else {
            return getResources().getString(R.string.text_conversion_list_view);
        }
    }
}
