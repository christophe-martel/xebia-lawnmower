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
package cma.xebia.lawnmower.controller.impl;



import cma.xebia.lawnmower.controller.IController;
import cma.xebia.lawnmower.business.entity.ILawn;
import cma.xebia.lawnmower.business.entity.ILawnMower;
import cma.xebia.lawnmower.business.entity.ILawnMowerBuilder;
import cma.xebia.lawnmower.business.entity.constants.CompassPoint;
import cma.xebia.lawnmower.business.entity.constants.Movement;
import cma.xebia.lawnmower.business.entity.lawn.Lawn;
import cma.xebia.lawnmower.business.service.IShearer;
import cma.xebia.lawnmower.utils.exception.LawnMowerException;
import cma.xebia.lawnmower.utils.file.ILawnMowerDesc;
import cma.xebia.lawnmower.utils.file.ILawnMowerDescReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
@Slf4j
public class LawnMowerController implements IController {
    
    @Getter
    private ILawnMowerDescReader reader = null;
    
    @Getter
    private IShearer shearer = null;
    
    @Getter
    private ILawnMowerBuilder builder = null;
    
    
    public LawnMowerController (
            @NonNull ILawnMowerDescReader reader,
            @NonNull ILawnMowerBuilder builder,
            @NonNull IShearer shearer) {
        this.reader = reader;
        this.builder = builder;
        this.shearer = shearer;
    }
    
    protected LawnMowerController () {
        log.info("start");
    }
    
    
    @Override
    public LawnMowerController init (String[] args) throws LawnMowerException {
        log.info("init");
        
        File specific = getFileFromArguments(args);
        if (null != specific) {
            getReader().setDescriptorPath(specific.getAbsolutePath());
        }
        
        getReader().read();
        
        return this;
    }
    
    
    @Override
    public LawnMowerController run () throws LawnMowerException {
        log.info("run");
        
        
        ILawn lawn = computeLawn();
        List<ILawnMower> lawnMowers = computeLawnMowers();
        
        shearer
            .init()
            .on(lawn)
            .use(lawnMowers)
            .mow();
        
        if (shearer.isFail()) {
            log.info("Oups, an error occurs ...");
            
        } else {
            log.info("Done");
            int i = -1;
            for (ILawnMower lm : shearer.getLawnMowers()) {
                log.info("lawn #{} is to position ({}x{}) and is in front of {}",
                    ++i,
                    lm.getX(),
                    lm.getY(),
                    lm.getInFrontOf());
                
            }
        }
        
        return this;
    }
    
    @Override
    public LawnMowerController finish () {
        log.info("end");
        
        return this;
    }
    
    protected ILawn computeLawn () throws LawnMowerException {
        log.info("create lawn");
        
        return (new Lawn())
            .setHeight(getReader().getLawn().getDimension().height)
            .setWidth(getReader().getLawn().getDimension().width)
        ;
    }
    
    
    protected List<ILawnMower> computeLawnMowers () throws LawnMowerException {
        log.info("create lawn mowers");
        List<ILawnMower> result = new ArrayList<>();
        
        for(ILawnMowerDesc desc : reader.getLawnMowers()) {
            result.add(getBuilder().create()
                .setX(desc.getPosition().x)
                .setY(desc.getPosition().y)
                .setInFrontOf(CompassPoint.valueOf(desc.getInFrontOf()))
                .setMovements(Movement.parseMovements(desc.getMovements())));
            
        }
        
        return result;
    }
    
    protected File getFileFromArguments (String[] args) {
        
        if (args.length < 1) {
            log.info("no arguments found");
            return null;
            
        }
        
        log.info("Found arguments {}", args[0]);
        
        File result = new File(args[0]);
        if (!result.exists()) {
            log.info("File {} doesn't exist", args[0]);
            return null;
        }
        
        if (!result.isFile()) {
            log.info("File {} doesn't regular", args[0]);
            return null;
        }
        
        return result;
    }
}
