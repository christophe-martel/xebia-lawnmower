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
package cma.xebia.lawnmower.business.entity.lawnmower.commands;

import cma.xebia.lawnmower.business.entity.constants.Movement;
import cma.xebia.lawnmower.business.entity.lawnmower.LawnMower;
import cma.xebia.lawnmower.utils.exception.LawnMowerException;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;


/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
@Slf4j
public class CommandsImpl implements Commands {
    
    @Accessors(chain = true)
    @Setter
    private LawnMower lawnMower = null;
    
    @Accessors(chain = true)
    @Getter
    @Setter
    private Map<Movement, Action> movements = new LinkedHashMap<>();
    
    /**
     *
     * @return
     * @throws LawnMowerException
     */
    @Override
    public Commands run() throws LawnMowerException {
        int sz = this.lawnMower.getMovements().size();
        if (sz < 1) {
            log.debug("No action found");
            return this;
            
        }
        log.debug("run all actions");
        for (int i = 0; i < sz; i++) {
            this.executeCommand(this.lawnMower.getMovements().get(i));
        }
        
        
        return this;
    }
    
    protected CommandsImpl executeCommand (Movement key) throws LawnMowerException {
        if (!this.movements.containsKey(key)) {
            log.error("unknown command");
            throw new LawnMowerException(String.format("unknown command '%s'", key));
        }
        
        this.movements.get(key).apply(this.lawnMower);
        
        return this;
    }
    
}