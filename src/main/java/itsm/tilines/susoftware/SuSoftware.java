/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package itsm.tilines.susoftware;

import org.jpl7.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

/**
 * @author Wadalupe
 */
public class SuSoftware extends JFrame {
    final static String MAINPANEL = "Panel principal", CARACTERISTICAS = "Panel de caracteristicas", CATEGORIA = "Panel de categoria", COMPLEJIDAD = "Panel de complejidad", SISTEMAS = "Panel de Sistemas Operatvos", RATING = "Panel de rating", PRICING = "Panel de pricing";
    JPanel panelMain, panelLeftButtons, panelLeft, panelRight, leftButtonContainer, panelCategories, panelFeatures, panelComplexity, panelOS, panelRating, panelPricing;
    JButton btnCategories, btnFeatures, btnComplexity, btnOS, btnRating, btnPricing, btnBack, btnSearch, btnClearFilters;
    CheckboxGroup cbgFeatures, cbgCategories, cbgComplexity, cbgOS, cbgRating, cbgPricing;

    ArrayList<Checkbox> checkBoxesFeatures = new ArrayList<>(),
            checkBoxesCategories = new ArrayList<>(),
            checkBoxesComplexity = new ArrayList<>(),
            checkBoxesOS = new ArrayList<>(),
            checkBoxesRating = new ArrayList<>(),
            checkBoxesPricing = new ArrayList<>();

    JTable tableSoftware;
    JScrollPane scrollTableSoftware;
    ModeloDatos dataModel = new ModeloDatos();
    ArrayList<Object[]> data = new ArrayList<>();
    Object[] auxArr = {1, 2, 3, 4, 5, 6, 7};

    ArrayList<Software> softwares = new ArrayList<>();

    HashSet<String>[] tipeData = new HashSet[6];

    Dimension screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize();
    Term t = Term.textToTerm("consult('src/main/java/itsm/tilines/susoftware/Software.pl')");

    public SuSoftware() {
        try {
            // Set System L&F
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        }
        catch (UnsupportedLookAndFeelException | ClassNotFoundException | IllegalAccessException |
               InstantiationException e) {
            // handle exception
        }

        for(int i = 0; i < 6; i++)
            tipeData[i] = new HashSet<>();

        panelMain = new JPanel();
        panelLeft = new JPanel();
        panelRight = new JPanel();
        panelLeftButtons = new JPanel();

        panelCategories = new JPanel();
        panelFeatures = new JPanel();
        panelComplexity = new JPanel();
        panelOS = new JPanel();
        panelRating = new JPanel();
        panelPricing = new JPanel();

        tableSoftware = new JTable();

        scrollTableSoftware = new JScrollPane(tableSoftware, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        btnBack = new JButton("Regresar");
        btnSearch = new JButton("Buscar");
        btnClearFilters = new JButton("Borrar filtros");
        btnCategories = new JButton("Categoria");
        btnFeatures = new JButton("Caracteristicas");
        btnComplexity = new JButton("Complejidad");
        btnOS = new JButton("Sistema Operativo");
        btnRating = new JButton("Rating");
        btnPricing = new JButton("Pricing");

        cbgFeatures = new CheckboxGroup();
        cbgCategories = new CheckboxGroup();
        cbgComplexity = new CheckboxGroup();
        cbgRating = new CheckboxGroup();
        cbgPricing = new CheckboxGroup();
        cbgOS = new CheckboxGroup();

        getPrologData();

        addCheckBoxs(panelCategories, checkBoxesCategories, cbgCategories, tipeData[0]);
        addCheckBoxs(panelFeatures, checkBoxesFeatures, cbgFeatures,tipeData[1]);
        addCheckBoxs(panelComplexity, checkBoxesComplexity, cbgComplexity, tipeData[2]);
        addCheckBoxs(panelRating, checkBoxesRating, cbgRating, tipeData[3]);
        addCheckBoxs(panelPricing, checkBoxesPricing, cbgPricing, tipeData[4]);
        addCheckBoxs(panelOS, checkBoxesOS, cbgOS, tipeData[5]);

        cbgFeatures = new CheckboxGroup();

        panelLeft.setMaximumSize(new Dimension(screenSize.width / 3, screenSize.height));
        panelLeft.setPreferredSize(new Dimension(screenSize.width / 3, screenSize.height));
        panelLeft.setLayout(new CardLayout());

        CardLayout cardLayout = (CardLayout) (panelLeft.getLayout());


        getContentPane().setLayout(new BorderLayout());

        panelMain.setSize(screenSize);
        panelMain.setLayout(new BorderLayout());


        //leftButtonContainer.setBorder(BorderFactory.createEmptyBorder(ERROR, WIDTH, ABORT, HEIGHT));
        panelLeftButtons.setLayout(new GridLayout(6, 1));
        panelLeftButtons.setPreferredSize(new Dimension(screenSize.width / 3, screenSize.height));
        panelLeftButtons.add(btnCategories);
        panelLeftButtons.add(btnFeatures);
        panelLeftButtons.add(btnComplexity);
        panelLeftButtons.add(btnOS);
        panelLeftButtons.add(btnRating);
        panelLeftButtons.add(btnPricing);

        panelLeft.add(panelLeftButtons, MAINPANEL);

        panelCategories.setLayout(new BoxLayout(panelCategories, BoxLayout.Y_AXIS));
        panelLeft.add(panelCategories, CATEGORIA);

        panelFeatures.setLayout(new BoxLayout(panelFeatures, BoxLayout.Y_AXIS));
        panelLeft.add(panelFeatures, CARACTERISTICAS);

        panelComplexity.setLayout(new BoxLayout(panelComplexity, BoxLayout.Y_AXIS));
        panelLeft.add(panelComplexity, COMPLEJIDAD);

        panelOS.setLayout(new BoxLayout(panelOS, BoxLayout.Y_AXIS));
        panelLeft.add(panelOS, SISTEMAS);

        panelRating.setLayout(new BoxLayout(panelRating, BoxLayout.Y_AXIS));
        panelLeft.add(panelRating, RATING);

        panelLeft.add(panelPricing, PRICING);
        panelPricing.setLayout(new BoxLayout(panelPricing, BoxLayout.Y_AXIS));


        panelRight.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.ipady = 40;

        panelRight.setPreferredSize(new Dimension((screenSize.width / 3) * 2, screenSize.height));

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        panelRight.add(btnBack, c);

        c.gridy = 2;
        panelRight.add(btnSearch, c);

        c.gridy = 2;
        c.gridx = 1;
        panelRight.add(btnClearFilters, c);

        c.fill = GridBagConstraints.BOTH;
        c.weighty = 0.8;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 1;
        panelRight.add(scrollTableSoftware, c);

        scrollTableSoftware.setMaximumSize(new Dimension((screenSize.width / 3) * 2, screenSize.height));

        tableSoftware.setFillsViewportHeight(true);

        tableSoftware.setModel(dataModel);
        //dataModel.data = ;

        getContentPane().add(panelMain, BorderLayout.CENTER);
        panelMain.add(panelLeft, BorderLayout.LINE_START);
        panelMain.add(panelRight, BorderLayout.EAST);

        pack();

        btnClearFilters.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (CheckboxGroup checkboxGroup : Arrays.asList(cbgFeatures, cbgCategories, cbgComplexity, cbgOS, cbgPricing, cbgRating)) {
                    if(checkboxGroup.getSelectedCheckbox() != null)
                        checkboxGroup.setSelectedCheckbox(null);
                }
            }
        });
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelLeft, MAINPANEL);
            }
        });
        btnCategories.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelLeft, CATEGORIA);
            }
        });
        btnFeatures.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelLeft, CARACTERISTICAS);
            }
        });
        btnComplexity.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelLeft, COMPLEJIDAD);
                System.out.println("hola");
            }
        });
        btnOS.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelLeft, SISTEMAS);
            }
        });
        btnRating.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelLeft, RATING);
            }
        });
        btnPricing.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelLeft, PRICING);
            }
        });

    }

    private void search() {
        StringBuilder sbQuery = new StringBuilder();

        if(cbgCategories.getSelectedCheckbox() != null) {
            String s = cbgCategories.getSelectedCheckbox().getLabel();
            sbQuery.append("por_categoria(X, \'" + s + "\', A, B, C, D, E, F),");
        }
        if(cbgFeatures.getSelectedCheckbox() != null) {
            String s = cbgFeatures.getSelectedCheckbox().getLabel();
            sbQuery.append("por_caracteristicas(X, \'" + s + "\', A, B, C, D, E, F),");
        }
        if(cbgRating.getSelectedCheckbox() != null) {
            String s = cbgRating.getSelectedCheckbox().getLabel();
            sbQuery.append("por_rating(X, " + s + ", A, B, C, D, E, F),");
        }
        if(cbgOS.getSelectedCheckbox() != null) {
            String s = cbgOS.getSelectedCheckbox().getLabel();
            sbQuery.append("por_so(X, \'" + s + "\', A, B, C, D, E, F),");
        }
        if(cbgPricing.getSelectedCheckbox() != null) {
            String s = cbgPricing.getSelectedCheckbox().getLabel();
            sbQuery.append("por_precio(X, \'" + s + "\', A, B, C, D, E, F),");
        }
        if(cbgComplexity.getSelectedCheckbox() != null) {
            String s = cbgComplexity.getSelectedCheckbox().getLabel();
            sbQuery.append("por_complejidad(X, " + s + ", A, B, C, D, E, F),");
        }

        String query;
        if(sbQuery.isEmpty())
            query = sbQuery.append("software(X, A, B, C, D, E, F).").toString();
        else {
            query = sbQuery.toString();
            query = query.substring(0, query.length() - 1) + ".";
        }

        Query q4 = new Query(query);

        data.clear();
        softwares.clear();
        Map<String, Term> solution;
        while (q4.hasMoreSolutions()) {
            solution = q4.nextSolution();

            Object[] tempData = {
                    solution.get("X"),
                    solution.get("A"),
                    solution.get("B"),
                    solution.get("C"),
                    solution.get("D"),
                    solution.get("E"),
                    solution.get("F")
            };
            data.add(tempData);

            softwares.add(new Software(tempData));

            dataModel.data = data;
            dataModel.fireTableDataChanged();
        }
    }

    public static void main(String[] args) {
        SuSoftware SUS = new SuSoftware();
        SUS.setExtendedState(Frame.MAXIMIZED_BOTH);
        SUS.setVisible(true);
        SUS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SUS.setLocationRelativeTo(null);
    }

    public void getPrologData() {
        Query.hasSolution(t);
        Query q4 = new Query(
                new Compound("software",
                        new Term[]{
                                new Variable("X"),
                                new Variable("A"),
                                new Variable("B"),
                                new Variable("C"),
                                new Variable("D"),
                                new Variable("E"),
                                new Variable("F")
                        }
                )
        );
        Map<String, Term> solution;
        while (q4.hasMoreSolutions()) {
            solution = q4.nextSolution();

            Object[] tempData = {solution.get("X"), solution.get("A"), solution.get("B"), solution.get("C"), solution.get("D"), solution.get("E"), solution.get("F")};
            data.add(tempData);

            softwares.add(new Software(tempData));
            addTipeData(tempData);

            dataModel.data = data;
            dataModel.fireTableDataChanged();
        }
//            
//        Iterator<Map<String, Term>> solutionsIter = new Query("software(X,Y)");
//        while (solutionsIter.hasNext()) {
//            Map<String, Term> sol = solutionsIter.next();
//            System.out.println("\t Solution found (now asserting to block?): " + sol.toString());
//            Query.oneSolution(String.format("assertz(software(%s))", sol.get("X").toString()));
//        }
//        Query.hasSolution("listing(software/1)");
    }

    public void addTipeData(Object[] data) {
        String category = data[1].toString();
        tipeData[0].add(category.startsWith("\'") ? category.substring(1, category.length() - 1) : category);

        String features = data[2].toString();
        tipeData[1].add(features.startsWith("\'") ? features.substring(1, features.length() - 1) : features);

        String complexity = data[3].toString();
        tipeData[2].add(complexity.startsWith("\'") ? complexity.substring(1, complexity.length() - 1) : complexity);

        String pricing = data[4].toString();
        tipeData[3].add(pricing.startsWith("\'") ? pricing.substring(1, pricing.length() - 1) : pricing);

        String SO =  data[5].toString();
        tipeData[4].add(SO.startsWith("\'") ? SO.substring(1, SO.length() - 1) : SO);

        String castedOSS = data[6].toString();
        String[] oss = castedOSS.substring(1, castedOSS.length() - 1).split(",\\s");
        for(String o : oss)
            tipeData[5].add(o.charAt(0) == '\'' ? o.trim().substring(1, o.length() - 1) : o.trim());
    }

    public void addCheckBoxs(JPanel panel, ArrayList<Checkbox> checkBoxes, CheckboxGroup checkboxGroup, HashSet<String> set) {
        for(String s : set) {
            checkBoxes.add(new Checkbox(s, checkboxGroup, false));
            panel.add(checkBoxes.get(checkBoxes.size() - 1));
        }
    }
}

