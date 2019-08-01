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
    
    /**�������̶��壨inputStream��**/ 
    @Test
    public void deployProcessDefinition_inputStream(){
        InputStream inputStreamBpmn  = this.getClass().getResourceAsStream("start.bpmn");
        InputStream inputStreamPng  = this.getClass().getResourceAsStream("start.png  ");
        Deployment deployment = processEngine.getRepositoryService()//�����̶���Ͳ��������ص�service
                       .createDeployment()//����һ���������
                       .name("��ʼ�")//��ӵĲ�������
                       .addInputStream("start.bpmn", inputStreamBpmn)
                       .addInputStream("start.png", inputStreamPng)
                       .deploy();//��ɲ���
        System.out.println("����ID:"+deployment.getId());//
        System.out.println("�������ƣ�"+deployment.getName());//
    }
    
    /**��������ʵ��**/
    @Test
    public void startProcessInstance(){
        //���̶����key
        String processDefinitionKey = "start";
        ProcessInstance processInstance = processEngine.getRuntimeService()//������ִ�е�����ʵ�������̶�����ص�service
                     .startProcessInstanceByKey(processDefinitionKey);//ʹ�����̶����key��������ʵ����key����helloworld.bpmn�ļ��е�id������ֵ��ʹ��keyֵ������Ĭ���ǰ������°汾�����̶�������
        System.out.println("����ʵ��ID:"+processInstance.getId());//����ʵ��ID:101
        System.out.println("���̶���ID:"+processInstance.getProcessDefinitionId());//���̶���ID:helloworld:1:4
        
        /**�ж������Ƿ��������ѯ����ִ�е�ִ�ж����*/
         ProcessInstance singleResult = processEngine.getRuntimeService()
                        .createProcessInstanceQuery()
                        .processInstanceId(processInstance.getId())
                        .singleResult();
         //˵������ʵ��������
        if(singleResult ==null) {
            HistoricProcessInstance singleResult2 = processEngine.getHistoryService()
                            .createHistoricProcessInstanceQuery()
                            .processInstanceId(processInstance.getId())
                            .singleResult();
            System.out.println(singleResult2.getId()+"   "+singleResult2.getStartTime()+"   "+singleResult2.getEndTime()+"   "+singleResult2.getDurationInMillis());
        }
        
    }
    
}
