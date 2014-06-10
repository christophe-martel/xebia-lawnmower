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
import cma.xebia.lawnmower.business.entity.ILawnMower;
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
public class DefaultCommands implements ICommands {
    
    @Accessors(chain = true)
    @Setter
    private ILawnMower lawnMower = null;
    
    @Accessors(chain = true)
    @Getter
    @Setter
    private Map<Movement, IAction> movements = new LinkedHashMap<>();
    
    
    @Override
    public ICommands run() {
        log.info("Call to defaut movement computer ...");
        return this;
    }
    
    
}
