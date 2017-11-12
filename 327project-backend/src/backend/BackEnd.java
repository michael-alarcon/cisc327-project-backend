/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

/**
 *
 * @author Michael Alarcon
 */
public class BackEnd {

    public static void run(String oldMasterAccountsFile, String mergedTSF, 
            String newMasterAccountsFile, String validAccountsFile) {
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 4) {
            run(args[0], args[1], args[2], args[3]);
        } else {
            System.out.println("ERROR");
        }
    }

}
