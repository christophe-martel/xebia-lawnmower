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

package fr.martel.christophe.lawnmower.utils.validator;

import fr.martel.christophe.lawnmower.utils.exception.LawnmowerException;
import lombok.Getter;
import lombok.Setter;

import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * maximal limit "mawHeight" and "minHeight" are excluded from range.
 * "acceptingZero" change minimal limit between "0" (if FALSE) and "-1"
 * minimal limit is excluded from range.
 * 
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
public class PositionValidator implements IPositionValidator {
    
    final static Logger logger = LoggerFactory.getLogger(PositionValidator.class);
    
    @Accessors(chain = true)
    @Getter
    private int maxWidth = 1;
    
    @Accessors(chain = true)
    @Getter
    private int maxHeight = 1;
    
    @Accessors(chain = true)
    @Getter
    @Setter
    private boolean includingZero = false;
    
    /**
     * 
     * @param maxWidth
     * @return 
     * @throws LawnmowerException 
     */
    @Override
    public PositionValidator setMaxWidth (int maxWidth) throws LawnmowerException {
        if (maxWidth < 1) {
            logger.error("maxWidth '{}' must be greater than 0", maxWidth);
            throw new LawnmowerException("unvalid maxWidth");
        }
        
        this.maxWidth = maxWidth;
        return this;
    }
    
    /**
     * 
     * @param maxHeight
     * @return
     * @throws LawnmowerException 
     */
    @Override
    public PositionValidator setMaxHeight (int maxHeight) throws LawnmowerException {
        if (maxHeight < 1) {
            logger.error("maxHeight '{}' must be greater than 0", maxHeight);
            throw new LawnmowerException("unvalid maxHeight");
        }
        
        
        this.maxHeight = maxHeight;
        return this;
    }
    
    
    /**
     * 
     * @param x
     * @param y
     * @return 
     */
    @Override
    public boolean isValid(int x, int y) {
        return this.isValidRange(x, this.getMaxWidth())
            && this.isValidRange(y, this.getMaxHeight());
    }
    
    /**
     * 
     * @param i
     * @param max
     * @return return TRUE if i is between 0 and max
     */
    protected boolean isValidRange (int i, int max) {
        int minLimit = true == this.isIncludingZero()
            ? -1
            : 0;
        
        return (minLimit < i )
                && (i < max);
    }
    
}
