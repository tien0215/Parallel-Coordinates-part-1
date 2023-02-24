import org.apache.derby.iapi.services.io.ArrayOutputStream;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.awt.geom.Path2D;

public class Vis extends JPanel implements ActionListener, MouseInputListener {

    private String greeting;
    private Map<String, Double> assign;
    private List<Double> xValues, xRatios;
    private List<Double> yValues, yRatios;
    private Rectangle box;
    private Point mouseDown;
    private ArrayList<Axis> allAxis;
    private int size;


    public Vis() {
        addMouseListener(this);
        addMouseMotionListener(this);
        allAxis=new ArrayList<>();
        assign=new HashMap<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String sql = e.getActionCommand();
        try {
            Connection conn = DriverManager.getConnection("jdbc:derby:MyDbTest");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g2) {
        Graphics2D g1=(Graphics2D)g2;
        super.paintComponent(g1);
        int h=getHeight();
        int w=getWidth();
        int ly=50;
        int lx=120;
        int dx=0;
        int size;

        //drawing axis and labels

        if (allAxis.size()!=0) {
            if(allAxis.get(1).numberData.size()!=0){
                size=allAxis.get(1).numberData.size();
            }
           else{
               size=allAxis.get(1).stringData.size();
            }
            int dxx = (w - 2 * lx) / (allAxis.size()-1);
            for (var x : allAxis) {
                g1.drawLine(lx + dx, ly, lx + dx, h - ly);
                g1.drawString(x.columnName,dx+lx-10,h-(ly/2));
                if(x.type==Axis.ColumnType.NUMERIC){
                    double r=x.max-x.min;
                    for(int i=1;i<=5;i++){
                        g1.drawString(String.valueOf(Math.round(x.min+(r/4)*i)*100.00/100.00),(lx+dx),h-ly-((h-2*ly)/4*(i-1)));
                    }
                }
                else{
                    ArrayList<String> copy1=new ArrayList<>();
                    double count=1;
                    for(var c: x.stringData){
                        if(!copy1.contains(c)){
                            copy1.add(c);
                            assign.put(c,count);
                            count++;
                        }
                    }

                    for(int i=1;i<=copy1.size();i++){
                        g1.drawString(copy1.get(i-1),(lx+dx),h-ly-((h-2*ly)/(copy1.size()+1)*(i)));
                    }
                }
                dx += dxx;
            }
            ly=50;
            lx=120;
            dx=0;
         
            Path2D.Double p=new Path2D.Double();
            for(int j=0;j<size;j++) {
                for (int jj = 0; jj < allAxis.size(); jj++) {
                    Axis a1 = allAxis.get(jj);

                        if (jj == 0) {

                            if (a1.type == Axis.ColumnType.NUMERIC) {
                                Double y = h - ly - ((a1.numberData.get(j)-a1.min) / (a1.max - a1.min)) * (h - 2 * ly);
                                p.moveTo(lx + dx, y);
                            } else {
                                Double convert = assign.get(a1.stringData.get(j));
                                Double y = h - ly - (convert / (a1.max - a1.min)) * (h - 2 * ly);
                                p.moveTo(lx + dx, y);

                            }
                        } else {

                            if (a1.type == Axis.ColumnType.NUMERIC) {
                                Double y = h - ly - (((a1.numberData.get(j)-a1.min) / (a1.max - a1.min)) * (h - 2 * ly));
                                p.lineTo(lx + dx, y);

                            } else {
                                Double convert = assign.get(a1.stringData.get(j));
                                Double y = h - ly - (convert / (a1.max - a1.min)) * (h - 2 * ly);
                                p.lineTo(lx + dx, y);

                            }
                        }
                    dx += dxx;
                    }
                    g1.draw(p);
                    dx = 0;
                    p.closePath();
                }


        }

    }


    public void setData(ArrayList<Axis> myAx){
        for ( var x: myAx){
            if (x.type==Axis.ColumnType.TEXT) {
                x.min = 0;
                ArrayList<String> copy=new ArrayList<>();
                for(var c: x.stringData){
                    if(!copy.contains(c)){
                        copy.add(c);
                    }
                }
                x.max = copy.size()+1;
                // might change
            }
            else{
                double d=Double.MAX_VALUE;
                for (var n:x.numberData){
                    if (n>x.max){
                        x.max=n;
                    }
                }
                for (var n: x.numberData){
                    if(n<d){
                        d=n;
                    }
                }
                x.min=d;
            }
        }
        allAxis=myAx;
        repaint();

    }


    @Override
    public void mouseClicked(MouseEvent e) {
        //nevermind
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        box = null;
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //nevermind
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //nevermind
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
