public class MapGen {

    int[] biomemap;//biome map
    Block[][] blocks;//block block map
    int[] groundlvlmap;//ground level map
    int length, height;
    int[][] map;


    public MapGen(int size){
        switch (size){
            case 1:
                length=1000;
                height=120;
                break;
            case 2:
                length=2000;
                height=200;
                break;
            case 3:
                length=3000;
                height=300;
                break;
        }
        System.out.println("generating map");
        biomemap=new int[length];
        map=new int[length][height];
        blocks=new Block[length][height];
        groundlvlmap=new int[length];
        int cground=height/2;
        for (int x=0; x<length;x++){
            groundlvlmap[x]=cground;
            for (int y=0; y<height; y++){
                if (y>=cground){//at or below ground level
                    if (y<cground+1){//grass layer
                        map[x][y]=1;
                    }else {//sub grass
                        if (y<cground+(height/10)){//dirt layer
                            map[x][y]=4;
                        }else {//rock layer
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
            int dice=(int)(Math.random()*100);
            if (randChangeSize<30){
                if (cground<=height*.8) {
                    cground++;
                    if (dice<10){
                        cground++;
                    }
                }else {
                    cground--;
                    if (dice<10){
                        cground++;
                    }
                }
            }else if (randChangeSize<60){
                cground--;
                if (dice<10){
                    cground--;
                }
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
                        int dtbe=0;
                        boolean dtbefound=false;
                        while (!dtbefound){
                            if (dtbe>20){
                                dtbefound=true;
                            }else {
                                if (x-dtbe>0){
                                    if (biomemap[x-dtbe]==0&&biomemap[x+dtbe]==0){
                                        dtbe++;
                                    }else {
                                        dtbefound=true;
                                    }
                                }else {
                                    if (biomemap[x+dtbe]==0){
                                        dtbe++;
                                    }else {
                                        dtbefound = true;
                                        System.out.println(dtbe+" blocks from next biome");
                                    }
                                }
                            }

                        }
                        dtbe=(int)((double)dtbe*(Math.random()+1.0));
                        for (int i=1;i<1+dtbe; i++){
                            System.out.println("Placing sand");
                            map[x][y+i]=8;
                        }
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

    }

    public Block[][] getBlocks() { return blocks; }
    public int[][] getMap() { return map; }
    public int[] getBiomemap() { return biomemap; }
    public int[] getGroundlvlmap() { return groundlvlmap; }
    public int getHeight() { return height; }
    public int getLength() { return length; }
}