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
package fr.martel.christophe.lawnmower.model.lawn;

import fr.martel.christophe.lawnmower.model.ILawn;
import lombok.AccessLevel;
import lombok.experimental.Accessors;
import lombok.Getter;
import lombok.Setter;


/**
 * 
 * @author Christophe Martel <mail.christophe.martel@gmail.com>tophe Martel <mail.christophe.martel@gmail.com>
 */
public class Lawn
        implements ILawn {
    @Accessors(chain = true)
    @Getter
    @Setter(AccessLevel.PACKAGE)
    private int width = 0;
    
    @Accessors(chain = true)
    @Getter
    @Setter(AccessLevel.PACKAGE)
    private int height = 0;
    
}
