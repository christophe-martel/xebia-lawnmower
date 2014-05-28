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
import fr.martel.christophe.lawnmower.model.IAutomaticLawnMower;
import fr.martel.christophe.lawnmower.process.commands.ACommand;
import fr.martel.christophe.lawnmower.process.commands.ICommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
public class G extends ACommand {
    
    final static Logger logger = LoggerFactory.getLogger(G.class);
    
    @Override
    public ICommand apply(IAutomaticLawnMower automaticLawnMower) {
        
        switch (automaticLawnMower.getInFrontOf()) {
            case N : {
                automaticLawnMower.setInFrontOf(CompassPoint.W);
                
            } break;
            case E : {
                automaticLawnMower.setInFrontOf(CompassPoint.N);
                
            } break;
            case S : {
                automaticLawnMower.setInFrontOf(CompassPoint.E);
                
            } break;
            case W :
            default : {
                automaticLawnMower.setInFrontOf(CompassPoint.S);
                
            } break;
        }
        logger.info("Turn on {}", automaticLawnMower.getInFrontOf().getLabel());
        return this;
    }
    
}
