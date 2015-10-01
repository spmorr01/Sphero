package com.willydevelopment.jj.sphero;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.orbotix.ConvenienceRobot;
import com.orbotix.DualStackDiscoveryAgent;
import com.orbotix.Ollie;
import com.orbotix.Sphero;
import com.orbotix.classic.RobotClassic;
import com.orbotix.common.DiscoveryException;
import com.orbotix.common.Robot;
import com.orbotix.common.RobotChangedStateListener;
import com.orbotix.le.RobotLE;

public class HomeScreen extends AppCompatActivity implements RobotChangedStateListener {

    TextView connectTextBox;
    public static ConvenienceRobot mRobot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        connectTextBox = (TextView)findViewById(R.id.connectTextView);
        DualStackDiscoveryAgent.getInstance().addRobotStateListener( this );


    }

    public void startActivity()
    {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Intent ButtonActivities = new Intent(this, ButtonActivities.class);
        startActivity(ButtonActivities);
    }


    @Override
    protected void onStart() {
        super.onStart();
        // This line assumes that this object is a Context
        try {
            DualStackDiscoveryAgent.getInstance().startDiscovery(this);
        } catch( DiscoveryException e ) {
            //handle exception
        }
    }

    @Override
    public void handleRobotChangedState(Robot robot, RobotChangedStateListener.RobotChangedStateNotificationType type) {
        switch (type) {
            case Offline:
                break;
            case Connecting:
                break;
            case Connected:
                connectTextBox.setText("Connected!");
                break;
            case Online:
                DualStackDiscoveryAgent.getInstance().stopDiscovery();
                if (robot instanceof RobotClassic) {
                    mRobot = new Sphero(robot);
                    mRobot.setZeroHeading();

                }
                if (robot instanceof RobotLE) {
                    mRobot = new Ollie(robot);
                    mRobot.setZeroHeading();
                }
                startActivity();
                break;
            case Disconnected:
                break;
            case FailedConnect:
                break;
        }
    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
