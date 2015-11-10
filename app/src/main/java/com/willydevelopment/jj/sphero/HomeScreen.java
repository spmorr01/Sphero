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
import com.orbotix.command.ConfigureCollisionDetectionCommand;
import com.orbotix.command.RollCommand;
import com.orbotix.common.DiscoveryException;
import com.orbotix.common.ResponseListener;
import com.orbotix.common.Robot;
import com.orbotix.common.RobotChangedStateListener;
import com.orbotix.common.internal.AsyncMessage;
import com.orbotix.common.internal.DeviceResponse;
import com.orbotix.common.internal.RobotVersion;
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
public class HomeScreen extends Activity implements RobotChangedStateListener{

    private static final float ROBOT_VELOCITY = 0.4f;
    private static final int STARTUP_ACTIVITY_RESULTS = 0;

    private ConvenienceRobot mRobot;
    public boolean robotShouldRespond;
    public boolean collisionOccurred;
    //public float positionY;
    public boolean reachedDistance;
    public float driveVariable;


    TextView connectTextBox;
    TextView statusTextView;
    TextView positionYTextView;
    TextView positionXTextView;
    Button course1Button;
    Button course2Button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        connectTextBox = (TextView)findViewById(R.id.connectTextView);
        statusTextView = (TextView)findViewById(R.id.statusTextView);
        positionYTextView = (TextView)findViewById(R.id.positionYTextView);
        positionXTextView = (TextView)findViewById(R.id.positionXTextView);
        course1Button = (Button)findViewById(R.id.course1Button);
        course1Button.setEnabled(false);
        course2Button = (Button)findViewById(R.id.course2Button);
        course2Button.setEnabled(false);


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
        //If the DiscoveryAgent is in discovery mode, stop it.
        if (DualStackDiscoveryAgent.getInstance().isDiscovering()) {
            DualStackDiscoveryAgent.getInstance().stopDiscovery();
        }

        //If a robot is connected to the device, disconnect it
        if (mRobot != null) {
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

    public void onCourse1ButtonClicked (View v) {
        course1Function();



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
                        reachedDistance = true;

                    }
                    /*do {
                        mRobot.drive(0f, ROBOT_VELOCITY);
                    } while (positionY <= 1000);
                    mRobot.stop();
                    statusTextView.setText("FINISHED!");
                }
            }
        });*/
    }


    /*public void cleanup() {
        mRobot.removeResponseListener(this);
        mRobot = null;
    }*/




    /*public void onAutoDriveButtonClicked (View view) {
        driveFunction();
        mRobot.enableCollisions( true );
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
                    collisionFunction();
                }
            }
        });
    }*/

    public void onCourse2ButtonClicked(View v) {
        course2function();
    }

    public void driveFunction() //WHY THE FLUFF CANT WE READ THE COLLISION CORRECTLY?!
    {
        mRobot.drive(0f, ROBOT_VELOCITY);
        mRobot.enableCollisions(true);
        //collisionListenerFunction();
    }

    public void course1Function() {
        mRobot.drive(90f, ROBOT_VELOCITY);
        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.stop();
        try {
            Thread.sleep(1500);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.drive(0f, ROBOT_VELOCITY);
        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.stop();
        try {
            Thread.sleep(1500);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.drive(270f, ROBOT_VELOCITY);
        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.stop();
        try {
            Thread.sleep(1500);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.drive(180f, ROBOT_VELOCITY);
        try {
            Thread.sleep(1150);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.stop();
        try {
            Thread.sleep(1500);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.drive(270f, ROBOT_VELOCITY);
        try {
            Thread.sleep(750);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.stop();
        try {
            Thread.sleep(1500);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.drive(0f, 0f);
        //course2function();
    }

    public void course2function(){
        mRobot.drive(45f, ROBOT_VELOCITY);
        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.stop();
        try {
            Thread.sleep(750);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.drive(315f, ROBOT_VELOCITY);
        try {
            Thread.sleep(1625);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.stop();
        try {
            Thread.sleep(750);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.drive(45f, ROBOT_VELOCITY);
        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.stop();
        try {
            Thread.sleep(750);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.drive(135f, ROBOT_VELOCITY);
        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.stop();
        try {
            Thread.sleep(750);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.drive(225f, 0.65f);
        try {
            Thread.sleep(2000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.stop();
        try {
            Thread.sleep(750);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.drive(0f, 0.65f);
        try {
            Thread.sleep(1625);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.stop();
        try {
            Thread.sleep(750);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.drive(135f, 0.65f);
        try {
            Thread.sleep(2000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.stop();
        try {
            Thread.sleep(750);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.drive(0f, 0.065f);
        try {
            Thread.sleep(1625);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.stop();
        try {
            Thread.sleep(750);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.drive(225f, 0.65f);
        try {
            Thread.sleep(2000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.stop();
        try {
            Thread.sleep(750);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.drive(90f, ROBOT_VELOCITY);
        try {
            Thread.sleep(1250);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.stop();
        try {
            Thread.sleep(750);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.drive(0f, 0f);
        //course2function();
    }

    @Override
    public void handleRobotChangedState(Robot robot, RobotChangedStateNotificationType type) {
        switch (type) {
            case Online: {
                DualStackDiscoveryAgent.getInstance().stopDiscovery();
                robotShouldRespond = false;
                connectTextBox.setText("Connected!");
                course1Button.setEnabled(true);
                course1Button.setBackgroundColor(Color.BLUE);
                course2Button.setEnabled(true);
                course2Button.setBackgroundColor(Color.RED);
                long sensorFlag = SensorFlag.VELOCITY.longValue() | SensorFlag.LOCATOR.longValue();
                mRobot = new ConvenienceRobot(robot);
                mRobot.setZeroHeading();
                mRobot.enableSensors(sensorFlag, SensorControl.StreamingRate.STREAMING_RATE100);
                break;
            }
        }
    }
}
