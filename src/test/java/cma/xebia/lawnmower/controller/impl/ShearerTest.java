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
import cma.xebia.lawnmower.business.entity.Dimensionable;
import cma.xebia.lawnmower.business.entity.Movable;
import cma.xebia.lawnmower.business.entity.Position;
import cma.xebia.lawnmower.business.entity.Positionable;
import cma.xebia.lawnmower.business.entity.constants.CompassPoint;
import cma.xebia.lawnmower.business.entity.constants.Movement;
import cma.xebia.lawnmower.business.entity.lawn.Lawn;
import cma.xebia.lawnmower.business.entity.obstacle.Obstacle;
import cma.xebia.lawnmower.utils.file.MovableDesc;
import cma.xebia.lawnmower.utils.file.DescReader;
import cma.xebia.lawnmower.utils.file.PositionDesc;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import static junit.framework.Assert.assertEquals;
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
        context = new ClassPathXmlApplicationContext(
                "/configuration/spring.xml",
                Main.class);
        
        controller = ((LawnMowerController) context.getBean(Constant.BEAN_CONTROLLER));
        
    }
    
    @Override
    protected void tearDown() throws Exception {
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
        
        DescReader r = controller.getReader()
            .setDefaultResourcePath("/setup/lawnmower.desc")
            .setCharset("UTF-8")
            .read();
        
        Dimensionable lawn = this.getPlayground(r);
        List<Positionable> obstacles = this.getObstacles(r);
        List<Movable> lawnMowers = this.getMovables(r);
        
        shearer
            .init()
            .on(lawn)
            .withObstacles(obstacles)
            .use(lawnMowers)
            .validate()
            .mow();
        
        assertEquals(false, shearer.isFail());
        
        assertEquals(0, shearer.getObstacles().size());
        
        assertEquals(2, shearer.getMovables().size());
        assertEquals(lawnMowers.get(0), shearer.getMovables().get(0));
        assertEquals(lawnMowers.get(1), shearer.getMovables().get(1));
        
        assertEquals(1, shearer.getMovables().get(0).getPosition().getX());
        assertEquals(3, shearer.getMovables().get(0).getPosition().getY());
        assertEquals(CompassPoint.N, shearer.getMovables().get(0).getPosition().getInFrontOf());
        
        assertEquals(5, shearer.getMovables().get(1).getPosition().getX());
        assertEquals(1, shearer.getMovables().get(1).getPosition().getY());
        assertEquals(CompassPoint.E, shearer.getMovables().get(1).getPosition().getInFrontOf());
        
    }
    
    
    /**
     * Test of run method, of class Shearer.
     */
    public void testRunWithObstacle() {
        
        
        IShearer shearer = controller.getShearer();
        
        DescReader r = controller.getReader()
            .setDefaultResourcePath("/setup/lawnmower+obstacles.desc")
            .setCharset("UTF-8")
            .read();
        
        Dimensionable lawn = this.getPlayground(r);
        List<Positionable> obstacles = this.getObstacles(r);
        List<Movable> lawnMowers = this.getMovables(r);
        
        shearer
            .init()
            .on(lawn)
            .withObstacles(obstacles)
            .use(lawnMowers)
            .validate()
            .mow();
        
        assertEquals(false, shearer.isFail());
        
        assertEquals(2, shearer.getObstacles().size());
        assertEquals(2, shearer.getObstacles().get(0).getPosition().getX());
        assertEquals(2, shearer.getObstacles().get(0).getPosition().getY());
        assertEquals(4, shearer.getObstacles().get(1).getPosition().getX());
        assertEquals(1, shearer.getObstacles().get(1).getPosition().getY());
        
        
        assertEquals(2, shearer.getMovables().size());
        assertEquals(lawnMowers.get(0), shearer.getMovables().get(0));
        assertEquals(lawnMowers.get(1), shearer.getMovables().get(1));
        
        assertEquals(1, shearer.getMovables().get(0).getPosition().getX());
        assertEquals(3, shearer.getMovables().get(0).getPosition().getY());
        assertEquals(CompassPoint.N, shearer.getMovables().get(0).getPosition().getInFrontOf());
        
        assertEquals(5, shearer.getMovables().get(1).getPosition().getX());
        assertEquals(1, shearer.getMovables().get(1).getPosition().getY());
        assertEquals(CompassPoint.E, shearer.getMovables().get(1).getPosition().getInFrontOf());
        
    }
    
    protected Dimensionable getPlayground (DescReader reader) {
        return new Lawn(
            reader.getLawn().getDimension().width,
            reader.getLawn().getDimension().height);
    }
    
    protected List<Positionable> getObstacles (DescReader reader) {
        List<Positionable> result = new ArrayList<>();
        
        for(PositionDesc desc : reader.getObstacles()) {
            
            result.add((new Obstacle())
                .setX(desc.getPosition().x)
                .setY(desc.getPosition().y))
            ;
            
        }
        
        
        return result;
        
    }
    
    protected List<Movable> getMovables (DescReader reader) {
        List<Movable> result = new ArrayList<>();
        
        for(MovableDesc desc : reader.getLawnMowers()) {
            result.add(controller.getBuilder().create()
                .program(Movement.parseMovements(desc.getMovements()))
                .moveTo(
                    (new Position())
                        .setX(desc.getPosition().x)
                        .setY(desc.getPosition().y)
                        .setInFrontOf(CompassPoint.valueOf(desc.getInFrontOf())))
                );
            
        }
        
        return result;
    }
}
