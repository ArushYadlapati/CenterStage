/* Copyright (c) 2023 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.Quaternion;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagLibrary;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.ArrayList;
import java.util.List;

@Autonomous()
public class AprilTagsDetection extends LinearOpMode {
    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera

    /**
     * {@link #aprilTag} is the variable to store our instance of the AprilTag processor.
     */
    private AprilTagProcessor aprilTag;

    /**
     * {@link #visionPortal} is the variable to store our instance of the vision portal.
     */
    private VisionPortal visionPortal;
    public double myTagPoseX;
    public double myTagPoseY;
    public double myTagPoseZ;
    public double myTagPosePitch;
    public double myTagPoseRoll;
    public double myTagPoseYaw;
    public AprilTagLibrary aprilTagLibrary;

    @Override
    public void runOpMode() {
        aprilTagLibrary = getCenterStageTagLibrary();
        initAprilTag();

        // Wait for the DS start button to be touched.
        telemetry.addData( "DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">",  "Touch Play to start OpMode");
        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {

                telemetryAprilTag();
                List<AprilTagDetection> currentDetections = aprilTag.getDetections();
                int numTags = currentDetections.size();
                if (numTags > 0 ) {
                    telemetry.addData("Tag", "####### %d Detected  ######", currentDetections.size());
                }
                else {
                    telemetry.addData("Tag", "----------- none - ----------");
                }


                // Push telemetry to the Driver Station.2
                telemetry.update();

                // Save CPU resources; can resume streaming when needed.
                if (gamepad1.dpad_down) {
                    visionPortal.stopStreaming();
                } else if (gamepad1.dpad_up) {
                    visionPortal.resumeStreaming();
                }

                // Share the CPU.
                sleep(20);
            }
        }

        // Save more CPU resources when camera is no longer needed.
        visionPortal.close();

    }   // end method runOpMode()
    public static AprilTagLibrary getCenterStageTagLibrary(){
        return new AprilTagLibrary.Builder()
                .addTag(1, "BlueAllianceLeft",     2, new VectorF(60.25f, 41.41f, 4f), DistanceUnit.INCH, new Quaternion( 0.683f, -0.183f, 0.183f, 0.683f, 0))
                .addTag(2, "BLueAllianceCenter",   2, new VectorF(60.25f, 35.41f,4f), DistanceUnit.INCH, new Quaternion(  0.683f,  -0.183f, 0.183f,  0.683f, 0))
                .addTag(3, "BlueAllianceRight",    2, new VectorF (60.25f, 29.41f,  4f), DistanceUnit.INCH, new Quaternion( 0.683f, -0.183f,0.183f,0.683f, 0))
                .addTag(4, "RedAllianceLeft",      2, new VectorF  (60.25f,-29.41f,4f), DistanceUnit.INCH, new Quaternion(  0.683f,  -0.183f, 0.183f,  0.683f, 0))
                .addTag(5, "RedAllianceCenter",    2, new VectorF (60.25f,-35.41f,4f), DistanceUnit.INCH, new Quaternion(  0.683f,  -0.183f, 0.183f,  0.683f, 0))
                .addTag(6, "RedAllianceRight",     2, new VectorF (60.25f,-41.41f,4f), DistanceUnit.INCH, new Quaternion(  0.683f,  -0.183f, 0.183f, 0.683f, 0))
                .addTag(7, "RedAudienceWallLarge", 5, new VectorF(-70.25f,-40.625f, 5.5f), DistanceUnit.INCH, new Quaternion(  0.7071f,  0, 0,  -7.071f, 0))
                .addTag(8, "RedAudienceWaltSmall", 2, new VectorF  (-70.25f,-35.125f,4f), DistanceUnit.INCH, new Quaternion(  0.7071f,  0, 0,  -7.071f , 0))
                .addTag(9, "BLueAudienceWaltSmall",2, new VectorF(-70.25f,35.125f,4), DistanceUnit.INCH, new Quaternion( 0.7071f,  0, 0,  -7.071f, 0))
                .addTag(10,"BlueAudienceWallLarge",5, new VectorF(-70.25f,40.625f,5.5f), DistanceUnit.INCH, new Quaternion(  0.7071f,  0, 0,  -7.071f, 0))
                .build();
    }
    //Initialize the AprilTag processor.
    private void initAprilTag() {

        // Create the AprilTag processor the easy way.
        aprilTag = AprilTagProcessor.easyCreateWithDefaults();

        // Create the vision portal the easy way.
        if (USE_WEBCAM) {
            visionPortal = VisionPortal.easyCreateWithDefaults(
                    hardwareMap.get(WebcamName.class, "Webcam 1"), aprilTag);
        } else {
            visionPortal = VisionPortal.easyCreateWithDefaults(
                    BuiltinCameraDirection.BACK, aprilTag);
        }


    }

    //Function to add telemetry about AprilTag detections.
    private void telemetryAprilTag() {
        List<VectorF> poses = new ArrayList<VectorF>();
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        telemetry.addData("# AprilTags Detected", currentDetections.size());

        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                myTagPoseX = detection.ftcPose.x;
                myTagPoseY = detection.ftcPose.y;
                myTagPoseZ = detection.ftcPose.z;

                // Testing Required
                VectorF vector = new VectorF((float) -myTagPoseX, (float) -myTagPoseY, (float) -myTagPoseZ);
                vector.add(aprilTagLibrary.lookupTag(detection.id).fieldPosition);
                poses.add(vector);


                myTagPosePitch = detection.ftcPose.pitch;
                myTagPoseRoll = detection.ftcPose.roll;
                myTagPoseYaw = detection.ftcPose.yaw;
                telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
                telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", myTagPoseX, myTagPoseY, myTagPoseZ));
                telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", myTagPosePitch, myTagPoseRoll, myTagPoseYaw));
                telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
            } else {
                telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
                telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
            }
        }
        // Average all the poses you have, getting a singular pose vector which should be more accurate

        // Add "key" information to telemetry
        telemetry.addLine("\nk\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
        telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
        telemetry.addLine("RBE = Range, Bearing & Elevation");
    }
}