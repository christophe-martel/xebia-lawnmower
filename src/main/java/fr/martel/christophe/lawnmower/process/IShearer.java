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

package fr.martel.christophe.lawnmower.process;

import fr.martel.christophe.lawnmower.model.ILawn;
import fr.martel.christophe.lawnmower.model.ILawnMower;
import java.util.ArrayList;

/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
public interface IShearer {
    
    public boolean isFail ();
    
    public IShearer on (ILawn lawn);
    
    public IShearer push(ILawnMower lawnMower);
    
    public IShearer push(ArrayList<ILawnMower> lawnMowers);
    
    public IShearer run ();
    
    public ILawn getLawn ();
    
    public ArrayList<ILawnMower> getLawnMowers ();
    
    
}
