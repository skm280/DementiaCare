package com.example.sung.dementiacare.photo.PhotoDiary;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.sung.dementiacare.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gun0912.tedbottompicker.TedBottomPicker;

/**
 * Created by Sung on 2017. 9. 3..
 */

public class PhotoDiaryActivity extends AppCompatActivity {
    ArrayList<PhotoDiaryDo> diary;
    PhotoDiaryAdapter adapter;
    PhotoDiaryDao photoDiaryDao;

    @BindView(R.id.tool_bar_with_plus)
    Toolbar toolbar;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.photo_gridview)
    GridView gridView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_diary);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPhoto));
        }

        toolbar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPhoto));
        toolbar_title.setTextColor(Color.WHITE);
        toolbar_title.setText("사진");

        photoDiaryDao = new PhotoDiaryDao(getApplicationContext(), null);
        diary = photoDiaryDao.getResults();

        adapter = new PhotoDiaryAdapter(getApplicationContext(), diary);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), PhotoDiaryDetailActivity.class);
                intent.putExtra("diary",diary.get(i));
                startActivity(intent);
            }
        });

    }

    @OnClick(R.id.add_btn)
    public void showPicker() {
        TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(this)
                .setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener() {
                    @Override
                    public void onImageSelected(final Uri uri) {
                        final PhotoDiaryDialog dialog = new PhotoDiaryDialog(PhotoDiaryActivity.this);
                        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialogInterface) {
                                dialog.setImage(uri.toString());
                            }
                        });

                        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {
                                if (dialog.getPhotoDiary() != null) {
                                    adapter.notifyDataSetChanged();
                                    diary.add(dialog.getPhotoDiary());
                                }
                            }
                        });

                        dialog.show();
                    }
                })
                .create();
        tedBottomPicker.show(getSupportFragmentManager());
    }

}