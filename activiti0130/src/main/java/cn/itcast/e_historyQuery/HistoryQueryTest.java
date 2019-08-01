package cn.itcast.e_historyQuery;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.junit.Test;

public class HistoryQueryTest {
    
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    
    /**查询历史流程实例*/
    @Test
    public void findHistoryProcessInstance() {
        String processInstanceId = "2001";
        HistoricProcessInstance instance = processEngine.getHistoryService()
                        .createHistoricProcessInstanceQuery()
                        .processInstanceId(processInstanceId)
                        .singleResult();
        System.out.println(instance.getId()+"  "+instance.getProcessDefinitionId()+"  "+instance.getStartTime()+"  "+instance.getEndTime()+"  "+instance.getDurationInMillis());
    }
    
    /**查询历史活动实例*/
    @Test
    public void findHistoryActiviti() {
        String processInstanceId = "2001";
        List<HistoricActivityInstance> list = processEngine.getHistoryService()
                        .createHistoricActivityInstanceQuery()
                        .processInstanceId(processInstanceId)
                        .orderByHistoricActivityInstanceStartTime().asc()
                        .list();
        if(list !=  null && list.size() >0) {
            for (HistoricActivityInstance hai : list) {
                System.out.println(hai.getId()+"   "+hai.getProcessInstanceId()+"   "+hai.getActivityType()+"   "+hai.getStartTime()+"   "+hai.getEndTime());
                System.out.println("############################");
            }
        }
    }
    
    /**查询历史任务*/
    @Test
    public void findHistoryTask() {
        String processInstanceId = "2001";
        List<HistoricTaskInstance> list = processEngine.getHistoryService()
                        .createHistoricTaskInstanceQuery()
                        .processInstanceId(processInstanceId)
                        .list();
        if(list != null && list.size() >0) {
            for (HistoricTaskInstance hit : list) {
                System.out.println(hit.getId()+" "+hit.getName()+" "+hit.getProcessInstanceId()+" "+hit.getStartTime()+" "+hit.getEndTime()+" "+hit.getDurationInMillis());
                System.out.println("###############################");
            }
        }
    }
    
    /***
     * 查询历史流程变量
     */
    @Test
    public void findHistoryProcessVariables() {
        String processInstanceId ="2001";
          List<HistoricVariableInstance> list = processEngine.getHistoryService()
                          .createHistoricVariableInstanceQuery()
                          .processInstanceId(processInstanceId)
                          .list();
        if(list != null && list.size() >0) {
            for (HistoricVariableInstance hvi : list) {
                System.out.println(hvi.getId()+"   "+hvi.getProcessInstanceId()+"   "+hvi.getVariableName()+"   "+hvi.getVariableTypeName()+"   "+hvi.getValue());
                System.out.println("###############################################");
            }
        }
    }
    
    
    
}
