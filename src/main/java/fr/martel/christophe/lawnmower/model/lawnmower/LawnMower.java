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
import fr.martel.christophe.lawnmower.model.ILawn;
import fr.martel.christophe.lawnmower.model.ILawnMower;
import fr.martel.christophe.lawnmower.process.commands.ICommands;
import fr.martel.christophe.lawnmower.process.validator.ILawnMowerValidator;
import java.util.ArrayList;
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
    private ILawn lawn = null;
    
    
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
    @Setter(AccessLevel.PACKAGE)
    private ICommands commands = null;
    
    @Accessors(chain = true)
    @Getter
    @Setter(AccessLevel.PACKAGE)
    private ILawnMowerValidator validator = null;
    
    public LawnMower(
            @NonNull ICommands commands,
            @NonNull ILawnMowerValidator validator) {
        this.commands = commands;
        this.validator = validator;
    }
    
    public LawnMower init () {
        this.validator.setLawnMower(this);
        
        return this;
    }
    
}
