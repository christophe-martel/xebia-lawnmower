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

import cma.xebia.lawnmower.model.constants.CompassPoint;
import cma.xebia.lawnmower.model.ILawnMower;
import cma.xebia.lawnmower.process.commands.AAction;
import cma.xebia.lawnmower.process.commands.IAction;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
public class D extends AAction {

    final static Logger logger = LoggerFactory.getLogger(D.class);
    
    @Override
    public IAction apply(ILawnMower lawnMower) {
        logger.trace("receive movement D");
        logger.trace("in front of: {}", lawnMower.getInFrontOf().name());
        logger.trace("current position {}.{}", lawnMower.getX(), lawnMower.getY());
        
        
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
        logger.debug("Turn on {}", lawnMower.getInFrontOf().getLabel());
        return this;
    }
    
}
