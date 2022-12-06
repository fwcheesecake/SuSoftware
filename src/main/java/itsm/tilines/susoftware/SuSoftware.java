/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package itsm.tilines.susoftware;

import org.jpl7.Compound;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.Variable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author Wadalupe
 */
public class SuSoftware extends JFrame {


    final static String MAINPANEL = "Panel principal", CARACTERISTICAS = "Panel de caracteristicas", CATEGORIA = "Panel de categoria", COMPLEJIDAD = "Panel de complejidad", SISTEMAS = "Panel de Sistemas Operatvos", RATING = "Panel de rating", PRICING = "Panel de pricing";
    JPanel mainPanel, leftButtons, leftPanel, rightPanel, leftButtonContainer, catPanel, carPanel, conPanel, soPanel, ratPanel, priPanel;
    JButton categoria, caracteristicas, complejidad, so, rating, pricing, back;
    CheckboxGroup carCheck, catCheck, conCheck, soCheck, ratCheck, priCheck;
    JTable softwareTable;
    JScrollPane scrollTable;
    ModeloDatos dataModel = new ModeloDatos();
    ArrayList<Object[]> data = new ArrayList<>();
    Object[] auxArr = {1, 2, 3, 4, 5, 6, 7};

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

        mainPanel = new JPanel();
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        leftButtons = new JPanel();

        catPanel = new JPanel();
        carPanel = new JPanel();
        conPanel = new JPanel();
        soPanel = new JPanel();
        ratPanel = new JPanel();
        priPanel = new JPanel();

        softwareTable = new JTable();

        scrollTable = new JScrollPane(softwareTable);

        categoria = new JButton("Categoria");
        caracteristicas = new JButton("Caracteristicas");
        complejidad = new JButton("Complejidad");
        so = new JButton("Sistema Operativo");
        rating = new JButton("Rating");
        pricing = new JButton("Pricing");

        getPrologData();

        carCheck = new CheckboxGroup();

        add(new Checkbox("one", carCheck, false));

        leftPanel.add(leftButtons);
        leftPanel.setLayout(new CardLayout());

        CardLayout cardLayout = (CardLayout) (leftPanel.getLayout());


        getContentPane().setLayout(new BorderLayout());

        mainPanel.setSize(screenSize);
        mainPanel.setLayout(new BorderLayout());


        //leftButtonContainer.setBorder(BorderFactory.createEmptyBorder(ERROR, WIDTH, ABORT, HEIGHT));
        leftButtons.setLayout(new GridLayout(6, 1));
        leftButtons.setPreferredSize(new Dimension(screenSize.width / 3, screenSize.height));
        leftButtons.add(categoria);
        leftButtons.add(caracteristicas);
        leftButtons.add(complejidad);
        leftButtons.add(so);
        leftButtons.add(rating);
        leftButtons.add(pricing);


        leftPanel.add(catPanel, "Categoria");
        leftPanel.add(carPanel, "Caracteristicas");
        leftPanel.add(conPanel, "Complejidad");
        leftPanel.add(soPanel, "Sistema Operativo");
        leftPanel.add(ratPanel, "Rating");
        leftPanel.add(priPanel, "Pricing");

        rightPanel.setLayout(new GridLayout(1, 1));
        rightPanel.setPreferredSize(new Dimension((screenSize.width / 3) * 2, screenSize.height));
        rightPanel.add(scrollTable);

        scrollTable.setMaximumSize(new Dimension((screenSize.width / 3) * 2, screenSize.height));

        softwareTable.setFillsViewportHeight(true);

        softwareTable.setModel(dataModel);
        //dataModel.data = ;

        getContentPane().add(mainPanel, BorderLayout.CENTER);
        mainPanel.add(leftPanel, BorderLayout.LINE_START);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        pack();

        categoria.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(leftPanel, "Categoria");
            }
        });
        caracteristicas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(leftPanel, "Caracteristicas");
            }
        });
        complejidad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(leftPanel, "Complejidad");
            }
        });
        so.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(leftPanel, "Sistema Operativo");
            }
        });
        rating.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(leftPanel, "Rating");
            }
        });
        pricing.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(leftPanel, "Pricing");
            }
        });

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
            System.out.println("X = " + solution.get("X"));
            Object[] tempData = {solution.get("X"), solution.get("A"), solution.get("B"), solution.get("C"), solution.get("D"), solution.get("E"), solution.get("F")};
            data.add(tempData);
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


}

