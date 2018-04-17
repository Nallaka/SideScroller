import java.awt.*;
import java.util.ArrayList;

public class Map implements Sprite {

    int[] biomemap;//biome map
    Block[][] blocks;//block block map
    private Character p1;
    int length, height;
    int blockSize;
    Color caveLayer=new Color(153, 153, 102);

    public Map(int size){
        switch (size){
            case 1:
                length=1000;
                height=100;
                blockSize=50;
                break;
            case 2:
                length=2000;
                height=200;
                blockSize=40;
                break;
            case 3:
                length=3000;
                height=300;
                blockSize=30;
                break;
        }
        System.out.println("generating map");
        biomemap=new int[length];
        int[][] map=new int[length][height];
        blocks=new Block[length][height];
        int cground=height/2;
        for (int x=0; x<length;x++){
            for (int y=0; y<height; y++){
                if (y>=cground){
                    if (y<cground+2){
                        map[x][y]=1;
                    }else {
                        if (y<cground+(height/10)*2){
                            map[x][y]=4;
                        }else {
                            if (Math.random() * 100 < 20) {
                                map[x][y] = 3;
                            } else {
                                map[x][y] = 2;
                            }
                        }
                    }
                }
            }
            int randChangeSize=(int)(Math.random()*100);
            if (randChangeSize<30){
                cground++;
            }else if (randChangeSize<60){
                cground--;
            }
        }
        System.out.println("generating biome map");
        int cbiomelength=0;
        int cbiome=0;
        for (int x=0;x<length-1;x++){
            biomemap[x]=cbiome;
            cbiomelength++;
            if (Math.random()*100<2&&cbiomelength>50){
                cbiomelength=0;
                System.out.println("change biome");
                int newbiome=(int)(Math.random()*100);
                if (newbiome<40){
                    cbiome=1;
                }else if (newbiome<80){
                    cbiome=2;
                }else {
                    cbiome=0;
                }
            }
        }
        boolean adjacentHasTree=false;
        //add biome features
        System.out.println("Adding biome features");
        for (int x=0; x<length-1; x++) {
            if (biomemap[x] == 0) {
                for (int y=0; y<height-1; y++){
                    if (map[x][y]==1){
                        map[x][y]=8;
                    }
                }
                if (Math.random() * 100 < 20&&!adjacentHasTree) {
                    adjacentHasTree=true;
                    System.out.println("generating tree at" + x);
                    boolean hasGrass = false;
                    int groundLevel = 0;
                    for (int y = 0; y < height; y++) {
                        if (map[x][y] == 8) {
                            hasGrass = true;
                            groundLevel = y;
                            System.out.println("groundlevel: " + groundLevel);
                            break;
                        }
                    }
                    if (hasGrass) {//making cacti
                        int treeHeight = (int) (5 * Math.random()) + 1;
                        for (int y = groundLevel - 1; y >= groundLevel - treeHeight; y--) {
                            System.out.println("putting bark at: (" + x + ", " + y + ")");
                            if (y >= 0) {
                                map[x][y] = 11;
                            }
                        }
                    }
                }else {
                    adjacentHasTree=false;
                }
            }else if (biomemap[x] == 1) {
                if (Math.random() * 100 < 20&&!adjacentHasTree) {
                    adjacentHasTree=true;
                    System.out.println("generating tree at" + x);
                    boolean hasGrass = false;
                    int groundLevel = 0;
                    for (int y = 0; y < height; y++) {
                        if (map[x][y] == 1 || map[x][y] == 4) {
                            hasGrass = true;
                            groundLevel = y;
                            System.out.println("groundlevel: " + groundLevel);
                            break;
                        }
                    }
                    if (hasGrass) {
                        int treeHeight = (int) (15 * Math.random()) + 3;
                        for (int y = groundLevel - 1; y >= groundLevel - treeHeight; y--) {
                            System.out.println("putting bark at: (" + x + ", " + y + ")");
                            if (y >= 0)
                                map[x][y] = 5;
                        }
                        for (int treex = x - 2; treex < x + 3; treex++) {
                            for (int treey = groundLevel - treeHeight - 2; treey < groundLevel - treeHeight + 2; treey++) {
                                if (!(treex == x && treey >= groundLevel - treeHeight&&treey<=0)) {
                                    if (map[treex][treey] == 0) {
                                        map[treex][treey] = 6;
                                    }
                                }
                            }
                        }
                    }
                    x++;
                }else {
                    adjacentHasTree=false;
                }
            }else if(biomemap[x]==2){
                if (Math.random() * 100 < 20&&!adjacentHasTree) {
                    adjacentHasTree=true;
                    System.out.println("generating tree at" + x);
                    boolean hasGrass = false;
                    int groundLevel = 0;
                    for (int y = 0; y < height; y++) {
                        if (map[x][y] == 1 || map[x][y] == 4) {
                            hasGrass = true;
                            groundLevel = y;
                            System.out.println("groundlevel: " + groundLevel);
                            break;
                        }
                    }
                    if (hasGrass) {
                        int treeHeight = (int) (15 * Math.random()) + 3;
                        for (int y = groundLevel - 1; y >= groundLevel - treeHeight; y--) {
                            System.out.println("putting bark at: (" + x + ", " + y + ")");
                            if (y >= 0)
                                map[x][y] = 5;
                        }
                        for (int treex = x - 2; treex < x + 3; treex++) {
                            for (int treey = groundLevel - treeHeight - 2; treey < groundLevel - treeHeight + 2; treey++) {
                                if (!(treex == x && treey >= groundLevel - treeHeight)) {
                                    if (map[treex][treey] == 0) {
                                        map[treex][treey] = 10;
                                    }
                                }
                            }
                        }
                    }
                }else {
                    adjacentHasTree=false;
                }
                for (int y=0; y<height-1; y++){
                    if (map[x][y]==1){
                        map[x][y]=9;
                    }
                }
            }
        }
        System.out.println("generating clouds");
        for (int x=3; x<length-10; x++){
            if (Math.random()*100<20){
                int cloudheight=2+(int)(Math.random()*10);
                int cloudsize=5+(int)(Math.random()*10);
                for(int cloudx=x; cloudx<x+cloudsize;cloudx++){
                    for (int cloudy=cloudheight;cloudy<cloudheight+(cloudsize/2); cloudy++){
                        if (cloudx>0&&cloudx<length-1&&cloudy>0&&cloudy<height-1){
                            if (map[cloudx][cloudy]==0) {
                                map[cloudx][cloudy] = 7;
                            }
                        }
                    }
                }
                x+=20;
            }
        }
        p1=new Character();
        for (int y=0; y<height; y++){
            for (int x=0; x<length; x++){
                System.out.print(map[x][y]+" ");
                blocks[x][y]=new Block(x,y,map[x][y], blockSize);
            }
            System.out.println("");
        }
    }

    public Map(String fileloc, int type){//type indicates if your using a old map and new char, or new map and old char

    }

    public Map(String fileloc, String charfile){

    }

    public void draw(Graphics g) {
        if (p1.getRealy()<(height*.1)){
            g.setColor(Color.black);
        }else if(p1.getRealy()<height*.8){
            g.setColor(Color.cyan);
        }else {
            g.setColor(caveLayer);
        }

        g.fillRect(0,0,WIDTH,HEIGHT);
        for(int loadx=p1.getLoadXMin();loadx<p1.getLoadXMax();loadx++){
            for(int loady=p1.getLoadYMin(); loady<p1.getLoadYMax(); loady++){
                blocks[loadx][loady].draw(g,WIDTH/2+((loadx-(length/2))*blockSize)-p1.getX(), HEIGHT/2+((loady-(height/2))*blockSize)+p1.getY());
            }
        }
        p1.draw(g);
    }

    public void leftClick(int x, int y){
        System.out.println("block clicked");
        if (p1.selectedItemID==12){
            System.out.println("breaking block");
            blocks[getRealMouseX(x)][getRealMouseY(y)]=new Block(getRealMouseX(x),getRealMouseY(y),0,blockSize);
        }
    }

    public boolean isNotStandable(int x, int y){
        if ( blocks[x][y].isNotStandable()){ return true;
        }else { return false; }
    }

    public boolean isPassable(int x, int y){
        if ( blocks[x][y].isPassable()){ return true;
        }else { return false; }
    }

    public boolean isNotClimbable(int x, int y){
        if ( blocks[x][y].isNotClimbable()){ return true;
        }else { return false; }
    }

    public int getRealMouseX(int x){
        int realx=(int)(p1.getRealx()-((WIDTH/2)/blockSize)+(x/blockSize));
        return realx;
    }
    public int getRealMouseY(int y){
        int realy=(int)(p1.getRealy()-((HEIGHT/2)/blockSize)+(y/blockSize));
        return realy;
    }


    public void move() {
        p1.move(this);
    }
    public void setMovingRight(boolean input){p1.setMovingRight(input);}
    public void setMovingLeft(boolean input){p1.setMovingLeft(input);}
    public void setClimbing(boolean input){p1.setClimbing(input);}
    public void jump(){p1.jump();}

    public int getY() {
        return 0;
    }
    public int getX() {
        return 0;
    }
}