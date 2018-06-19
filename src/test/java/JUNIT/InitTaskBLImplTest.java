/*
package JUNIT;

import com.software2.demo.ResultMessage;
import com.software2.demo.bean.InitTask;
import com.software2.demo.service.Impl.InitTaskBLService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

*/
/**
* InitTaskBLImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 29, 2018</pre> 
* @version 1.0 
*//*

public class InitTaskBLImplTest { 
InitTaskBLService initTaskBLService = new InitTaskBLService();
InitTask initTask1,initTask2,initTask3;
File f;
List<FileInputStream> lis = new ArrayList<>();
List<InitTask> res = new ArrayList<>();
List<InitTask> mylis = new ArrayList<>();
@Before
public void before() throws Exception {
    f = new File("E:/Test1.jpg");
    lis = new ArrayList<>();
    try {
        FileInputStream fileInputStream = new FileInputStream(f);
        lis.add(fileInputStream);
        initTask1 = new InitTask("TE-20180429-00000", new ArrayList<>(), "test", "test", "test", 0, 0, 0, "test", new ArrayList<>(), 0,0,20180501, 2);
        initTask2 = new InitTask("TE-20180429-00001", new ArrayList<>(), "test", "test", "test", 0, 0, 0, "test", new ArrayList<>(), 0,0,20180501, 0);
        initTask3 = new InitTask("TE-20180429-00002", new ArrayList<>(), "test", "test", "test", 0, 0, 0, "test", new ArrayList<>(), 0,0,20180501, 1);
        res.add(initTask1);
        res.add(initTask2);
        res.add(initTask3);
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
} 

@After
public void after() throws Exception { 
} 

*/
/**
* 
* Method: addITask(InitTask i)
* 
*//*

@Test
public void test01() throws Exception {
//TODO: Test goes here

    ResultMessage rs = ResultMessage.FAILED;
    if(initTaskBLService.addITask(initTask1).equals("IT-20180429-00000")){
        rs = ResultMessage.SUCCESS;
    }
    Assert.assertEquals(ResultMessage.SUCCESS,rs);

}
    */
/**
     *
     * Method: addITask(InitTask i)
     *
     *//*

    @Test
    public void test02() throws Exception {
//TODO: Test goes here
        ResultMessage rs = ResultMessage.FAILED;
        if(initTaskBLService.addITask(initTask2).equals("IT-20180429-00001")){
            rs = ResultMessage.SUCCESS;
        }
        Assert.assertEquals(ResultMessage.SUCCESS,rs);

    }
    */
/**
     *
     * Method: addITask(InitTask i)
     *
     *//*

    @Test
    public void test03() throws Exception {
//TODO: Test goes here

        ResultMessage rs = ResultMessage.FAILED;
        if(initTaskBLService.addITask(initTask3).equals("IT-20180429-00002")){
            rs = ResultMessage.SUCCESS;
        }
        Assert.assertEquals(ResultMessage.SUCCESS,rs);

    }

*/
/**
* 
* Method: getSingleITask(String id) 
* 
*//*

@Test
public void test04() throws Exception {
//TODO: Test goes here.
    Assert.assertEquals("IT-20180429-00000",initTaskBLService.getSingleITask("IT-20180429-00000").getID());
}

    */
/**
     *
     * Method: getSingleITask(String id)
     *
     *//*

    @Test
    public void test05() throws Exception {
//TODO: Test goes here.
        Assert.assertEquals("IT-20180429-00001",initTaskBLService.getSingleITask("IT-20180429-00001").getID());
    }


    */
/**
     *
     * Method: getSingleITask(String id)
     *
     *//*

    @Test
    public void test06() throws Exception {
//TODO: Test goes here.
        Assert.assertEquals("IT-20180429-00002",initTaskBLService.getSingleITask("IT-20180429-00002").getID());
    }

*/
/**
* 
* Method: getAll() 
* 
*//*

@Test
public void test07() throws Exception {
//TODO: Test goes here...
    Assert.assertEquals("IT-20180429-00000",initTaskBLService.getAll().get(0).getID());
} 

*/
/**
* 
* Method: getUser(String userID) 
* 
*//*

@Test
public void test08() throws Exception {
//TODO: Test goes here...
    Assert.assertEquals(null,null);
} 

*/
/**
* 
* Method: getUndone(String userID) 
* 
*//*

@Test
public void test09() throws Exception {
//TODO: Test goes here...
    Assert.assertEquals(null,null);
} 

*/
/**
* 
* Method: getDone(String userID) 
* 
*//*

@Test
public void test10() throws Exception {
//TODO: Test goes here...
    Assert.assertEquals(null,null);
} 

*/
/**
* 
* Method: getUnderGoing(String uid) 
* 
*//*

@Test
public void test11() throws Exception {
//TODO: Test goes here...
    Assert.assertEquals(null,null);
} 

*/
/**
* 
* Method: getUndergoing() 
* 
*//*

@Test
public void test12() throws Exception {
//TODO: Test goes here..
    Assert.assertEquals("IT-20180429-00000",initTaskBLService.getUndergoing().get(0).getID());
} 

*/
/**
* 
* Method: getUncomplete() 
* 
*//*

@Test
public void test13() throws Exception {
//TODO: Test goes here...
    Assert.assertEquals("IT-20180429-00001",initTaskBLService.getUncomplete().get(0).getID());
} 

*/
/**
* 
* Method: getComplete() 
* 
*//*

@Test
public void test14() throws Exception {
//TODO: Test goes here...
    System.out.println(initTaskBLService.getComplete().get(0).getID());
    Assert.assertEquals("IT-20180429-00002",initTaskBLService.getComplete().get(0).getID());
} 

*/
/**
* 
* Method: setState(InitTask i) 
* 
*//*

@Test
public void test15() throws Exception {
//TODO: Test goes here...
    initTask1.setID("IT-20180429-00000");
    Assert.assertEquals(ResultMessage.SUCCESS,initTaskBLService.setState(initTask1));
} 

*/
/**
* 
* Method: setRemarks(InitTask i) 
* 
*//*

@Test
public void test16() throws Exception {
//TODO: Test goes here...
    Assert.assertEquals(ResultMessage.SUCCESS,initTaskBLService.setRemarks(initTask1));
} 


} 
*/
