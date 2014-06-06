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
package cma.xebia.lawnmower.model.lawnMower;

import cma.xebia.lawnmower.model.constants.CompassPoint;
import cma.xebia.lawnmower.model.constants.Movement;
import cma.xebia.lawnmower.model.ILawnMower;
import cma.xebia.lawnmower.process.commands.ICommands;
import java.util.ArrayList;
import java.util.List;
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
        implements ILawnMower {
    
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
    
    @Accessors(chain = true)
    @Getter
    @Setter
    private List<Movement> movements = new ArrayList<>();
    
    @Accessors(chain = true)
    @Getter
    @Setter(AccessLevel.PACKAGE)
    private ICommands commands = null;
    
    LawnMower(@NonNull ICommands commands) {
        this.commands = commands;
    }
    
    LawnMower init () {
        this.commands.setLawnMower(this);
        return this;
    }
    
    @Override
    public String toString() {
        return String.format("LawnMower [%s%s%s]",
            this.getX(),
            this.getY(),
            this.getInFrontOf());
    }
    
    
    
}
