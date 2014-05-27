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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;

import org.slf4j.LoggerFactory;

/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
public class SettingReaderTest extends TestCase {
    
    final static org.slf4j.Logger logger = LoggerFactory.getLogger(SettingReaderTest.class);
    
    public SettingReaderTest(String testName) {
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
     * Test of parse method, of class LawnMowerDescReader.
     */
    public void testParse() {
        
        LawnMowerDescReader r = new LawnMowerDescReader();
        
        
        InputStream is = r.getClass().getResourceAsStream("/setup/lawnmower.desc");
        
        
        try (BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                logger.info(inputLine);
            }
        }
        catch (IOException ex) {
            logger.debug("plop", ex);
        }
        
        
    }
    
}
