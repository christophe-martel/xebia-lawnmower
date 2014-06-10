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
import cma.xebia.lawnmower.business.entity.constants.Movement;
import cma.xebia.lawnmower.business.entity.lawnmower.commands.DefaultCommands;
import cma.xebia.lawnmower.business.entity.lawnmower.commands.ICommands;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
public class LawnMowerTest implements ILawnMower {
    
    
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
    private ICommands commands = new DefaultCommands();

    public LawnMowerTest() {
    }
    
    
    
    
}
