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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.martel.christophe.lawnmower.constants.Application;

/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
public class LawnMowerDescReader implements ILawnMowerDescReader {
    
    final static Logger logger = LoggerFactory.getLogger(LawnMowerDescReader.class);
    
    @Accessors(chain = true)
    @Getter
    @Setter
    private String defaultResourcePath = Application.RES_LAWNMOWER_DESC;
    
    @Accessors(chain = true)
    @Getter
    @Setter
    private String descriptorPath = null;
    
    
    @Accessors(chain = true)
    @Getter
    @Setter
    private String charset = Application.LAWNMOWER_FILE_CHARSET;
    
    @Accessors(chain = true)
    @Getter
    @Setter
    private ILawnDesc lawn = new LawnDesc();
    
    @Accessors(chain = true)
    @Getter
    @Setter
    private ArrayList<ILawnMowerDesc> lawnMowers = new ArrayList<>();
    
    private int lastLawnMower = -1;
    
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
        
        
        
        try (BufferedReader in = this.getReader()) {
            
            if (null == in) {
                return this.fail("cannot read configuration file");
                
            }
            
            while (true) {
                inputLine = in.readLine();
                if (null == inputLine) {
                    logger.info("End of file");
                    break;
                }
                
                if (true == this.isFail()) {
                    logger.info("Prematured end of file");
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
    
    protected BufferedReader getReader () throws IOException {
        return null != this.descriptorPath
            ? this.getReaderFromFile()
            : this.getReaderFromResource();
    }
    
    protected BufferedReader getReaderFromResource () throws IOException {
        logger.debug("load resource file {} with charset {}", this.defaultResourcePath, this.charset);
        return new BufferedReader(
            new InputStreamReader(
                this.getClass().getResourceAsStream(this.defaultResourcePath),
                this.charset));
    }
    
    protected BufferedReader getReaderFromFile () throws IOException {
        logger.debug("load local file {} with charset {}", this.descriptorPath, this.charset);
        File f = new File(this.descriptorPath);
        
        if (true != f.exists()) {
            logger.debug("file {} doesn't exist", this.descriptorPath);
            return null;
        }
        
        if (true != f.isFile()) {
            logger.debug("file {} isn't regular", this.descriptorPath);
            return null;
        }
        
        return new BufferedReader(
            new InputStreamReader(
                new FileInputStream(this.descriptorPath),
                this.charset));
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
        if (2 != line.length()) {
            return this.fail("two characters expected");
        }
        
        if (true != line.matches("^\\d{2}$")) {
            return this.fail("two digits expected");
            
        }
        
        String[] data = line.split("");
        this.lawn.getDimension().setSize(
            Integer.parseInt(data[0], 10),
            Integer.parseInt(data[1], 10));
        
        logger.info(
            "init lawn with dimension {}x{}",
            this.lawn.getDimension().width,
            this.lawn.getDimension().height);
        
        return this;
    }
    
    protected LawnMowerDescReader parseLawnMowerPosition (String line) {
        if (3 != line.length()) {
            return this.fail("three characters expected");
        }
        
        if (true != line.matches("^\\d{2}[NESW]$")) {
            return this.fail("two digits followed by a direction [N|E|S|W] expected");
            
        }
        
        ILawnMowerDesc lmd = this
            .createNewLawnMowerDesc()
            .getCurrentLawnMowerDesc();
        
        String[] data = line.split("");
        lmd
            .getPosition()
                .setLocation(
                    Integer.parseInt(data[0], 10),
                    Integer.parseInt(data[1], 10));
        
        logger.info(
            "init lawn mower with position ({}, {})",
            lmd.getPosition().x,
            lmd.getPosition().y);
        
        lmd.setInFrontOf(data[2].charAt(0));
        
        logger.info("turn lawn on {}", lmd.getInFrontOf());
        
        return this;
    }
    
    protected LawnMowerDescReader parseLawnMowerMovements (String line) {
        
        if (true != line.matches("^[DGA]+$")) {
            return this.fail("Only valid movement [D|G|A] expected");
            
        }
        
        ILawnMowerDesc lmd = this
            .getCurrentLawnMowerDesc();
        
        for(String movement : line.split("")) {
            logger.info("add action {} to current lawn mower", movement);
            
            lmd.getMovements().add(movement.charAt(0));
        }
        
        
        return this;
    }
    
    protected LawnMowerDescReader createNewLawnMowerDesc () {
        this.lawnMowers.add(new LawnMowerDesc());
        this.lastLawnMower = this.lawnMowers.size() - 1;
        
        return this;
    }
    
    protected ILawnMowerDesc getCurrentLawnMowerDesc () {
        if (this.lastLawnMower < 0) {
            this.lawnMowers.add(new LawnMowerDesc());
            this.lastLawnMower = this.lawnMowers.size() - 1;
        }
        
        return this.lawnMowers.get(this.lastLawnMower);
    }
    
    protected LawnMowerDescReader fail (String message) {
        logger.error(message);
        this.fail = true;
        return this;
    }
}
