package com.willydevelopment.jj.sphero;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.orbotix.ConvenienceRobot;
import com.orbotix.DualStackDiscoveryAgent;
import com.orbotix.async.DeviceSensorAsyncMessage;
import com.orbotix.common.DiscoveryException;
import com.orbotix.common.ResponseListener;
import com.orbotix.common.Robot;
import com.orbotix.common.RobotChangedStateListener;
import com.orbotix.common.internal.AsyncMessage;
import com.orbotix.common.internal.DeviceResponse;
import com.orbotix.common.sensor.SensorFlag;
import com.orbotix.subsystem.SensorControl;

/**
 * Locator sample
 *
 * Keeps track of the robot's position by recording direction and speed.
 * This sample demonstrates how to use the Locator and Velocity sensors.
 *
 * For more explaination on driving, see the Button Drive sample
 *
 */
public class HomeScreen extends Activity implements RobotChangedStateListener, ResponseListener {

    private static final float ROBOT_VELOCITY = 0.2f;

    private ConvenienceRobot mRobot;
    public float positionAtX;

    TextView connectTextBox;
    TextView positionXTextView;
    TextView positionYTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_home_screen );
        connectTextBox = (TextView)findViewById(R.id.connectTextView);
        positionXTextView = (TextView)findViewById(R.id.positionXTextView);
        positionYTextView = (TextView)findViewById(R.id.positionYTextView);

        /*
            Associate a listener for robot state changes with the DualStackDiscoveryAgent.
            DualStackDiscoveryAgent checks for both Bluetooth Classic and Bluetooth LE.
            DiscoveryAgentClassic checks only for Bluetooth Classic robots.
            DiscoveryAgentLE checks only for Bluetooth LE robots.
       */
        DualStackDiscoveryAgent.getInstance().addRobotStateListener(this);

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

    /*@Override
    protected void onStop() {
        //If the DiscoveryAgent is in discovery mode, stop it.
        if( DualStackDiscoveryAgent.getInstance().isDiscovering() ) {
            DualStackDiscoveryAgent.getInstance().stopDiscovery();
        }

        //If a robot is connected to the device, disconnect it
        if( mRobot != null ) {
            mRobot.disconnect();
            mRobot = null;
        }

        super.onStop();
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DualStackDiscoveryAgent.getInstance().addRobotStateListener(null);
    }

    public void onTestDriveForwardButtonClicked (View v) {
        do {
            mRobot.drive(0.0f, ROBOT_VELOCITY);
       } while (positionAtX <= 2);
        mRobot.stop();
        positionXTextView.setText("FINISHED!");
    }


    //Set the displayed location to user determined values
    /*private void configureLocator() {
        if( mRobot == null )
            return;

        int newX = 0;
        int newY = 0;
        int newYaw = 0;

        try {
            newX = Integer.parseInt( mEditTextNewX.getText().toString() );
        } catch( NumberFormatException e ) {}

        try {
            newY = Integer.parseInt( mEditTextNewY.getText().toString() );
        } catch( NumberFormatException e ) {}

        try {
            newYaw = Integer.parseInt( mEditTextNewYaw.getText().toString() );
        } catch( NumberFormatException e ) {}

        int flag = mCheckboxFlag.isChecked() ?
                ConfigureLocatorCommand.ROTATE_WITH_CALIBRATE_FLAG_ON
                : ConfigureLocatorCommand.ROTATE_WITH_CALIBRATE_FLAG_OFF;

        mRobot.sendCommand( new ConfigureLocatorCommand( flag, newX, newY, newYaw ) );
    }*/

    @Override
    public void handleResponse(DeviceResponse response, Robot robot) {
    }

    @Override
    public void handleStringResponse(String stringResponse, Robot robot) {
    }

    @Override
    public void handleAsyncMessage(AsyncMessage asyncMessage, Robot robot) {
        if( asyncMessage instanceof DeviceSensorAsyncMessage ) {
            float positionX = ( (DeviceSensorAsyncMessage) asyncMessage ).getAsyncData().get( 0 ).getLocatorData().getPositionX();
            positionAtX += positionX;
            float positionY = ( (DeviceSensorAsyncMessage) asyncMessage ).getAsyncData().get( 0 ).getLocatorData().getPositionY();
            float velocityX = ( (DeviceSensorAsyncMessage) asyncMessage ).getAsyncData().get( 0 ).getLocatorData().getVelocity().x;
            float velocityY = ( (DeviceSensorAsyncMessage) asyncMessage ).getAsyncData().get( 0 ).getLocatorData().getVelocity().y;

            positionXTextView.setText(positionX + "cm");
            //mTextViewLocatorY.setText( positionY + "cm" );
            //mTextViewLocatorVX.setText( velocityX + "cm/s" );
            //mTextViewLocatorVY.setText( velocityY + "cm/s" );
        }
    }

    @Override
    public void handleRobotChangedState(Robot robot, RobotChangedStateNotificationType type) {
        switch (type) {
            case Online: {
                DualStackDiscoveryAgent.getInstance().stopDiscovery();

                connectTextBox.setText("Connected!");
                //Sensor flags can be bitwise ORed together to enable multiple sensors
                long sensorFlag = SensorFlag.VELOCITY.longValue() | SensorFlag.LOCATOR.longValue();

                //Save the robot as a ConvenienceRobot for additional utility methods
                mRobot = new ConvenienceRobot(robot);

                //Enable sensors based on earlier defined flags, and set the streaming rate.
                //This example streams data from the connected robot 10 times a second.
                mRobot.enableSensors( sensorFlag, SensorControl.StreamingRate.STREAMING_RATE100 );
                mRobot.addResponseListener( this );

                break;
            }
        }
    }
}
