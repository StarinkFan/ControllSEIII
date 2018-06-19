/*package JUNIT;

import com.software2.demo.ResultMessage;
import com.software2.demo.bean.Description;
import com.software2.demo.bean.Label;
import com.software2.demo.service.Impl.LabelBLService;
import com.software2.demo.service.LabelBLImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.ArrayList;
import java.util.List;

*//**
* LabelBLImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 29, 2018</pre> 
* @version 1.0 
*//*
public class LabelBLImplTest {
LabelBLService labelBLService;
label l;
List<label> lis = new ArrayList<>();
List<String> ids = new ArrayList<>();
@Before
public void before() throws Exception {
    labelBLService = new LabelBLImpl();
    l = new label("TE-20180429-00000","test","test",new Description("test"),"test");
    lis.add(l);
    ids.add("test");
} 

@After
public void after() throws Exception { 
} 

*//**
* 
* Method: addLabel(label label) 
* 
*//*
@Test
public void testAddLabel() throws Exception { 
//TODO: Test goes here...
    Assert.assertEquals( ResultMessage.SUCCESS,labelBLService.addLabel(l,""));
} 

*//**
* 
* Method: searchMyLabel(String userID) 
* 
*//*
@Test
public void testSearchMyLabel() throws Exception { 
//TODO: Test goes here...
    Assert.assertEquals(lis,labelBLService.getSingleLabel("test"));
} 

*//**
* 
* Method: deleteLabel(List<String> labelIDs) 
* 
*//*
@Test
public void testDeleteLabel() throws Exception { 
//TODO: Test goes here...
    ResultMessage rs = labelBLService.deleteLabel(ids,"test");
    labelBLService.addLabel(l,"");
    Assert.assertEquals(ResultMessage.SUCCESS,rs);
}

*//**
* 
* Method: modifyLabel(label l) 
* 
*//*
@Test
public void testModifyLabel() throws Exception { 
//TODO: Test goes here...
    Assert.assertEquals(ResultMessage.SUCCESS,labelBLService.modifyLabel(l));
} 

*//**
* 
* Method: getSingleLabel(String id) 
* 
*//*
@Test
public void testGetSingleLabel() throws Exception { 
//TODO: Test goes here...
    Assert.assertEquals(ResultMessage.SUCCESS,labelBLService.getSingleLabel("test"));
} 


}*/
