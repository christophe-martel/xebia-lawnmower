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

import cma.xebia.lawnmower.controller.impl.LawnMowerController;
import cma.xebia.lawnmower.utils.cmd.Argument;
import cma.xebia.lawnmower.utils.cmd.DefaultArgument;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 * @param <T>
 */
@Slf4j
public class LawnMowerHelpArgument<T extends LawnMowerController>
        extends DefaultArgument<T> {
    
    private final String helpTextPath;
    
    private final String charset;
    
    private final PrintStream out;
    
    public LawnMowerHelpArgument(
            String name,
            PrintStream out,
            String helpTextPath,
            String charset) {
        super(name);
        this.optionable = false;
        this.out = out;
        this.helpTextPath = helpTextPath;
        this.charset = charset;
    }
    
    @Override
    public Argument<T> applyOn(String argument, T object) {
        this.stop = true;
        
        try (   BufferedReader in = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream(this.helpTextPath),
                    this.charset))) {
            String inputLine;
            while (true) {
                inputLine = in.readLine();
                out.println(inputLine);
                if (null == inputLine) {
                    break;
                }
                
            }
            
        } catch (Exception ex) {
            log.error("Argument {}, {}", this.getName(), ex);
        }
        
        return this;
    }
    
}
