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

package cma.xebia.lawnmower.business.entity;

import cma.xebia.lawnmower.business.entity.constants.CompassPoint;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
public class Position implements Positionable {
    
    
    @Accessors(chain = true)
    @Getter
    @Setter
    private int x = 0;
    
    @Accessors(chain = true)
    @Getter
    @Setter
    private int y = 0;
    
    @Accessors(chain = true)
    @Getter
    @Setter
    private CompassPoint inFrontOf = CompassPoint.N;

    public Position () {
    }
    
    public Position (Position position) {
        this.x = position.x;
        this.y = position.y;
        this.inFrontOf = position.inFrontOf;
    }

    @Override
    public Position getPosition() {
        return this;
    }
    
    
    
    public boolean isInSameLocation (@NonNull Positionable positionable) {
        return this.isInSameLocation(positionable.getPosition());
    }
    
    public boolean isInSameLocation (@NonNull Position position ) {
        return (position.x == this.x)
                && (position.y == this.y);
    }
    
    @Override
    public String toString() {
        return String.format("Position [%s%s%s]",
            this.getX(),
            this.getY(),
            this.getInFrontOf());
    }
    
}
