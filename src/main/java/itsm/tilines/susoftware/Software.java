/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itsm.tilines.susoftware;

import javax.swing.*;
import java.util.ArrayList;

/**
 * @author Wadalupe
 */
public class Software {
    String name;
    String tag;
    String features;
    String complexity;
    double rating;
    String pricing;
    ArrayList<String> os;

    public Software(Object[] softwares) {
        name = softwares[0].toString();
        tag = softwares[1].toString();
        features = softwares[2].toString();
        complexity = softwares[3].toString();
        rating = Double.parseDouble(softwares[4].toString());
        pricing = softwares[5].toString();
        os = new ArrayList<>();

        String castedOSS = softwares[6].toString();
        String[] oss = castedOSS.substring(1, castedOSS.length() - 1).split(",\\s");
        for(String o : oss)
            os.add(o.charAt(0) == '\'' ? o.trim().substring(1, o.length() - 1) : o.trim());
    }
}
