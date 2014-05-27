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
public class MovementTest extends TestCase {
    
    public MovementTest (String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp () throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown () throws Exception {
        super.tearDown();
    }
    
    public void testLength () {
        assertEquals(Movement.values().length, 3);
        
    }
    
    public void testGetByNameString () {
        
        assertEquals(Movement.D, Movement.getByName("D"));
        assertEquals(Movement.G, Movement.getByName("G"));
        assertEquals(Movement.A, Movement.getByName("A"));
        
    }
    
    public void testGetByNameChar () {
        
        assertEquals(Movement.D, Movement.getByName('D'));
        assertEquals(Movement.G, Movement.getByName('G'));
        assertEquals(Movement.A, Movement.getByName('A'));
        
    }
    
    public void testNotFound () {
        
        assertNull(Movement.getByName("Droite"));
        assertNull(Movement.getByName("Gauche"));
        assertNull(Movement.getByName("Avancer"));
        
    }
    
    
    public void testGetLabel () {
        
        assertEquals("Droite", Movement.getByName("D").getLabel());
        assertEquals("Gauche", Movement.getByName("G").getLabel());
        assertEquals("Avancer", Movement.getByName("A").getLabel());
        
    }
    
}
