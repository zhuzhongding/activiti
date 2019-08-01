package cn.itcast.l_group;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class TaskTest {

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    
    /**部署流程定义（inputStream）**/ 
    @Test
    public void deployProcessDefinition_inputStream(){
        InputStream inputStreamBpmn  = this.getClass().getResourceAsStream("task.bpmn");
        InputStream inputStreamPng  = this.getClass().getResourceAsStream("task.png");
        Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的service
                       .createDeployment()//创建一个部署对象
                       .name("任务")//添加的部署名称
                       .addInputStream("task.bpmn", inputStreamBpmn)
                       .addInputStream("task.png", inputStreamPng)
                       .deploy();//完成部署
        System.out.println("部署ID:"+deployment.getId());//
        System.out.println("部署名称："+deployment.getName());//
    }
    
    /**启动流程实例**/
    @Test
    public void startProcessInstance(){
        //流程定义的key
        String processDefinitionKey = "task";
        /**启动流程实例的同时，设置流程变量，*/
//        Map<String,Object> variables = new HashMap<String,Object>();
//        variables.put("userId", "周芷若");
        ProcessInstance processInstance = processEngine.getRuntimeService()//与正在执行的流程实例和流程对象相关的service
                     .startProcessInstanceByKey(processDefinitionKey);//使用流程定义的key启动流程实例，key对象helloworld.bpmn文件中的id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
        System.out.println("流程实例ID:"+processInstance.getId());//流程实例ID:101
        System.out.println("流程定义ID:"+processInstance.getProcessDefinitionId());//流程定义ID:helloworld:1:4
    }
    
    /**查询当前人的个人任务**/
    @Test
    public void findMyPersonalTask(){
        String assignee = "大F";
        List<Task> list = processEngine.getTaskService()
                        .createTaskQuery()
                        //查询条件where部分
                        .taskAssignee(assignee)
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
    
    /**查询当前人的组任务**/
    @Test
    public void findMyGroupTask(){
        String candidateUser = "小A";
        List<Task> list = processEngine.getTaskService()
                        .createTaskQuery()
                        //查询条件where部分
//                        .taskAssignee(assignee)
//                        .processInstanceId(processInstanceId)//使用流程实例ID查询
//                        .processDefinitionId(processDefinitionId)//使用流程定义ID查询
                        .taskCandidateUser(candidateUser)//组任务的办理人查询
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
        String taskId = "7604";
//        Map<String,Object> variables =new HashMap<String,Object>();
//        variables.put("message", "重要");
        processEngine.getTaskService()
                    .complete(taskId);
        System.out.println("完成任务，任务ID:"+taskId);
    }
    
    /**查询正在执行的任务办理人表*/
    @Test
    public void findRunPersonTask() {
        String taskId = "7604";
        List<IdentityLink> list = processEngine.getTaskService()
                        .getIdentityLinksForTask(taskId);
        if(list != null && list.size() >0) {
            for (IdentityLink identityLink : list) {
                System.out.println(identityLink.getTaskId()+"   "+identityLink.getType()+"   "+identityLink.getProcessInstanceId()+"   "+identityLink.getUserId());
            }
        }
    }
    
    /**查询历史任务的办理人表*/
    @Test
    public void findHistoryPersonTask() {
        //流程实例
        String processInstanceId = "7601";
        List<HistoricIdentityLink> list = processEngine.getHistoryService()
                        .getHistoricIdentityLinksForProcessInstance(processInstanceId);
        if(list != null && list.size() >0) {
            for (HistoricIdentityLink historicIdentityLink : list) {
                System.out.println(historicIdentityLink.getTaskId()+"  "+historicIdentityLink.getType()+"  "+historicIdentityLink.getUserId()+"  "+historicIdentityLink.getProcessInstanceId());
            }
        }
    }
    
    /**拾取任务，将组任务分配给个人任务，指定任务的办理人字段*/
    @Test
    public void claim() {
        String taskId = "7604";
        String userId = "小A";
        //分配的个人任务（可以是组任务中的成员，也可以是非组任务的成员）
        processEngine.getTaskService()
                        .claim(taskId, userId);
    }
    
    /**将个人任务回退到组任务*/
    @Test
    public void setAssignee() {
        //任务
        String taskId = "7604";
        processEngine.getTaskService()
                        .setAssignee(taskId, null);
    }
    
    /**向组任务中添加成员*/
    @Test
    public void addGroupUser() {
        //任务
        String taskId = "7604";
        //成员办理人
        String userId = "大H";
        processEngine.getTaskService()
                        .addCandidateUser(taskId, userId);
    }
    
    /**从组任务中删除成员*/
    @Test
    public void deleteGroupUser() {
      //任务
      String taskId = "7604";
      //成员办理人
      String userId = "小B";
      processEngine.getTaskService()
                      .deleteCandidateUser(taskId, userId);
    }
    
}
