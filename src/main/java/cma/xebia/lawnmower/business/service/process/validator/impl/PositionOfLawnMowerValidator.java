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

package cma.xebia.lawnmower.business.service.process.validator.impl;


import cma.xebia.lawnmower.business.entity.lawn.Lawn;
import cma.xebia.lawnmower.business.entity.lawnmower.LawnMower;
import cma.xebia.lawnmower.business.service.process.validator.LawnMowerValidator;
import cma.xebia.lawnmower.utils.validator.DefaultPositionValidator;
import cma.xebia.lawnmower.utils.validator.IPositionValidator;
import lombok.Setter;
import lombok.experimental.Accessors;


/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
public class PositionOfLawnMowerValidator implements LawnMowerValidator {
    
    @Accessors(chain = true)
    @Setter
    private LawnMower lawnMower = null;
    
    private IPositionValidator positionValidator = new DefaultPositionValidator();
    
    
    public PositionOfLawnMowerValidator(IPositionValidator positionValidator) {
        this.positionValidator = positionValidator;
    }
    
    @Override
    public boolean isValid (Lawn lawn) {
        boolean result = false;
        
        if (null == lawn) {
            return result;
            
        }
        
        return this
            .positionValidator
            .setMaxWidth(lawn.getWidth())
            .setMaxHeight(lawn.getHeight())
            .isValid(
                this.lawnMower.getX(),
                this.lawnMower.getY());
        
    }
    
    
    
}
