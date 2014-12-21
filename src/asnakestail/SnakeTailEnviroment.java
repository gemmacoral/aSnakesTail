/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asnakestail;

import audio.AudioPlayer;
import environment.Environment;
import environment.GraphicsPalette;
import environment.LocationValidatorIntf;
import grid.Grid;
import images.ResourceTools;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author gemmaelse
 */
class SnakeTailEnviroment extends Environment implements GridDrawData, LocationValidatorIntf {

    private Grid grid;
    private Snake snake;
    private Score score;
//    private final Color CLEAR = new Color();

    public final int SLOW_SPEED = 7;
    public final int MEDIUM_SPEED = 5;
    public final int HIGH_SPEED = 3;

    private int moveDelayCounter = 0;
    private int moveDelayLimit = HIGH_SPEED;
    private Image BOOK;
    private Image TIME;

    private ArrayList<GridObject> gridObjects;

    /**
     * @return the grid
     */
    public Grid getGrid() {
        return grid;
    }

    /**
     * @param grid the grid to set
     */
    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    /**
     * @return the snake
     */
    public Snake getSnake() {
        return snake;
    }

    /**
     * @param snake the snake to set
     */
    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public SnakeTailEnviroment() {
    }

    @Override
    public void initializeEnvironment() {
        score = new Score();
        score.setPosition(new Point(10, 30));

        this.setBackground(ResourceTools.loadImageFromResource("resources/background.jpg"));
        BOOK = ResourceTools.loadImageFromResource("resources/book.png");
        TIME = ResourceTools.loadImageFromResource("resources/TIME.png");

        setGrid(new Grid(25, 25, 25, 25, new Point(50, 50), Color.BLACK));

        snake = new Snake();
        snake.setLocationValidator(this);
        snake.setDrawData(this);
        snake.setDirection(Direction.DOWN);

        ArrayList<Point> body = new ArrayList<>();
        body.add(new Point(3, 1));
        body.add(new Point(4, 1));
        body.add(new Point(2, 2));
        body.add(new Point(2, 3));

        snake.setBody(body);

        gridObjects = new ArrayList<>();
        gridObjects.add(new GridObject(GridObjectType.BOOK, getRandomPoint()));
        gridObjects.add(new GridObject(GridObjectType.TIME, getRandomPoint()));

    }

    @Override
    public void timerTaskHandler() {
        if (snake != null) {
            //if counter is >= limit then reset counter and move snake 
            //else increment counter
            if (moveDelayCounter >= moveDelayLimit) {
                moveDelayCounter = 0;
                snake.move();
            } else {
                moveDelayCounter++;
            }
        }
    }

    @Override
    public void keyPressedHandler(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_C) {
            getGrid().setShowCellCoordinates(!grid.getShowCellCoordinates());
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            snake.setDirection(Direction.LEFT);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            snake.setDirection(Direction.RIGHT);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            snake.setDirection(Direction.DOWN);
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            snake.setDirection(Direction.UP);
        } else if (e.getKeyCode() == KeyEvent.VK_M) {
            AudioPlayer.play("/resources/page-flip-8.wav");
        } else if (e.getKeyCode() == KeyEvent.VK_P) {
            snake.togglePaused();
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            snake.grow(2);
        }
    }

    @Override
    public void keyReleasedHandler(KeyEvent e) {
    }

    @Override
    public void environmentMouseClicked(MouseEvent e) {
    }

    @Override
    public void paintEnvironment(Graphics graphics) {
        if (score != null) {
            score.draw(graphics);
        }
        if (getGrid() != null) {
            getGrid().paintComponent(graphics);
        }

        if (snake != null) {
            snake.draw(graphics);
        }
        if (gridObjects != null) {
            for (GridObject gridObject : gridObjects) {
                if (gridObject.getType() == GridObjectType.BOOK) {
                    graphics.drawImage(BOOK, grid.getCellSystemCoordinate(gridObject.getLocation()).x,
                            grid.getCellSystemCoordinate(gridObject.getLocation()).y,
                            grid.getCellHeight(), grid.getCellWidth(), this);

//                
                }
                if (gridObject.getType() == GridObjectType.TIME) {
                    graphics.drawImage(TIME, grid.getCellSystemCoordinate(gridObject.getLocation()).x,
                            grid.getCellSystemCoordinate(gridObject.getLocation()).y,
                            grid.getCellHeight(), grid.getCellWidth(), this);
//                }else if (gridObject.getType() == GridObjectType.BOOK) {
//                    graphics.drawImage(BOOK, grid.getCellSystemCoordinate(gridObject.getLocation()).x,
//                            grid.getCellSystemCoordinate(gridObject.getLocation()).y,
//                            grid.getCellHeight(), grid.getCellWidth(), this);

//                    GraphicsPalette.drawUnicorn(graphics, grid.getCellSystemCoordinate(gridObject.getLocation()), grid.getCellSize(), Color.YELLOW, environment.Direction.UP);
                }
            }
        }
    }

    public Point getRandomPoint() {
        return new Point((int) (grid.getRows() * Math.random()), (int) (grid.getColumns() * Math.random()));
    }

//<editor-fold defaultstate="collapsed" desc="GridDrawData Interface">
    @Override
    public int getCellHeight() {
        return getGrid().getCellHeight();
    }

    @Override
    public int getCellWidth() {
        return getGrid().getCellWidth();
    }

    @Override
    public Point getCellSystemCoordinate(Point cellCoordinate) {
        return getGrid().getCellSystemCoordinate(cellCoordinate);
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="LocationValidatorIntf">
    @Override
    public Point validateLocation(Point point) {
        //asses and adjust point as required...
        if (point.x >= this.grid.getColumns()) {
            point.x = 0;

        } else if (point.x < 0) {
            point.x = this.grid.getColumns() - 1;
        }
        if (point.y >= this.grid.getColumns()) {
            point.y = 0;

        } else if (point.y < 0) {
            point.y = this.grid.getColumns() - 1;
        }

        for (GridObject object : gridObjects) {

            if (object.getLocation().equals(point)) {
                System.out.println("HIT =" + object.getType());
                object.setLocation(this.getRandomPoint());
                AudioPlayer.play("/resources/page-flip-8.wav");
                snake.grow(1);
            }

//            if (object.getLocation().equals(point)) {
//                System.out.println("HIT =" + object.getType());
//                object.setLocation(this.getRandomPoint());
//                snake.remove(-1);
//                AudioPlayer.play("/resources/page-flip-8.wav");
//                snake;

            }

            //then return the point
            return point;
        }
//</editor-fold>

    }
