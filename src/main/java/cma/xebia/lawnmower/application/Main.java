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
package cma.xebia.lawnmower.application;

import cma.xebia.lawnmower.controller.IController;
import cma.xebia.lawnmower.utils.exception.LawnMowerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
@Slf4j
public class Main {
    
    private Main () {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main (String[] args) {
        log.debug("start application");
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "/configuration/spring.xml",
                Main.class);
        
        try {
            ((IController) context.getBean(Constant.BEAN_CONTROLLER))
                .init(args)
                .run()
                .finish();
        } catch (LawnMowerException lme) {
            log.error("Oups", lme);
            
        }
        
    }
    
}
