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

package cma.xebia.lawnmower.process;

import cma.xebia.lawnmower.model.constants.Movement;
import cma.xebia.lawnmower.model.ILawnMower;
import cma.xebia.lawnmower.model.ILawn;
import cma.xebia.lawnmower.process.commands.IAction;
import cma.xebia.lawnmower.process.validator.ILawnValidator;
import cma.xebia.lawnmower.utils.exception.LawnMowerException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    private List<ILawnMower> lawnMowers = new ArrayList<>();
    
    
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
    public IShearer use(ILawnMower lawnMower) {
        if (true == this.lawnMowers.contains(lawnMower)) {
            return this;
        }
        this.lawnMowers.add(lawnMower);
        return this;
    }
    
    @Override
    public IShearer use(List<ILawnMower> lawnMowers) {
        for (ILawnMower lawnMower : lawnMowers) {
            this.use(lawnMower);
            
        }
        return this;
    }
    
    @Override
    public IShearer mow () {
        this.setFail(true);
        try {
            
            this.calibrateLawnMower();
            logger.info("mow law {}x{} ...", lawn.getWidth(), lawn.getHeight());
            int i = -1;
            for (ILawnMower lm : lawnMowers) {
                logger.info("with lawn {} #{} and movements {}", lm, ++i, lm.getMovements());
                lm.getCommands().run();
                logger.info("Lawn is : {}", lm);
            }
            this.setFail(false);
        } catch (LawnMowerException ex) {
            logger.error("error", ex);
        }
        return this;
    }
    
    
    protected IShearer calibrateLawnMower () {
        logger.info("calibration of lawn mowers");
        int i = -1;
        for (ILawnMower lawnmower : this.lawnMowers) {
            logger.info("calibrate lawn mowers #{}", ++i);
            for (Map.Entry<Movement, IAction> entry : lawnmower.getCommands().getMovements().entrySet()) {
                entry
                    .getValue()
                    .getPositionValidator()
                    .setMaxWidth(this.lawn.getWidth())
                    .setMaxHeight(this.lawn.getHeight())
                ;
            }
        }
        
        return this;
    }
    
    
    
    
}
