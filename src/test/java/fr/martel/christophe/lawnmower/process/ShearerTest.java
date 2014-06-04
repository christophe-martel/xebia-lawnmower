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

import fr.martel.christophe.lawnmower.application.Main;
import fr.martel.christophe.lawnmower.constants.Application;
import fr.martel.christophe.lawnmower.constants.CompassPoint;
import fr.martel.christophe.lawnmower.constants.Movement;
import fr.martel.christophe.lawnmower.model.ILawn;
import fr.martel.christophe.lawnmower.model.ILawnMower;
import fr.martel.christophe.lawnmower.utils.file.ILawnMowerDesc;
import fr.martel.christophe.lawnmower.utils.file.ILawnMowerDescReader;
import fr.martel.christophe.lawnmower.utils.file.LawnMowerDescReader;
import java.util.ArrayList;
import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
public class ShearerTest extends TestCase {
    
    
    public ShearerTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    /**
     * Test of run method, of class Shearer.
     */
    public void testRun() {
        
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "/configuration/spring.xml",
                Main.class);
        
        IShearer shearer = ((IShearer) context
            .getBean(Application.BEAN_SHEARER));
        
        ILawnMowerDescReader r = (new LawnMowerDescReader())
            .setDefaultResourcePath("/setup/lawnmower.desc")
            .setCharset("UTF-8")
            .read();
        
        ILawn lawn = ((ILawn) context
            .getBean(Application.BEAN_LAWN))
            .setHeight(r.getLawn().getDimension().height)
            .setWidth(r.getLawn().getDimension().width)
        ;
        
        ArrayList<ILawnMower> lawnMowers = new ArrayList<>();
        for(ILawnMowerDesc desc : r.getLawnMowers()) {
            lawnMowers.add(
                ((ILawnMower) context
                    .getBean(Application.BEAN_LAWN_MOWER))
                .setX(desc.getPosition().x)
                .setY(desc.getPosition().y)
                .setInFrontOf(CompassPoint.getByName(desc.getInFrontOf()))
                .setMovements(Movement.parse(desc.getMovements())));
            
        }
        
        shearer
            .on(lawn)
            .push(lawnMowers)
            .run();
        
        assertEquals(false, shearer.isFail());
        
        assertEquals(2, shearer.getLawnMowers().size());
        
        assertEquals(1, shearer.getLawnMowers().get(0).getX());
        assertEquals(3, shearer.getLawnMowers().get(0).getY());
        assertEquals(CompassPoint.N, shearer.getLawnMowers().get(0).getInFrontOf());
        
        assertEquals(5, shearer.getLawnMowers().get(1).getX());
        assertEquals(1, shearer.getLawnMowers().get(1).getY());
        assertEquals(CompassPoint.E, shearer.getLawnMowers().get(1).getInFrontOf());
        
    }
    
    
}
