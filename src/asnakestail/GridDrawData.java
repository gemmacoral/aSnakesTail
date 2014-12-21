/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package asnakestail;

import java.awt.Point;

/**
 *
 * @author gemmaelse
 */
public interface GridDrawData {
    public int getCellHeight();
    public int getCellWidth();
    
    public Point getCellSystemCoordinate (Point cellCoorenate);
    
}
