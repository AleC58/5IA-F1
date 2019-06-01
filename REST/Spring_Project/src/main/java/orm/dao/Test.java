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
		
		Constructor c = new Constructor();
		c.setConstructorId(1);
		
		System.out.println(f1.pilotiCostruttore(c, 2010).toString());

        System.out.println(f1.risultatoGara("Circuit de Monaco",2010).toString());
    }

}
