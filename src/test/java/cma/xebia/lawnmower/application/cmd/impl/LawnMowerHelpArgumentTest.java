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

package cma.xebia.lawnmower.application.cmd.impl;

import cma.xebia.lawnmower.SpringLoaded;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import junit.framework.TestCase;

/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
public class LawnMowerHelpArgumentTest extends SpringLoaded {
    
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    
    public LawnMowerHelpArgumentTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        System.setOut(new PrintStream(outContent));
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        System.setOut(null);
    }
    
    /**
     * Test of applyOn method, of class LawnMowerHelpArgument.
     */
    public void testApplyOn() {
        
        LawnMowerHelpArgument instance = new LawnMowerHelpArgument(
            "help",
            System.out,
            "/cmd/help.txt",
            "UTF-8");
        
        
        String expResult = this.getResourceAsString("/cmd/help.txt", "UTF-8");
        instance.applyOn("--help", controller);
        assertEquals(expResult, outContent.toString());
        
    }
    
    
    protected String getResourceAsString (String path, String charset) {
        StringBuilder builder = new StringBuilder();
        try (   BufferedReader in = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream(path),
                    charset))) {
            String inputLine;
            while (true) {
                inputLine = in.readLine();
                builder.append(inputLine);
                builder.append("\n");
                if (null == inputLine) {
                    break;
                }
                
            }
            
        } catch (Exception ex) {
            return "";
        }
        
        return builder.toString();
    }
}
