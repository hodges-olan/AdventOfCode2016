/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Day22;

/**
 *
 * @author 71100096
 */
public class storageNode {
    String name;
    int x;
    int y;
    int size;
    int used;
    int avail;
    int usePercentage;

    public storageNode(String[] node) {
        this.name = node[0];
        this.x = Integer.parseInt((node[0].split("-"))[1].replaceAll("x", ""));
        this.y = Integer.parseInt((node[0].split("-"))[2].replaceAll("y", ""));
        this.size = Integer.parseInt(node[1].replaceAll("[a-zA-Z]", ""));
        this.used = Integer.parseInt(node[2].replaceAll("[a-zA-Z]", ""));
        this.avail = Integer.parseInt(node[3].replaceAll("[a-zA-Z]", ""));
        this.usePercentage = Integer.parseInt(node[4].replaceAll("%", ""));
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }

    public int getAvail() {
        return avail;
    }

    public void setAvail(int avail) {
        this.avail = avail;
    }

    public int getUsePercentage() {
        return usePercentage;
    }

    public void setUsePercentage(int usePercentage) {
        this.usePercentage = usePercentage;
    }

    @Override
    public String toString() {
        return "storageNode{" + "name=" + name + ", x=" + x + ", y=" + y + ", size=" + size + ", used=" + used + ", avail=" + avail + ", usePercentage=" + usePercentage + '}';
    }
    
}
