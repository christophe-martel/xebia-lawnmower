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
package cma.xebia.lawnmower.business.entity.lawnmower;

import cma.xebia.lawnmower.business.entity.Movable;
import cma.xebia.lawnmower.business.entity.Position;
import cma.xebia.lawnmower.business.entity.Positionable;
import cma.xebia.lawnmower.business.entity.constants.CompassPoint;
import cma.xebia.lawnmower.business.entity.constants.Movement;
import cma.xebia.lawnmower.business.entity.lawnmower.commands.Action;
import cma.xebia.lawnmower.business.entity.lawnmower.commands.Commands;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.experimental.Accessors;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
public class LawnMower
        implements  Positionable,
                    Movable {
    
    @Accessors(chain = true)
    @Getter
    private Position position = null;
    
    
    @Accessors(chain = true)
    @Getter
    private List<Action> movements = new ArrayList<>();
    
    @Accessors(chain = true)
    @Setter(AccessLevel.PACKAGE)
    private Commands commands = null;
    
    LawnMower(@NonNull Commands commands) {
        this.commands = commands;
    }
    
    public LawnMower program (List<Movement> movements) {
        this.movements = new ArrayList<>();
        
        for (Movement movement : movements) {
            this.movements.add(this.commands.getMovements().get(movement));
            
        }
        
        return this;
    }
    
    @Override
    public Movable moveTo(@NonNull Positionable anotherLocation) {
        this.position = new Position(anotherLocation.getPosition());
        
        return this;
    }
    
    
    
    @Override
    public String toString() {
        return String.format("LawnMower {%s}",
            this.getPosition());
    }
    
    
    
}
