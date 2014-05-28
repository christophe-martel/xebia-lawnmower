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

package fr.martel.christophe.lawnmower.model.lawn;

import fr.martel.christophe.lawnmower.process.validator.ILawnValidator;
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
public class LawnBuilder implements ILawnBuilder {
    
    final static Logger logger = LoggerFactory.getLogger(LawnBuilder.class);
    
    private Lawn lawn = null;
    
    @Accessors(chain = true)
    @Getter
    @Setter
    private ArrayList<ILawnValidator> validators = new ArrayList<>();
    
    @Override
    public LawnBuilder addValidator (ILawnValidator validator) {
        if (true != this.validators.contains(validator)) {
            this.validators.add(validator);
        }
        return this;
    }
    
    
    @Override
    public LawnBuilder newLawn () {
        lawn = new Lawn();
        return this;
    }
    
    @Override
    public LawnBuilder setWidth (int width) {
        logger.info("set width to {}", Integer.toString(width, 10));
        lawn.setWidth(width);
        return this;
    }
    
    @Override
    public LawnBuilder setHeight (int height) {
        logger.info("set height to {}", Integer.toString(height, 10));
        lawn.setHeight(height);
        return this;
    }
    
    /**
     * 
     * @return 
     * @throws LawnMowerException
     */
    @Override
    public Lawn getLawn () throws LawnMowerException {
        
        if (true != this.validateLawn()) {
            logger.error("lawn is not valid");
            throw new LawnMowerException("unvalid lawn");
        }
        
        
        return lawn;
    }
    
    protected boolean validateLawn () {
        logger.info("validate lawn");
        int sz = this.validators.size();
        for (int i = 0; i < sz; i++) {
            if (true != this.validators.get(i).isValid(this.lawn)) {
                return false;
            }
        }
        
        return true;
    }
    
    
}
