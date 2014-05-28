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
import fr.martel.christophe.lawnmower.constants.Movement;
import fr.martel.christophe.lawnmower.model.IAutomaticLawnMower;
import fr.martel.christophe.lawnmower.process.commands.ICommands;
import fr.martel.christophe.lawnmower.process.validator.ILawnMowerValidator;
import fr.martel.christophe.lawnmower.utils.exception.LawnMowerException;
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
public class LawnMowerBuilder implements ILawnMowerBuilder {
    
    
    final static Logger logger = LoggerFactory.getLogger(LawnMowerBuilder.class);
    
    private IAutomaticLawnMower lawnMower = new LawnMower();
    
    @Accessors(chain = true)
    @Getter
    @Setter
    private ArrayList<ILawnMowerValidator> validators = new ArrayList<>();
    
    @Accessors(chain = true)
    @Getter
    @Setter
    private ICommands commands = null;
    
    @Override
    public LawnMowerBuilder addValidator (ILawnMowerValidator validator) {
        if (true != this.validators.contains(validator)) {
            this.validators.add(validator);
        }
        return this;
    }
    
    /**
     *  create a new instance of ILawnMower initialized with validators and commands
     * @return 
     */
    @Override
    public LawnMowerBuilder newLawnMower () {
        lawnMower = new LawnMower();
        
        return this;
    }
    
    @Override
    public ILawnMowerBuilder withDefaultCommands () {
        lawnMower.setCommands(this.getCommands());
        return this;
    }
    
    @Override
    public LawnMowerBuilder setX (int x) {
        logger.debug("set x to {}", Integer.toString(x, 10));
        lawnMower.setX(x);
        return this;
    }
    
    @Override
    public LawnMowerBuilder setY (int y) {
        logger.debug("set y to {}", Integer.toString(y, 10));
        lawnMower.setY(y);
        return this;
    }

    @Override
    public ILawnMowerBuilder setInFrontOf(CompassPoint inFrontOf) {
        logger.debug("turn on {}", inFrontOf.getLabel());
        lawnMower.setInFrontOf(inFrontOf);
        return this;
    }

    @Override
    public ILawnMowerBuilder setMovements(ArrayList<Character> movements) {
        logger.debug("add movements");
        
        Movement m = null;
        for (Character movement : movements) {
            m = Movement.getByName(movement);
            logger.debug("add movement {{}}", m.getLabel());
            lawnMower.addMovement(m);
            
        }
        
        return this;
    }
    
    
    
    /**
     * 
     * @return 
     * @throws LawnMowerException
     */
    @Override
    public IAutomaticLawnMower getLawnMower () throws LawnMowerException {
        
        if (true != this.validateLawnMower()) {
            logger.error("lawn is not valid");
            throw new LawnMowerException("unvalid lawn");
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
