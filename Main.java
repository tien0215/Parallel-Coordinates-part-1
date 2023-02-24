import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main extends JFrame {

    private Vis contents;

    public Main() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000,700);
        setTitle("My First CS490R Program");
        contents = new Vis();
        setContentPane(contents);
        var abigail = createMenu();
        setJMenuBar(abigail);
        setVisible(true);
    }

    private JMenuBar createMenu() {
        var mb = new JMenuBar();
        var file = new JMenu("Table");
        var option1 = new JMenuItem("CIS 2012");
        var option3= new JMenuItem("Marathon");

        option1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Just clicked option 1");
                try {
                    Connection conn = DriverManager.getConnection("jdbc:derby:pollster");
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM cis2012");
                    ResultSetMetaData meta = rs.getMetaData();
                    int n = meta.getColumnCount();
                    ArrayList<Axis> axes = new ArrayList<>();
                    for (int i=1; i<=n; i++) {
                        Axis ax;
                        String label = meta.getColumnLabel(i);
                        int ctype = meta.getColumnType(i);
                        if (ctype == Types.CHAR || ctype == Types.VARCHAR) {
                            ax = new Axis(label, Axis.ColumnType.TEXT);
                        } else {
                            ax = new Axis(label, Axis.ColumnType.NUMERIC);
                        }
                        axes.add(ax);
                    }


                    while (rs.next()) {
                        int count=1;
                        for (var a: axes) {
                            //ask esther: what is your column label?
                            //ask esther: are you numeric or text?

                            if (a.type== Axis.ColumnType.NUMERIC){
                                a.numberData.add(rs.getDouble(count));
                                //store that into the axis object's numberdata array
                            }
                            if (a.type== Axis.ColumnType.TEXT){
                                a.stringData.add(rs.getString(count));
                                //store that into the axis object's stringdata array
                            }
                            count++;
                        }

                    }
                    contents.setData(axes);
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        JMenuItem option2 = new JMenuItem("CIS 2019");
        option2.addActionListener(e -> {
            try {
                Connection conn = DriverManager.getConnection("jdbc:derby:pollster");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM cis2019");
                ResultSetMetaData meta = rs.getMetaData();
                int n = meta.getColumnCount();
                ArrayList<Axis> axes = new ArrayList<>();
                for (int i=1; i<=n; i++) {
                    Axis ax;
                    String label = meta.getColumnLabel(i);
                    int ctype = meta.getColumnType(i);
                    if (ctype == Types.CHAR || ctype == Types.VARCHAR) {
                        ax = new Axis(label, Axis.ColumnType.TEXT);
                    } else {
                        ax = new Axis(label, Axis.ColumnType.NUMERIC);
                    }
                    axes.add(ax);
                }


                while (rs.next()) {
                    int count = 1;
                    for (var a : axes) {
                        //ask esther: what is your column label?
                        //ask esther: are you numeric or text?

                        if (a.type == Axis.ColumnType.NUMERIC) {
                            a.numberData.add(rs.getDouble(count));
                            //store that into the axis object's numberdata array
                        }
                        if (a.type == Axis.ColumnType.TEXT) {
                            a.stringData.add(rs.getString(count));
                            //store that into the axis object's stringdata array
                        }
                        count++;
                    }
                }
                contents.setData(axes);
                conn.close();
                //TODO have each axis calculate its min/max values
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        });
        option3.addActionListener(e -> {
            try {
                Connection conn = DriverManager.getConnection("jdbc:derby:pollster");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM marathon");
                ResultSetMetaData meta = rs.getMetaData();
                int n = meta.getColumnCount();
                ArrayList<Axis> axes = new ArrayList<>();
                for (int i=1; i<=n; i++) {
                    Axis ax;
                    String label = meta.getColumnLabel(i);
                    int ctype = meta.getColumnType(i);
                    if (ctype == Types.CHAR || ctype == Types.VARCHAR) {
                        ax = new Axis(label, Axis.ColumnType.TEXT);
                    } else {
                        ax = new Axis(label, Axis.ColumnType.NUMERIC);
                    }
                    axes.add(ax);
                }


                while (rs.next()) {
                    int count=1;
                    for (var a: axes) {
                        //ask esther: what is your column label?
                        //ask esther: are you numeric or text?

                        if (a.type== Axis.ColumnType.NUMERIC){
                            a.numberData.add(rs.getDouble(count));
                            //store that into the axis object's numberdata array
                        }
                        if (a.type== Axis.ColumnType.TEXT){
                            a.stringData.add(rs.getString(count));
                            //store that into the axis object's stringdata array
                        }
                        count++;
                    }

                }
                contents.setData(axes);
                conn.close();
                //TODO have each axis calculate its min/max values
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        });
        file.add(option1);
        file.add(option2);
        file.add(option3);
        mb.add(file);
        return mb;
    }

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}
