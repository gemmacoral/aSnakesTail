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
import java.awt.image.ImageObserver;

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
        segmentImage = (ResourceTools.loadImageFromResource("resources/hermi copy.png"));
    }

    public void draw(Graphics graphics) {
        for (Point bodySegmentLocation : getSafeBody()) {
            Image segment = segmentImage.getScaledInstance(drawData.getCellWidth(), drawData.getCellHeight(), Image.SCALE_FAST);

            drawData.getCellSystemCoordinate(bodySegmentLocation);

            Point topLeft = drawData.getCellSystemCoordinate(bodySegmentLocation);
            graphics.drawImage(segment, topLeft.x, topLeft.y, null);
//            graphics.drawImage(logo, 70, 25, this);
        }

    }

    /**
     * @return the body
     */
    public ArrayList<Point> getSafeBody() {
        ArrayList<Point> safeBody = new ArrayList<>();
        for (Point location : body) {
            safeBody.add(location);
        }
        return safeBody;
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


    /**
     * @return the selfHit
     */
    public boolean isSelfHit() {
        for (int i = 1; i < this.body.size(); i++) {
            if (this.getHead().equals(this.body.get(i))) {
//                System.out.println("HIT= "+getHead());
                return true;
            }
        }
        return false;
    }

    /*
     * @param selfHit the selfHit to set
     */
}
