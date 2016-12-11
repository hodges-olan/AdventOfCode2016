/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Day11;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hodges-olan
 */
public class Day11 {
    
    public static void main(String args[]) {
        
        Map building = new HashMap();
        building = initializeBuilding(building);
        
    }

    private static Map initializeBuilding(Map building) {
        building.put("SG", "1");
        building.put("SM", "1");
        building.put("PG", "1");
        building.put("PM", "1");
        building.put("TG", "2");
        building.put("RG", "2");
        building.put("RM", "2");
        building.put("CG", "2");
        building.put("CM", "2");
        building.put("TM", "3");
        return building;
    }
    
}
