package cn.itcast.i_start;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class StartTest {

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    
    /**部署流程定义（inputStream）**/ 
    @Test
    public void deployProcessDefinition_inputStream(){
        InputStream inputStreamBpmn  = this.getClass().getResourceAsStream("start.bpmn");
        InputStream inputStreamPng  = this.getClass().getResourceAsStream("start.png  ");
        Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的service
                       .createDeployment()//创建一个部署对象
                       .name("开始活动")//添加的部署名称
                       .addInputStream("start.bpmn", inputStreamBpmn)
                       .addInputStream("start.png", inputStreamPng)
                       .deploy();//完成部署
        System.out.println("部署ID:"+deployment.getId());//
        System.out.println("部署名称："+deployment.getName());//
    }
    
    /**启动流程实例**/
    @Test
    public void startProcessInstance(){
        //流程定义的key
        String processDefinitionKey = "start";
        ProcessInstance processInstance = processEngine.getRuntimeService()//与正在执行的流程实例和流程对象相关的service
                     .startProcessInstanceByKey(processDefinitionKey);//使用流程定义的key启动流程实例，key对象helloworld.bpmn文件中的id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
        System.out.println("流程实例ID:"+processInstance.getId());//流程实例ID:101
        System.out.println("流程定义ID:"+processInstance.getProcessDefinitionId());//流程定义ID:helloworld:1:4
        
        /**判断流程是否结束，查询正在执行的执行对象表*/
         ProcessInstance singleResult = processEngine.getRuntimeService()
                        .createProcessInstanceQuery()
                        .processInstanceId(processInstance.getId())
                        .singleResult();
         //说明流程实例结束了
        if(singleResult ==null) {
            HistoricProcessInstance singleResult2 = processEngine.getHistoryService()
                            .createHistoricProcessInstanceQuery()
                            .processInstanceId(processInstance.getId())
                            .singleResult();
            System.out.println(singleResult2.getId()+"   "+singleResult2.getStartTime()+"   "+singleResult2.getEndTime()+"   "+singleResult2.getDurationInMillis());
        }
        
    }
    
}
