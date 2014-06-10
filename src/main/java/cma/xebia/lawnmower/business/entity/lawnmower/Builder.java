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

import cma.xebia.lawnmower.business.entity.ILawnMower;
import cma.xebia.lawnmower.business.entity.ILawnMowerBuilder;
import cma.xebia.lawnmower.business.entity.lawnmower.commands.ICommands;
import com.rits.cloning.Cloner;
import lombok.experimental.Accessors;

/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
public class Builder implements ILawnMowerBuilder {
    
    @Accessors(chain = true)
    private ICommands commands = null;
    
    private Cloner cloner = null;
    
    public Builder (ICommands commands, Cloner cloner) {
        this.commands = commands;
        this.cloner = cloner;
    }
    
    @Override
    public ILawnMower create () {
        LawnMower result = new LawnMower(this.cloner.deepClone(this.commands));
        result.init();
        
        return result;
    }
    
}
