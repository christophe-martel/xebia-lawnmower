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
import fr.martel.christophe.lawnmower.model.IAutomaticLawnMower;
import fr.martel.christophe.lawnmower.process.commands.ICommands;
import fr.martel.christophe.lawnmower.process.validator.ILawnMowerValidator;
import fr.martel.christophe.lawnmower.utils.exception.LawnmowerException;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
public class Builder implements IBuilder {
    
    
    final static Logger logger = LoggerFactory.getLogger(Builder.class);
    
    private IAutomaticLawnMower lawnMower = null;
    
    @Accessors(chain = true)
    @Getter
    @Setter
    private ArrayList<ILawnMowerValidator> validators = new ArrayList<>();
    
    @Override
    public Builder addValidator (ILawnMowerValidator validator) {
        if (true != this.validators.contains(validator)) {
            this.validators.add(validator);
        }
        return this;
    }
    
    
    @Override
    public Builder newLawnMower () {
        lawnMower = new LawnMower();
        return this;
    }
    
    
    @Override
    public Builder setX (int x) {
        logger.debug("set x to {}", Integer.toString(x, 10));
        lawnMower.setX(x);
        return this;
    }
    
    @Override
    public Builder setY (int y) {
        logger.debug("set height to {}", Integer.toString(y, 10));
        lawnMower.setY(y);
        return this;
    }

    @Override
    public IBuilder setCommands(ICommands commands) {
        logger.debug("add commands");
        lawnMower.setCommands(commands);
        return this;
    }

    @Override
    public IBuilder setInFrontOf(CompassPoint inFrontOf) {
        logger.debug("turn on {}", inFrontOf.getLabel());
        lawnMower.setInFrontOf(inFrontOf);
        return this;
    }
    
    
    /**
     * 
     * @return 
     * @throws LawnmowerException
     */
    @Override
    public IAutomaticLawnMower getLawnMower () throws LawnmowerException {
        
        if (true != this.validateLawnMower()) {
            logger.error("lawn is not valid");
            throw new LawnmowerException("unvalid lawn");
        }
        
        
        return lawnMower;
    }
    
    protected boolean validateLawnMower () {
        logger.debug("validate lawnmower");
        int sz = this.validators.size();
        for (int i = 0; i < sz; i++) {
            if (true != this.validators.get(i).isValid(this.lawnMower)) {
                return false;
            }
        }
        
        return true;
    }
    
    
    
}
