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
package fr.martel.christophe.lawnmower.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
public class Main {
    
    final static Logger logger = LoggerFactory.getLogger(Main.class);
    
    /**
     * @param args the command line arguments
     */
    public static void main (String[] args) {
        (new Main())
            .configure()
            .init(args)
            .run()
            .finish();
        
    }
    
    protected Main() {
        logger.debug("start");
    }
    
    
    
    protected Main configure () {
        logger.debug("configure");
        
        
        return this;
    }
    
    protected Main init (String[] args) {
        logger.debug("init");
        
        
        return this;
    }
    
    protected Main run () {
        logger.debug("run");
        
        return this;
    }
    
    protected Main finish () {
        logger.debug("end");
        
        return this;
    }
    
    
}
