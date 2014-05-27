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
package fr.martel.christophe.lawnmower.model.lawnmower;

import fr.martel.christophe.lawnmower.constants.CompassPoint;
import fr.martel.christophe.lawnmower.constants.Movement;
import fr.martel.christophe.lawnmower.model.IAutomaticLawnMower;
import fr.martel.christophe.lawnmower.process.commands.DefaultCommands;
import fr.martel.christophe.lawnmower.process.commands.ICommands;
import java.util.ArrayList;
import lombok.experimental.Accessors;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
public class LawnMower
        implements IAutomaticLawnMower {
    
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
    private ArrayList<Movement> movements = new ArrayList<>();
    
    @Accessors(chain = true)
    @Getter
    @Setter
    private ICommands commands = new DefaultCommands();
    
    @Override
    public IAutomaticLawnMower addMovement(Movement movement) {
        this.movements.add(movement);
        return this;
    }
    
}
