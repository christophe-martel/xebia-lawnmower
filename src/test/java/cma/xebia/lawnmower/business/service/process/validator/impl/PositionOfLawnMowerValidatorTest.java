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

package cma.xebia.lawnmower.business.service.process.validator.impl;

import cma.xebia.lawnmower.business.entity.ILawn;
import cma.xebia.lawnmower.business.entity.ILawnMower;
import cma.xebia.lawnmower.business.entity.LawnMowerTest;
import cma.xebia.lawnmower.business.entity.lawn.Lawn;
import cma.xebia.lawnmower.business.entity.lawnmower.LawnMower;
import cma.xebia.lawnmower.utils.validator.PositionValidator;
import junit.framework.TestCase;

/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
public class PositionOfLawnMowerValidatorTest extends TestCase {
    
    protected PositionOfLawnMowerValidator validator = null;
    
    public PositionOfLawnMowerValidatorTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        validator = new PositionOfLawnMowerValidator(
            (new PositionValidator())
                .setIncluding(true)
                .setMinHeight(0)
                .setMaxHeight(9)
                .setMinWidth(0)
                .setMaxWidth(9))
        ;
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        validator = null;
    }
    
    
    
    
    /**
     * Test of isValid method, of class PositionOfLawnMowerValidator.
     */
    public void testIsValid() {
        
        
        assertTrue(validator
            .setLawnMower((new LawnMowerTest()).setX(2).setY(2))
            .isValid((new Lawn()).setHeight(9).setWidth(9)));
        
        assertFalse(validator
            .setLawnMower((new LawnMowerTest()).setX(9).setY(9))
            .isValid((new Lawn()).setHeight(5).setWidth(5)));
        
        assertFalse(validator
            .setLawnMower((new LawnMowerTest()).setX(9).setY(2))
            .isValid((new Lawn()).setHeight(5).setWidth(5)));
        
        assertFalse(validator
            .setLawnMower((new LawnMowerTest()).setX(2).setY(9))
            .isValid((new Lawn()).setHeight(5).setWidth(5)));
        
    }
    
    /**
     * Test of isValid method, of class SizeOfLawnValidator with null Ilawn.
     */
    public void testIsValidWithNullLawn() {
        assertFalse(validator
            .setLawnMower((new LawnMowerTest()).setX(0).setY(0))
            .isValid(null));
    }
    
}
