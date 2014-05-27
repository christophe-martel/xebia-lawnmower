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

package fr.martel.christophe.lawnmower.constants;

import junit.framework.TestCase;


/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
public class CompassPointTest extends TestCase {
    
    public CompassPointTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    
    public void testLength () {
        assertEquals(CompassPoint.values().length, 4);
        
    }
    
    public void testGetByNameString () {
        
        assertEquals(CompassPoint.N, CompassPoint.getByName("N"));
        assertEquals(CompassPoint.E, CompassPoint.getByName("E"));
        assertEquals(CompassPoint.S, CompassPoint.getByName("S"));
        assertEquals(CompassPoint.W, CompassPoint.getByName("W"));
        
    }
    
    public void testGetByNameChar () {
        
        assertEquals(CompassPoint.N, CompassPoint.getByName('N'));
        assertEquals(CompassPoint.E, CompassPoint.getByName('E'));
        assertEquals(CompassPoint.S, CompassPoint.getByName('S'));
        assertEquals(CompassPoint.W, CompassPoint.getByName('W'));
        
    }
    
    public void testNotFound () {
        
        assertNull(CompassPoint.getByName("North"));
        assertNull(CompassPoint.getByName("Est"));
        assertNull(CompassPoint.getByName("South"));
        assertNull(CompassPoint.getByName("West"));
        
    }
    
    
    public void testGetLabel () {
        
        assertEquals("North", CompassPoint.getByName("N").getLabel());
        assertEquals("Est", CompassPoint.getByName("E").getLabel());
        assertEquals("South", CompassPoint.getByName("S").getLabel());
        assertEquals("West", CompassPoint.getByName("W").getLabel());
        
    }
}
