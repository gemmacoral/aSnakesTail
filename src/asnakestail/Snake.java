/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asnakestail;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import environment.LocationValidatorIntf;
import images.ResourceTools;
import java.awt.Image;

/**
 *
 * @author gemmaelse
 */
public class Snake {

    private ArrayList<Point> body = new ArrayList<>();
    private Direction direction = Direction.RIGHT;
    private GridDrawData drawData;
    private boolean paused;
    private int growthCounter;
    private LocationValidatorIntf locationValidator;
    private Image segmentImage;

    //grow
    //eat
    //move
    //die
    //draw

    {
        segmentImage = (ResourceTools.loadImageFromResource("resources/hermi.png"));
    }

    public void draw(Graphics graphics) {
        for (Point bodySegmentLocation : body) {
            Image segment = segmentImage.getScaledInstance(drawData.getCellWidth(), drawData.getCellHeight(), Image.SCALE_FAST);
//            System.out.println("Location =" + bodySegmentLocation);
//            System.out.println("System Loc =" + drawData.getCellSystemCoordinate(bodySegmentLocation))
            drawData.getCellSystemCoordinate(bodySegmentLocation);

            graphics.setColor(Color.BLACK);

            Point topLeft = drawData.getCellSystemCoordinate(bodySegmentLocation);
            graphics.drawImage(segment, topLeft.x, topLeft.y, null);

//            Point topLeft = drawData.getCellSystemCoordinate(bodySegmentLocation);
//           graphics.fillOval(topLeft.x, topLeft.y, drawData.getCellWidth(), drawData.getCellHeight());
        }

    }

    /**
     * @return the body
     */
    public ArrayList<Point> getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(ArrayList<Point> body) {
        this.body = body;
    }

    /**
     * @return the direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * @return the drawData
     */
    public GridDrawData getDrawData() {
        return drawData;
    }

    /**
     * @param drawData the drawData to set
     */
    public void setDrawData(GridDrawData drawData) {
        this.drawData = drawData;
    }
    private final int HEAD_POSITION = 0;

    public void grow(int length) {
        growthCounter += length;
    }

   

    public void move() {
        if (!paused) {
            Point newHead = (Point) getHead().clone();

            if (direction == Direction.DOWN) {
                newHead.y++;
            } else if (direction == Direction.UP) {
                newHead.y--;
            } else if (direction == Direction.LEFT) {
                newHead.x--;
            } else if (direction == Direction.RIGHT) {
                newHead.x++;
            }
            if (locationValidator != null) {
                body.add(HEAD_POSITION, locationValidator.validateLocation(newHead));
            }

//            body.remove(body.size() - 1);
            if (growthCounter <= 0) {
                body.remove(body.size() - 1);
            } else {
                growthCounter--;
            }
        }
    }

    public void togglePaused() {
        if (paused) {
            paused = false;

        } else {
            paused = true;
        }
        /**
         * @return the paused
         */
    }

    /**
     * @param paused the paused to set
     */
    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    /**
     * @return the growthCounter
     */
    public int getGrowthCounter() {
        return growthCounter;
    }

    /**
     * @param growthCounter the growthCounter to set
     */
    public void setGrowthCounter(int growthCounter) {
        this.growthCounter = growthCounter;
    }

    /**
     * @return the locationValidator
     */
    public LocationValidatorIntf getLocationValidator() {
        return locationValidator;
    }

    /**
     * @param locationValidator the locationValidator to set
     */
    public void setLocationValidator(LocationValidatorIntf locationValidator) {
        this.locationValidator = locationValidator;
    }

    private Point getHead() {
        return body.get(HEAD_POSITION);
    }

    void remove(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
