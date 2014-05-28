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
import fr.martel.christophe.lawnmower.utils.exception.LawnMowerException;
import java.util.ArrayList;

/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
public interface ILawnMowerBuilder {

    public ILawnMowerBuilder addValidator(ILawnMowerValidator validator);
    
    public ILawnMowerBuilder setCommands (ICommands commands);
    
    public ILawnMowerBuilder setValidators (ArrayList<ILawnMowerValidator> validators);
    
    
    /**
     *
     * @return
     * @throws LawnMowerException
     */
    public IAutomaticLawnMower getLawnMower() throws LawnMowerException;
    
    
    /**
     *  create a new instance of ILawnMower initialized with validators and commands
     * @return 
     */
    public ILawnMowerBuilder newLawnMower();
    
    public ILawnMowerBuilder withDefaultCommands ();
    
    public ILawnMowerBuilder setX(int x);
    
    public ILawnMowerBuilder setY(int y);
    
    public ILawnMowerBuilder setInFrontOf(CompassPoint inFrontOf);
    
    public ILawnMowerBuilder setMovements(ArrayList<Character> movements);
    
}
