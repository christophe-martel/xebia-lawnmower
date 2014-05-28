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
import fr.martel.christophe.lawnmower.process.validator.ILawnValidator;
import fr.martel.christophe.lawnmower.utils.validator.DefaultPositionValidator;
import fr.martel.christophe.lawnmower.utils.validator.IPositionValidator;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
public class SizeOfLawnValidator
        implements ILawnValidator {
    
    
    @Accessors(chain = true)
    @Getter
    @Setter
    private IPositionValidator positionValidator = new DefaultPositionValidator();
    
    
    /**
     * 
     * @param lawn
     * @return return TRUE if width and height are between 0 and 10, excluded
     */
    @Override
    public boolean isValid(ILawn lawn) {
        if (null == lawn) {
            return false;
            
        }
        
        return this
            .getPositionValidator()
            .isValid(
                lawn.getWidth(),
                lawn.getHeight());
    }
    
}