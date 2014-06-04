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

package fr.martel.christophe.lawnmower.process;

import fr.martel.christophe.lawnmower.model.ILawnMower;
import fr.martel.christophe.lawnmower.model.ILawn;
import fr.martel.christophe.lawnmower.process.validator.ILawnValidator;
import fr.martel.christophe.lawnmower.utils.exception.LawnMowerException;
import java.util.ArrayList;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
public class Shearer implements IShearer {
    
    final static Logger logger = LoggerFactory.getLogger(Shearer.class);
    
    private ILawnValidator validator = null;

    public Shearer (ILawnValidator validator) {
        this.validator = validator;
    }
    
    @Accessors(chain = true)
    @Getter
    @Setter(AccessLevel.PRIVATE)
    private ILawn lawn = null;
    
    @Accessors(chain = true)
    @Getter
    @Setter(AccessLevel.PRIVATE)
    private ArrayList<ILawnMower> lawnMowers = new ArrayList<>();
    
    
    @Accessors(chain = true)
    @Getter
    @Setter(AccessLevel.PRIVATE)
    private boolean fail = false;
    
    @Override
    public IShearer on(ILawn lawn) {
        this.lawn = lawn;
        return this;
    }

    @Override
    public IShearer push(ILawnMower lawnMower) {
        this.lawnMowers.add(lawnMower);
        return this;
    }
    
    @Override
    public IShearer push(ArrayList<ILawnMower> lawnMowers) {
        for (ILawnMower lawnMower : lawnMowers) {
            this.push(lawnMower);
            
        }
        return this;
    }
    
    @Override
    public IShearer run () {
        this.setFail(true);
        try {
            for (ILawnMower lm : lawnMowers) {
                logger.debug("lawn: {}x{}", lawn.getWidth(), lawn.getHeight());
                logger.debug("run sequence: {}", lm.getMovements());
                
                lm.getCommands().apply(lm);
            }
            this.setFail(false);
        } catch (LawnMowerException ex) {
            logger.error("error", ex);
        }
        return this;
    }
    
    
    
}
