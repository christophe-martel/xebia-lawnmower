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

package fr.martel.christophe.lawnmower.process.commands;

import fr.martel.christophe.lawnmower.constants.Movement;
import fr.martel.christophe.lawnmower.model.ILawnMower;
import java.util.LinkedHashMap;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
public class DefaultCommands implements ICommands {
    
    final static Logger logger = LoggerFactory.getLogger(DefaultCommands.class);
    
    @Accessors(chain = true)
    @Getter
    @Setter
    private LinkedHashMap<Movement, IMovement> movements = new LinkedHashMap<>();
    
    
    @Override
    public ICommands apply(ILawnMower lawnMower) {
        logger.info("Call to defaut movement computer ...");
        return this;
    }
    
}
