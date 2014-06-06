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

package cma.xebia.lawnmower.process;

import cma.xebia.lawnmower.model.constants.Movement;
import cma.xebia.lawnmower.model.ILawnMower;
import cma.xebia.lawnmower.model.ILawn;
import cma.xebia.lawnmower.process.commands.IAction;
import cma.xebia.lawnmower.process.validator.ILawnMowerValidator;
import cma.xebia.lawnmower.process.validator.ILawnValidator;
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
    
    private ILawnValidator      lawnValidator = null;
    private ILawnMowerValidator lawnMowerValidator = null;
    
    @Accessors(chain = true)
    @Getter
    @Setter(AccessLevel.PRIVATE)
    private ILawn lawn = null;
    
    @Accessors(chain = true)
    @Getter
    @Setter(AccessLevel.PRIVATE)
    private List<ILawnMower> lawnMowers = new ArrayList<>();
    
    
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
    @Setter(AccessLevel.PRIVATE)
    private List<String> errors = new ArrayList<>();
    
    public Shearer (
            @NonNull ILawnValidator lawnValidator,
            @NonNull ILawnMowerValidator lawnMowerValidator) {
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
    public IShearer on(ILawn lawn) {
        this.lawn = lawn;
        return this;
    }
    
    @Override
    public IShearer use(ILawnMower lawnMower) {
        if (true == this.lawnMowers.contains(lawnMower)) {
            return this;
        }
        this.lawnMowers.add(lawnMower);
        return this;
    }
    
    @Override
    public IShearer use(List<ILawnMower> lawnMowers) {
        for (ILawnMower lawnMower : lawnMowers) {
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
        for (ILawnMower lawnmower : this.lawnMowers) {
            log.info("verify lawn mowers #{}", ++i);
            if (true == this.lawnMowerValidator
                    .setLawnMower(lawnmower)
                    .isValid(lawn)) {
                continue;
            }
            
            this.setFail(true);
            this.errors.add(String.format(
                "lawn mowers #{} is out of bounds", i));
        }
        
        return this;
    }
    
    @Override
    public IShearer mow () {
        if (true == this.isMowed()) {
            log.info("already mowed");
            return this;
        }
        if (true != this.isValidated()) {
            log.info("automatic validation");
            this.validate();
        }
        this.setMowed(true);
        if (true == this.isFail()) {
            log.info("an error occurs...");
            return this;
            
        }
        this.setFail(true);
        try {
            
            this.calibrateLawnMower();
            
            log.info("mow law {}x{} ...", lawn.getWidth(), lawn.getHeight());
            int i = -1;
            for (ILawnMower lm : lawnMowers) {
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
        for (ILawnMower lawnmower : this.lawnMowers) {
            log.info("calibrate lawn mowers #{}", ++i);
            for (Map.Entry<Movement, IAction> entry : lawnmower.getCommands().getMovements().entrySet()) {
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
