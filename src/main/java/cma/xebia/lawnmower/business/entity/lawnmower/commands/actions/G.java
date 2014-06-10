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

package cma.xebia.lawnmower.business.entity.lawnmower.commands.actions;

import cma.xebia.lawnmower.business.entity.constants.CompassPoint;
import cma.xebia.lawnmower.business.entity.ILawnMower;
import cma.xebia.lawnmower.business.entity.lawnmower.commands.AAction;
import cma.xebia.lawnmower.business.entity.lawnmower.commands.IAction;
import lombok.extern.slf4j.Slf4j;


/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
@Slf4j
public class G extends AAction {
    
    @Override
    public IAction apply(ILawnMower lawnMower) {
        
        switch (lawnMower.getInFrontOf()) {
            case N :
                lawnMower.setInFrontOf(CompassPoint.W);
                break;
                
            case E :
                lawnMower.setInFrontOf(CompassPoint.N);
                break;
            
            case S :
                lawnMower.setInFrontOf(CompassPoint.E);
                break;
            
            case W :
            default :
                lawnMower.setInFrontOf(CompassPoint.S);
                break;
        }
        log.debug("Turn on {}", lawnMower.getInFrontOf().getLabel());
        return this;
    }
    
}
