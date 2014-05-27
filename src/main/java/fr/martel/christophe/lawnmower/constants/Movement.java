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
public enum Movement {
    D("Droite"),
    G("Gauche"),
    A("Avancer");
    
    
    @Getter
    private String label = "";
    
    Movement (String label) {
        this.label = label;
    }
    
    /**
     * 
     * @param name
     * @return NULL if not found
     */
    static Movement getByName (String name) {
        for(Movement m : Movement.values()){
            if (m.name().equals(name)) {
                return m;
            }
            
        }
        return null;
    }
    
    
    /**
     * 
     * @param c
     * @return NULL if not found
     */
    static Movement getByName (char c) {
        return Movement.getByName(Character.toString(c));
    }
    
}