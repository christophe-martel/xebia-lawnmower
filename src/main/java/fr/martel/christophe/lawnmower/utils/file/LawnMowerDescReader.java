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
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
public class LawnMowerDescReader implements ILawnMowerDescReader {
    
    final static Logger logger = LoggerFactory.getLogger(LawnMowerDescReader.class);
    
    @Accessors(chain = true)
    @Getter
    @Setter
    private String defaultResourcePath = null;
    
    @Accessors(chain = true)
    @Getter
    @Setter
    private String descriptorPath = null;
    
    @Accessors(chain = true)
    @Getter
    @Setter
    private ILawnDesc lawn = new LawnDesc();
    
    @Accessors(chain = true)
    @Getter
    @Setter
    private ArrayList<ILawnMowerDesc> lawnMowers = new ArrayList<>();
    
    private int lastLawnMower = 0;
    
    @Accessors(chain = true)
    @Getter
    private boolean fail = true;
    
    
    @Override
    public ILawnMowerDescReader read() {
        this.fail = false;
        this.lastLawnMower = 0;
        this.lawn = new LawnDesc();
        this.lawnMowers = new ArrayList<>();
        
        this.readFile();
        
        return this;
    }
    
    
    protected LawnMowerDescReader readFile () {
        String inputLine = null;
        int lineCounter = -1;
        InputStream is = getClass().getResourceAsStream("/setup/lawnmower.desc");
        
        
        try (BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
            
            while (true) {
                inputLine = in.readLine();
                if (null == inputLine) {
                    logger.info("End of file");
                    break;
                }
                
                lineCounter++;
                
                inputLine = inputLine.trim();
                if (true == inputLine.isEmpty()) {
                    logger.debug("Ignoring empty line #{}", lineCounter);
                    continue;
                }
                
                this.parseLine(lineCounter, inputLine);
            }
        } catch (IOException ex) {
            logger.error("An exception occurs", ex);
            this.fail = true;
        }
        
        return this;
    }
    
    protected LawnMowerDescReader parseLine (int lineCounter, String line) {
        logger.debug("Parse line #{} with value:{}", lineCounter, line);
        
        if (0 == lineCounter) {
            this.parseLawnDimension(line);
            return this;
            
        } else if (1 == (lineCounter % 2)) {
            this.parseLawnMowerPosition(line);
            return this;
            
        }
        
        this.parseLawnMowerMovements(line);
        
        
        return this;
    }
    
    
    protected LawnMowerDescReader parseLawnDimension (String line) {
        
        
        return this;
    }
    
    protected LawnMowerDescReader parseLawnMowerPosition (String line) {
        
        return this;
    }
    
    protected LawnMowerDescReader parseLawnMowerMovements (String line) {
        
        return this;
    }
    
}
