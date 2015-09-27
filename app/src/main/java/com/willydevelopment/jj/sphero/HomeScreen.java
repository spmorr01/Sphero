package com.willydevelopment.jj.sphero;

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
import com.orbotix.command.RGBLEDOutputCommand;
import com.orbotix.command.RollCommand;
import com.orbotix.common.DiscoveryException;
import com.orbotix.common.Robot;
import com.orbotix.common.RobotChangedStateListener;
import com.orbotix.le.RobotLE;

public class HomeScreen extends AppCompatActivity {

    TextView connectTextBox;
    private ConvenienceRobot mRobot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        connectTextBox = (TextView)findViewById(R.id.connectTextView);

        DualStackDiscoveryAgent.getInstance().addRobotStateListener(new RobotChangedStateListener() {
            @Override
            public void handleRobotChangedState(Robot robot, RobotChangedStateNotificationType type) {
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
                            mRobot.sendCommand( new RGBLEDOutputCommand( 0.5f, 0.5f, 0.5f ) );
                            mRobot.sendCommand( new RollCommand( 90f, .5f, RollCommand.State.GO ) );
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            mRobot.sendCommand(new RollCommand(0f, 0.0f, RollCommand.State.STOP));
                        }
                        if (robot instanceof RobotLE) {
                            mRobot = new Ollie(robot);
                        }

                        break;
                    case Disconnected:
                        break;
                    case FailedConnect:
                        break;
                }
            }
        });
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
    protected void onStop() {
        if( mRobot != null )
            mRobot.disconnect();
        super.onStop();
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
