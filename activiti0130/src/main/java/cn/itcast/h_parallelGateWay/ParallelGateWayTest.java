package cn.itcast.h_parallelGateWay;

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

public class ParallelGateWayTest {

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    
    /**部署流程定义（inputStream）**/ 
    @Test
    public void deployProcessDefinition_inputStream(){
        InputStream inputStreamBpmn  = this.getClass().getResourceAsStream("parallelGateWay.bpmn");
        InputStream inputStreamPng  = this.getClass().getResourceAsStream("parallelGateWay.png  ");
        Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的service
                       .createDeployment()//创建一个部署对象
                       .name("并行网关")//添加的部署名称
                       .addInputStream("parallelGateWay.bpmn", inputStreamBpmn)
                       .addInputStream("parallelGateWay.png", inputStreamPng)
                       .deploy();//完成部署
        System.out.println("部署ID:"+deployment.getId());//
        System.out.println("部署名称："+deployment.getName());//
    }
    
    /**启动流程实例**/
    @Test
    public void startProcessInstance(){
        //流程定义的key
        String processDefinitionKey = "parallelGateWay";
        ProcessInstance processInstance = processEngine.getRuntimeService()//与正在执行的流程实例和流程对象相关的service
                     .startProcessInstanceByKey(processDefinitionKey);//使用流程定义的key启动流程实例，key对象helloworld.bpmn文件中的id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
        System.out.println("流程实例ID:"+processInstance.getId());//流程实例ID:101
        System.out.println("流程定义ID:"+processInstance.getProcessDefinitionId());//流程定义ID:helloworld:1:4
    }
    
    /**查询当前人的个人任务**/
    @Test
    public void findMyPersonalTask(){
        String assignee = "商家";
        List<Task> list = processEngine.getTaskService()
                        .createTaskQuery()
                        //查询条件where部分
//                        .taskAssignee(assignee)
//                        .processInstanceId(processInstanceId)//使用流程实例ID查询
//                        .processDefinitionId(processDefinitionId)//使用流程定义ID查询
//                        .taskCandidateUser(candidateUser)//组任务的办理人查询
//                        .executionId(executionId)//使用执行对象ID查询
                        //排序
                        .orderByTaskCreateTime().asc()//使用创建时间的升序排列
//                        .listPage(firstResult, maxResults)//分页查询
//                        .count()//返回结果集的数量
//                        .singleResult()//返回唯一结果集
                        .list();//返回列表
        if(list != null && list.size() >0) {
            for (Task task : list) {
                System.out.println("任务ID:"+task.getId());
                System.out.println("任务名称:"+task.getName());
                System.out.println("任务的创建时间:"+task.getCreateTime());
                System.out.println("任务的办理人:"+task.getAssignee());
                System.out.println("流程实例ID:"+task.getProcessInstanceId());
                System.out.println("执行对象ID:"+task.getExecutionId());
                System.out.println("流程定义ID:"+task.getProcessDefinitionId());
                System.out.println("#########################################");
            }
        }
    }
    
    /**完成我的任务**/
    @Test
    public void comMyPersonalTask() {
        String taskId = "4102";
//        Map<String,Object> variables =new HashMap<String,Object>();
//        variables.put("money", 200);
        processEngine.getTaskService()
                    .complete(taskId);
        System.out.println("完成任务，任务ID:"+taskId);
    }
    
    
}
