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
package fr.martel.christophe.lawnmower.constants;

import lombok.Getter;

/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
public enum CompassPoint {
    N("North"),
    E("Est"),
    S("South"),
    W("West");
    
    @Getter
    private String label = "";
    
    CompassPoint (String label) {
        this.label = label;
    }
    
    /**
     * 
     * @param name
     * @return NULL if not found
     */
    static CompassPoint getByName (String name) {
        for(CompassPoint cp : CompassPoint.values()){
            if (cp.name().equals(name)) {
                return cp;
            }
            
        }
        return null;
    }
    
    /**
     * 
     * @param c
     * @return NULL if not found
     */
    static CompassPoint getByName (char c) {
        return CompassPoint.getByName(Character.toString(c));
    }
}