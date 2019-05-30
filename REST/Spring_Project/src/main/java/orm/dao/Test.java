/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao;

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
        
        System.out.println(f1.classificaPilotiS(2010).toString());
    }
    
}
