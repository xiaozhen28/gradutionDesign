package tools;

/**
 * @author zhenjie
 * @version 1.0.0
 * @ClassName face.java
 * @Description TODO
 * @createTime 2022年04月26日 11:51:00
 */
public class Face {
    public int vN; //面的顶点数
    public int[] vI; //面的顶点索引

    //设置面的顶点数
    public void SetNum(int en){
        vN=en;
        vI=new int[vN];
    }


}
