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

import cma.xebia.lawnmower.business.service.IShearer;
import cma.xebia.lawnmower.application.Main;
import cma.xebia.lawnmower.application.Constant;
import cma.xebia.lawnmower.business.entity.constants.CompassPoint;
import cma.xebia.lawnmower.business.entity.constants.Movement;
import cma.xebia.lawnmower.business.entity.ILawn;
import cma.xebia.lawnmower.business.entity.ILawnMower;
import cma.xebia.lawnmower.business.entity.lawn.Lawn;
import cma.xebia.lawnmower.controller.impl.LawnMowerController;
import cma.xebia.lawnmower.utils.file.ILawnMowerDesc;
import cma.xebia.lawnmower.utils.file.ILawnMowerDescReader;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
@Slf4j
public class ShearerTest extends TestCase {
    
    private ApplicationContext context = null;
    
    private LawnMowerController controller = null;
    
    
    
    public ShearerTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        context = new ClassPathXmlApplicationContext(
                "/configuration/spring.xml",
                Main.class);
        
        controller = ((LawnMowerController) context.getBean(Constant.BEAN_CONTROLLER));
        
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        ((ConfigurableApplicationContext) context).close();
    }
    
    
    public void testNonNull () {
        assertNotNull(controller.getReader());
        assertNotNull(controller.getBuilder());
        assertNotNull(controller.getShearer());
    }
    
    /**
     * Test of run method, of class Shearer.
     */
    public void testRun() {
        
        
        IShearer shearer = controller.getShearer();
        
        ILawnMowerDescReader r = controller.getReader()
            .setDefaultResourcePath("/setup/lawnmower.desc")
            .setCharset("UTF-8")
            .read();
        
        ILawn lawn = (new Lawn())
            .setHeight(r.getLawn().getDimension().height)
            .setWidth(r.getLawn().getDimension().width)
        ;
        
        List<ILawnMower> lawnMowers = new ArrayList<>();
        for(ILawnMowerDesc desc : r.getLawnMowers()) {
            lawnMowers.add(controller.getBuilder().create()
                .setX(desc.getPosition().x)
                .setY(desc.getPosition().y)
                .setInFrontOf(CompassPoint.valueOf(desc.getInFrontOf()))
                .setMovements(Movement.parseMovements(desc.getMovements())));
            
        }
        
        shearer
            .init()
            .on(lawn)
            .use(lawnMowers)
            .validate()
            .mow();
        
        assertEquals(false, shearer.isFail());
        
        assertEquals(2, shearer.getLawnMowers().size());
        assertEquals(lawnMowers.get(0), shearer.getLawnMowers().get(0));
        assertEquals(lawnMowers.get(1), shearer.getLawnMowers().get(1));
        
        assertEquals(1, shearer.getLawnMowers().get(0).getX());
        assertEquals(3, shearer.getLawnMowers().get(0).getY());
        assertEquals(CompassPoint.N, shearer.getLawnMowers().get(0).getInFrontOf());
        
        assertEquals(5, shearer.getLawnMowers().get(1).getX());
        assertEquals(1, shearer.getLawnMowers().get(1).getY());
        assertEquals(CompassPoint.E, shearer.getLawnMowers().get(1).getInFrontOf());
        
    }
    
    
}
