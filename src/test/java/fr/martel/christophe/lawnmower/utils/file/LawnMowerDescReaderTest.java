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

package fr.martel.christophe.lawnmower.utils.file;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
public class LawnMowerDescReaderTest extends TestCase {
    
    final static Logger logger = LoggerFactory.getLogger(LawnMowerDescReaderTest.class);
    
    public LawnMowerDescReaderTest(String testName) {
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

    /**
     * Test of read method, of class LawnMowerDescReader.
     */
    public void testParseExpectedFile() {
        logger.info("Parse expected file");
        
        
        ILawnMowerDescReader r = (new LawnMowerDescReader())
            .setDefaultResourcePath("/setup/lawnmower.desc")
            .setCharset("UTF-8")
            .read();
        
        logger.info("is fail ?");
        assertFalse(r.isFail());
        
        logger.info("check lawn");
        assertEquals(new Dimension(5, 5), r.getLawn().getDimension());
        
        logger.info("check number of lawn mowers");
        assertEquals(2, r.getLawnMowers().size());
        
        logger.info("check lawn mower #1");
        assertEquals(new Point(1, 2), r.getLawnMowers().get(0).getPosition());
        assertEquals('N', r.getLawnMowers().get(0).getInFrontOf().charValue());
        assertEquals("GAGAGAGAA", this.join(r.getLawnMowers().get(0).getMovements()));
        
        logger.info("check lawn mower #2");
        assertEquals(new Point(3, 3), r.getLawnMowers().get(1).getPosition());
        assertEquals('E', r.getLawnMowers().get(1).getInFrontOf().charValue());
        assertEquals("AADAADADDA", this.join(r.getLawnMowers().get(1).getMovements()));
        
    }
    
    
    protected String join (ArrayList<Character> chars) {
        
        StringBuilder result = new StringBuilder();
        for (Character o : chars) {
            result.append(o);
        }
        
        return result.toString();
    }
}
