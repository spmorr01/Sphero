package com.willydevelopment.jj.sphero;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private int loopCount;
    private int distance;


    TextView connectTextBox;
    TextView statusTextView;
    TextView positionYTextView;
    TextView positionXTextView;
    EditText distanceEditText;
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
        distanceEditText = (EditText)findViewById(R.id.distanceEditText);
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
        course2Function();
    }

    public void onCourse3ButtonClicked(View v){
        course3Function();
    }

    public void onCourse4ButtonClicked(View v){
        course4Function();
    }

    public void onCourse5ButtonClicked(View v){
        course5Function();
    }

    public void course1Function(){
        mRobot.drive(0f, ROBOT_VELOCITY);
        try {
            Thread.sleep(1500);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.stop();
        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.drive(180f, ROBOT_VELOCITY);
        try {
            Thread.sleep(1500);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.stop();
        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.drive(0f, 0f);
        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
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
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        course2Function();
    }

    public void course2Function(){
        mRobot.drive(0f, ROBOT_VELOCITY);
        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.stop();
        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
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
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.drive(180f, ROBOT_VELOCITY);
        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.stop();
        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.drive(90f, ROBOT_VELOCITY);
        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.stop();
        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.drive(0f, 0f);
        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.drive(270f, 1.0f);
        try {
            Thread.sleep(2000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.stop();
        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        course3Function();
    }

    public void course3Function() {
        loopCount = 1;
        mRobot.drive(90f, ROBOT_VELOCITY);
        try {
            Thread.sleep(750);                 //1000 milliseconds is one second.
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        do {
            mRobot.drive(90f, ROBOT_VELOCITY);
            try {
                Thread.sleep(250);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            mRobot.drive(80f, ROBOT_VELOCITY);
            try {
                Thread.sleep(250);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            mRobot.drive(70f, ROBOT_VELOCITY);
            try {
                Thread.sleep(250);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            mRobot.drive(60f, ROBOT_VELOCITY);
            try {
                Thread.sleep(250);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            mRobot.drive(50f, ROBOT_VELOCITY);
            try {
                Thread.sleep(250);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            mRobot.drive(40f, ROBOT_VELOCITY);
            try {
                Thread.sleep(250);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            mRobot.drive(30f, ROBOT_VELOCITY);
            try {
                Thread.sleep(250);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            mRobot.drive(20f, ROBOT_VELOCITY);
            try {
                Thread.sleep(250);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            mRobot.drive(10f, ROBOT_VELOCITY);
            try {
                Thread.sleep(250);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            mRobot.drive(0f, ROBOT_VELOCITY);
            try {
                Thread.sleep(250);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            mRobot.drive(350f, ROBOT_VELOCITY);
            try {
                Thread.sleep(250);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            mRobot.drive(340f, ROBOT_VELOCITY);
            try {
                Thread.sleep(250);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            mRobot.drive(330f, ROBOT_VELOCITY);
            try {
                Thread.sleep(250);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            mRobot.drive(320f, ROBOT_VELOCITY);
            try {
                Thread.sleep(250);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            mRobot.drive(310f, ROBOT_VELOCITY);
            try {
                Thread.sleep(250);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            mRobot.drive(300f, ROBOT_VELOCITY);
            try {
                Thread.sleep(250);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            mRobot.drive(290f, ROBOT_VELOCITY);
            try {
                Thread.sleep(250);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            mRobot.drive(280f, ROBOT_VELOCITY);
            try {
                Thread.sleep(250);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            mRobot.drive(270f, ROBOT_VELOCITY);
            try {
                Thread.sleep(250);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            mRobot.drive(260f, ROBOT_VELOCITY);
            try {
                Thread.sleep(250);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            mRobot.drive(250f, ROBOT_VELOCITY);
            try {
                Thread.sleep(250);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            mRobot.drive(240f, ROBOT_VELOCITY);
            try {
                Thread.sleep(250);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            mRobot.drive(230f, ROBOT_VELOCITY);
            try {
                Thread.sleep(250);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            mRobot.drive(220f, ROBOT_VELOCITY);
            try {
                Thread.sleep(250);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            mRobot.drive(210f, ROBOT_VELOCITY);
            try {
                Thread.sleep(250);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            mRobot.drive(200f, ROBOT_VELOCITY);
            try {
                Thread.sleep(250);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            mRobot.drive(190f, ROBOT_VELOCITY);
            try {
                Thread.sleep(250);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            mRobot.drive(180f, ROBOT_VELOCITY);
            try {
                Thread.sleep(250);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            mRobot.drive(170f, ROBOT_VELOCITY);
            try {
                Thread.sleep(250);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            mRobot.drive(160f, ROBOT_VELOCITY);
            try {
                Thread.sleep(250);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            mRobot.drive(150f, ROBOT_VELOCITY);
            try {
                Thread.sleep(250);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            mRobot.drive(140f, ROBOT_VELOCITY);
            try {
                Thread.sleep(250);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            mRobot.drive(130f, ROBOT_VELOCITY);
            try {
                Thread.sleep(250);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            mRobot.drive(120f, ROBOT_VELOCITY);
            try {
                Thread.sleep(250);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            mRobot.drive(110f, ROBOT_VELOCITY);
            try {
                Thread.sleep(250);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            mRobot.drive(100f, ROBOT_VELOCITY);
            try {
                Thread.sleep(250);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            loopCount += 1;
        } while (loopCount <= 3);
        mRobot.stop();
        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mRobot.drive(0f, 0f);
        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        course4Function();
    }

    public void course4Function(){
        mRobot.drive(0f, ROBOT_VELOCITY);
        mRobot.enableCollisions(true);
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
                    mRobot.stop();
                    mRobot.setLed(1.0f, 0f, 0f);
                }
            }
        });
    }

    public void course5Function(){
        int errorCount = 0;
        try {
            distance = Integer.parseInt(distanceEditText.getText().toString());
        } catch (Exception e){
            errorCount += 1;
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setMessage("Please enter a valid numeric value.");
            dlgAlert.setTitle("Error: Invalid Integer");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();

            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
        }
        if (errorCount == 0){
            mRobot.drive(0f, ROBOT_VELOCITY);
            mRobot.addResponseListener(new ResponseListener() {
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

                        if (positionY == distance) {
                            mRobot.stop();
                            mRobot.setLed(0f, 1.0f, 0f);
                        }

                    //statusTextView.setText("FINISHED!");
                }
            }
        });
        }
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
            case Offline: {
                connectTextBox.setText("Disconnected...");
            }
        }
    }

}
