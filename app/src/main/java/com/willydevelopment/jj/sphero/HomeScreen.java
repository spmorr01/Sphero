package com.willydevelopment.jj.sphero;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.orbotix.ConvenienceRobot;
import com.orbotix.DualStackDiscoveryAgent;
import com.orbotix.async.CollisionDetectedAsyncData;
import com.orbotix.async.DeviceSensorAsyncMessage;
import com.orbotix.command.RollCommand;
import com.orbotix.common.DiscoveryException;
import com.orbotix.common.ResponseListener;
import com.orbotix.common.Robot;
import com.orbotix.common.RobotChangedStateListener;
import com.orbotix.common.internal.AsyncMessage;
import com.orbotix.common.internal.DeviceResponse;
import com.orbotix.common.sensor.DeviceSensorsData;
import com.orbotix.common.sensor.SensorFlag;
import com.orbotix.subsystem.SensorControl;

/**
 * Locator sample
 *
 * Keeps track of the robot's position by recording direction and speed.
 * This sample demonstrates how to use the Locator and Velocity sensors.
 *
 * For more explanation on driving, see the Button Drive sample
 *
 */
public class HomeScreen extends Activity implements RobotChangedStateListener, ResponseListener {

    private static final float ROBOT_VELOCITY = 0.4f;

    private ConvenienceRobot mRobot;
    public float positionAtY;
    public boolean reachedDistance;
    public float driveVariable;
    CollisionDetectedAsyncData message;

    TextView connectTextBox;
    TextView statusTextView;
    TextView positionYTextView;
    Button testDriveForwardButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        connectTextBox = (TextView)findViewById(R.id.connectTextView);
        statusTextView = (TextView)findViewById(R.id.statusTextView);
        positionYTextView = (TextView)findViewById(R.id.positionYTextView);
        testDriveForwardButton = (Button)findViewById(R.id.TestDriveForwardButton);
        testDriveForwardButton.setEnabled(false);


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

    /*@Override
    protected void onStop() {
        if( mRobot != null )
            mRobot.disconnect();
        super.onStop();
    }*/

    @Override
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DualStackDiscoveryAgent.getInstance().addRobotStateListener(null);
    }

    public void onTestDriveForwardButtonClicked (View v) {
        statusTextView.setText("Rolling...");
        mRobot.drive(0f, ROBOT_VELOCITY);

        /*mRobot.addResponseListener(new ResponseListener() {
            @Override
            public void handleResponse(DeviceResponse response, Robot robot) {
            }

            @Override
            public void handleStringResponse(String stringResponse, Robot robot) {
            }

            @Override
            public void handleAsyncMessage(AsyncMessage asyncMessage, Robot robot) {
                if (asyncMessage instanceof DeviceSensorAsyncMessage) {
                    float positionY = ((DeviceSensorAsyncMessage) asyncMessage).getAsyncData().get(0).getLocatorData().getPositionY();
                    positionYTextView.setText(positionY + "cm");

                    if (positionY == 300) {
                        mRobot.stop();
                        testDriveForwardButton.setEnabled(false);
                        statusTextView.setText("FINISHED!");
                    }
                }
            }
        }); */
    }


    public void cleanup() {
        mRobot.removeResponseListener(this);
        mRobot = null;
    }

    @Override
    public void handleResponse(DeviceResponse response, Robot robot) {
        // Do something with the response here
    }

    @Override
    public void handleStringResponse(String stringResponse, Robot robot) {
        // Handle string responses from the robot here
    }

    @Override
    public void handleAsyncMessage(AsyncMessage asyncMessage, Robot robot) {
        if (asyncMessage instanceof CollisionDetectedAsyncData) {
            //message = (CollisionDetectedAsyncData) asyncMessage;
                collisionResponseFunction();
            return;
        }

    }

    public void onAutoDriveButtonClicked (View view)
    {
        beginAutoDriveFunction();
    }

    public void beginAutoDriveFunction()
    {
        driveFunction();
    }

    public void driveFunction() //WHY THE FLUFF CANT WE READ THE COLLISION CORRECTLY?!
    {
        mRobot.drive(0f, ROBOT_VELOCITY);
        mRobot.enableCollisions(true);
        //collisionListenerFunction();


    }

    /*public void collisionListenerFunction()
    {
        mRobot.addResponseListener(new ResponseListener() {
            @Override
            public void handleResponse(DeviceResponse deviceResponse, Robot robot) {

            }

            @Override
            public void handleStringResponse(String s, Robot robot) {

            }

            @Override
            public void handleAsyncMessage(AsyncMessage asyncMessage, Robot robot) {
                if (asyncMessage instanceof CollisionDetectedAsyncData) {
                    collisionResponseFunction();
                }
                //driveFunction();
            }
        });
    }*/

    public void collisionResponseFunction()
    {
        mRobot.drive(180f, ROBOT_VELOCITY);
        return;
        //beginAutoDriveFunction();
        /*try {
            Thread.sleep(750);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mRobot.sendCommand(new RollCommand(0f, 0.0f, RollCommand.State.STOP));
        try {
            Thread.sleep(750);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mRobot.drive(270f, ROBOT_VELOCITY);
        try {
            Thread.sleep(750);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mRobot.sendCommand(new RollCommand(0f, 0.0f, RollCommand.State.STOP));
        message = null;*/
    }


    @Override
    public void handleRobotChangedState(Robot robot, RobotChangedStateNotificationType type) {
        switch (type) {
            case Online: {
                DualStackDiscoveryAgent.getInstance().stopDiscovery();
                connectTextBox.setText("Connected!");
                testDriveForwardButton.setEnabled(true);
                testDriveForwardButton.setBackgroundColor(Color.BLUE);
                //Sensor flags can be bitwise ORed together to enable multiple sensors
                long sensorFlag = SensorFlag.VELOCITY.longValue() | SensorFlag.LOCATOR.longValue();
                //Save the robot as a ConvenienceRobot for additional utility methods
                mRobot = new ConvenienceRobot(robot);
                mRobot.addResponseListener(this);
                mRobot.setZeroHeading();
                //Enable sensors based on earlier defined flags, and set the streaming rate.
                //This example streams data from the connected robot 10 times a second.
                mRobot.enableSensors(sensorFlag, SensorControl.StreamingRate.STREAMING_RATE50);
                //mRobot.addResponseListener( this );

                break;
            }
        }
    }
}
