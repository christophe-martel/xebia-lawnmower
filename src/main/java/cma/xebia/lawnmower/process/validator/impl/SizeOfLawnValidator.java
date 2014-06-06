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

package cma.xebia.lawnmower.process.validator.impl;

import cma.xebia.lawnmower.model.ILawn;
import cma.xebia.lawnmower.process.validator.ILawnValidator;
import cma.xebia.lawnmower.utils.validator.DefaultPositionValidator;
import cma.xebia.lawnmower.utils.validator.IPositionValidator;

/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
public class SizeOfLawnValidator
        implements ILawnValidator {
    
    
    private IPositionValidator positionValidator = new DefaultPositionValidator();
    
    
    public SizeOfLawnValidator (IPositionValidator positionValidator) {
        this.positionValidator = positionValidator;
    }
    
    /**
     * 
     * @param lawn
     * @return return TRUE if width and height are between 1 and 9, included
     */
    @Override
    public boolean isValid (ILawn lawn) {
        boolean result = false;
        
        if (null == lawn) {
            return result;
            
        }
        
        return this
            .positionValidator
            .isValid(
                lawn.getWidth(),
                lawn.getHeight());
    }
    
}
