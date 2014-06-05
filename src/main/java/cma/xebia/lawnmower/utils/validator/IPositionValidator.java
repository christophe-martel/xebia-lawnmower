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

package cma.xebia.lawnmower.utils.validator;

import cma.xebia.lawnmower.utils.exception.LawnMowerException;

/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
public interface IPositionValidator {
    
    /**
     *
     * @param x
     * @param y
     * @return
     */
    boolean isValid (int x, int y);
    
    /**
     *
     * @param maxHeight
     * @return
     * @throws LawnMowerException
     */
    IPositionValidator setMaxHeight (int maxHeight);

    /**
     *
     * @param maxWidth
     * @return
     * @throws LawnMowerException
     */
    IPositionValidator setMaxWidth (int maxWidth);
    
    
    
    /**
     *
     * @param minHeight
     * @return
     * @throws LawnMowerException
     */
    IPositionValidator setMinHeight (int minHeight);

    /**
     *
     * @param minWidth
     * @return
     * @throws LawnMowerException
     */
    IPositionValidator setMinWidth (int minWidth);
    
    /**
     * 
     * @param including
     * @return 
     */
    IPositionValidator setIncluding (boolean including);
    
}
