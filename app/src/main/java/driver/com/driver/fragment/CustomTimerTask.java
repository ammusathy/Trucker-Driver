package driver.com.driver.fragment;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;

import com.google.android.gms.maps.model.Marker;

import java.util.TimerTask;

/**
 * Created by androidusr1 on 16/7/16.
 */
public class CustomTimerTask extends TimerTask {
    private Context context;
    private Handler mHandler = new Handler();
    Marker marker;

    // Write Custom Constructor to pass Context
    public CustomTimerTask(Context con) {
        this.context = con;
    }

    @Override
    public void run() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        final Handler handler = new Handler();
                        final long start = SystemClock.uptimeMillis();
                        final long duration = 3000;

                        final Interpolator interpolator = new BounceInterpolator();

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                long elapsed = SystemClock.uptimeMillis() - start;
                                float t = Math.max(
                                        1 - interpolator.getInterpolation((float) elapsed
                                                / duration), 0);
                                marker.setAnchor(0.5f, 0.1f + 1 * t);

                                if (t > 0.0) {
                                    // Post again 16ms later.
                                    handler.postDelayed(this, 16);
                                }
                            }
                        });
                    }
                });
            }
        }).start();

    }
}

