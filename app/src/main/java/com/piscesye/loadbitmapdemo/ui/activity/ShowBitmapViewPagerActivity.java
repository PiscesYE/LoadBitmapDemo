package com.piscesye.loadbitmapdemo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.piscesye.loadbitmapdemo.R;
import com.piscesye.loadbitmapdemo.control.loadBitmapHelper;
import com.piscesye.loadbitmapdemo.ui.fragment.BitmapViewPagerFragment;

public class ShowBitmapViewPagerActivity extends AppCompatActivity {
    public static final String POSITION = "positon";

    private ViewPager showImages;
    private ProgressBar showImagesProgress;

    public static Intent newIntent(Context context, int position){
        Intent intent = new Intent(context, ShowBitmapViewPagerActivity.class);
        intent.putExtra(POSITION, position);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bitmap_view_pager);
        setTitle("显示图片");

        int imagesNum = loadBitmapHelper.getImageSubDirListLength();
        int imagePostion = getIntent().getIntExtra(POSITION, 1);

        showImagesProgress = findViewById(R.id.progress_view_pager);
        showImagesProgress.setMax(imagesNum);
        showImagesProgress.setProgress(imagePostion);

        showImages = findViewById(R.id.view_pager_show_images);
        ImageFragmentAdapter adapter = new ImageFragmentAdapter(
                getSupportFragmentManager(), imagesNum);
        showImages.setAdapter(adapter);
        showImages.setCurrentItem(imagePostion);
        showImages.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                showImagesProgress.setProgress(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
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
        switch (item.getItemId()){
            case R.id.menu_home_return:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    class ImageFragmentAdapter extends FragmentPagerAdapter{
        private int size;

        public ImageFragmentAdapter(FragmentManager fm, int viewSize) {
            super(fm);
            size = viewSize;
        }

        @Override
        public Fragment getItem(int i) {
            return BitmapViewPagerFragment.newInstance(i);
        }

        @Override
        public int getCount() {
            return size;
        }
    }
}
