package sommarengine.tool;

import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class GridSort<T> {

    public interface Sort<T,Y,R> {
        R sort(T val1, Y val2);
    }

    private class Point {
        int x,y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Point setX(int x) {
            this.x = x;
            return this;
        }

        public Point setY(int y) {
            this.y = y;
            return this;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "x: " + x + " | y: " + y;
        }
    }

    private int gridSize = 0;

    private HashMap<Point, List<T>> items = new HashMap<>();

    public GridSort(int sizeOfGrid) {
        this.gridSize = sizeOfGrid;
    }

    private Set<Point> areas = new HashSet<>();

    private Set<Point> getAreas(Vector3fc pos, float radius) {
        areas.clear();
        int x = (int) (pos.x()/gridSize);
        int y = (int) (pos.y()/gridSize);
        //int z = (int) (pos.z/gridSize);
        int sx = (int) (pos.x()-radius)/gridSize;
        int sy = (int) (pos.y()-radius)/gridSize;
        int ex = (int) (pos.x()+radius)/gridSize;
        int ey = (int) (pos.y()+radius)/gridSize;
        Console.log("RANDIUS",radius,sx,sy,ex,ey,pos.x()+radius,pos.y()+radius);
        areas.add(new Point(x, y));

        for(int i = sx; i <= ex; i++) {
            for(int j = sy; j <= ey; j++) {
                Console.log("dist",i,j,pos.distance(i*gridSize,j*gridSize,0) < radius,pos.distance(i*gridSize,j*gridSize,0),radius);
                if(pos.distance(i*gridSize,j*gridSize,0) < radius) {
                    areas.add(new Point(i, j));
                    areas.add(new Point(i-1, j));
                    areas.add(new Point(i, j-1));
                    areas.add(new Point(i-1, j-1));
                }
            }
        }

        return areas;
    }
/*
    private void restore(Set<Vector2i> pos) {
        pos.forEach((p)->posPool.restore(p));
    }
*/
    public void add(T item, Vector3fc position, float radius) {
        Set<Point> areas = getAreas(position,radius);
        System.out.println("AREAS SIZE: " + areas.size());
        for (Point area: areas) {
            if(items.containsKey(area)) {
                items.get(area).add(item);
            } else {
                Console.log("adding",area);
                List<T> list = new ArrayList<>();
                list.add(item);
                items.put(area, list);
            }
        }
        System.out.println("get: " + items.containsKey(new Point(1,0)));
        //if(items.containsKey())

        //restore(areas);
    }

    private Point point = new Point(0,0);

    private Point toPoint(Vector3fc pos) {
        return point.setX((int) pos.x()).setY((int) pos.y());
    }

    public void add(T item, Vector3fc position, int width, int height) {

    }
    private Set<Point> grids = new HashSet<>();
    public List<T> getClosestItems(Vector3fc org,int width,int height, int numberOfItems, Sort<T,T, Integer> sort) {
        grids.clear();
        Point p = toPoint(org);
        grids.add(new Point(p.getX()/gridSize,p.getY()/gridSize));
        grids.add(new Point((p.getX()+width)/gridSize,(p.getY()+height)/gridSize));
        grids.add(new Point((p.getX()+width)/gridSize,(p.getY())/gridSize));
        grids.add(new Point((p.getX())/gridSize,(p.getY()+height)/gridSize));
        List<T> retList = new ArrayList<>();
        for (Point point : grids) {
            System.out.println("checking grid:" + point + " | " + items.containsKey(point));
            if(items.containsKey(point)) {
                List<T> gridList = items.get(point);
                for (int i = 0; i < gridList.size()-1 && i < numberOfItems; i++) {
                    int val = sort.sort(gridList.get(i),gridList.get(i+1));
                    if(val != -1)
                        retList.add(gridList.get(i+val));
                }
            }

        }
        return retList;
    }

    public void recalculate(T item, Vector3f position, int radius) {

    }

}
