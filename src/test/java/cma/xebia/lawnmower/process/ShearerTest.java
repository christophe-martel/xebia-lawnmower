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

import cma.xebia.lawnmower.application.Main;
import cma.xebia.lawnmower.application.Constant;
import cma.xebia.lawnmower.model.constants.CompassPoint;
import cma.xebia.lawnmower.model.constants.Movement;
import cma.xebia.lawnmower.model.ILawn;
import cma.xebia.lawnmower.model.ILawnMower;
import cma.xebia.lawnmower.model.lawn.Lawn;
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
    
    private Main main = null;
    
    
    
    public ShearerTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        context = new ClassPathXmlApplicationContext(
                "/configuration/spring.xml",
                Main.class);
        
        main = ((Main) context.getBean(Constant.BEAN_MAIN));
        
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        ((ConfigurableApplicationContext) context).close();
    }
    
    
    public void testNonNull () {
        assertNotNull(main.getReader());
        assertNotNull(main.getBuilder());
        assertNotNull(main.getShearer());
    }
    
    /**
     * Test of run method, of class Shearer.
     */
    public void testRun() {
        
        
        IShearer shearer = main.getShearer();
        
        ILawnMowerDescReader r = main.getReader()
            .setDefaultResourcePath("/setup/lawnmower.desc")
            .setCharset("UTF-8")
            .read();
        
        ILawn lawn = (new Lawn())
            .setHeight(r.getLawn().getDimension().height)
            .setWidth(r.getLawn().getDimension().width)
        ;
        
        List<ILawnMower> lawnMowers = new ArrayList<>();
        for(ILawnMowerDesc desc : r.getLawnMowers()) {
            lawnMowers.add(main.getBuilder().create()
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
