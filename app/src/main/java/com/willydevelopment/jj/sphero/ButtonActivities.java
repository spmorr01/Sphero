package com.willydevelopment.jj.sphero;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.orbotix.async.DeviceSensorAsyncMessage;
import com.orbotix.command.RGBLEDOutputCommand;
import com.orbotix.common.ResponseListener;
import com.orbotix.common.Robot;
import com.orbotix.common.internal.AsyncMessage;
import com.orbotix.common.internal.DeviceResponse;

public class ButtonActivities extends AppCompatActivity implements ResponseListener {

    public int collision;
    TextView positionXTextView;
    TextView positionYTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_activites);
        positionXTextView = (TextView)findViewById(R.id.positionXTextView);
        positionYTextView = (TextView)findViewById(R.id.positionYTextView);

    }

    public void onTestDriveForwardButtonClicked(View view)
    {
        HomeScreen.mRobot.sendCommand( new RGBLEDOutputCommand( 0.5f, 0.5f, 0.5f ) );
        HomeScreen.mRobot.drive(0f, .3f);
    }

    /*public void onTestDriveBackwardsButtonClicked(View view)
    {
        HomeScreen.mRobot.setZeroHeading();
        HomeScreen.mRobot.sendCommand( new RGBLEDOutputCommand( 0.5f, 0.5f, 0.5f ) );
        HomeScreen.mRobot.sendCommand(new RollCommand(180f, .5f, RollCommand.State.GO));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HomeScreen.mRobot.sendCommand(new RollCommand(0f, 0.0f, RollCommand.State.STOP));
    }

    public void onSquareDriveButtonClicked(View view)
    {
        HomeScreen.mRobot.setZeroHeading();
        HomeScreen.mRobot.sendCommand( new RGBLEDOutputCommand( 0.5f, 0.5f, 0.5f ) );
        HomeScreen.mRobot.sendCommand(new RollCommand(0f, .5f, RollCommand.State.GO));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HomeScreen.mRobot.sendCommand(new RollCommand(0f, 0.0f, RollCommand.State.STOP));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //------------------------------------------------------------------------------
        HomeScreen.mRobot.sendCommand(new RollCommand(90f, .5f, RollCommand.State.GO));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HomeScreen.mRobot.sendCommand(new RollCommand(90f, 0.0f, RollCommand.State.STOP));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //------------------------------------------------------------------------------
        HomeScreen.mRobot.sendCommand(new RollCommand(180f, .5f, RollCommand.State.GO));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HomeScreen.mRobot.sendCommand(new RollCommand(180f, 0.0f, RollCommand.State.STOP));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //------------------------------------------------------------------------------
        HomeScreen.mRobot.sendCommand(new RollCommand(270f, .5f, RollCommand.State.GO));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HomeScreen.mRobot.sendCommand(new RollCommand(270f, 0.0f, RollCommand.State.STOP));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HomeScreen.mRobot.sendCommand(new RollCommand(0f, 0.0f, RollCommand.State.STOP));
    }

    public void onAutoDriveButtonClicked (View view)
    {
        HomeScreen.mRobot.enableCollisions(true);

        collision = 0;
        HomeScreen.mRobot.setZeroHeading();
        HomeScreen.mRobot.drive(0f, 0.51f);


        HomeScreen.mRobot.addResponseListener(new ResponseListener() {
            @Override
            public void handleResponse(DeviceResponse deviceResponse, Robot robot) {

            }

            @Override
            public void handleStringResponse(String s, Robot robot) {

            }

            @Override
            public void handleAsyncMessage(AsyncMessage asyncMessage, Robot robot) {
                if (asyncMessage instanceof CollisionDetectedAsyncData) {
                    //Collision occurred.
                    collision = 1;
                    do {
                        HomeScreen.mRobot.drive(0f, 0.0f);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        HomeScreen.mRobot.drive(180f, 1f);
                    } while (collision == 1);
                }
            }
        });
    }

    /*@Override
    protected void onStop() {
        if( HomeScreen.mRobot != null )
            HomeScreen.mRobot.disconnect();
        super.onStop();
    }*/

    @Override
    public void handleResponse(DeviceResponse response, Robot robot) {
    }

    @Override
    public void handleStringResponse(String stringResponse, Robot robot) {
    }

    @Override
    public void handleAsyncMessage(AsyncMessage asyncMessage, Robot robot) {
        if (asyncMessage instanceof DeviceSensorAsyncMessage) {
            float positionX = ((DeviceSensorAsyncMessage) asyncMessage).getAsyncData().get(0).getLocatorData().getPositionX();
            float positionY = ((DeviceSensorAsyncMessage) asyncMessage).getAsyncData().get(0).getLocatorData().getPositionY();

            positionXTextView.setText(positionX + "cm");
            positionYTextView.setText(positionY + "cm");

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_button_activites, menu);
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
