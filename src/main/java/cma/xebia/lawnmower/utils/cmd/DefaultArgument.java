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

package cma.xebia.lawnmower.utils.cmd;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 * @param <T>
 */
public abstract class DefaultArgument<T> implements Argument<T> {
    
    
    @Accessors(chain = true)
    @Getter
    final private String name;
    
    protected boolean stop = false;
    
    protected boolean optionable = true;
    
    public DefaultArgument(@NonNull String name) {
        this.name = name;
    }
    
    @Override
    public boolean mustStop() {
        return stop;
    }
    
    @Override
    public boolean isCorrespondingTo(@NonNull String argument) {
        
        if (!this.optionable) {
            return argument.equals(String.format("--%s", this.name));
        }
        return argument.startsWith(String.format("--%s=", this.name));
    }
    
    protected String getDecodedArgument (@NonNull String argument) {
        String[] splitted = argument.split("=", 2);
        
        if (splitted.length < 1) {
            return "";
        }
        
        return splitted[1];
    }
    
}
