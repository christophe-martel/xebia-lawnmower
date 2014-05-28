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

package fr.martel.christophe.lawnmower.process.commands.impl;

import fr.martel.christophe.lawnmower.model.IAutomaticLawnMower;
import fr.martel.christophe.lawnmower.process.commands.AMovement;

import fr.martel.christophe.lawnmower.process.commands.IMovement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
public class A extends AMovement  {
    
    final static Logger logger = LoggerFactory.getLogger(A.class);
    
    @Override
    public IMovement apply(IAutomaticLawnMower automaticLawnMower) {
        logger.debug("receive movement A");
        logger.debug("in front of: {}", automaticLawnMower.getInFrontOf().name());
        logger.debug("current position {}.{}", automaticLawnMower.getX(), automaticLawnMower.getY());
        
        int newX = automaticLawnMower.getX();
        int newY = automaticLawnMower.getY();
        
        
        switch (automaticLawnMower.getInFrontOf()) {
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
        
        logger.debug("estimated position {}.{}", newX, newY);
        
        if (false != this.getPositionValidator().isValid(newX, newY)) {
            logger.info("cannot reach point {}.{}", newX, newY);
            
        } else {
            logger.info("move to point {}.{}", newX, newY);
            automaticLawnMower
                .setX(newX)
                .setY(newY);
            
        }
        
        
        return this;
    }
    
}
