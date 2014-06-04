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

import fr.martel.christophe.lawnmower.constants.CompassPoint;
import fr.martel.christophe.lawnmower.model.ILawnMower;
import fr.martel.christophe.lawnmower.process.commands.AMovement;
import fr.martel.christophe.lawnmower.process.commands.IMovement;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
public class D extends AMovement {

    final static Logger logger = LoggerFactory.getLogger(D.class);
    
    @Override
    public IMovement apply(ILawnMower lawnMower) {
        logger.debug("receive movement D");
        logger.debug("in front of: {}", lawnMower.getInFrontOf().name());
        logger.debug("current position {}.{}", lawnMower.getX(), lawnMower.getY());
        
        
        switch (lawnMower.getInFrontOf()) {
            case N : {
                lawnMower.setInFrontOf(CompassPoint.E);
                
            } break;
            case E : {
                lawnMower.setInFrontOf(CompassPoint.S);
                
            } break;
            case S : {
                lawnMower.setInFrontOf(CompassPoint.W);
                
            } break;
            case W :
            default : {
                lawnMower.setInFrontOf(CompassPoint.N);
                
            } break;
        }
        logger.info("Turn on {}", lawnMower.getInFrontOf().getLabel());
        return this;
    }
    
}
