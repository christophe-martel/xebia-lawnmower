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
import fr.martel.christophe.lawnmower.model.IAutomaticLawnMower;
import fr.martel.christophe.lawnmower.utils.exception.LawnMowerException;
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
public class Commands implements ICommands {
    
    final static Logger logger = LoggerFactory.getLogger(Commands.class);
    
    
    @Accessors(chain = true)
    @Getter
    @Setter
    private LinkedHashMap<Movement, IMovement> movements = new LinkedHashMap<>();
    
    /**
     *
     * @param automaticLawnMower
     * @return
     * @throws LawnMowerException
     */
    @Override
    public ICommands apply(
                IAutomaticLawnMower automaticLawnMower
            ) throws LawnMowerException {
        logger.info("Compute next position");
        
        int sz = automaticLawnMower.getMovements().size();
        
        if (sz < 1) {
            logger.info("Any movement found");
            return this;
            
        }
        
        for (int i = 0; i < sz; i++) {
            this.executeCommand(
                automaticLawnMower,
                automaticLawnMower.getMovements().get(i));
            
            
        }
        
        
        return this;
    }
    
    protected Commands executeCommand (
                IAutomaticLawnMower automaticLawnMower,
                Movement key
            ) throws LawnMowerException {
        if (true != this.movements.containsKey(key)) {
            logger.error("unknown command");
            throw new LawnMowerException(String.format("unknown command '%s'", key));
        }
        
        this.movements.get(key).apply(automaticLawnMower);
        
        return this;
    }
}
