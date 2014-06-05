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
package cma.xebia.lawnmower.application;

import cma.xebia.lawnmower.model.constants.CompassPoint;
import cma.xebia.lawnmower.model.constants.Movement;
import cma.xebia.lawnmower.model.ILawnMower;
import cma.xebia.lawnmower.model.ILawn;
import cma.xebia.lawnmower.process.IShearer;
import cma.xebia.lawnmower.utils.exception.LawnMowerException;
import cma.xebia.lawnmower.utils.file.ILawnMowerDesc;
import cma.xebia.lawnmower.utils.file.ILawnMowerDescReader;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
public class Main {
    
    final static private Logger logger = LoggerFactory.getLogger(Main.class);
    
    static private ApplicationContext context = null;
    
    private ILawnMowerDescReader reader = null;
    
    private ILawn lawn = null;
    
    private List<ILawnMower> lawnMowers = new ArrayList<>();
    
    
    /**
     * @param args the command line arguments
     */
    public static void main (String[] args) {
        try {
            (new Main())
                .configure()
                .init(args)
                .run()
                .finish();
        } catch (LawnMowerException lme) {
            logger.error("Oups", lme);
            
        }
        
    }
    
    public static ILawn getNewLawn () {
        return ((ILawn) context
            .getBean(Constant.BEAN_LAWN));
    }
    
    public static ILawnMower getNewLawnMower () {
        return ((ILawnMower) context
            .getBean(Constant.BEAN_LAWN_MOWER));
    }
    
    
    public static IShearer getNewShearer () {
        return ((IShearer) context
            .getBean(Constant.BEAN_SHEARER));
    }
    
    
    protected Main() {
        logger.info("start");
    }
    
    
    
    protected Main configure () {
        logger.info("configure");
        
        context = new ClassPathXmlApplicationContext(
                "/configuration/spring.xml",
                Main.class);
        
        return this;
    }
    
    protected Main init (String[] args) throws LawnMowerException {
        logger.info("init");
        
        this
            .initDescriptor(args)
            .initLawn()
            .initLawnMowers()
        ;
        
        return this;
    }
    
    
    protected Main run () throws LawnMowerException {
        logger.info("run");
        
        IShearer shearer = Main
            .getNewShearer()
            .on(this.lawn)
            .use(this.lawnMowers)
            .mow();
        
        if (true == shearer.isFail()) {
            logger.info("Oups, an error occurs ...");
            
        } else {
            logger.info("Done");
            int i = -1;
            for (ILawnMower lm : shearer.getLawnMowers()) {
                logger.info("lawn #{} is to position ({}x{}) and is in front of {}",
                    ++i,
                    lm.getX(),
                    lm.getY(),
                    lm.getInFrontOf());
                
            }
        }
        
        return this;
    }
    
    protected Main finish () {
        logger.info("end");
        
        return this;
    }
    
    protected Main initLawn () throws LawnMowerException {
        logger.info("create lawn");
        
        this.lawn = Main
            .getNewLawn()
            .setHeight(this.reader.getLawn().getDimension().height)
            .setWidth(this.reader.getLawn().getDimension().width)
        ;
        
        return this;
    }
    
    
    protected Main initLawnMowers () throws LawnMowerException {
        logger.info("create lawn mowers");
        
        for(ILawnMowerDesc desc : this.reader.getLawnMowers()) {
            this.lawnMowers.add(
                Main
                    .getNewLawnMower()
                    .setX(desc.getPosition().x)
                    .setY(desc.getPosition().y)
                    .setInFrontOf(CompassPoint.valueOf(desc.getInFrontOf()))
                    .setMovements(Movement.parseMovements(desc.getMovements())));
            
        }
        
        return this;
    }
    
    protected Main initDescriptor (String[] args) {
        this.reader = (ILawnMowerDescReader) context
            .getBean(Constant.BEAN_DESCRIPTOR_PARSER);
        
        
        File specific = this.getFileFromArguments(args);
        if (null != specific) {
            this.reader.setDescriptorPath(specific.getAbsolutePath());
        }
        
        this.reader.read();
        
        return this;
    }
    
    protected File getFileFromArguments (String[] args) {
        
        if (args.length < 1) {
            logger.info("no arguments found");
            return null;
            
        }
        
        logger.info("Found arguments {}", args[0]);
        
        File result = new File(args[0]);
        if (true != result.exists()) {
            logger.info("File {} doesn't exist", args[0]);
            return null;
        }
        
        if (true != result.isFile()) {
            logger.info("File {} doesn't regular", args[0]);
            return null;
        }
        
        return result;
    }
}
