package cn.itcast.d_processVariables;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

public class ProcessVariablesTest {

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    
    /**部署流程定义**/
    @Test
    public void deployProcessDefinition_inputStream(){
        InputStream inputStreambpmn = this.getClass().getResourceAsStream("/diagrams/processVariables.bpmn");
        InputStream InputStreampng  = this.getClass().getResourceAsStream("/diagrams/processVariables.png");
        Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的service
                       .createDeployment()//创建一个部署对象
                       .name("流程定义")//添加的部署名称
                       .addInputStream("processVariables.bpmn", inputStreambpmn)
                       .addInputStream("processVariables.png", InputStreampng)
                       .deploy();//完成部署
        System.out.println("部署ID:"+deployment.getId());//1
        System.out.println("部署名称："+deployment.getName());//helloworld入门程序
    }
    
    /**启动流程实例**/
    @Test
    public void startProcessInstance(){
        //流程定义的key
        String processDefinitionKey = "processVariables";
        ProcessInstance processInstance = processEngine.getRuntimeService()//与正在执行的流程实例和流程对象相关的service
                     .startProcessInstanceByKey(processDefinitionKey);//使用流程定义的key启动流程实例，key对象helloworld.bpmn文件中的id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
        System.out.println("流程实例ID:"+processInstance.getId());//流程实例ID:101
        System.out.println("流程定义ID:"+processInstance.getProcessDefinitionId());//流程定义ID:helloworld:1:4
    }
    
    /**设置流程变量*/
    @Test
    public void setVariables() {
        /**与任务（正在执行）*/
        TaskService taskService = processEngine.getTaskService();
        //任务ID
        String taskId = "2004";
        /**设置流程变量，使用基本数据类型*/
//        taskService.setVariableLocal(taskId, "请假天数", 3);
//        taskService.setVariable(taskId, "请假时间", new Date());
//        taskService.setVariable(taskId, "请假原因", "回家探亲");
        /**设置流程变量，使用javabean类型*/
        /**
         * 当一个javabean(实现序列号)放置到流程变量中，要求javabean的属性值不变
         * 如果发生改变，再获取值的时候会抛出异常
         * 解决方案：添加序列化ID：private static final long serialVersionUID = 3816444255375694117L;
         * 同时实现Serializable
         * 
         */
        Person person = new Person();
        person.setId(10);
        person.setName("翠花");
        taskService.setVariable(taskId, "人员信息", person);
        
        
        System.out.println("设置流程变量成功");
    }
    
    @Test
    public void getVariables() {
        /**与任务（正在执行）*/
        TaskService taskService = processEngine.getTaskService();
        //任务ID
        String taskId = "2004";
        /**获取流程变量，使用基本数据类型*/
//        Integer days = (Integer)taskService.getVariable(taskId, "请假天数");
//        Date date = (Date)taskService.getVariable(taskId, "请假时间");
//        String reason = (String)taskService.getVariable(taskId, "请假原因");
//        System.out.println("请假天数："+days+",请假日期:"+date+",请假原因:"+reason);
        /**获取流程变量，使用javabean类型*/
        Person person = (Person)taskService.getVariable(taskId, "人员信息");
        System.out.println(person.getId()+"   "+person.getName());
    }
    
    
    /**模拟设置和获取流程变量的场景*/
    @Test
    public void setAndGetVariables() {
        /**与流程实例，执行对象（正在执行）*/
        RuntimeService runtimeService = processEngine.getRuntimeService();
        
        /**与任务（正在执行）*/
        TaskService taskService = processEngine.getTaskService();
        /**设置流程变量*/
//        runtimeService.setVariable(executionId, variableName, value);//表示使用执行对象ID，和流程变量的名称，设置流程变量的值（一次只能设置一个值）
//        runtimeService.setVariableLocal(executionId, variableName, value);//表示使用执行对象ID,和Map集合设置流程变量，map集合的key就是流程变量的名称，map集合的value就是流程变量的值
        
//        taskService.setVariable(taskId, variableName, value);//表示使用任务ID，和流程变量的名称，设置流程变量的值（一次只能设置一个值）
//        taskService.setVariables(taskId, variables);//表示使用任务ID，和map集合设置流程变量，map集合的key就是流程变量的名称，map集合的value就是流程变量的值
        
//        runtimeService.startProcessInstanceByKey(processDefinitionKey, variables)//启动流程实例的同时，可以设置流程变量，用map集合
//        taskService.complete(taskId, variables);//完成任务的同时，设置流程变量，使用Map集合
        
        /**获取流程变量*/
//        runtimeService.getVariable(executionId, variableName);//使用执行对象ID和流程变量名称获取流程变量的值
//        runtimeService.getVariables(executionId);//使用执行对象ID获取所有的流程变量，将流程变量放到map集合中，map集合的key就是流程变量的名称，map集合的value就是流程变量的值
//        runtimeService.getVariables(executionId, variableNames);//使用执行对象ID获取流程变量的值，通过设置流程变量的名称存放到集合中，获取指定流程变量的值
        
//      taskService.getVariable(taskId, variableName);//使用任务ID和流程变量名称获取流程变量的值
//      taskService.getVariables(taskId);//使用任务ID获取所有的流程变量，将流程变量放到map集合中，map集合的key就是流程变量的名称，map集合的value就是流程变量的值
//      taskService.getVariables(taskId, variableNames);//使用任务ID获取流程变量的值，通过设置流程变量的名称存放到集合中，获取指定流程变量的值
        
    }
    
    /**完成我的任务**/
    @Test
    public void comMyPersonalTask() {
        String taskId = "2202";
        processEngine.getTaskService()
                    .complete(taskId);
        System.out.println("完成任务，任务ID:"+taskId);
    }
    
    /**查询流程变量历史表*/
    @Test
    public void findHistoryProcessVariables() {
          List<HistoricVariableInstance> list = processEngine.getHistoryService()
                          .createHistoricVariableInstanceQuery()
                          .variableName("请假天数")
                          .list();
        if(list != null && list.size() >0) {
            for (HistoricVariableInstance hvi : list) {
                System.out.println(hvi.getId()+"   "+hvi.getProcessInstanceId()+"   "+hvi.getVariableName()+"   "+hvi.getVariableTypeName()+"   "+hvi.getValue());
                System.out.println("###############################################");
            }
        }
        
    }
     
    
    
    
}
