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



import cma.xebia.lawnmower.business.entity.Dimensionable;
import cma.xebia.lawnmower.business.entity.Movable;
import cma.xebia.lawnmower.business.entity.Positionable;
import cma.xebia.lawnmower.controller.IController;
import cma.xebia.lawnmower.business.entity.lawnmower.LawnMowerBuilder;
import cma.xebia.lawnmower.business.entity.constants.CompassPoint;
import cma.xebia.lawnmower.business.entity.constants.Movement;
import cma.xebia.lawnmower.business.entity.lawn.Lawn;
import cma.xebia.lawnmower.business.entity.obstacle.Obstacle;
import cma.xebia.lawnmower.business.service.IShearer;
import cma.xebia.lawnmower.utils.cmd.Argument;
import cma.xebia.lawnmower.utils.exception.LawnMowerException;
import cma.xebia.lawnmower.utils.file.MovableDesc;
import cma.xebia.lawnmower.utils.file.DescReader;
import cma.xebia.lawnmower.utils.file.PositionDesc;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
    final private Set<Argument<LawnMowerController>> arguments;
    
    @Getter
    final private Argument<LawnMowerController> help;
    
    @Getter
    final private DescReader reader;
    
    @Getter
    final private IShearer shearer;
    
    @Getter
    final private LawnMowerBuilder builder;
    
    private boolean doNotRun = false;
    
    public LawnMowerController (
            @NonNull Set<Argument<LawnMowerController>> arguments,
            @NonNull Argument<LawnMowerController> help,
            @NonNull DescReader reader,
            @NonNull LawnMowerBuilder builder,
            @NonNull IShearer shearer) {
        this.arguments = arguments;
        this.help = help;
        this.reader = reader;
        this.builder = builder;
        this.shearer = shearer;
    }
    
    @Override
    public LawnMowerController init (String[] args) throws LawnMowerException {
        log.info("init");
        
        boolean argumentsAreCorrect = true;
        for (String arg : args) {
            argumentsAreCorrect &= this.parseArgument(arg);
            
        }
        
        if (!argumentsAreCorrect) {
            this.help.applyOn("", this);
            this.doNotRun = true;
            return this;
        }
        
        this.getReader().read();
        
        return this;
    }
    
    protected boolean parseArgument (String argument) {
        
        for (Argument<LawnMowerController> a : this.arguments) {
            if (!a.isCorrespondingTo(argument)) {
                continue;
            }
            if (a.applyOn(argument, this).mustStop()) {
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public LawnMowerController run () throws LawnMowerException {
        if (this.doNotRun) {
            log.debug("do not run");
            return this;
            
        }
        
        log.info("run");
        
        
        Dimensionable lawn = this.computeLawn();
        List<Movable> lawnMowers = this.computeLawnMowers();
        List<Positionable> obstacle = this.computeObstacles();
        
        shearer
            .init()
            .on(lawn)
            .withObstacles(obstacle)
            .use(lawnMowers)
            .mow();
        
        if (shearer.isFail()) {
            log.info("Oups, an error occurs ...");
            
        } else {
            log.info("Done");
            int i = -1;
            for (Positionable positionable : shearer.getMovables()) {
                log.info("lawn #{} is to position ({}x{}) and is in front of {}",
                    ++i,
                    positionable.getPosition().getX(),
                    positionable.getPosition().getY(),
                    positionable.getPosition().getInFrontOf());
                
            }
        }
        
        return this;
    }
    
    @Override
    public LawnMowerController finish () {
        if (this.doNotRun) {
            log.debug("do not run");
            return this;
            
        }
        log.info("end");
        
        return this;
    }
    
    protected Dimensionable computeLawn () throws LawnMowerException {
        log.info("create lawn");
        
        return new Lawn(
            getReader().getLawn().getDimension().width,
            getReader().getLawn().getDimension().height)
        ;
    }
    
    protected List<Positionable> computeObstacles () {
        log.info("create obstacles");
        List<Positionable> result = new ArrayList<>();
        
        for(PositionDesc desc : reader.getObstacles()) {
            
            result.add((new Obstacle())
                .setX(desc.getPosition().x)
                .setY(desc.getPosition().y))
            ;
            
        }
        
        
        return result;
    }
    
    protected List<Movable> computeLawnMowers () throws LawnMowerException {
        log.info("create lawn mowers");
        List<Movable> result = new ArrayList<>();
        
        for(MovableDesc desc : reader.getLawnMowers()) {
            result.add(getBuilder()
                .create(
                    desc.getPosition().x,
                    desc.getPosition().y,
                    CompassPoint.valueOf(desc.getInFrontOf()))
                .program(Movement.parseMovements(desc.getMovements()))
            );
            
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
