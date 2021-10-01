package com.example.media;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.media.List.Item;
import com.example.media.Pager.AllSongsFragment;
import com.example.media.Pager.ViewPagerAdapter;
import com.example.media.Queue.SongsQueueRecyclerViewAdapter;
import com.example.media.RoomDB.AppDatabase;
import com.example.media.RoomDB.ItemDao;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;


public class MainActivity extends AppCompatActivity implements AllSongsFragment.PassData {

    /*PERMISSIONSP*/
        private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;

        private static final String[] REQUIRED_SDK_PERMISSIONS = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};


        protected void checkPermissions () {
            final List<String> missingPermissions = new ArrayList<String>();
            for (final String permission : REQUIRED_SDK_PERMISSIONS) {
                final int result = ContextCompat.checkSelfPermission(this, permission);
                if (result != PackageManager.PERMISSION_GRANTED) {
                    missingPermissions.add(permission);
                }
            }
            if (!missingPermissions.isEmpty()) {
                final String[] permissions = missingPermissions
                        .toArray(new String[missingPermissions.size()]);
                ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_ASK_PERMISSIONS);
            } else {
                final int[] grantResults = new int[REQUIRED_SDK_PERMISSIONS.length];
                Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED);
                onRequestPermissionsResult(REQUEST_CODE_ASK_PERMISSIONS, REQUIRED_SDK_PERMISSIONS,
                        grantResults);
            }
        }

        @Override
        public void onRequestPermissionsResult ( int requestCode, @NonNull String permissions[],
        @NonNull int[] grantResults){
            switch (requestCode) {
                case REQUEST_CODE_ASK_PERMISSIONS:
                    for (int index = permissions.length - 1; index >= 0; --index) {
                        if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "Required permission '" + permissions[index]
                                    + "' not granted, exiting", Toast.LENGTH_LONG).show();
                            finish();
                            return;
                        }
                    }
                    break;
            }
        }

    //,

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof AllSongsFragment) {
            AllSongsFragment headlinesFragment = (AllSongsFragment) fragment;
            headlinesFragment.onItemSelectedListener(this);
        }
    }

    ArrayList<Item> items;
    Directory AllSongs;



    ArrayList<Directory> albumsList;
    ArrayList<Directory> artistsList;
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void CreateLists(){
        Map<String, List<Item>> songByArtist
                = new HashMap<>();

        songByArtist =  items.stream().collect(Collectors.groupingBy(Item::getArtist));

        for (Map.Entry<String, List<Item>> entry : songByArtist.entrySet())
        {
            Log.i("sad", "Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        Map<String, List<Item>> songByAlbum
                = new HashMap<>();

        songByAlbum =  items.stream().collect(Collectors.groupingBy(Item::getAlbum));

        for (Map.Entry<String, List<Item>> entry : songByAlbum.entrySet())
        {
            Log.i("sad", "Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
    }
    ArrayList<Item>queueItems;
    SongsQueueRecyclerViewAdapter queueListAdapter;


    int playingPos=-1;

    Button updatedb;

    ImageView mPlay;
    ImageView mPrev;
    ImageView mNext;

    MediaPlayer mediaPlayer;
    ImageView play;
    ImageView queue;

    private void Play(){
        mediaPlayer.start();
        queueItems.get(playingPos).setPlay();
        play.setImageResource(R.drawable.pause_24);
        mPlay.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
    }
    private void Pause(){
        mediaPlayer.pause();
        queueItems.get(playingPos).setPause();
        play.setImageResource(R.drawable.play_24);
        mPlay.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
    }



    private void PlaySong(int position)
    {
        TextView tittle=(TextView)findViewById(R.id.p_title);
        TextView artist=(TextView)findViewById(R.id.p_artist);
        ImageView art=(ImageView)findViewById(R.id.imageView);
        ImageView sArt=(ImageView)findViewById(R.id.p_image);

        queueItems.get(position).setPlay();
        play.setImageResource(R.drawable.pause_24);
        mPlay.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);




        tittle.setText(queueItems.get(position).getName());
        artist.setText(queueItems.get(position).getArtist());
        art.setImageURI(queueItems.get(position).getAlbumArt());
        sArt.setImageURI(queueItems.get(position).getAlbumArt());

        if(playingPos!=-1){
            queueItems.get(playingPos).setPause();
        }

        playingPos=position;

        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(getApplicationContext(), queueItems.get(position).getUri());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
    }




    //***ON CREATE*****ON CREATE*****ON CREATE*****ON CREATE*****ON CREATE*****ON CREATE*****ON CREATE*****ON CREATE*****ON CREATE*****ON CREATE*****ON CREATE*****ON CREATE*****ON CREATE*****ON CREATE****
    ViewPager2 viewPager;
    private ViewPagerAdapter pagerAdapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        checkPermissions();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"db").allowMainThreadQueries().build();
        ItemDao dao =db.itemDao();

        Lists lists = null;
        
        if(dao.getSize()<=0)
        {
            lists=new Lists(MainActivity.this);
            dao.insertAll(lists.getSongs().getQueue());
        }
        else
        {
            Directory dir=new Directory();
            dir.setQueue(dao.getAll());
            lists=new Lists(dir);
        }

        viewPager=findViewById(R.id.pager);
        pagerAdapter=new ViewPagerAdapter(this,lists);

        viewPager.setAdapter(pagerAdapter);


        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );



        RecyclerView recyclerView=findViewById(R.id.queue_list);

        final View playBar=(View)findViewById(R.id.play_bar);
        final View bottomSheet=(View) findViewById(R.id.play_tab);
        final BottomSheetBehavior behavior = BottomSheetBehavior.from(findViewById(R.id.play_tab));

        bottomSheet.setVisibility(View.INVISIBLE);

        mPlay=(ImageView)findViewById(R.id.m_play);
        mPrev=(ImageView)findViewById(R.id.m_prev);
        mNext=(ImageView)findViewById(R.id.m_next);



        play=(ImageView)findViewById(R.id.p_play);
        queue=(ImageView)findViewById(R.id.queue);

        final SeekBar seekBar=(SeekBar)findViewById(R.id.seek_bar);

        /*Clicls*/{


            /*PLAYBUTTONS CLICK*/
            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!queueItems.get(playingPos).getPlaying()) {
                        Play();
                    } else {
                        Pause();
                    }
                }
            });
            mPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!queueItems.get(playingPos).getPlaying()) {
                        Play();
                    } else {
                        Pause();
                    }
                }
            });

            /*PLAYBAR CLICK*/
            playBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    } else {
                        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }

                }
            });

            /*PREV CLICK*/
            mPrev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (playingPos - 1 < 0) {
                        PlaySong(playingPos);
                    } else {
                        PlaySong(playingPos - 1);
                        queueListAdapter.highLightSet(playingPos);
                    }
                }
            });


            /*NEXT CLICK*/
            mNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (playingPos + 1 > queueItems.size() - 1) {
                        PlaySong(0);
                    } else {
                        PlaySong(playingPos + 1);
                        queueListAdapter.highLightSet(playingPos);
                    }
                }
            });


            /*QUEUE CLICK*/
            queue.setOnClickListener(new View.OnClickListener() {
                RecyclerView mQueue = (RecyclerView) findViewById(R.id.queue_list);

                @Override
                public void onClick(View v) {
                    if (mQueue.getVisibility() == View.VISIBLE) {
                        mQueue.setVisibility(View.INVISIBLE);
                    } else {
                        recyclerView.scrollToPosition(playingPos);
                        mQueue.setVisibility(View.VISIBLE);
                    }

                }
            });
        }

        /*SEEKBAR*/{
        /*GET SIZE FOR SEEKBAR*/
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                seekBar.setMax(mediaPlayer.getDuration());
            }
        });

        /*UPDATE SEEKBAR TIME*/{
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
             @Override
             public void run() {
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
             }
        }, 0, 1000);
        }
        /*SEEK BAR METHODS OVERRIDE*/
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        }

        /*BOTTOM SHEET*/
        behavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if(newState==BottomSheetBehavior.STATE_EXPANDED){
                    play.setVisibility(View.INVISIBLE);
                    queue.setVisibility(View.VISIBLE);
                }
                else if(newState==BottomSheetBehavior.STATE_COLLAPSED){
                    play.setVisibility(View.VISIBLE);
                    queue.setVisibility(View.INVISIBLE);

                    RecyclerView mQueue=(RecyclerView) findViewById(R.id.queue_list);
                    mQueue.setVisibility(View.INVISIBLE);
                }
            }



            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });

    }

    @Override
    public void OnDataReceived(Directory directory) {

        queueItems = new ArrayList<Item>(directory.getQueue());

        queueListAdapter =new SongsQueueRecyclerViewAdapter(queueItems,directory.getPlayPos());

        RecyclerView mQueue=(RecyclerView) findViewById(R.id.queue_list);

        mQueue.setLayoutManager(new LinearLayoutManager(this));
        mQueue.setAdapter(queueListAdapter);

        queueListAdapter.setOnItemClickListener(new SongsQueueRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                PlaySong(position);
            }
        });

        mQueue.setVisibility(View.INVISIBLE);

        final View bottomSheet=(View) findViewById(R.id.play_tab);
        bottomSheet.setVisibility(View.VISIBLE);

        PlaySong(directory.getPlayPos());

    }


}


