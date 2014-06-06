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

package cma.xebia.lawnmower.process.commands.actions;

import cma.xebia.lawnmower.model.ILawnMower;
import cma.xebia.lawnmower.process.commands.AAction;
import cma.xebia.lawnmower.process.commands.IAction;
import lombok.extern.slf4j.Slf4j;


/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
@Slf4j
public class A extends AAction  {
    
    @Override
    public IAction apply(ILawnMower lawnMower) {
        int newX = lawnMower.getX();
        int newY = lawnMower.getY();
        
        
        switch (lawnMower.getInFrontOf()) {
            case N : {
                newY++;
                
            } break;
            case E : {
                newX++;
                
            } break;
            case S : {
                newY--;
                
            } break;
            case W :
            default : {
                newX--;
                
            } break;
        }
        
        if (true != this.getPositionValidator().isValid(newX, newY)) {
            log.debug("cannot reach point {}.{}", newX, newY);
            
        } else {
            log.debug("move to point {}.{}", newX, newY);
            lawnMower
                .setX(newX)
                .setY(newY);
            
        }
        
        
        return this;
    }
    
}
