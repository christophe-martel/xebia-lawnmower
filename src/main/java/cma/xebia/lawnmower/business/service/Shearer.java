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

package cma.xebia.lawnmower.business.service;

import cma.xebia.lawnmower.business.entity.constants.Movement;
import cma.xebia.lawnmower.business.entity.lawn.Lawn;
import cma.xebia.lawnmower.business.entity.lawnmower.LawnMower;
import cma.xebia.lawnmower.business.entity.lawnmower.commands.Action;
import cma.xebia.lawnmower.business.service.process.validator.LawnMowerValidator;
import cma.xebia.lawnmower.business.service.process.validator.LawnValidator;
import cma.xebia.lawnmower.utils.exception.LawnMowerException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;


/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
@Slf4j
public class Shearer implements IShearer {
    
    private LawnValidator      lawnValidator = null;
    private LawnMowerValidator lawnMowerValidator = null;
    
    @Accessors(chain = true)
    @Getter
    private Lawn lawn = null;
    
    @Accessors(chain = true)
    @Getter
    private List<LawnMower> lawnMowers = new ArrayList<>();
    
    
    @Accessors(chain = true)
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private boolean validated = false;
    
    @Accessors(chain = true)
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private boolean mowed = false;
    
    @Accessors(chain = true)
    @Getter
    @Setter(AccessLevel.PRIVATE)
    private boolean fail = false;
    
    @Accessors(chain = true)
    @Getter
    private List<String> errors = new ArrayList<>();
    
    public Shearer (
            @NonNull LawnValidator lawnValidator,
            @NonNull LawnMowerValidator lawnMowerValidator) {
        this.lawnValidator = lawnValidator;
        this.lawnMowerValidator = lawnMowerValidator;
        
    }
    
    @Override
    public IShearer init () {
        this.lawnMowerValidator.setLawnMower(null);
        this.fail = false;
        this.validated = false;
        this.mowed = false;
        this.lawnMowers = new ArrayList<>();
        this.lawn = null;
        this.errors.clear();
        
        return this;
    }
    
    @Override
    public IShearer on(Lawn lawn) {
        this.lawn = lawn;
        return this;
    }
    
    @Override
    public IShearer use(LawnMower lawnMower) {
        if (this.lawnMowers.contains(lawnMower)) {
            return this;
        }
        this.lawnMowers.add(lawnMower);
        return this;
    }
    
    @Override
    public IShearer use(List<LawnMower> lawnMowers) {
        for (LawnMower lawnMower : lawnMowers) {
            this.use(lawnMower);
            
        }
        return this;
    }

    @Override
    public IShearer validate () {
        if (this.isValidated()) {
            return this;
        }
        
        return this
            .setValidated(true)
            .verifyLawn()
            .verifyLawnMower();
    }
    
    
    
    protected Shearer verifyLawn () {
        log.info("verification of lawn");
        this.lawnValidator.isValid(lawn);
        
        return this;
    }
    
    protected Shearer verifyLawnMower () {
        log.info("verification of lawn mowers");
        int i = -1;
        for (LawnMower lawnmower : this.lawnMowers) {
            log.info("verify lawn mowers #{}", ++i);
            if (this.lawnMowerValidator
                    .setLawnMower(lawnmower)
                    .isValid(lawn)) {
                continue;
            }
            
            this.setFail(true);
            this.errors.add("lawn mowers #" + i + " is out of bounds");
        }
        
        return this;
    }
    
    @Override
    public IShearer mow () {
        if (this.isMowed()) {
            log.info("already mowed");
            return this;
        }
        if (!this.isValidated()) {
            log.info("automatic validation");
            this.validate();
        }
        this.setMowed(true);
        if (this.isFail()) {
            log.info("an error occurs...");
            return this;
            
        }
        this.setFail(true);
        try {
            
            this.calibrateLawnMower();
            
            log.info("mow law {}x{} ...", lawn.getWidth(), lawn.getHeight());
            int i = -1;
            for (LawnMower lm : lawnMowers) {
                log.info("with {} #{} and movements {}", lm, ++i, lm.getMovements());
                lm.getCommands().run();
                log.info("Lawn mower is now : {}", lm);
            }
            this.setFail(false);
        } catch (LawnMowerException ex) {
            log.error("error", ex);
            this.errors.add(ex.getMessage());
        }
        return this;
    }
    
    
    protected IShearer calibrateLawnMower () {
        log.info("calibration of lawn mowers");
        int i = -1;
        for (LawnMower lawnmower : this.lawnMowers) {
            log.info("calibrate lawn mowers #{}", ++i);
            for (Map.Entry<Movement, Action> entry : lawnmower.getCommands().getMovements().entrySet()) {
                entry
                    .getValue()
                    .getPositionValidator()
                    .setMaxWidth(this.lawn.getWidth())
                    .setMaxHeight(this.lawn.getHeight())
                ;
            }
        }
        
        return this;
    }
    
    
    
}
