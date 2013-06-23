package com.example.hata.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.example.hata.R;
import com.example.hata.data.Account;
import com.example.hata.data.Constants;
import com.perm.kate.api.Api;
import com.perm.kate.api.KException;
import com.perm.kate.api.User;
import org.json.JSONException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

public class ProfileActivity extends SherlockActivity {
    
    private final int REQUEST_LOGIN=1;

    public User profile = new User();

    private ArrayList<User> profiles = new ArrayList<User>();

    private Collection<Long> uids = new ArrayList<Long>();

    private String FIELDS = "uid,first_name,last_name,nickname,sex,bdate,city,photo_medium_rec, contacts";
    
    ImageButton authorizeButton;
    ImageButton logoutButton;
    TextView greetings;
    private ImageView photo;
    Account account=new Account();
    Api api;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        /************ActionBar*******************/

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);

        setupUI();
        
        //Восстановление сохранённой сессии
        account.restore(this);
        
        //Если сессия есть создаём API для обращения к серверу
        if(account.access_token!=null){
            api=new Api(account.access_token, Constants.API_ID);
            getProfile();
        }
        
        showButtons();
    }

    private void setupUI() {
        authorizeButton=(ImageButton)findViewById(R.id.authorize);
        logoutButton=(ImageButton)findViewById(R.id.logout);
        greetings = (TextView) findViewById(R.id.greeting);
        authorizeButton.setOnClickListener(authorizeClick);
        logoutButton.setOnClickListener(logoutClick);
        photo = (ImageView) findViewById(R.id.photo);
    }
    
    private OnClickListener authorizeClick=new OnClickListener(){
        @Override
        public void onClick(View v) {
            startLoginActivity();
        }
    };
    
    private OnClickListener logoutClick=new OnClickListener(){
        @Override
        public void onClick(View v) {
            logOut();
        }
    };

    private void startLoginActivity() {
        Intent intent = new Intent();
        intent.setClass(this, LoginActivity.class);
        startActivityForResult(intent, REQUEST_LOGIN);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_LOGIN) {
            if (resultCode == RESULT_OK) {

                //авторизовались успешно 
                account.access_token=data.getStringExtra("token");
                account.user_id=data.getLongExtra("user_id", 0);
                account.save(ProfileActivity.this);

                api=new Api(account.access_token, Constants.API_ID);

                showButtons();
                getProfile();
            }
        }
    }

    private void logOut() {
        api=null;
        account.access_token=null;
        account.user_id=0;
        account.save(ProfileActivity.this);
        showButtons();
    }
    
    void showButtons(){
        if(api!=null){
            authorizeButton.setVisibility(View.GONE);
            logoutButton.setVisibility(View.VISIBLE);
            greetings.setVisibility(View.VISIBLE);
            photo.setVisibility(View.VISIBLE);
        }else{
            authorizeButton.setVisibility(View.VISIBLE);
            logoutButton.setVisibility(View.GONE);
            greetings.setVisibility(View.GONE);
            photo.setVisibility(View.GONE);

        }
    }

    void getProfile(){
        uids.add(account.user_id);
        try {
            profiles = api.getProfiles(uids,null,FIELDS,"nom",null,null);
            profile = profiles.get(0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (KException e) {
            e.printStackTrace();
        }

        photo.setImageBitmap(getBitmapFromURL(profile.photo_medium_rec));

        String Welcome = "Здравствуйте, \n" + profile.first_name + " " + profile.last_name +"!";
        greetings.setText(Welcome);

    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }
}