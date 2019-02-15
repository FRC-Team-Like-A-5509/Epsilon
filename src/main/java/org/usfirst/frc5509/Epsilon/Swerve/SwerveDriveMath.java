package org.usfirst.frc5509.Epsilon.Swerve;

public class SwerveDriveMath {
    public static SwerveDriveResult getDriveValues(SwerveDriveConfig config, SwerveHeading desired) {


        SwerveDriveResult currentPosition = new SwerveDriveResult();
        return getDriveValues(config, desired, currentPosition);

    }

    public static SwerveDriveResult getDriveValues(SwerveDriveConfig config, SwerveHeading desired, SwerveDriveResult currentPosition) {
        SwerveDriveResult result = new SwerveDriveResult();

        double A = desired.x - desired.rotation * (config.wheel_base / config.getRadius());
        double B = desired.x + desired.rotation * (config.wheel_base / config.getRadius());
        double C = desired.y - desired.rotation * (config.track_width / config.getRadius());
        double D = desired.y + desired.rotation * (config.track_width / config.getRadius());

        // calculate speed/rotation for each wheel

        double desiredAngleFR = Math.toDegrees(Math.atan2(B, C));
        VectorAngle infoFR = ticksForDesiredAngleReverse(config, currentPosition.getFrontRightModule().turnHeadingTicks, desiredAngleFR);
        result.getFrontRightModule().speedPercentage = Math.sqrt(Math.pow(B, 2) + Math.pow(C, 2)) * infoFR.vectorDirection();
        result.getFrontRightModule().setTurnHeadingTicks(infoFR.getAngleInTicks());
       // result.getFrontRightModule().setTurnHeadingDegrees(config, Math.toDegrees(Math.atan2(B, C)));


        double desiredAngleFL = Math.toDegrees(Math.atan2(B, D));
        VectorAngle infoFL = ticksForDesiredAngleReverse(config, currentPosition.getFrontLeftModule().turnHeadingTicks, desiredAngleFL);
        result.getFrontLeftModule().speedPercentage = Math.sqrt(Math.pow(B, 2) + Math.pow(D, 2)) * infoFL.vectorDirection();
        result.getFrontLeftModule().setTurnHeadingTicks(infoFL.getAngleInTicks());
       // result.getFrontLeftModule().setTurnHeadingDegrees(config, Math.toDegrees(Math.atan2(B, D)));

        double desiredAngleBL = Math.toDegrees(Math.atan2(A, D));
        VectorAngle infoBL = ticksForDesiredAngleReverse(config, currentPosition.getBackLeftModule().turnHeadingTicks, desiredAngleBL);
        result.getBackLeftModule().speedPercentage = Math.sqrt(Math.pow(A, 2) + Math.pow(D, 2)) * infoBL.vectorDirection();
        result.getBackLeftModule().setTurnHeadingTicks(infoBL.getAngleInTicks());
        //result.getBackLeftModule().setTurnHeadingDegrees(config, Math.toDegrees(Math.atan2(A, D)));


        double desiredAngleBR = Math.toDegrees(Math.atan2(A, C));
        VectorAngle infoBR = ticksForDesiredAngleReverse(config, currentPosition.getBackRightModule().turnHeadingTicks, desiredAngleBR);
        result.getBackRightModule().speedPercentage = Math.sqrt(Math.pow(A, 2) + Math.pow(C, 2)) * infoBR.vectorDirection();
        result.getBackRightModule().setTurnHeadingTicks(infoBR.getAngleInTicks());
        //result.getBackRightModule().setTurnHeadingDegrees(config, Math.toDegrees(Math.atan2(A, C)));

        result.normalizeSpeeds();


        return result;
    }

    /**
     * Returns the ticks of where you should go to get the desired angle from the current angle
     * This takes into account that ticks accumulate
     */
    public static int ticksForDesiredAngle(SwerveDriveConfig config,
                                           double currentTicks,
                                           double desiredDegrees) {

        double currentDegreeCirc = 0;
        int numCircles = 0;
        double currentDegree = currentTicks / config.getTicksPerDegree();

        int currentDegreeInt = (int) currentDegree;

        numCircles = currentDegreeInt / 360;


        if (desiredDegrees < 0) {

            desiredDegrees += 360;
        }

        if (currentDegree % 360 < 0) {

            currentDegreeCirc = currentDegree % 360 + 360;
        } else {
            currentDegreeCirc = currentDegree % 360;
        }

        // numCircles = (int) ((currentDegree - currentDegreeCirc) / 360);

        /*
        System.out.println("-------------");
        System.out.println("testNum: " + testNum);
        System.out.println("currentDegree: " + currentDegree);
        System.out.println("currentDegreeCirc: " + currentDegreeCirc);
        System.out.println("numCircles: " + numCircles);
        System.out.println("-------------");
        */


        double currentRadians = Math.toRadians(currentDegreeCirc);
        double desiredRadians = Math.toRadians(desiredDegrees);

        double xcomponent = Math.sin(currentRadians) * Math.sin(desiredRadians);
        double ycomponent = Math.cos(currentRadians) * Math.cos(desiredRadians);

        double travelRadians = Math.acos(xcomponent + ycomponent);

        double travelDegrees = Math.toDegrees(travelRadians);

        double clockwiseEnd = (currentDegreeCirc + travelDegrees) % 360;
        double notClockwiseEnd = (currentDegreeCirc - travelDegrees) % 360;

        if (clockwiseEnd == desiredDegrees) {

            return (int) (((currentDegree + travelDegrees) * config.getTicksPerDegree()) /*+ (360 * numCircles)*/);

        } else if (notClockwiseEnd == desiredDegrees) {

            return (int) (((currentDegree - travelDegrees) * config.getTicksPerDegree()) /*+ (360 * numCircles)*/);

        } else {
            throw new RuntimeException();
        }


    }

    public static VectorAngle ticksForDesiredAngleReverse(SwerveDriveConfig config,
                                                   double currentTicks,
                                                   double desiredDegrees) {


        double currentDegreeCirc = 0;
        double desiredDegreesNew = 0;
        double currentDegree = currentTicks / config.getTicksPerDegree();

        if (desiredDegrees < 0) {

            desiredDegrees += 360;
        }

        if (currentDegree % 360 < 0) {

            currentDegreeCirc = currentDegree % 360 + 360;
        } else {
            currentDegreeCirc = currentDegree % 360;
        }


        double currentRadians = Math.toRadians(currentDegreeCirc);
        double desiredRadians = Math.toRadians(desiredDegrees);

        double xcomponent = Math.sin(currentRadians) * Math.sin(desiredRadians);
        double ycomponent = Math.cos(currentRadians) * Math.cos(desiredRadians);

        double travelRadians = Math.acos(xcomponent + ycomponent);

        double travelDegrees = Math.toDegrees(travelRadians);

        if (Math.abs(travelDegrees) > 90) {

            desiredDegreesNew = (desiredDegrees + 180) % 360;

            double desiredRadiansNew = Math.toRadians(desiredDegreesNew);

            double xcomponentNew = Math.sin(currentRadians) * Math.sin(desiredRadiansNew);
            double ycomponentNew = Math.cos(currentRadians) * Math.cos(desiredRadiansNew);

            double travelRadiansNew = Math.acos(xcomponentNew + ycomponentNew);
            double travelDegreesNew = Math.toDegrees(travelRadiansNew);

            double clockwiseEnd = (currentDegreeCirc + travelDegreesNew) % 360;
            double notClockwiseEnd = (currentDegreeCirc - travelDegreesNew) % 360;

            if(notClockwiseEnd < 0){
                notClockwiseEnd += 360;
            }


            System.out.println("------");
            System.out.println("travelDegrees: " + travelDegrees);
            System.out.println("travelDegreesNew: " + travelDegreesNew);
            System.out.println("desiredDegreesNew: " + desiredDegreesNew);
            System.out.println("clockwiseEnd: " + clockwiseEnd);
            System.out.println("notClockwiseEnd: " + notClockwiseEnd);
            System.out.println("------");


            if (Math.abs(clockwiseEnd- desiredDegreesNew) < .0001) {

                double returningAngle = (int) (((currentDegree + travelDegreesNew) * config.getTicksPerDegree()));
                VectorAngle returner = new VectorAngle(false, returningAngle);
                return returner;

            } else if (Math.abs(notClockwiseEnd - desiredDegreesNew) < .0001) {

                double returningAngle =  (int) (((currentDegree - travelDegreesNew) * config.getTicksPerDegree()));
                VectorAngle returner = new VectorAngle(false, returningAngle);
                return returner;

            } else {
                throw new NullPointerException();
            }

        } else {

            double clockwiseEnd = (currentDegreeCirc + travelDegrees) % 360;
            double notClockwiseEnd = (currentDegreeCirc - travelDegrees) % 360;

            if(notClockwiseEnd < 0){
                notClockwiseEnd += 360;
            }

            if (Math.abs(clockwiseEnd - desiredDegrees) < .0001) {

                double returningAngle = (int) (((currentDegree + travelDegrees) * config.getTicksPerDegree()));
                VectorAngle returner = new VectorAngle(true, returningAngle);
                return returner;

            } else if (Math.abs(notClockwiseEnd - desiredDegrees) < .0001) {

                double returningAngle =  (int) (((currentDegree - travelDegrees) * config.getTicksPerDegree()));
                VectorAngle returner = new VectorAngle(true, returningAngle);
                return returner;

            } else {
                throw new RuntimeException("Variables no matchy: Clockwise: " + clockwiseEnd +"\n"
                        + "NotClockwise: " + notClockwiseEnd + "\n"
                + "DesiredDegree: "+ desiredDegrees);
            }

        }


    }

}
