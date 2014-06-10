/*
 * Copyright (C) 2014 Christophe Martel <mail.christophe.martel@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package cma.xebia.lawnmower.business.entity;

import cma.xebia.lawnmower.business.entity.constants.CompassPoint;
import cma.xebia.lawnmower.business.entity.constants.Movement;
import cma.xebia.lawnmower.business.entity.lawnmower.commands.ICommands;
import java.util.List;


/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
public interface ILawnMower {
    
    public int getX();
    
    public int getY();
    
    public CompassPoint getInFrontOf();
    
    public ILawnMower setX(int x);
    
    public ILawnMower setY(int y);
    
    public ILawnMower setInFrontOf(CompassPoint cp);
    
    public List<Movement> getMovements();
    
    public ICommands getCommands();
    
    public ILawnMower setMovements(List<Movement> movements);
    
}