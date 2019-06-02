/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import orm.entity.Constructor;
import orm.entity.Driver;

/**
 *
 * @author Riccardo Forese
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        F1_DAO_Interface f1 = new F1_DAO_Implements();
		
		Driver c = new Driver();
		c.setDriverId(1);
		
                System.out.println(f1.gareAnno(2010));
		
		System.out.println(f1.costruttorePilota(c, 2019));
                
                
                System.out.println(f1.puntiPilotaS(100, 2010));
		
		System.out.println(f1.classificaPilotiS(2010));

        System.out.println(f1.risultatoGara("Circuit de Monaco",2010).toString());
    }

}
