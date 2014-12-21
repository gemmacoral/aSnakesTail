/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asnakestail;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author gemmaelse
 */
public class Score {

    public void draw(Graphics graphics) {
        graphics.setFont(font);
        graphics.drawString("Score:" + value, position.x, position.y);

    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }
    private int value = 0;
    private Point position;
    private Font font= new Font("Apple chancery", Font.PLAIN, 40);

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }

    public void addValue(int value) {
        this.value += value;
    }

    /**
     * @return the position
     */
    public Point getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Point position) {
        this.position = position;
    }
}
