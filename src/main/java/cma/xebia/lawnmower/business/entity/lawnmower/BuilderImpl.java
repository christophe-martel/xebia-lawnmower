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

import cma.xebia.lawnmower.business.entity.Position;
import cma.xebia.lawnmower.business.entity.Positionable;
import cma.xebia.lawnmower.business.entity.constants.CompassPoint;
import cma.xebia.lawnmower.business.entity.lawnmower.commands.Commands;
import com.rits.cloning.Cloner;
import lombok.NonNull;
import lombok.experimental.Accessors;

/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
public class BuilderImpl implements LawnMowerBuilder {
    
    @Accessors(chain = true)
    private Commands commands = null;
    
    private Cloner cloner = null;
    
    public BuilderImpl (Commands commands, Cloner cloner) {
        this.commands = commands;
        this.cloner = cloner;
    }
    
    @Override
    public LawnMower create () {
        LawnMower result = new LawnMower(this.cloner.deepClone(this.commands));
        
        return result;
    }
    
    @Override
    public LawnMower create (@NonNull Positionable positioned) {
        LawnMower result = this.create();
        
        result.moveTo(positioned);
        
        return result;
    }
    
    @Override
    public LawnMower create (int x, int y, CompassPoint inFrontOf) {
        Position result = new Position();
        result
            .setX(x)
            .setY(y)
            .setInFrontOf(inFrontOf);
        
        return this.create(result);
    }
}
