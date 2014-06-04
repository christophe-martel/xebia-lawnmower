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

package fr.martel.christophe.lawnmower.process.validator.impl;

import fr.martel.christophe.lawnmower.model.ILawn;
import fr.martel.christophe.lawnmower.model.ILawnMower;
import fr.martel.christophe.lawnmower.process.validator.ILawnMowerValidator;
import fr.martel.christophe.lawnmower.utils.exception.LawnMowerException;
import fr.martel.christophe.lawnmower.utils.validator.DefaultPositionValidator;
import fr.martel.christophe.lawnmower.utils.validator.IPositionValidator;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
public class PositionOfLawnMowerValidator implements ILawnMowerValidator {
    
    final static Logger logger = LoggerFactory.getLogger(PositionOfLawnMowerValidator.class);
    
    @Accessors(chain = true)
    @Setter
    private ILawnMower lawnMower = null;
    
    private IPositionValidator positionValidator = new DefaultPositionValidator();
    
    
    public PositionOfLawnMowerValidator(IPositionValidator positionValidator) {
        this.positionValidator = positionValidator;
    }
    
    @Override
    public boolean isValid (ILawn lawn) {
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
