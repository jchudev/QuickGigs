package app.quickgigs.com.quickgigs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

/**
 * Created by swhite on 10/26/16.
 */

public class BottomBarListener implements BottomNavigationView.OnNavigationItemSelectedListener {
    private Context mContext;

    public BottomBarListener(Context context) {
        this.mContext = context;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create:
                Intent createJobs = new Intent(mContext, MainActivity.class);
                mContext.startActivity(createJobs);
                ((AppCompatActivity)mContext).finish();
                break;
            case R.id.open:
                Intent openJobs = new Intent(mContext, OpenJobs.class);
                mContext.startActivity(openJobs);
                ((AppCompatActivity)mContext).finish();
                break;
            case R.id.claimed:
                Intent claimedJobs = new Intent(mContext, ClaimedJobs.class);
                mContext.startActivity(claimedJobs);
                ((AppCompatActivity)mContext).finish();
                break;
        }
//        updateToolbarText(item.getTitle());
        return true;
    }

    private void updateToolbarText(CharSequence text) {
        AppCompatActivity activity = (AppCompatActivity) mContext;
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(text);
        }
    }
}

