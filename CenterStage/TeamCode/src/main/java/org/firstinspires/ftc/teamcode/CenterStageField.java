package org.firstinspires.ftc.teamcode;

import java.util.ArrayList;

/*
 * This is a class that provide mapping and routing services to the other components.
 *
 * Field coordination system:
 * - A coordinate describes the position of a 3D points related to the origin
 * - The origin is at the center of the field
 * - The x axis runs from the middle of the back stage to the drone landing zone
 * - The y axis runs from the middle of the red alliance to the middle of the blue alliance
 * - The z axis runs from the ground to the roof
 * - All coordination unit is centi-meter
 *
 * The key APIs provided this class include:
 * - getRoute(): to calculate the shorted route between two points in the field
 */
public class CenterStageField {

    /**
     * The support field types
     */
    public enum FieldType {
        HalfField, ///< Half field
        FullField, ///< Full field
    }

    /**
     * The field coordinate
     */
    public class FieldCoordinate {
        double x; ///< the x coordinate
        double y; ///< the y coordinate
        double z; ///< the z coordinate
    }
    public final double UNLIMITED_HEIGHT = 10000; ///< a large number of unlimited height
    public final double GROUND_HEIGHT = 0; ///< zero height for the locations blocked by truss or backdrops
    public final double INVALID_HEIGHT = -1; ///< invalid height for the locations outside the field

    /**
     * The full constructor
     *
     * @param fieldType   the type of the center stage field
     * @param robotLength the length of the robot (in cm)
     * @param robotWidth  the width of the robot (in cm)
     */
    public CenterStageField(final FieldType fieldType, final double robotLength, final double robotWidth) {
        // Initialize the field layout, including
        // - field geometry
        // - the locations and heights of the truss and gateway
        // TODO: to be implemented later
    }

    /**
     * A helper function to find the height of a given 2D (x, y) coordinate in the field
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the maximum passable height of the given coordinate
     */
    double findHeight(final double x, final double y) {
        double height = INVALID_HEIGHT;

        // TODO: to be implemented later

        return height;
    }

    /**
     * Find the best route to move from point a to source in destination
     *
     * @param source      the source coordinate
     * @param destination the destination coordinate
     * @return A vector of field coordinates that the robot should move to. The first element of
     * the route is the next intermediate point and the last element is the destination
     */
    ArrayList<FieldCoordinate> findRoute(final FieldCoordinate source, final FieldCoordinate destination) {
        ArrayList<FieldCoordinate> route = new ArrayList<FieldCoordinate>();

        // Find the shortest route
        // TODO: to be implemented later

        return route;
    }
}