package fr.martel.christophe.lawnmower.process.commands;

import fr.martel.christophe.lawnmower.constants.Movement;
import fr.martel.christophe.lawnmower.model.IAutomaticLawnMower;
import fr.martel.christophe.lawnmower.utils.LawnmowerException;
import java.util.LinkedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
public class Commands implements ICommands {
    
    final static Logger logger = LoggerFactory.getLogger(Commands.class);
    
    final private LinkedHashMap<Movement, ICommand> commands = new LinkedHashMap<>();
    
    /**
     *
     * @param automaticLawnMower
     * @return
     * @throws LawnmowerException
     */
    @Override
    public ICommands apply(
                IAutomaticLawnMower automaticLawnMower
            ) throws LawnmowerException {
        logger.debug("Compute next position");
        
        int sz = automaticLawnMower.getMovements().size();
        
        if (sz < 1) {
            logger.debug("Any movement found");
            return this;
            
        }
        
        for (int i = 0; i < sz; i++) {
            this.executeCommand(
                automaticLawnMower,
                automaticLawnMower.getMovements().get(i));
            
            
        }
        
        
        return this;
    }
    
    public Commands addCommand (Movement key, ICommand command) throws LawnmowerException {
        if (true == this.commands.containsKey(key)) {
            logger.error("duplicate command !");
            throw new LawnmowerException(String.format("duplicate command '%s'", key));
        }
        
        this.commands.put(key, command);
        
        return this;
    }
    
    protected Commands executeCommand (
                IAutomaticLawnMower automaticLawnMower,
                Movement key
            ) throws LawnmowerException {
        if (true != this.commands.containsKey(key)) {
            logger.error("unknown command");
            throw new LawnmowerException(String.format("unknown command '%s'", key));
        }
        
        this.commands.get(key).apply(automaticLawnMower);
        
        return this;
    }
}